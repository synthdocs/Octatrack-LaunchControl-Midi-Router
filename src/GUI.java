import javax.sound.midi.MidiDevice;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener
{
    private JLabel label = new JLabel("Close this window to quit");

    public GUI(MidiDevice device)
    {
        // close midi devices and quit if we get a window closing event
        this.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                System.out.println("JDialog window closed event received");
                device.close();
                System.exit(0);
            }

            public void windowClosing(WindowEvent e) {
                System.out.println("jdialog window closing event received");
                device.close();
                System.exit(0);
            }
        });
        setTitle("MidiRouter");
        setSize(400,  70);
        setLocation(100, 100);

        this.add(label);

        this.setVisible(true);


    }






     public void actionPerformed(ActionEvent e)
     {
        // no actions
     }
}