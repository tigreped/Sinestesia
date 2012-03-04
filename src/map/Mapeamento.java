package map;

import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import midi.Escalas;
import midi.MidiManager;
import midi.Nota;
import midi.Player;
import util.Base;
import util.Fachada;

/*
 *
 * 03/01/2011 - ? - Ainda tem que resolver uma serie de problemas. Primeiro, a normalizacao nao pode
 * gerar valores como 0 para duracao da nota, por exemplo. Como lidar com pausas? Ou as notas serao
 * contiguas? 
 * 
 * 04/01/2011 - Se o arquivo for muito grande, talvez seja melhor permitir duas abordagens -
 * 1o: selecionar uma regiao da imagem que sera mapeada. 2o: minimizar a imagem.OK 
 * 
 * 18/01/2011 - OK - Realizada troca de canais para tracks com sucesso. Melhor resultado. Implementado o algoritmo
 * secondo(?) com sucesso. 
 * 
 * 10/02/2011 - OK - Prestar atencao quando o volume da faixa estiver baixo, pode aparentar erro, quando nao é.
 * 
 * 10/02/2011 - OK - Corrigir a questao dos valores RGB alterados pelo metodo getRGBValue(); 
 * 
 * 14/02/2011 - BUG - No mapeamento Tetha todas as faixas estao assumindo o valor do ultimo instrumento.
 * 
 * 09/08/2011 - OK - Remoção dos offset, pois acredito que podiam estar resultando em valores maiores que 127 para as notas, 
 * o que podia acarretar em estouros.
 * 
 * 10/08/2011 - BUG - Conferir o motivo das durações em ticks estar diferindo tanto da duração estabelecida nas constantes
 * à partir da duração inicial da semibreve!
 * 
 * 10/08/2011 - OK - Retornar a implementação de canais por faixa, ao invés de editar uma track por faixa. 
 * Assim a seleção de instrumentos e a concorrência funcionam corretamente, utilizando statusBytes;
 * 
 * TODO list:
 * 
 * Sugestao: Implementar os metodos de girar imagem para gerar mais resultados a partir de uma imagem.
 * 
 * Revisar os mapeamentos implementados e padronizar coisas comuns a todos, como a varredura de pixels.
 * 
 * Mapeamento theta funciona, mas muito sensivel a mudança de fator, BPM, e da resolução da imagem.
 * 
 * 23/09/2011 - OK - Corrigido um problema na seleção dos instrumentos, agora faixas executam corretamente. 
 * Algoritmo zero() executa apenas imagens pequenas, reduzidas, entre 50 e 100 px de largura e altura.
 * 
 * @author Pedro Guimaraes
 */

public class Mapeamento extends Base {

