package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LoadAndShow extends JPanel {
	BufferedImage image;
	Dimension size = new Dimension();

	public LoadAndShow(BufferedImage image) {
		this.image = image;
		size.setSize(image.getWidth(), image.getHeight());
	}

	/**
	 * Drawing an image can allow for more flexibility in processing/editing.
	 */
	protected void paintComponent(Graphics g) {
		// Center image in this component.
		int x = (getWidth() - size.width) / 2;
		int y = (getHeight() - size.height) / 2;
		g.drawImage(image, x, y, this);
	}

	public Dimension getPreferredSize() {
		return size;
	}

	public static void main(String[] args) throws IOException {
		String path = "/home/pedro/workspace/Sinestesia/images/kalima-sm.jpg";
		BufferedImage image = ImageIO.read(new File(path));
		Dimension size = new Dimension(image.getWidth(), image.getHeight());
		LoadAndShow test = new LoadAndShow(image);
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new JScrollPane(test));
		f.setSize(400, 400);
		f.setLocation(200, 200);
		f.setVisible(true);
		// showIcon(image);
	}

	/**
	 * Easy way to show an image: load it into a JLabel and add the label to a
	 * container in your gui.
	 */
	private static void showIcon(BufferedImage image) {
		ImageIcon icon = new ImageIcon(image);
		JLabel label = new JLabel(icon, JLabel.CENTER);
		JOptionPane.showMessageDialog(null, label, "icon", -1);
	}
}