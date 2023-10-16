import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

public class WindowController implements WindowListener {
    private final ActionController controller;
    private final Viewer viewer;

    public WindowController(ActionController controller, Viewer viewer) {
        this.controller = controller;
        this.viewer = viewer;
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        if(!controller.hasUnsavedChanges()) {
            System.exit(0);
        } else {
            viewer.showExitMessage();
        }
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
