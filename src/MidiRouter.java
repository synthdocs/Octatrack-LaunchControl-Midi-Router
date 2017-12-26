import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;

public class MidiRouter
{


    public static void main (String[] args)
    {
        // these are the names linux gives to the launch control and the midi to usb interface cable I'm using
        // You can change these to suit your default needs
        // (run the gui variant of the program to see what your OS calls your devices)
        String launchControlDeviceName = "Control";
        String usbPortDeviceName = "CH345";

        if ( args.length == 0 ) // no command line argument signifies the program is to be run without gui
        {
            // if the program is to run without the gui it will just default to the Launch Control as Input and Port 1 as Output
            // This is likely the case if the program is running on a headless raspberry pi from startup
            DeviceManager mngr = new DeviceManager();
            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

            // scanning over all midi devices currently connected
            for (int i = 0; i < infos.length; i++)
            {

                // on linux device names are followed by some info, seperated by a space e.g. Control [hW:1,0,0]
                String deviceWholeName = infos[i].toString();

                // the info can be variable, so in order to uniquely identify devices we isolate the first part of name
                // split the full identifier around the space
                String[] parts = deviceWholeName.split(" ");
                String deviceName = parts[0];

                try {
                    // if we are on the launch control, set it as the input
                    if (deviceName.equals(launchControlDeviceName) && MidiSystem.getMidiDevice(infos[i]).getMaxTransmitters() == -1)
                    {
                        mngr.setInput(MidiSystem.getMidiDevice(infos[i]));

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



                    try
                    {
                        // If we find the desired usb output, set it as the output device
                        if (deviceName.equals(usbPortDeviceName) && MidiSystem.getMidiDevice(infos[i]).getMaxReceivers() == -1)
                        {
                            mngr.setOutput(MidiSystem.getMidiDevice(infos[i]));
                        }
                    }
                    catch (MidiUnavailableException ex)
                    {
                        System.err.println("couldn't set output device");
                    }




            }
            try
            {
                mngr.connect();
            }
            catch ( NullPointerException ex )
            {
                System.err.println("Devices called " + launchControlDeviceName + " and " + usbPortDeviceName + " not found.\n" +
                        "Make sure devices are connected and retry.\nIf you are unsure what devices are connected, try " +
                        "rerunning with a command line argument to run the program with a GUI and have a look at the device lists");
            }
        }

        else if ( args[0].equals("gui")) // if command line argument "gui" is given, run the gui variant of the program
        {


                // this line will through a null pointer exception if the required devices aren't found and can't be connected
                DeviceManager mngr = new DeviceManager();
                GUI gui = new GUI(mngr);


        }
        else
        {
            System.err.println("Invalid Argument");
            System.exit(0);
        }



    }

}
