package main;

import gui.Visual;

import java.awt.EventQueue;

import javax.sound.midi.Sequence;

import midi.Escalas;
import midi.MidiManager;
import midi.Player;
import util.Base;
import util.Constantes;

/**
 * Classe main: Sinestesia. A classe deve chamar uma interface grafica, que por
 * sua vez chamara as operacoes necessarias.
 * 
 * @author tigreped
 */
public class Main {
	public static void main(String args[]) {
		// Inicializa o arquivo de log.
		Base.initializeLog();

		
		// Inicializa as constantes:
		Base.setConstantes(new Constantes(1600));
		// Inicializa as escalas:
		Base.setEscalas(new Escalas());

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Visual(960);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Base.closeLog();
	}
}