	public static void zero(Raster raster, Sequence sequence,
			ArrayList<Integer> instrumentos, int combina, int escala, int tom) {

		// Largura da imagem:
		int numeroDeColunas = raster.getWidth();

		// Altura da imagem:
		int numeroDeLinhas = raster.getHeight();

		// Quantidade de faixas simultaneas desejadas pelo usuário:
		int numeroDeFaixas = instrumentos.size();

		// Variavel temporaria que recebe a informacao do pixel:
		int[] rgb = new int[3];

		ArrayList<Nota> notas = new ArrayList<Nota>();

		// Laço que varre cada pixel da imagem e garante que nao tente varrer
		// fora da area da imagem, mesmo que para isso seja necessario ignorar
		// alguns valores:
		for (int i = 0; i < numeroDeLinhas; i++) {
			for (int j = 0; j < numeroDeColunas; j++) {
				Nota nota = new Nota();
				raster.getPixel(j, i, rgb);
				getRGBValues(combina, nota, rgb);

				int tecla = nota.getTecla();
				long duracao = nota.getDuracao();
				int intensidade = nota.getIntensidade();

				// Normaliza os valores da tecla buscando a nota de uma escala
				// especifica:
				switch(escala) {
					case 0:
						nota.setTecla(escalas.getNotaAtonica(tecla, tom));
						break;
					case 1:
						nota.setTecla(escalas.getNotaNaturalMaior(tecla, tom));
						break;
					case 2:
						nota.setTecla(escalas.getNotaNaturalMenor(tecla, tom));
						break;
					case 3:
						nota.setTecla(escalas.getNotaMenorHarmonica(tecla, tom));
						break;
					case 4:
						nota.setTecla(escalas.getNotaPentatonicaMaior(tecla, tom));
						break;
				}		

				// Normaliza os valores da duracao buscando nas constantes:
				nota.setDuracao(constantes
						.getDuracao(normalizaDuracao(duracao)));

				// Normaliza a intensidade:
				nota.setIntensidade(normalizaIntensidade(intensidade));

				// Adiciona nota a lista de notas mapeadas.
				notas.add(nota);
			}
		}

		int numeroDeNotas = notas.size();

		int notasPorFaixa = Math.round(numeroDeNotas / numeroDeFaixas);

		int contadorNotas = 0;

		Track track = sequence.createTrack();

		// Para cada faixa:
		for (int canal = 1; canal <= numeroDeFaixas; canal++) {

			long inicio = 0;

			// Configura o instrumento da faixa(default == 0/piano):
			int instrumento = instrumentos.get(canal - 1);

			/**
			 * TODO Não é um player que é responsável por isso, é um
			 * MidiManager. mandar a track para o MidiManager logo no início,
			 * para evitar ter que ficar passando como parâmetro.
			 */
			MidiManager.changeProgram(track, canal, instrumento);

			// Configura as notas desta faixa:
			for (int n = 0; n < notasPorFaixa; n++) {

				Nota nota = notas.get(contadorNotas++);

				nota.setCanal(canal);

				// Configura o início da nota para a duração da anterior.
				nota.setInicio(inicio);

				// NoteON:
				ShortMessage sm = new ShortMessage();
				try {
					sm.setMessage(constantes.mapeamentoCanalNoteOn.get(nota
							.getCanal()), nota.getTecla(), nota
							.getIntensidade());
				} catch (InvalidMidiDataException i) {
					i.printStackTrace();
				}
				track.add(new MidiEvent(sm, inicio));

				inicio += nota.getDuracao();

				// NoteOFF:
				sm = new ShortMessage();
				try {
					sm.setMessage(constantes.mapeamentoCanalNoteOff.get(nota
							.getCanal()), nota.getTecla(), nota
							.getIntensidade());
				} catch (InvalidMidiDataException e) {
					e.printStackTrace();
				}
				track.add(new MidiEvent(sm, inicio));
			}
		}
	}

	/*
	 * Metodos de normalizacao fazem a conversso proporcional dos valores RGB
	 * (0~255) para os valores de intensidade, duracao e nota, levando-se em
	 * consideração os offsets. Por exemplo, para as notas, a conversão deve
	 * considerar um offset de 40, evitando notas muito graves, portanto a
	 * normalização deve gerar valores entre 0 e 60(+40), totalizando uma gama
	 * de notas possíveis entre 40 e 100.
	 */
	private static int normalizaIntensidade(int intensidade) {
		return (int) Math.round(intensidade * 0.498039216);
	}

	private static int normalizaDuracao(long duracao) {
		return (int) Math.round(duracao * 0.0235);
	}

	/**
	 * Metodo que define qual sera a ordem na qual serao pegos os valores de R G
	 * B por pixel e mapeados para intensidade, duracao e nota.
	 * 
	 * @param combina
	 * @param tecla
	 * @param duracao
	 * @param intensidade
	 * @param rgb
	 */
	public static void getRGBValues(int combina, Nota nota, int rgb[]) {
		switch (combina) {
		case 0:
			nota.setTecla(nota.getTecla() + rgb[0]);
			nota.setDuracao(nota.getDuracao() + rgb[1]);
			nota.setIntensidade(nota.getIntensidade() + rgb[2]);
			break;
		case 1:
			nota.setTecla(nota.getTecla() + rgb[0]);
			nota.setDuracao(nota.getDuracao() + rgb[2]);
			nota.setIntensidade(nota.getIntensidade() + rgb[1]);
			break;
		case 2:
			nota.setTecla(nota.getTecla() + rgb[1]);
			nota.setDuracao(nota.getDuracao() + rgb[0]);
			nota.setIntensidade(nota.getIntensidade() + rgb[2]);
			break;
		case 3:
			nota.setTecla(nota.getTecla() + rgb[1]);
			nota.setDuracao(nota.getDuracao() + rgb[2]);
			nota.setIntensidade(nota.getIntensidade() + rgb[0]);
			break;
		case 4:
			nota.setTecla(nota.getTecla() + rgb[2]);
			nota.setDuracao(nota.getDuracao() + rgb[1]);
			nota.setIntensidade(nota.getIntensidade() + rgb[0]);
			break;
		case 5:
			nota.setTecla(nota.getTecla() + rgb[2]);
			nota.setDuracao(nota.getDuracao() + rgb[0]);
			nota.setIntensidade(nota.getIntensidade() + rgb[1]);
			break;
		}
	}
}
//EOF