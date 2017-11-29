import javax.sound.midi.*;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import javax.sound.midi.Receiver;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class MidiRouter
{


    public static void main (String[] args)
    {

       Helper helper = new Helper();
       GUI gui = new GUI(helper.connect());



    }

}
