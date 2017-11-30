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
    private DeviceManager deviceManager;
    private JButton connectButton = new JButton("Connect");

    public GUI(DeviceManager h)
    {
        deviceManager = h;
        // close midi devices and quit if we get a window closing event
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosed(WindowEvent e)
            {

                deviceManager.closeAll();
                System.exit(0);
            }

            public void windowClosing(WindowEvent e)
            {
                deviceManager.closeAll();
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


        connectButton.addActionListener(this);

        this.add(connectButton);

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






            for ( int i = 0; i< deviceManager.getSystemMidiTransmitterDevices().size(); i++ )
            {

               MidiDevice.Info info = deviceManager.getSystemMidiTransmitterDevices().get(i).getDeviceInfo();
               inputNames.add(info.toString());


            }
            String[] nameArray = (String[]) inputNames.toArray(new String[inputNames.size()]);
            inputDevicesDropdown = new JComboBox(nameArray);






        }
        catch(MidiUnavailableException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Couldn't load list of input devices!",
                                            "Alert", JOptionPane.INFORMATION_MESSAGE);

        }


    }


    public void populateOutputDropdown()
    {

        ArrayList<String> inputNames = new ArrayList<String>();

        try
        {






            for ( int i = 0; i< deviceManager.getSystemMidiReceiverDevices().size(); i++ )
            {

                MidiDevice.Info info = deviceManager.getSystemMidiReceiverDevices().get(i).getDeviceInfo();
                inputNames.add(info.toString());


            }
            String[] nameArray = (String[]) inputNames.toArray(new String[inputNames.size()]);
            outputDevicesDropdown = new JComboBox(nameArray);






        }
        catch(MidiUnavailableException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Couldn't load list of output devices!",
                                            "Alert", JOptionPane.INFORMATION_MESSAGE);

        }


    }






     public void actionPerformed(ActionEvent e)
     {
        if ( e.getSource() == connectButton )
        {
            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
            for (int i = 0; i < infos.length; i++)
            {

                String selected = (String) inputDevicesDropdown.getSelectedItem();

                if (  selected.equals(infos[i].toString()))
                {
                    try
                    {
                        deviceManager.setInput(MidiSystem.getMidiDevice(infos[i]));
                    }
                    catch (MidiUnavailableException ex)
                    {
                       System.err.println("couldn't set input device");
                    }

                }

                selected = (String) outputDevicesDropdown.getSelectedItem();

                if (  selected.equals(infos[i].toString()))
                {
                    try
                    {
                        deviceManager.setOutput(MidiSystem.getMidiDevice(infos[i]));
                    }
                    catch (MidiUnavailableException ex)
                    {
                        System.err.println("couldn't set output device");
                    }

                }


            }
            deviceManager.connect();





        }
     }
}