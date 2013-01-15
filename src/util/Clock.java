package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Clock implements ActionListener{

	JLabel timeLabel = new JLabel();
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	Timer timer;
	int time = 0;
	boolean paused = false;
	String tempo = "";

	public String convertTime() {
		String Seconds = "", Minutes = "", Hours = "";
		int seconds = 0;
		int minutes = 0;
		int hours = 0;
		if (time > 60) {
			seconds = time % 60;
			minutes = time / 60;
			if (time > 3600)
				hours = time / 3600;
		} else {
			seconds = time;
		}
		if (seconds < 10)
			Seconds = "0" + Integer.toString(seconds);
		else
			Seconds = Integer.toString(seconds);
		if (minutes < 10)
			Minutes = "0" + Integer.toString(minutes);
		else
			Minutes = Integer.toString(minutes);
		if (hours < 10)
			Hours = "0" + Integer.toString(hours);
		else
			Hours = Integer.toString(hours);
		tempo = Hours + ":" + Minutes + ":" + Seconds;
		return tempo;
	}

	public Clock(int refresh) {
		timeLabel.setText(convertTime());
		timeLabel.setBounds(250, 550, 90, 15);
		timer = new Timer(refresh, this);
		timer.setRepeats(true);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		// If the timer caused this event.
		if (e.getSource().equals(timer)) {
			// Then set a new time.
			if (!paused) {
				++time;
				timeLabel.setText(convertTime());
			}
			else
				timeLabel.setText(tempo);
		}
	}
	
	public JLabel getLabel() {
		return timeLabel;
	}
	
	public void pause() {
		paused = true;
	}
	
	public void resume() {
		paused = false;
	}
}//EOF