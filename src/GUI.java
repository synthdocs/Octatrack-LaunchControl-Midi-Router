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
    private JToggleButton dumpButton = new JToggleButton("Console Dump Off");


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
        setTitle("Midi Signal Router");
        setSize(600,  180);
        setLocation(100, 100);

        this.populateInputDropdown();
        this.populateOutputDropdown();
        this.setLayout(new GridLayout(2,1));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.add(new JLabel("   Input Device:"));
        topPanel.add(inputDevicesDropdown);
        this.add(topPanel);

        JPanel midPanel = new JPanel();
        midPanel.setLayout(new GridBagLayout());
        midPanel.add(new JLabel("Output Device: "));
        midPanel.add(outputDevicesDropdown);
        this.add(midPanel);

        JPanel bottomPanel = new JPanel();
        connectButton.addActionListener(this);

        //bottomPanel.add(connectButton);
        this.add(connectButton);

        dumpButton.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent ev)
            {
                if(ev.getStateChange()==ItemEvent.SELECTED)
                {
                    System.out.println("CONSOLE MIDI DUMP ON");
                    dumpButton.setText("Console Dump On");
                    deviceManager.setDump(true);
                    if (deviceManager.isConnected())
                    {
                        deviceManager.connect();

                    }
                }
                else if(ev.getStateChange()==ItemEvent.DESELECTED) {
                    System.out.println("CONSOLE MIDI DUMP OFF");
                    dumpButton.setText("Console Dump Off");
                    deviceManager.setDump(false);

                    // if we are already connected, toggling the dump will reconnect with new settings
                    if (deviceManager.isConnected())
                    {
                        deviceManager.connect();

                    }
                }
            }
        });
        this.add(dumpButton);


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

               if (!(info.toString().equals("Real Time Sequencer")))
               {
                   inputNames.add(info.toString());
               }


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

        ArrayList<String> outputNames = new ArrayList<String>();

        try
        {






            for ( int i = 0; i< deviceManager.getSystemMidiTransmitterDevices().size(); i++ )
            {

                MidiDevice.Info info = deviceManager.getSystemMidiReceiverDevices().get(i).getDeviceInfo();

                if (!(info.toString().equals("Real Time Sequencer")) && !(info.toString().equals("Gervill"))  )
                {
                    outputNames.add(info.toString());
                }


            }
            String[] nameArray = (String[]) outputNames.toArray(new String[outputNames.size()]);
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
                //System.err.println("selected input: " + selected );
                try {
                    if (selected.equals(infos[i].toString()) && MidiSystem.getMidiDevice(infos[i]).getMaxTransmitters() == -1)
                    {
                        deviceManager.setInput(MidiSystem.getMidiDevice(infos[i]));

                    }

                }

                catch (MidiUnavailableException ex)
                {
                    System.err.println("couldn't set input device");
                }
                catch (NullPointerException ex)
                {
                    JOptionPane.showMessageDialog(null, "Device(s) not Selected!",
                                                    "Alert", JOptionPane.INFORMATION_MESSAGE);
                }

                selected = (String) outputDevicesDropdown.getSelectedItem();

                if (  selected.equals(infos[i].toString()))
                {
                    try
                    {
                        if (selected.equals(infos[i].toString()) && MidiSystem.getMidiDevice(infos[i]).getMaxReceivers() == -1)
                        {
                            deviceManager.setOutput(MidiSystem.getMidiDevice(infos[i]));
                        }
                    }
                    catch (MidiUnavailableException ex)
                    {
                        System.err.println("couldn't set output device");
                    }

                }


            }

                deviceManager.connect();
                connectButton.setEnabled(false);
                connectButton.setText("Connected");
                inputDevicesDropdown.setEnabled(false);
                outputDevicesDropdown.setEnabled(false);







        }

     }
}