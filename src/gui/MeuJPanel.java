package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

class MeuJPanel extends JPanel {

	// private String nome;
	private BufferedImage imagem;
	private JLabel barraDeEstado;
	private int estado;
	private int numeroDePontos;
	private int[][] pontos;
	private int[] angulos;
	private int posicao;

	// carrega a imagem
	public MeuJPanel(String filepath) {
		estado = 0;
		numeroDePontos = 0;
		posicao = 0;
		try {
			System.out.println(filepath);
			imagem = ImageIO.read(new File(filepath));
			barraDeEstado = new JLabel("");
			add(barraDeEstado, BorderLayout.SOUTH);
			addMouseListener(new OuvidorDeMouseClicado());
		} catch (Exception e) {
			System.out.println("ERRO: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	public MeuJPanel(BufferedImage imagem) {

		this.imagem = imagem;
		addMouseListener(new OuvidorDeMouseClicado());
		barraDeEstado = new JLabel("");
		estado = 0;
		numeroDePontos = 0;
		posicao = 0;
	}

	public BufferedImage getImagem() {

		return this.imagem;
	}

	public void setImagem(BufferedImage imagem) {

		this.imagem = imagem;
	}

	// exibe imageIcon no painel
	public void paintComponent(Graphics g) {
		// Center image in this component.
		int x = (getWidth() - imagem.getWidth()) / 2;
		int y = (getHeight() - imagem.getWidth()) / 2;
		g.drawImage(imagem, x, y, this);
	}

	// retorna as dimensões da imagem
	public Dimension getPreferredSize() {

		return new Dimension(imagem.getWidth(), imagem.getHeight());
	}

	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

	/**
	 * @return the numeroDePontos
	 */
	public int getNumeroDePontos() {
		return numeroDePontos;
	}

	/**
	 * @param numeroDePontos
	 *            the numeroDePontos to set
	 */
	public void setNumeroDePontos(int numeroDePontos) {
		System.out.println("Iniciei..., Numero De Pontos: " + numeroDePontos);
		this.numeroDePontos = numeroDePontos;
		this.pontos = new int[numeroDePontos][2];
		this.angulos = new int[numeroDePontos];
		this.posicao = 0;
		barraDeEstado.setText("0 pontos selecionados de " + numeroDePontos
				+ ".");
	}

	/**
	 * @return the pontos
	 */
	public int[][] getPontos() {
		return pontos;
	}

	/**
	 * @return the posicao
	 */
	public int getPosicao() {
		return posicao;
	}

	/**
	 * @param posicao
	 *            the posicao to set
	 */
	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	// Classe interna para tratar eventos do mouse
	private class OuvidorDeMouseClicado extends MouseAdapter {

		// trata evento de clique de mouse e determina qual botão foi
		// pressionado
		public void mouseClicked(MouseEvent event) {

		}
	}
}
