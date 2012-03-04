package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.util.ArrayList;

import javax.sound.midi.Sequence;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileFilter;

import map.Mapeamento;
import midi.MidiManager;
import midi.Player;
import util.Base;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class Visual extends Base {

	// Variáveis globais:
	private Player player;
	private Sequence sequence;
	private JFrame frame;
	private JDesktopPane desktopPane;
	private MeuFrameInterno meuFrameInterno;
	private JInternalFrame internalFrame;
	private JScrollPane scrollPane;
	private JList list, list_1;
	private ArrayList<Integer> instrumentosSelecionados;
	private JComboBox comboBox, comboBox_1, comboBox_2;
	private JButton btnPlay, btnStop;
	private boolean paused, playing, stoped;
	private JTextField textField_1;

	/**
	 * Create the application.
	 */
	public Visual(int ppq) {
		// Cria e inicializa uma sequence com um dado tempo:
		sequence = MidiManager.inicializaSequence(ppq);
		initialize();
		frame.setVisible(true);
		paused = false;
		playing = false;
		stoped = true;
	}

	/**
	 * Inicializa os conteúdos do frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 100, 602, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
		frame.setResizable(false);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 37, 230, 171);
		desktopPane.add(scrollPane);

		carregaListaDeInstrumentos();

		JButton btnAdicionar = adicionarInstrumentos();
		btnAdicionar.setBounds(23, 215, 100, 25);
		desktopPane.add(btnAdicionar);

		JButton btnRemover = removerInstrumentos();
		btnRemover.setBounds(154, 215, 100, 25);
		desktopPane.add(btnRemover);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(24, 273, 230, 171);
		desktopPane.add(scrollPane_1);

		list_1 = new JList();
		list_1.setBackground(Color.WHITE);
		list_1
				.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0,
						0)));
		scrollPane_1.setViewportView(list_1);

		JLabel lblNewLabel = new JLabel("Lista de instrumentos:");
		lblNewLabel.setBounds(24, 10, 211, 15);
		desktopPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Instrumentos selecionados:");
		lblNewLabel_1
				.setToolTipText("Cada instrumento corresponderá a uma faixa da músixa.");
		lblNewLabel_1.setBounds(24, 252, 211, 15);
		desktopPane.add(lblNewLabel_1);

		internalFrame = new JInternalFrame();
		internalFrame.setBounds(346, 39, 230, 171);
		desktopPane.add(internalFrame);

		// [R][G][B]:
		comboBox = new JComboBox();
		comboBox.setEditable(false);
		comboBox.addItem("[R] [G] [B]");
		comboBox.addItem("[R] [B] [G]");
		comboBox.addItem("[G] [R] [B]");
		comboBox.addItem("[G] [B] [R]");
		comboBox.addItem("[B] [G] [R]");
		comboBox.addItem("[B] [R] [G]");
		comboBox.setBounds(346, 245, 230, 24);
		desktopPane.add(comboBox);

		JLabel lblCombinaorgb = new JLabel("[Nota] [Intensidade] [Duração]:");
		lblCombinaorgb.setBounds(346, 220, 227, 15);
		desktopPane.add(lblCombinaorgb);

		JLabel lblEscala = new JLabel("Escala:");
		lblEscala.setBounds(346, 276, 70, 15);
		desktopPane.add(lblEscala);

		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(346, 303, 230, 24);
		desktopPane.add(comboBox_1);
		comboBox_1.addItem("Atônica");
		comboBox_1.addItem("Jônio (Natural maior)");
		// comboBox_1.addItem("Dórico");
		// comboBox_1.addItem("Frígio");
		// comboBox_1.addItem("Lídio");
		// comboBox_1.addItem("Mixolídio");
		comboBox_1.addItem("Eólio (Natural menor)");
		// comboBox_1.addItem("Lócrio");
		comboBox_1.addItem("Menor Harmônica");
		comboBox_1.addItem("Pentatônica Maior");

		JButton btnConverter = converter();
		btnConverter.setBounds(463, 431, 113, 25);
		desktopPane.add(btnConverter);

		JLabel lblNewLabel_2 = new JLabel("Tom:");
		lblNewLabel_2.setBounds(346, 339, 70, 15);
		desktopPane.add(lblNewLabel_2);

		// Tom:
		comboBox_2 = new JComboBox();
		comboBox_2.addItem("C (Dó)");
		comboBox_2.addItem("C# (Dó sustenido)");
		comboBox_2.addItem("D (Ré)");
		comboBox_2.addItem("D# (Ré sustenido)");
		comboBox_2.addItem("E (Mi)");
		comboBox_2.addItem("F (Fá)");
		comboBox_2.addItem("F# (Fá sustenido)");
		comboBox_2.addItem("G (Sol)");
		comboBox_2.addItem("G# (Sol sustenido)");
		comboBox_2.addItem("A (Lá)");
		comboBox_2.addItem("Bb (Si bemol)");
		comboBox_2.addItem("B (Si)");
		comboBox_2.setBounds(346, 366, 230, 24);
		desktopPane.add(comboBox_2);

		JLabel lblPlayer = new JLabel("MIDI PLAYER:");
		lblPlayer.setBounds(254, 495, 90, 15);
		desktopPane.add(lblPlayer);

		btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnPlay.isEnabled()) {
					// Executa do início:
					if (stoped && !paused && !playing) {
						stoped = false;
						playing = true;
						try {

							player = new Player(sequence);
							player.run();
							btnPlay.setText("Pause");

						} catch (NumberFormatException n) {
							JOptionPane
									.showMessageDialog(null,
											"O campo velocidade(bpm) deve conter números inteiros entre 1 e 250");
						}
					}
					// Resume:
					else if (!stoped && !playing && paused) {
						if (player != null) {
							playing = true;
							paused = false;
							btnPlay.setText("Pause");
							// Pega o novo valor em BPM:
							player.resume();
						}
					}
					// Pause:
					else if (!stoped && playing && !paused) {
						if (player != null) {
							paused = true;
							playing = false;
							btnPlay.setText("Play");
							player.pause();
						}
					}
				}
			}
		});

		btnPlay.setEnabled(false);
		btnPlay.setBounds(204, 544, 90, 25);
		desktopPane.add(btnPlay);

		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnStop.isEnabled()) {
					if (player != null) {
						stoped = true;
						paused = false;
						playing = false;
						btnPlay.setText("Play");
						player.stop();
					}	
				}
			}
		});

		btnStop.setEnabled(false);
		btnStop.setBounds(306, 544, 90, 25);
		desktopPane.add(btnStop);

		JLabel lblImagem = new JLabel("Imagem:");
		lblImagem.setBounds(346, 10, 70, 15);
		desktopPane.add(lblImagem);

		JSeparator separator = new JSeparator();
		separator.setBounds(24, 475, 552, 8);
		desktopPane.add(separator);
		
		JLabel lblNmeroDePulsos = new JLabel("Número de pulsos por semínima:");
		lblNmeroDePulsos.setBounds(346, 402, 242, 15);
		desktopPane.add(lblNmeroDePulsos);
		
		textField_1 = new JTextField();
		textField_1.setText("1600");
		textField_1.setBounds(346, 431, 100, 25);
		desktopPane.add(textField_1);
		textField_1.setColumns(10);

		internalFrame.setVisible(true);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);

		JMenuItem mntmAbrirImagem = carregarImagem();
		mnMenu.add(mntmAbrirImagem);

		JMenuItem mntmExportarMidi = new JMenuItem("Exportar MIDI");
		mntmExportarMidi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser fc = new JFileChooser();
				fc.setApproveButtonText("Salvar");
				fc.setDialogTitle("Exportar MIDI");
				fc.setDialogType(JFileChooser.SAVE_DIALOG);
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);
				String filepath = javax.swing.JOptionPane.showInputDialog("Escolha o caminho e o nome do arquivo que deseja salvar", null);				
				try {
					MidiManager.salvar(sequence, filepath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mnMenu.add(mntmExportarMidi);

		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnMenu.add(mntmSair);
	}

	private void carregaListaDeInstrumentos() {
		list = new JList();
		list.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		list.setModel(new javax.swing.DefaultComboBoxModel(constantes
				.getListaNomeInstrumentos().toArray()));
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(list);
	}

	private JButton adicionarInstrumentos() {
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				list_1.setSelectionInterval(0, list_1.getLastVisibleIndex());
				ArrayList<Object> listaDeItens = new ArrayList<Object>();
				// Adiciona à lista os itens já selecionados:
				for (Object o : list_1.getSelectedValues())
					listaDeItens.add(o);
				// Adiciona à lista os novos itens:
				for (Object o : list.getSelectedValues())
					listaDeItens.add(o);
				list_1.setModel(new javax.swing.DefaultComboBoxModel(
						listaDeItens.toArray()));
			}
		});
		return btnAdicionar;
	}

	private JButton removerInstrumentos() {
		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Separa os itens selecionados para remoção:
				ArrayList<Object> itensSelecionados = new ArrayList<Object>();
				for (Object o : list_1.getSelectedValues())
					itensSelecionados.add(o);
				// Seleciona todos os itens:
				list_1.setSelectionInterval(0, list_1.getLastVisibleIndex());
				ArrayList<Object> lista = new ArrayList<Object>();
				for (Object o : list_1.getSelectedValues())
					lista.add(o);
				// Remove os itens selecionados da lista de objetos:
				for (Object o : itensSelecionados)
					lista.remove(o);
				list_1.setModel(new javax.swing.DefaultComboBoxModel(lista
						.toArray()));
			}
		});
		return btnRemover;
	}

	private JButton converter() {
		JButton btnConverter = new JButton("Converter");
		btnConverter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage imagem;

				try {
					imagem = meuFrameInterno.getPanel().getImagem();
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null,
							"Não há nenhuma imagem selecionada!", "",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				Raster raster = imagem.getRaster();

				instrumentosSelecionados();

				// Caso não tenha sido selecionado nenhum instrumento:
				if (instrumentosSelecionados.size() < 1) {
					JOptionPane
							.showMessageDialog(
									null,
									"Escolha entre 1 a 9 instrumentos para realizar o mapeamento.",
									"", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Caso não tenham sido selecionados mais que 9 instrumentos:
				else if (instrumentosSelecionados.size() > 9) {
					JOptionPane
							.showMessageDialog(
									null,
									"Instrumentos demais. Escolha até no máximo 9 instrumentos para realizar o mapeamento.",
									"", JOptionPane.ERROR_MESSAGE);
					return;
				}

				else {
					// Chama os métodos de conversão:

					int mapeamentoRGB = comboBox.getSelectedIndex();
					int escala = comboBox_1.getSelectedIndex();
					int tom = comboBox_2.getSelectedIndex();
					Mapeamento.zero(raster, sequence, instrumentosSelecionados,
							mapeamentoRGB, escala, tom);
					btnPlay.setEnabled(true);
					btnStop.setEnabled(true);
				}
			}
		});
		return btnConverter;
	}

	private JMenuItem carregarImagem() {
		JMenuItem mntmAbrirImagem = new JMenuItem("Carregar imagem");
		mntmAbrirImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// exibe a nova janela interna
				JFileChooser fc = new JFileChooser();
				fc.setApproveButtonText("Abrir");
				fc.setDialogTitle("Abrir imagem");
				fc.setDialogType(JFileChooser.OPEN_DIALOG);
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);
				fc.setCurrentDirectory(new File("."));
				fc.setFileFilter(new FileFilter() {
					public boolean accept(File f) {
						if (!f.isFile())
							return true;
						else
							return f.getName().toLowerCase().endsWith(".png")
									|| f.getName().toLowerCase().endsWith(
											".bmp")
									|| f.getName().toLowerCase().endsWith(
											".jpg");
					}

					public String getDescription() {
						return "Imagens [PNG, BMP, JPG]";
					}
				});
				fc.setMultiSelectionEnabled(false);
				if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				String nome = fc.getSelectedFile().getAbsolutePath();

				meuFrameInterno = new MeuFrameInterno(nome);
				// Anexa o frame interno:
				internalFrame.getContentPane().add(meuFrameInterno);
				meuFrameInterno.setVisible(true); // mostra o frame interno
				meuFrameInterno.pack();
			} // fim do método actionPerformed
		} // fim da classe interna anonima
				);// fim da chama para addActionListener
		return mntmAbrirImagem;
	}

	private void instrumentosSelecionados() {
		// Seleciona todos os itens da lista:
		list_1.setSelectionInterval(0, list_1.getLastVisibleIndex());
		// Joga os itens selecionados num array:
		Object[] itensSelecionados = list_1.getSelectedValues();
		instrumentosSelecionados = new ArrayList<Integer>();

		for (int i = 0; i < itensSelecionados.length; i++)
			instrumentosSelecionados.add(constantes.getListaNomeInstrumentos()
					.indexOf((String) itensSelecionados[i]));
	}
}
