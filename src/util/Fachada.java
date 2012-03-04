package util;

import java.util.ArrayList;

/**
 * Encapsula métodos utilizados por várias classes do programa.
 * 
 * @author Pedro Guimarães
 */
public class Fachada
{
    /**
     * Calcula os divisores comuns de dois números inteiros, provavelmente as dimensões de uma
     * imagem.
     * 
     * @param a
     * @param b
     * @return lista com os divisores comuns de a e b.
     */
    public static ArrayList<Integer> divisoresComuns(int a, int b)
    {
        ArrayList<Integer> divisores = new ArrayList<Integer>();

        for (int i = 1; i < Math.max(a, b); i++)

            if (a % i == 0 && b % i == 0)
                divisores.add(i);

        return divisores;
    }

    /**
     * Calcula todos os divisores de um número inteiro. Utilizado para apresentar as opções para o
     * número de canais para uma imagem.
     * 
     * @param c
     * @return lista de todos os divisores do parâmetro.
     */
    public static ArrayList<Integer> divisores(int c)
    {
        ArrayList<Integer> divisores = new ArrayList<Integer>();

        for (int j = 1; j <= c; j++)
            if (c % j == 0)
                divisores.add(j);

        return divisores;
    }
}
