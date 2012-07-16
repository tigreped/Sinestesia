package main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import util.Base;
import util.Constantes;

import midi.Player;

public class PlayerTest {

	public static void main(String args[]) {

		Sequence sequence = null;
		Base.initializeLog();
		Base.setConstantes(new Constantes(1600));

		try {
			InputStream is = new FileInputStream("mid/angelina low.mid");
			if (!is.markSupported()) {
				is = new BufferedInputStream(is);
			}
			try {
				sequence = MidiSystem.getSequence(is);
			} catch (InvalidMidiDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			is.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
/*		Player player = new Player(sequence);
		player.run(); */
	}
}
