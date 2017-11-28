import javax.sound.midi.MidiMessage;
import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import javax.sound.midi.Receiver;


public class MidiRouter
{






    public static void main(String[] args)
        {
           try
           {
               MidiDeviceInitialiser mdi = new MidiDeviceInitialiser();
               Receiver rec = mdi.connectTransmitterReceiver();






               mdi.close();


           }
           catch (MidiUnavailableException ex)
           {

           }


        }

}


