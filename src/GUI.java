import javax.sound.midi.MidiDevice;
import javax.sound.midi.*;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.util.*;

public class GUI extends JFrame implements ActionListener
{
    private JLabel label = new JLabel("Close this window to quit");
    private JComboBox inputDevicesDropdown;
    private JComboBox outputDevicesDropdown;
    private Helper theHelper;

    public GUI(Helper h)
    {
        theHelper = h;
        // close midi devices and quit if we get a window closing event
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosed(WindowEvent e)
            {

                theHelper.closeAll();
                System.exit(0);
            }

            public void windowClosing(WindowEvent e)
            {
                theHelper.closeAll();
                System.exit(0);
            }
        });
        setTitle("MidiRouter");
        setSize(400,  70);
        setLocation(100, 100);

        this.populateInputDropdown();
        this.populateOutputDropdown();
        this.setLayout(new GridLayout());
        this.add(inputDevicesDropdown);
        this.add(outputDevicesDropdown);

        //this.add(label);

        this.setVisible(true);


    }



    public void populateInputDropdown()
    {
        // first for the input devices
        // we'll need a list of names to show the user
        ArrayList<String> inputNames = new ArrayList<String>();

        try
        {






            for ( int i = 0; i< theHelper.getSystemMidiTransmitterDevices().size(); i++ )
            {

               MidiDevice.Info info = theHelper.getSystemMidiTransmitterDevices().get(i).getDeviceInfo();
               inputNames.add(info.toString());


            }
            String[] nameArray = (String[]) inputNames.toArray(new String[inputNames.size()]);
            inputDevicesDropdown = new JComboBox(nameArray);






        }
        catch(MidiUnavailableException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Couldn't load list of input devices!", "Alert", JOptionPane.INFORMATION_MESSAGE);

        }


    }


    public void populateOutputDropdown()
    {
        // first for the input devices
        // we'll need a list of names to show the user
        ArrayList<String> inputNames = new ArrayList<String>();

        try
        {






            for ( int i = 0; i< theHelper.getSystemMidiReceiverDevices().size(); i++ )
            {

                MidiDevice.Info info = theHelper.getSystemMidiReceiverDevices().get(i).getDeviceInfo();
                inputNames.add(info.toString());


            }
            String[] nameArray = (String[]) inputNames.toArray(new String[inputNames.size()]);
            outputDevicesDropdown = new JComboBox(nameArray);






        }
        catch(MidiUnavailableException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Couldn't load list of output devices!", "Alert", JOptionPane.INFORMATION_MESSAGE);

        }


    }






     public void actionPerformed(ActionEvent e)
     {
        // no actions
     }
}