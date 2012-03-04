package midi;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Track;

import util.Base;

/**
 * Classe utilizada para interpretar as MidiMessages de uma dada track de uma midi.
 * 
 * @author Pedro Guimaraes
 */
public class Interpretador extends Base {

    public static void main(Track track)
    {
        for (int i = 0; i < track.size(); i++)
            analisa(track.get(i));
    }

    public static void analisa(MidiEvent evento)
    {
        MidiMessage msg = evento.getMessage();

        int size = msg.getLength();

        byte[] mensagem = new byte[size];

        mensagem = msg.getMessage();

        // Pega os bytes da mensagem:
        // OBS: IMPORTANTE notar o uso da convers�o de byte para int:
        int statusByte = (int) (mensagem[0] & 0xFF);
        int dataByte1 = (int) (mensagem[1] & 0xFF);
        int dataByte2 = (int) (mensagem[1] & 0xFF);

        String status = constantes.mapeamentoStatusBytes.get(statusByte);

        // Note off events:
        if (statusByte > 127 && statusByte < 144)
        {
            // System.out.print(constantes.mapeamentoNotas.get(dataByte1));
            // System.out.print(" - Vol.: " + dataByte2);
        }

        // Note on events:
        else if (statusByte > 143 && statusByte < 160);
   
        // Program change events:
        else if (statusByte > 191 && statusByte < 208);

        else;
//        {
//            System.out.print("Tick: " + evento.getTick() + " - ");
//            System.out.print(status + ": ");
//            System.out.print("First data byte: " + dataByte1 + " Second data byte: " + dataByte2);
//            System.out.println();
//        }
    }
}
