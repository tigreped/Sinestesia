package util;

import java.io.FileOutputStream;
import java.io.PrintStream;

import midi.Escalas;
import midi.Nota;

/**
 * Classe utilizada para adicionar objetos compartilhados por mais de uma classe.
 * @author pedro
 *
 */
public class Base {
	
	public static Constantes constantes;
	
	public static Escalas escalas;
	
	public static PrintStream logFile;
	
	public static void out(String msg){
		System.out.println(msg);
		try {
		log(msg);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void outNota(Nota nota) {
		out("Nota: " + constantes.mapeamentoNotas.get(nota.getTecla())
				+ " Duracao: " + nota.getDuracao() + " Volume: "
				+ nota.getIntensidade());
	}
	
	public static String getNomeDuracao(Nota nota) {
        return constantes.mapeamentoTempos.get(nota.getDuracao());
	}
	
	public static String getNomeTecla(Nota nota) {
		return constantes.mapeamentoNotas.get(nota.getTecla());
	}
		
	public static void setConstantes(Constantes c) {
		constantes = c;
	}

	public static void setEscalas(Escalas e) {
		escalas = e;
	}

	/**
	 * Inicializa o arquivo de log com o tempo da execução.
	 */
	public static void initializeLog() {
		try {
			logFile = new PrintStream(new FileOutputStream("log/" + System.currentTimeMillis()/1000 + ".log"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Salva a saída de texto da execução em um arquivo .log
	 * 
	 * @param sequence
	 * @throws Exception
	 */
	public static void log(String s)
			throws Exception {
		try {
			logFile.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void closeLog(){
		logFile.close();
	}
}
