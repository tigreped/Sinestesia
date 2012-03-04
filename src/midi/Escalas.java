package midi;

import util.Base;

public class Escalas extends Base {
	
	private int[] naturalMaior = null;
	
	private int[] naturalMenor = null;
	
	private int[] pentatonicaMaior = null;
	
	private int[] menorHarmonica = null;

	public Escalas() {
		carregaNaturalMaior();
		carregaPentatonicaMaior();
		carregaMenorHarmonica();
	}

	public void carregaNaturalMaior() {

		naturalMaior = new int[35];

		int notaInicial = 0;

		int j = 0;

		for (int i = 0; i < 5; i++) {
			// tonica:
			naturalMaior[j++] = +notaInicial;
			notaInicial += 2;
			// segunda (maior):
			naturalMaior[j++] = +notaInicial;
			notaInicial += 2;
			// terça (maior):
			naturalMaior[j++] = +notaInicial;
			notaInicial += 1;
			// quarta (justa):
			naturalMaior[j++] = +notaInicial;
			notaInicial += 2;
			// quinta (maior):
			naturalMaior[j++] = +notaInicial;
			notaInicial += 2;
			// sexta (maior):
			naturalMaior[j++] = +notaInicial;
			notaInicial += 2;
			// sétima (maior):
			naturalMaior[j++] = +notaInicial;
			notaInicial += 2;
		}
	}
	
	public void carregaNaturalMenor() {

		naturalMenor = new int[35];

		int notaInicial = 0;

		int j = 0;

		for (int i = 0; i < 5; i++) {
			// tonica: 
			naturalMenor[j++] = notaInicial;
			notaInicial += 2;
			// segunda (maior):
			naturalMenor[j++] = notaInicial;
			notaInicial += 1;
			// terça (menor):
			naturalMenor[j++] = notaInicial;
			notaInicial += 2;
			//quarta (justa):
			naturalMenor[j++] = notaInicial;
			notaInicial += 2;
			// quinta (maior):
			naturalMenor[j++] = notaInicial;
			notaInicial += 1;
			// sexta (menor):
			naturalMenor[j++] = notaInicial;
			notaInicial += 2;
			// setima (maior aumentada):
			naturalMenor[j++] = notaInicial;
			notaInicial += 2;
		}
	}
	
	public void carregaPentatonicaMaior() {
		// 5 notas por oitava, em 6 oitavas = 30 notas
		pentatonicaMaior = new int[40];

		int notaInicial = 0;

		int j = 0;

		// Para cada oitava:
		for (int i = 0; i < 8; i++) {
			// tonica:
			pentatonicaMaior[j++] = notaInicial;
			notaInicial += 2;
			// segunda (maior):
			pentatonicaMaior[j++] = notaInicial;
			notaInicial += 2;
			// terça (maior):
			pentatonicaMaior[j++] = notaInicial;
			notaInicial += 3;
			// quinta (maior):
			pentatonicaMaior[j++] = notaInicial;
			notaInicial += 2;
			// sétima (maior):
			pentatonicaMaior[j++] = notaInicial;
			notaInicial += 3;
		}
	}

	public void carregaMenorHarmonica() {

		menorHarmonica = new int[35];

		int notaInicial = 0;

		int j = 0;

		for (int i = 0; i < 5; i++) {
			// tonica: 
			menorHarmonica[j++] = notaInicial;
			notaInicial += 2;
			// segunda (maior):
			menorHarmonica[j++] = notaInicial;
			notaInicial += 1;
			// terça (menor):
			menorHarmonica[j++] = notaInicial;
			notaInicial += 2;
			//quarta (justa):
			menorHarmonica[j++] = notaInicial;
			notaInicial += 2;
			// quinta (maior):
			menorHarmonica[j++] = notaInicial;
			notaInicial += 1;
			// sexta (menor):
			menorHarmonica[j++] = notaInicial;
			notaInicial += 3;
			// setima (maior aumentada):
			menorHarmonica[j++] = notaInicial;
			notaInicial += 1;
		}
	}
	
	public int getNotaAtonica(int nota, int tom) {
		return constantes.C2 + tom + (int) (Math.round(nota * 0.235294118));
	}
	
	public int getNotaNaturalMaior(int nota, int tom) {
		return constantes.C2 + tom + naturalMaior[(int) Math.round(nota * 0.1333333)];
	}

	public int getNotaNaturalMenor (int nota, int tom) {
		return constantes.C2 + tom + naturalMaior[(int) Math.round(nota * 0.1333333)];
	}

	public int getNotaPentatonicaMaior(int nota, int tom) {
		return constantes.C2 + tom + pentatonicaMaior[(int) Math.round(nota * 0.055)];
	}
	
	public int getNotaMenorHarmonica(int nota, int tom) {
		return constantes.C2 + tom + menorHarmonica[(int) Math.round(nota * 0.1333333)];
	}
}
