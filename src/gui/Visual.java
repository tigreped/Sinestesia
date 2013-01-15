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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileFilter;

import map.Mapeamento;
import midi.MidiManager;
import midi.Player;
import util.Base;
import util.Clock;

public class Visual extends Base {

	// Variáveis globais:
	private Player player;
	private Sequence sequence = null;
	private int ppq;
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
	//private Clock clock;

	/**
	 * Create the application.
	 */
	public Visual(int ppq) {
		this.ppq = ppq;
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
		list_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollPane_1.setViewportView(list_1);

		JLabel lblNewLabel = new JLabel("List of instruments:");
		lblNewLabel.setBounds(24, 10, 211, 15);
		desktopPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Selected instruments:");
		lblNewLabel_1
				.setToolTipText("Each instrument corresponds to one music track.");
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

		JLabel lblCombinaorgb = new JLabel("[Note] [Intensity] [Duration]:");
		lblCombinaorgb.setBounds(346, 220, 227, 15);
		desktopPane.add(lblCombinaorgb);

		JLabel lblEscala = new JLabel("Escale:");
		lblEscala.setBounds(346, 276, 70, 15);
		desktopPane.add(lblEscala);

		// Escalas:
		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(346, 303, 230, 24);
		desktopPane.add(comboBox_1);
		comboBox_1.addItem("Chromatic");
		comboBox_1.addItem("Jonio (Natural major)");
		comboBox_1.addItem("Eolio (Natural minor)");
		comboBox_1.addItem("Minor Harmonic");
		comboBox_1.addItem("Major Pentatonic");

		JButton btnConverter = converter();
		btnConverter.setBounds(405, 415, 113, 25);
		desktopPane.add(btnConverter);

		JLabel lblNewLabel_2 = new JLabel("Key:");
		lblNewLabel_2.setBounds(346, 339, 70, 15);
		desktopPane.add(lblNewLabel_2);

		// Tom:
		comboBox_2 = new JComboBox();
		comboBox_2.addItem("C");
		comboBox_2.addItem("C# / Db");
		comboBox_2.addItem("D");
		comboBox_2.addItem("D# / Eb");
		comboBox_2.addItem("E / Fb");
		comboBox_2.addItem("F / E#");
		comboBox_2.addItem("F# / Gb");
		comboBox_2.addItem("G");
		comboBox_2.addItem("G# / Ab");
		comboBox_2.addItem("A");
		comboBox_2.addItem("A# / Bb");
		comboBox_2.addItem("B / Cb");
		comboBox_2.setBounds(346, 366, 230, 24);
		desktopPane.add(comboBox_2);

		JLabel lblPlayer = new JLabel("MIDI PLAYER:");
		lblPlayer.setBounds(254, 495, 90, 15);
		desktopPane.add(lblPlayer);

		btnPlay = new JButton("Play");
		// Play:
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Refresh every second
				//clock = new Clock(1000);
				// desktopPane.add(clock.getLabel());
				if (btnPlay.isEnabled()) {
					int bpm = getBpm();
					if (bpm != -1) {
						// Habilita o botão stop;
						btnStop.setEnabled(true);
						// Executa do início:
						if (stoped && !paused && !playing) {
							stoped = false;
							playing = true;
							try {
								player = new Player(sequence, bpm);
								player.run();
								//clock.resume();
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
								// Pega o novo valor em BPM:
								bpm = getBpm();
								if (bpm != -1) {
									player.setBpm(bpm);
									playing = true;
									paused = false;
									btnPlay.setText("Pause");
									//clock.resume();
									player.resume();
								}
								else {
									javax.swing.JOptionPane.showMessageDialog(frame,
											"Please, insert BPM values between 0 and 150!");
								}
							}
						}
						// Pause:
						else if (!stoped && playing && !paused) {
							if (player != null) {
								paused = true;
								playing = false;
								btnPlay.setText("Play");
								player.pause();
								//clock.pause();
							}
						}
					} //end if (bpm != -1)
					else
						javax.swing.JOptionPane.showMessageDialog(frame,
								"Please, insert BPM values between 0 and 150!");
				}
			}
		});

		btnPlay.setEnabled(false);
		btnPlay.setBounds(350, 530, 90, 25);
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
						btnStop.setEnabled(false);
					}
				}
			}
		});

		btnStop.setEnabled(false);
		btnStop.setBounds(450, 530, 90, 25);
		desktopPane.add(btnStop);

		JLabel lblImagem = new JLabel("Image:");
		lblImagem.setBounds(346, 10, 70, 15);
		desktopPane.add(lblImagem);

		JSeparator separator = new JSeparator();
		separator.setBounds(24, 475, 552, 8);
		desktopPane.add(separator);

		JLabel lblNmeroDePulsos = new JLabel("Beats per minute(BPM):");
		lblNmeroDePulsos.setBounds(50, 535, 242, 15);
		desktopPane.add(lblNmeroDePulsos);

		textField_1 = new JTextField();
		textField_1.setText("100");
		textField_1.setBounds(250, 530, 90, 25);
		desktopPane.add(textField_1);
		textField_1.setColumns(10);

		internalFrame.setVisible(true);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);

		JMenuItem mntmAbrirImagem = carregarImagem();
		mnMenu.add(mntmAbrirImagem);

		JMenuItem mntmExportarMidi = new JMenuItem("Export MIDI");
		mntmExportarMidi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (sequence != null) {				
					JFileChooser fc = new JFileChooser();
					fc.setApproveButtonText("Save");
					fc.setDialogTitle("Export MIDI");
					fc.setDialogType(JFileChooser.SAVE_DIALOG);
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fc.setAcceptAllFileFilterUsed(false);
					String filepath = javax.swing.JOptionPane
							.showInputDialog(
									"Enter filename of your desire",
									null);
					while (filepath.contains("/")) {
						filepath = javax.swing.JOptionPane
								.showInputDialog(
										"No folders allowed. The file will be exported to a /mid subfolder. Choose the filename of your desire:",
										null);					
					}
					try {
						MidiManager.salvar(sequence, filepath);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else
					javax.swing.JOptionPane
					.showMessageDialog(frame, "You must first set and convert an image before trying to export it.");
			}	
		});
		mnMenu.add(mntmExportarMidi);

		JMenuItem mntmSair = new JMenuItem("Quit");
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
		JButton btnAdicionar = new JButton("Add");
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
		JButton btnRemover = new JButton("Remove");
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
		JButton btnConverter = new JButton("Convert");
		btnConverter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage imagem;

				try {
					imagem = meuFrameInterno.getPanel().getImagem();
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null,
							"There is no selected image!", "",
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
									"Pick from 1 to 9 instruments to begin the conversion.",
									"", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Caso não tenham sido selecionados mais que 9 instrumentos:
				else if (instrumentosSelecionados.size() > 9) {
					JOptionPane
							.showMessageDialog(
									null,
									"Too many instruments. Pick from 1 to 9 instruments to begin the conversion.",
									"", JOptionPane.ERROR_MESSAGE);
					return;
				}

				else {
					// Chama os métodos de conversão:

					int mapeamentoRGB = comboBox.getSelectedIndex();
					int escala = comboBox_1.getSelectedIndex();
					int tom = comboBox_2.getSelectedIndex();
					// Reinicializa uma sequence com o dado tempo:
					sequence = MidiManager.inicializaSequence(ppq);
					Mapeamento.um(raster, sequence, instrumentosSelecionados,
							mapeamentoRGB, escala, tom);
					btnPlay.setEnabled(true);
				}
			}
		});
		return btnConverter;
	}

	private JMenuItem carregarImagem() {
		JMenuItem mntmAbrirImagem = new JMenuItem("Load image");
		mntmAbrirImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// exibe a nova janela interna
				JFileChooser fc = new JFileChooser();
				fc.setApproveButtonText("Open");
				fc.setDialogTitle("Open image");
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
									|| f.getName().toLowerCase()
											.endsWith(".bmp")
									|| f.getName().toLowerCase()
											.endsWith(".jpg");
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

	private int getBpm() {
		String txt = textField_1.getText();
		int bpm = 100;
		if (txt.equalsIgnoreCase("")) {
			bpm = -1; // erro
		} else {
			bpm = Integer.parseInt(txt);
			if (bpm < 0 || bpm > 150)
				bpm = -1; // erro
		}
		return bpm;
	}
}
