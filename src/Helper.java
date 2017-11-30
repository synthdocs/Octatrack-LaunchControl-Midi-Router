import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.*;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import java.io.PrintStream;
import java.util.ArrayList;

public class Helper {


    DumpReceiver receiver;
    String name;
    MidiDevice device;
    MidiDevice launchControl;
    private MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();


    // calling connect with no arguments default searches for launchpad and usb out
    public MidiDevice connect() {
        // dump received midi data to screen, just a palceholder for now till we get the output set up
        receiver = new DumpReceiver(System.out, false);


        // looping over all connected midi devices

        receiver = new DumpReceiver(new PrintStream(System.out), false);
        ArrayList<Transmitter> t = new ArrayList();

        try {


            for (int i = 0; i < infos.length; i++) {
                name = infos[i].toString();
                System.err.println("device" + name);
                device = MidiSystem.getMidiDevice((infos[i]));
                System.out.println("Testing " + infos[i] + " " + device.getMaxTransmitters() + " " + device.getMaxReceivers());

                try {
                    device = MidiSystem.getMidiDevice(infos[i]);
                    //System.out.println(infos[i] + "with transmitters: " + launchControl.getMaxTransmitters());
                } catch (MidiUnavailableException e) {
                    System.err.println("System midi unavailable!!");
                }

                // if our device is called Launch Control and it has transmitters
                // nb there is a Launch Control device for receivers, we don't want that one!!
                if (name.equals("Launch Control") && device.getMaxTransmitters() != 0) {
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


        } catch (MidiUnavailableException ex) {

        }
        return launchControl;
    }


    public MidiDevice connect(MidiDevice input, MidiDevice output) {
        try {
            input.open();
            output.open();
            Receiver rec = output.getReceiver();
            Transmitter t = input.getTransmitter();
            t.setReceiver(rec);

        } catch (MidiUnavailableException ex) {

        }
        return input;

    }

    // returns a list of all midi devices that can transmit
    public ArrayList<MidiDevice.Info> getSystemMidiTransmitterDevices() throws MidiUnavailableException
    {

        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        ArrayList<MidiDevice.Info> transmittersInfos = new ArrayList<MidiDevice.Info>();


        for (int i = 0; i < infos.length; i++)
        {


            device = MidiSystem.getMidiDevice((infos[i]));


            device = MidiSystem.getMidiDevice(infos[i]);



            if (device.getMaxTransmitters() != 0)
            {
                transmittersInfos.add(device.getDeviceInfo());
            }


        }
        return transmittersInfos;
    }




    // returns a lit of all midi devices that can receive
    public ArrayList<MidiDevice.Info> getSystemMidiReceiverDevices()
    {
        ArrayList<MidiDevice.Info> receiverInfos = new ArrayList<MidiDevice.Info>();


        return receiverInfos;
    }


}
