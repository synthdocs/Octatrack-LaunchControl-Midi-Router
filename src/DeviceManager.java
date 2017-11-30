import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.*;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import java.io.PrintStream;
import java.util.ArrayList;

public class DeviceManager
{


private DumpReceiver dumpReceiver; // can be used to dump MIDI data to console
private String name;
private MidiDevice device;
private MidiDevice input = null;
private MidiDevice output = null;
private boolean haveInput = false;
private boolean haveOutput = false;
private boolean dump = false;

MidiDevice launchControl;
private MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();


public MidiDevice getInput() {
    return input;
}

public MidiDevice getOutput() {
    return output;
}

// returns a list of all midi devices that can transmit
public ArrayList<MidiDevice> getSystemMidiTransmitterDevices() throws MidiUnavailableException
{


    ArrayList<MidiDevice> transmittersDevices = new ArrayList<MidiDevice>();


    for (int i = 0; i < infos.length; i++)
    {


        if (MidiSystem.getMidiDevice((infos[i])).getMaxTransmitters() != 0)
        {

            transmittersDevices.add(MidiSystem.getMidiDevice(infos[i]));
        }


    }
    return transmittersDevices;


}




// returns a lit of all midi devices that can receive
public ArrayList<MidiDevice> getSystemMidiReceiverDevices() throws MidiUnavailableException
{

    ArrayList<MidiDevice> receiverDevices = new ArrayList<MidiDevice>();


    for (int i = 0; i < infos.length; i++)
    {



        if (MidiSystem.getMidiDevice((infos[i])).getMaxReceivers() != 0)
        {
            receiverDevices.add(MidiSystem.getMidiDevice(infos[i]));
        }


    }
    return receiverDevices;
}

// connects the two midi devices
public MidiDevice connect()
{
    try
    {
        input.open();
        output.open();
        //Receiver rec = output.getReceiver();

        Transmitter t = input.getTransmitter();
        //t.setReceiver(rec);
        // just dumping to the console for now
        t.setReceiver(dumpReceiver);
        t.setReceiver(output.getReceiver());

    }

    catch (MidiUnavailableException ex)
    {

    }
    haveInput = true;
    return input;

}

// closes the connection
public void closeAll()
{
    // only try to close if we have an input/output and its open
    if (haveInput && !(input.isOpen()))
    {
        input.close();
    }
    if (haveOutput && !(output.isOpen()))
    {
        output.close();
    }

}


public void setInput(MidiDevice dev)
{




    input = dev;



}

public void setOutput(MidiDevice dev)
{

    output = dev;







}


}
