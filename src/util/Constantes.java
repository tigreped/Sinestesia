package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class Constantes {

	/* Duração das notas: */

	// 1 compasso | 16 tempos:
	public int SEMIBREVE;

	// 1/2 compasso | 8 tempos:
	public int MINIMA;

	// 1/4 compasso | 4 tempos:
	public int SEMINIMA;

	// 1/8 compasso | 2 tempos:
	public int COLCHEIA;

	// 1/16 compasso | 1 tempo:
	public int SEMICOLCHEIA;

	// 1/32 compasso | 1/2 tempo:
	public int FUSA;

	// 1/64 compasso | 1/4 tempo:
	public int SEMIFUSA;
	
	// C2 no teclado MIDI:
	public int C2 = 36;

	public HashMap<Integer, String> mapeamentoTempos;

	public HashMap<Integer, Integer> mapeamentoDuracoes;

	public ArrayList<String> mapeamentoNotas;

	public HashMap<Integer, String> mapeamentoStatusBytes;

	public HashMap<Integer, Integer> mapeamentoCanalNoteOn = null;

	public HashMap<Integer, Integer> mapeamentoCanalNoteOff = null;

	public HashMap<Integer, Integer> mapeamentoCanalPrograma = null;

	public ArrayList<String> listaNomeInstrumentos;

	/**
	 * @param seminima
	 *            define o valor em ticks de uma seminima, relativo ao PPQ
	 *            (pulses per quarter)
	 */
	public Constantes(int seminima) {
		SEMIBREVE = seminima * 4;
		MINIMA = seminima * 2;
		SEMINIMA = seminima;
		COLCHEIA = seminima / 2;
		SEMICOLCHEIA = seminima / 4;
		FUSA = seminima / 8;
		SEMIFUSA = seminima / 16;
		inicializa();
	}

	private void inicializa() {
		// Inicializa as outras contantes:
		inicializaHashDuracoes();
		inicializaHashTempos();
		inicializaHashNotas();
		inicializaHashStatusBytes();
		inicializaInstrumentos();
		carregaMapeamentoCanalNoteOn();
		carregaMapeamentoCanalNoteOff();
		carregaMapeamentoCanalPrograma();
	}

	private void inicializaHashDuracoes() {
		mapeamentoDuracoes = new HashMap<Integer, Integer>();
		mapeamentoDuracoes.put(0, SEMIBREVE);
		mapeamentoDuracoes.put(1, MINIMA);
		mapeamentoDuracoes.put(2, SEMINIMA);
		mapeamentoDuracoes.put(3, COLCHEIA);
		mapeamentoDuracoes.put(4, SEMICOLCHEIA);
		mapeamentoDuracoes.put(5, FUSA);
		mapeamentoDuracoes.put(6, SEMIFUSA);
	}

	private void inicializaHashTempos() {
		mapeamentoTempos = new HashMap<Integer, String>();
		mapeamentoTempos.put(SEMIBREVE, "Semibreve");
		mapeamentoTempos.put(MINIMA, "Minima");
		mapeamentoTempos.put(SEMINIMA, "Seminima");
		mapeamentoTempos.put(COLCHEIA, "Colcheia");
		mapeamentoTempos.put(SEMICOLCHEIA, "Semicolcheia");
		mapeamentoTempos.put(FUSA, "Fusa");
		mapeamentoTempos.put(SEMIFUSA, "Semifusa");
	}

	private void inicializaHashNotas() {

		/** Notas do teclado MIDI: **/

		mapeamentoNotas = new ArrayList<String>();

		// Primeira oitava:
		mapeamentoNotas.add("C1"); // 0
		mapeamentoNotas.add("C#1");
		mapeamentoNotas.add("D1");
		mapeamentoNotas.add("D#1");
		mapeamentoNotas.add("E1");
		mapeamentoNotas.add("F1");
		mapeamentoNotas.add("F#1");
		mapeamentoNotas.add("G1");
		mapeamentoNotas.add("G#1");
		mapeamentoNotas.add("A1");
		mapeamentoNotas.add("A#1");
		mapeamentoNotas.add("B1");

		// Segunda oitava:
		mapeamentoNotas.add("C2"); // 12
		mapeamentoNotas.add("C#2");
		mapeamentoNotas.add("D2");
		mapeamentoNotas.add("D#2");
		mapeamentoNotas.add("E2");
		mapeamentoNotas.add("F2");
		mapeamentoNotas.add("F#2");
		mapeamentoNotas.add("G2");
		mapeamentoNotas.add("G#2");
		mapeamentoNotas.add("A2");
		mapeamentoNotas.add("A#2");
		mapeamentoNotas.add("B2");

		// Terceira oitava:
		mapeamentoNotas.add("C3"); // 24
		mapeamentoNotas.add("C#3");
		mapeamentoNotas.add("D3");
		mapeamentoNotas.add("D#3");
		mapeamentoNotas.add("E3");
		mapeamentoNotas.add("F3");
		mapeamentoNotas.add("F#3");
		mapeamentoNotas.add("G3");
		mapeamentoNotas.add("G#3");
		mapeamentoNotas.add("A3");
		mapeamentoNotas.add("A#3");
		mapeamentoNotas.add("B3");

		// Quarta oitava:
		mapeamentoNotas.add("C4"); // 36
		mapeamentoNotas.add("C#4");
		mapeamentoNotas.add("D4");
		mapeamentoNotas.add("D#4");
		mapeamentoNotas.add("E4");
		mapeamentoNotas.add("F4");
		mapeamentoNotas.add("F#4");
		mapeamentoNotas.add("G4");
		mapeamentoNotas.add("G#4");
		mapeamentoNotas.add("A4");
		mapeamentoNotas.add("A#4");
		mapeamentoNotas.add("B4");

		// Quinta oitava:
		mapeamentoNotas.add("C5"); // 48
		mapeamentoNotas.add("C#5");
		mapeamentoNotas.add("D5");
		mapeamentoNotas.add("D#5");
		mapeamentoNotas.add("E5");
		mapeamentoNotas.add("F5");
		mapeamentoNotas.add("F#5");
		mapeamentoNotas.add("G5");
		mapeamentoNotas.add("G#5");
		mapeamentoNotas.add("A5");
		mapeamentoNotas.add("A#5");
		mapeamentoNotas.add("B5");

		// Sexta oitava:
		mapeamentoNotas.add("C6"); // 60
		mapeamentoNotas.add("C#6");
		mapeamentoNotas.add("D6");
		mapeamentoNotas.add("D#6");
		mapeamentoNotas.add("E6");
		mapeamentoNotas.add("F6");
		mapeamentoNotas.add("F#6");
		mapeamentoNotas.add("G6");
		mapeamentoNotas.add("G#6");
		mapeamentoNotas.add("A6");
		mapeamentoNotas.add("A#6");
		mapeamentoNotas.add("B6");

		// S�tima oitava:
		mapeamentoNotas.add("C7"); // 72
		mapeamentoNotas.add("C#7");
		mapeamentoNotas.add("D7");
		mapeamentoNotas.add("D#7");
		mapeamentoNotas.add("E7");
		mapeamentoNotas.add("F7");
		mapeamentoNotas.add("F#7");
		mapeamentoNotas.add("G7");
		mapeamentoNotas.add("G#7");
		mapeamentoNotas.add("A7");
		mapeamentoNotas.add("A#7");
		mapeamentoNotas.add("B7");

		// Oitava oitava:
		mapeamentoNotas.add("C8"); // 84
		mapeamentoNotas.add("C#8");
		mapeamentoNotas.add("D8");
		mapeamentoNotas.add("D#8");
		mapeamentoNotas.add("E8");
		mapeamentoNotas.add("F8");
		mapeamentoNotas.add("F#8");
		mapeamentoNotas.add("G8");
		mapeamentoNotas.add("G#8");
		mapeamentoNotas.add("A8");
		mapeamentoNotas.add("A#8");
		mapeamentoNotas.add("B8");

		// Nona oitava:
		mapeamentoNotas.add("C9"); // 96
		mapeamentoNotas.add("C#9");
		mapeamentoNotas.add("D9");
		mapeamentoNotas.add("D#9");
		mapeamentoNotas.add("E9");
		mapeamentoNotas.add("F9");
		mapeamentoNotas.add("F#9");
		mapeamentoNotas.add("G9");
		mapeamentoNotas.add("G#9");
		mapeamentoNotas.add("A9");
		mapeamentoNotas.add("A#9");
		mapeamentoNotas.add("B9");

		// D�cima oitava:
		mapeamentoNotas.add("C10"); // 108
		mapeamentoNotas.add("C#10");
		mapeamentoNotas.add("D10");
		mapeamentoNotas.add("D#10");
		mapeamentoNotas.add("E10");
		mapeamentoNotas.add("F10");
		mapeamentoNotas.add("F#10");
		mapeamentoNotas.add("G10");
		mapeamentoNotas.add("G#10");
		mapeamentoNotas.add("A10");
		mapeamentoNotas.add("A#10");
		mapeamentoNotas.add("B10");

		// D�cima primeira oitava:
		mapeamentoNotas.add("C11"); // 120
		mapeamentoNotas.add("C#11");
		mapeamentoNotas.add("D11");
		mapeamentoNotas.add("D#11");
		mapeamentoNotas.add("E11");
		mapeamentoNotas.add("F11");
		mapeamentoNotas.add("F#11");
		mapeamentoNotas.add("G11");
	}

	public void inicializaHashStatusBytes() {

		mapeamentoStatusBytes = new HashMap<Integer, String>();

		mapeamentoStatusBytes.put(128, "Chanel 1 Note Off");
		mapeamentoStatusBytes.put(129, "Chanel 2 Note Off");
		mapeamentoStatusBytes.put(130, "Chanel 3 Note Off");
		mapeamentoStatusBytes.put(131, "Chanel 4 Note Off");
		mapeamentoStatusBytes.put(132, "Chanel 5 Note Off");
		mapeamentoStatusBytes.put(133, "Chanel 6 Note Off");
		mapeamentoStatusBytes.put(134, "Chanel 7 Note Off");
		mapeamentoStatusBytes.put(135, "Chanel 8 Note Off");
		mapeamentoStatusBytes.put(136, "Chanel 9 Note Off");
		mapeamentoStatusBytes.put(137, "Chanel 10 Note Off");
		mapeamentoStatusBytes.put(138, "Chanel 11 Note Off");
		mapeamentoStatusBytes.put(139, "Chanel 12 Note Off");
		mapeamentoStatusBytes.put(140, "Chanel 13 Note Off");
		mapeamentoStatusBytes.put(141, "Chanel 14 Note Off");
		mapeamentoStatusBytes.put(142, "Chanel 15 Note Off");
		mapeamentoStatusBytes.put(143, "Chanel 16 Note Off");

		mapeamentoStatusBytes.put(144, "Chanel 1 Note On");
		mapeamentoStatusBytes.put(145, "Chanel 2 Note On");
		mapeamentoStatusBytes.put(146, "Chanel 3 Note On");
		mapeamentoStatusBytes.put(147, "Chanel 4 Note On");
		mapeamentoStatusBytes.put(148, "Chanel 5 Note On");
		mapeamentoStatusBytes.put(149, "Chanel 6 Note On");
		mapeamentoStatusBytes.put(150, "Chanel 7 Note On");
		mapeamentoStatusBytes.put(151, "Chanel 8 Note On");
		mapeamentoStatusBytes.put(152, "Chanel 9 Note On");
		mapeamentoStatusBytes.put(153, "Chanel 10 Note On");
		mapeamentoStatusBytes.put(154, "Chanel 11 Note On");
		mapeamentoStatusBytes.put(155, "Chanel 12 Note On");
		mapeamentoStatusBytes.put(156, "Chanel 13 Note On");
		mapeamentoStatusBytes.put(157, "Chanel 14 Note On");
		mapeamentoStatusBytes.put(158, "Chanel 15 Note On");
		mapeamentoStatusBytes.put(159, "Chanel 16 Note On");

		mapeamentoStatusBytes.put(192, "Chanel 1 program change");
		mapeamentoStatusBytes.put(193, "Chanel 2 program change");
		mapeamentoStatusBytes.put(194, "Chanel 3 program change");
		mapeamentoStatusBytes.put(195, "Chanel 4 program change");
		mapeamentoStatusBytes.put(196, "Chanel 5 program change");
		mapeamentoStatusBytes.put(197, "Chanel 6 program change");
		mapeamentoStatusBytes.put(198, "Chanel 7 program change");
		mapeamentoStatusBytes.put(199, "Chanel 8 program change");
		mapeamentoStatusBytes.put(200, "Chanel 9 program change");
		mapeamentoStatusBytes.put(201, "Chanel 10 program change");
		mapeamentoStatusBytes.put(202, "Chanel 11 program change");
		mapeamentoStatusBytes.put(203, "Chanel 12 program change");
		mapeamentoStatusBytes.put(204, "Chanel 13 program change");
		mapeamentoStatusBytes.put(205, "Chanel 14 program change");
		mapeamentoStatusBytes.put(206, "Chanel 15 program change");
		mapeamentoStatusBytes.put(207, "Chanel 16 program change");

		mapeamentoStatusBytes.put(255, "System reset");
	}

	public void inicializaInstrumentos() {
		listaNomeInstrumentos = new ArrayList<String>();
		Instrument[] instrumentos = null;
		try {
			Synthesizer synth = null;
			synth = MidiSystem.getSynthesizer();
			synth.open();
			Soundbank soundbank = synth.getDefaultSoundbank();
			instrumentos = synth.getAvailableInstruments();
			// Encerra o sintetizador:
			synth.close();
		} catch (MidiUnavailableException m) {
			m.printStackTrace();
		}

		for (int i = 0; i < instrumentos.length; i++)
			listaNomeInstrumentos.add(instrumentos[i].getName());
	}
	
	public ArrayList<String> getListaNomeInstrumentos() {
		return listaNomeInstrumentos;
	}

	public void carregaMapeamentoCanalNoteOff() {
		mapeamentoCanalNoteOff = new HashMap<Integer, Integer>();
		mapeamentoCanalNoteOff.put(1, 128);
		mapeamentoCanalNoteOff.put(2, 129);
		mapeamentoCanalNoteOff.put(3, 130);
		mapeamentoCanalNoteOff.put(4, 131);
		mapeamentoCanalNoteOff.put(5, 132);
		mapeamentoCanalNoteOff.put(6, 133);
		mapeamentoCanalNoteOff.put(7, 134);
		mapeamentoCanalNoteOff.put(8, 135);
		mapeamentoCanalNoteOff.put(9, 136);
		mapeamentoCanalNoteOff.put(10, 137);
		mapeamentoCanalNoteOff.put(11, 138);
		mapeamentoCanalNoteOff.put(12, 139);
		mapeamentoCanalNoteOff.put(13, 140);
		mapeamentoCanalNoteOff.put(14, 141);
		mapeamentoCanalNoteOff.put(15, 142);
		mapeamentoCanalNoteOff.put(16, 143);
	}

	public void carregaMapeamentoCanalNoteOn() {
		mapeamentoCanalNoteOn = new HashMap<Integer, Integer>();
		mapeamentoCanalNoteOn.put(1, 144);
		mapeamentoCanalNoteOn.put(2, 145);
		mapeamentoCanalNoteOn.put(3, 146);
		mapeamentoCanalNoteOn.put(4, 147);
		mapeamentoCanalNoteOn.put(5, 148);
		mapeamentoCanalNoteOn.put(6, 149);
		mapeamentoCanalNoteOn.put(7, 150);
		mapeamentoCanalNoteOn.put(8, 151);
		mapeamentoCanalNoteOn.put(9, 152);
		mapeamentoCanalNoteOn.put(10, 153);
		mapeamentoCanalNoteOn.put(11, 154);
		mapeamentoCanalNoteOn.put(12, 155);
		mapeamentoCanalNoteOn.put(13, 156);
		mapeamentoCanalNoteOn.put(14, 157);
		mapeamentoCanalNoteOn.put(15, 158);
		mapeamentoCanalNoteOn.put(16, 159);
	}

	public void carregaMapeamentoCanalPrograma() {
		mapeamentoCanalPrograma = new HashMap<Integer, Integer>();
		mapeamentoCanalPrograma.put(1, 192);
		mapeamentoCanalPrograma.put(2, 193);
		mapeamentoCanalPrograma.put(3, 194);
		mapeamentoCanalPrograma.put(4, 195);
		mapeamentoCanalPrograma.put(5, 196);
		mapeamentoCanalPrograma.put(6, 197);
		mapeamentoCanalPrograma.put(7, 198);
		mapeamentoCanalPrograma.put(8, 199);
		mapeamentoCanalPrograma.put(9, 200);
		mapeamentoCanalPrograma.put(10, 201);
		mapeamentoCanalPrograma.put(11, 202);
		mapeamentoCanalPrograma.put(12, 203);
		mapeamentoCanalPrograma.put(13, 204);
		mapeamentoCanalPrograma.put(14, 205);
		mapeamentoCanalPrograma.put(15, 206);
		mapeamentoCanalPrograma.put(16, 207);
	}

	public Integer getDuracao(int duracao) {
		return mapeamentoDuracoes.get(duracao);
	}

	public void printTempos() {
		Collection<String> tempos = mapeamentoTempos.values();
		Collection<Integer> duracoes = mapeamentoDuracoes.values();

		Iterator t = tempos.iterator();
		Iterator d = duracoes.iterator();
		System.out.println("Tempos:");
		for (int i = 0; i < tempos.size(); i++)
			System.out.println(d.next() + " " + t.next());
	}

}
