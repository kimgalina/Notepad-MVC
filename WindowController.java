import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

public class WindowController implements WindowListener {
    private final ActionController controller;

    public WindowController(ActionController controller) {
        this.controller = controller;
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Eggs are not supposed to be green.");
        System.exit(0);
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
}
