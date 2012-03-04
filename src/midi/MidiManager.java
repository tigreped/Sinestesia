package midi;

import java.io.File;
import java.io.IOException;
import java.util.Random;

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


/**
 * Gerencia operações sobre o MIDI.
 * @author pedro
 *
 */
public class MidiManager extends Base {

		/**
		 * Metodo para a insercao de uma nota a uma track.
		 * 
		 * @param track
		 * @param nota
		 */
		public static void addNote(Track track, Nota nota) {
			// statusByte para NoteOn do canal:
			int noteOn = constantes.mapeamentoCanalNoteOn.get(nota.getCanal());
			int noteOff = constantes.mapeamentoCanalNoteOff.get(nota.getCanal());
			track.add(createNoteEvent(noteOn, nota));
			track.add(createNoteEvent(noteOff, nota));
		}

		private static MidiEvent createNoteEvent(int comando, Nota nota) {

			ShortMessage message = new ShortMessage();

			// Evento de Note On:
			if (constantes.mapeamentoCanalNoteOn.containsKey(comando)) {
				try {
					// Configura a tecla e a itnensidade da nota:
					message.setMessage(comando, nota.getTecla(), nota
							.getIntensidade());

				} catch (InvalidMidiDataException e) {
					e.printStackTrace();
				}
				return new MidiEvent(message, nota.getInicio());
			}
			// Evento de Note Off:
			else {
				try {
					// Configura a tecla e a itnensidade da nota:
					message.setMessage(comando, nota.getTecla(), 0);
				} catch (InvalidMidiDataException e) {
					e.printStackTrace();
				}
				return new MidiEvent(message, nota.getInicio() + nota.getDuracao());
			}
		}

		/**
		 * Encerra a sequence
		 * 
		 * @param tick
		 * @return
		 */
		public static MidiEvent stopSequence(long tick) {
			ShortMessage message = new ShortMessage();
			MidiEvent event = null;
			try {
				// Encerra a sequence - 252
				message.setMessage(252, 0, 0);
			} catch (InvalidMidiDataException e) {
				out("Erro no método stopSequence");
				e.printStackTrace();
				System.exit(1);
			}
			event = new MidiEvent(message, tick);

			return event;
		}

		/**
		 * Metodo utilizado para alterar o instrumento da track passada como
		 * argumento. A informacao eh configurada na faixa atraves do envio de uma
		 * ShortMessage para a track, indicando a escolha do novo instrumento.
		 * 
		 * @param track
		 * @param nota
		 * @param instrument
		 */
		public static void changeProgram(Track track, int canal, int instrumento) {
			ShortMessage message = new ShortMessage();
			try {
				int statusByte = constantes.mapeamentoCanalPrograma.get(canal);
				message.setMessage(statusByte, instrumento, 0);
			} catch (InvalidMidiDataException i) {
				out("Player.changeProgram():");
				i.printStackTrace();
			}

			// O segundo parâmetro, com o valor 0, indica que essa mensagem
			// feve ser aplicada no tick 0, ou seja, desde o início da track.
			MidiEvent event = new MidiEvent(message, 0);

			track.add(event);
		}

		/**
		 * Altera o banco da faixa:
		 * 
		 * @param track
		 * @param bank
		 */
		public static void changeBank(Track track, int bank) {
			ShortMessage message = new ShortMessage();
			try {
				message.setMessage(176, 0, bank);
			} catch (InvalidMidiDataException i) {
				out("Player.changeBank(): " + i);
			}

			MidiEvent event = new MidiEvent(message, 0);

			track.add(event);
		}

		/**
		 * Gerar musica aleatoria. Utilizado para testes.
		 * 
		 * @throws InvalidMidiDataException
		 */
		public static void geraRandomMusic(Sequence sequence, int nCanais)
				throws InvalidMidiDataException {
			Escalas escalas = new Escalas();

			Random r = new Random();
			ShortMessage sm = null;
			int tecla = 0;
			int intensidade = 120;
			int duracao = 0;

			Track track = sequence.createTrack();

			int noteOn = 144; // Canal 1
			int noteOff = 128; // Canal 1

			for (int a = 1; a <= nCanais; a++) {

				// Configura o canal com algum instrumento:
				sm = new ShortMessage();
				int statusByte = constantes.mapeamentoCanalPrograma.get(a);
				int instrumento = 0;
				sm.setMessage(statusByte, instrumento, 0);
				track.add(new MidiEvent(sm, 0));

				long inicio = 0;

				// Adiciona as notas:
				for (int i = 0; i < 100; i++) {
					tecla = escalas.getNotaPentatonicaMaior(r.nextInt(29), 0);
					duracao = 480 * r.nextInt(5);

					// NoteON:
					sm = new ShortMessage();
					sm.setMessage(noteOn, tecla, intensidade);
					track.add(new MidiEvent(sm, inicio));

					inicio += duracao;

					// NoteOFF:
					sm = new ShortMessage();
					sm.setMessage(noteOff, tecla, 0);
					track.add(new MidiEvent(sm, inicio));
				}
				noteOn++;
				noteOff++;
			}
		}

		/**
		 * Salva a Sequence para um arquivo .mid(SMF). O valor '0' para o segundo
		 * parametro significa salvar o SMF tipo 0(apenas uma track). Caso haja mais
		 * de uma track, deve ser usado o valor '1' (multi-tracks).
		 * 
		 * @param sequence
		 * @throws Exception
		 */
		public static void salvar(Sequence sequence, String filename)
				throws Exception {
			File outputFile = new File("mid/" + filename + ".mid");
			try {
				int nTracks = sequence.getTracks().length;
				if (nTracks == 1)
					MidiSystem.write(sequence, 0, outputFile);
				else if (nTracks > 1)
					MidiSystem.write(sequence, 1, outputFile);
				else
					throw new Exception(
							"Erro. Impossivel salvar. Numero de tracks na sequencia invalido: "
									+ nTracks);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		public static Sequence inicializaSequence(int tempo) {
			Sequence sequence = null;
			try {
				sequence = new Sequence(Sequence.PPQ, tempo);

			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
				System.exit(1);
			}
			return sequence;
		}
}
