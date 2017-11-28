import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import javax.sound.midi.Receiver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MidiDeviceInitialiser {

    private MidiDevice launchControl;
    private MidiDevice usbToMidiOut;
    private String name;
    private Transmitter transmitter;
    private Receiver receiver;





    public MidiDeviceInitialiser() throws MidiUnavailableException
    {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < infos.length; i++) {
            name = infos[i].toString();


            if (name.equals("Launch Control"))
            {
                System.out.println(infos[i]);
                try
                {
                    launchControl = MidiSystem.getMidiDevice(infos[i]);
                } catch (MidiUnavailableException e) {
                    // Handle or throw exception...
                }
            } // TODO - also get the case of the midi/usb converter thing and make it a device to
        }
        try
        {
            if (!(launchControl.isOpen()))
            {
                try
                {
                    launchControl.open();
                    System.out.println(name + " opened");
                }
                catch (MidiUnavailableException e)
                {
                    System.err.println("Launch Control couldn't be opened");
                }

            }
            // TODO - Open the outputDevice too
        }
        catch (NullPointerException ex)
        {
            System.err.println("Launch Control not found");
        }

        // returns a list of transmitters that are currently connected


    }

    public Receiver connectTransmitterReceiver()
    {
        try
        {
            transmitter = launchControl.getTransmitter();
            receiver = usbToMidiOut.getReceiver();
            transmitter.setReceiver(receiver);

        }
        catch (MidiUnavailableException ex)
        {
            System.err.println("couldn't hook up");
            ex.printStackTrace();

        }

        return receiver;

    }

    public void close()
    {
        launchControl.close();
        usbToMidiOut.close();
        System.out.println("devices closed");
    }



}
















