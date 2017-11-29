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


    public static void main (String[] args) {
        DumpReceiver receiver;
        String name;
        MidiDevice device;
        MidiDevice launchControl;

        // dump received midi data to screen, just a palceholder for now till we get the output set up
        receiver = new DumpReceiver(System.out, false);



        // looping over all connected midi devices
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        receiver = new DumpReceiver(new PrintStream(System.out), false);
        ArrayList<Transmitter> t = new ArrayList();

        try {


            for (int i = 0; i < infos.length; i++)
            {
                name = infos[i].toString();
                System.err.println("device" + name);
                device = MidiSystem.getMidiDevice((infos[i]));
                System.out.println("Testing " + infos[i] + " " + device.getMaxTransmitters() + " " + device.getMaxReceivers());

                try
                {
                    device = MidiSystem.getMidiDevice(infos[i]);
                    //System.out.println(infos[i] + "with transmitters: " + launchControl.getMaxTransmitters());
                }
                catch (MidiUnavailableException e)
                {
                    System.err.println("System midi unavailable!!");
                }

                // if our device is called Launch Control and it has transmitters
                // nb there is a Launch Control device for receivers, we don't want that one!!
                if (name.equals("Launch Control") && device.getMaxTransmitters() != 0)
                {
                    // set the reference
                    launchControl = device;
                    //open the device
                    launchControl.open();
                    // get a transmitter
                    Transmitter trans = launchControl.getTransmitter();
                    // connect it to the receiver
                    trans.setReceiver(receiver);

                }
            }

            // Infinite loop for now just to keep the program running
            for (; ;)
            {

            }







        }
        catch (MidiUnavailableException ex)
        {

        }

    }

}
