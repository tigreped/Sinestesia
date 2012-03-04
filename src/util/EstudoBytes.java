package util;

class Teste {

	/**
	 * Um byte corresponde a 8 bits(00000000 a 11111111). Se java trabalha com
	 * inteiros SIGNED, então o tipo byte deve aceitar valores entre -128 a 127,
	 * com o byte mais a esquerda determinando o sinal. Nesse caso, a
	 * representação UNSIGNED (positiva) em inteiros, deve ser de 0 a 255.
	 * 
	 * Ou seja, ao atribuir valores fora do intervalo -128 a 127 a uma variável
	 * do tipo byte, dá algum tipo de erro de estouro.
	 * 
	 * A contagem de bytes em relação aos inteiros seria algo do tipo 0 ~ 127,
	 * -128(128) ~ -1(255).
	 * 
	 * Como em hexadecimal, 0xFF representa 255, fazer um AND(&) com o byte, vai
	 * implicar em uma maneira de contornar o sinal, fazendo com que o byte seja
	 * convertido para um inteiro unsigned, entre 0 e 255.
	 */
	public static void main(String args[]) {

		System.out.println(0xFF); // 255 em Hexadecimal:
		byte[] bytes = new byte[5];
		bytes[0] = -128; // 255?
		bytes[1] = -1; // 128?
		bytes[2] = 0; // 0?
		bytes[3] = 127; // ?
		bytes[4] = -127;

		System.out.println("Sem máscara: ");
		for (int i = 0; i < 5; i++)
			System.out.println(bytes[i]);
		System.out.println("Com máscara: ");
		for (int j = 0; j < 5; j++)
			System.out.println((int) (bytes[j] & 0xFF));
	}

}
