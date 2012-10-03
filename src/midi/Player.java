package midi;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import util.Base;

public class Player extends Base implements Runnable, ControllerEventListener{

	private Sequence sequence;

	private Sequencer sequencer;
	
	private int bpm;

	private boolean paused = false;

	public Player(Sequence s, int bpm) {
		sequence = s;
		sequencer = null;
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			int[] controller = new int[1];
			controller[0] = 252;
			sequencer.addControllerEventListener(this, controller);
			setBpm(bpm);
		} catch (MidiUnavailableException e) {
			System.out.println("Sequencer não inicializado.");
			e.printStackTrace();
		}
	}

	public void run() {
		play();
		//System.out.println(Thread.currentThread().getName());
	}

	@Override
	public void controlChange(ShortMessage arg0) {
		// Waits for the sequencer to reach the end of the song:
		if (arg0.getData1() == ShortMessage.STOP) {
			stop();
			System.out.println("Acabou a execução!");
		}	
	}
	
	/**
	 * Executa a sequence e encerra o sequencer apos a execucao.
	 * 
	 * @param sequencer
	 * @param sequence
	 */
	public void play() {
		if (sequencer != null) {
			// Executa a musica da sequence:
			try {
				sequencer.setSequence(sequence);
				sequencer.setTempoInBPM(bpm);
				// Imprime informações sobre a sequence:
				out("Tipo de divisão:" + sequence.getDivisionType());
				out("Duração:" + sequence.getTickLength() + " ticks");
				out("Duração:" + (sequence.getMicrosecondLength() / 1000000)
						/ 60 + "m"
						+ (sequence.getMicrosecondLength() / 1000000) % 60
						+ "s");

				// Info on the patch
				if (sequence.getPatchList() != null)
					for (Patch p : sequence.getPatchList()) {
						out("Patch:");
						out("Banco: " + p.getBank());
						out("Programa: " + p.getProgram());
					}
				else
					out("Sem patches.");

				out("Tempo factor:" + sequencer.getTempoFactor());

				out("Tempo em BPM:" + sequencer.getTempoInBPM());

				// Realiza a analise das mensagens MIDI das tracks em execucao:
				for (Track t : sequence.getTracks()) {
					Interpretador.main(t);
				}
				sequencer.start();
					
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Para a execução da MID e volta para o início do arquivo.
	 */
	public void stop() {
		if (sequencer != null && sequencer.isOpen()) {
			sequencer.stop();
			sequencer.setMicrosecondPosition(0);
		}
	}

	/**
	 * Pausa a execução da MID.
	 */
	public void pause() {
		if (sequencer != null && sequencer.isOpen() && !paused) {
			paused = true;
			sequencer.stop();
		}
	}

	/**
	 * Reinicia a execução da MID de onde parou.
	 */
	public void resume() {
		if (sequencer != null && sequencer.isOpen() && paused) {
			paused = false;
			out("Tempo factor:" + sequencer.getTempoFactor());
			out("Tempo in BPM:" + sequencer.getTempoInBPM());
			sequencer.start();
		}
	}

	public void mute(int canal) {
		Track tracks[] = sequencer.getSequence().getTracks();
		// 176: Chan 1 Control/Mode Change
		// 7: channel volume
		ShortMessage message = new ShortMessage();
		try {
			message.setMessage(176+canal, 7, 0);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		tracks[0].add(new MidiEvent(message, sequencer.getMicrosecondPosition()+100));
	}
	
	/**
	 * Encerra o sequencer.
	 */
	public void close() {
		if (sequencer != null && sequencer.isOpen()) {
			sequencer.close();
		}
	}
	
	/**
	 * Sets BPM for the song execution.
	 * @param bpm
	 */
	public void setBpm(int bpm) {
		this.bpm = bpm;
		sequencer.setTempoInBPM(bpm);
	}
}
