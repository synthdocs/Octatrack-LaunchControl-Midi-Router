import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class MyReceiver implements Receiver
{
    public MyReceiver(String deviceInfo)
    {

    }

    @Override
    public void close()
    {

    }

    @Override
    public void send(MidiMessage message, long timeStamp)
    {
        System.err.println("something happened");

    }




    public static void main(String[] args)
    {

    }


}