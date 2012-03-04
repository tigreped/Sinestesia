package midi;

/**
 * Classe utilizada para buscar informações sobre um arquivo MIDI.
 */

import java.io.File;

import javax.sound.midi.*;

public class MidiFileInfo
{
    public static void main(String[] args)
    {
        String strSource = "Teste.mid";
        MidiFileFormat fileFormat = null;
        Sequence sequence = null;
        try
        {
            File file = new File(strSource);
            fileFormat = MidiSystem.getMidiFileFormat(file);
            sequence = MidiSystem.getSequence(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        /*
         * And now, we output the data.
         */
        if (fileFormat == null)
        {
            System.out.println("Cannot determine format");
        }
        else
        {
            System.out
                    .println("---------------------------------------------------------------------------");
            System.out.println("Midi File Type: " + fileFormat.getType());

            float fDivisionType = fileFormat.getDivisionType();
            String strDivisionType = null;
            if (fDivisionType == Sequence.PPQ)
            {
                strDivisionType = "PPQ";
            }
            else if (fDivisionType == Sequence.SMPTE_24)
            {
                strDivisionType = "SMPTE, 24 frames per second";
            }
            else if (fDivisionType == Sequence.SMPTE_25)
            {
                strDivisionType = "SMPTE, 25 frames per second";
            }
            else if (fDivisionType == Sequence.SMPTE_30DROP)
            {
                strDivisionType = "SMPTE, 29.97 frames per second";
            }
            else if (fDivisionType == Sequence.SMPTE_30)
            {
                strDivisionType = "SMPTE, 30 frames per second";
            }

            System.out.println("DivisionType: " + strDivisionType);

            String strResolutionType = null;
            if (fileFormat.getDivisionType() == Sequence.PPQ)
            {
                strResolutionType = " ticks per beat";
            }
            else
            {
                strResolutionType = " ticks per frame";
            }
            System.out.println("Resolution: " + fileFormat.getResolution() + strResolutionType);

            String strFileLength = null;
            if (fileFormat.getByteLength() != MidiFileFormat.UNKNOWN_LENGTH)
            {
                strFileLength = "" + fileFormat.getByteLength() + " bytes";
            }
            else
            {
                strFileLength = "unknown";
            }
            System.out.println("Length: " + strFileLength);

            String strDuration = null;
            if (fileFormat.getMicrosecondLength() != MidiFileFormat.UNKNOWN_LENGTH)
            {
                strDuration = "" + fileFormat.getMicrosecondLength() + " microseconds)";
            }
            else
            {
                strDuration = "unknown";
            }
            System.out.println("Duration: " + strDuration);

            System.out.println("[Sequence says:] Length: " + sequence.getTickLength()
                    + " ticks (= " + sequence.getMicrosecondLength() + " us)");
            System.out
                    .println("---------------------------------------------------------------------------");
        }
    }

    private static void printUsageAndExit()
    {
        System.out.println("MidiFileInfo: usage:");
        System.out.println("\tjava MidiFileInfo [-s|-f|-u] [-i] <midifile>");
        System.exit(1);
    }
}

/*** MidiFileInfo.java ***/

