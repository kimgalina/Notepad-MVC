import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

/**
 * This class acts as a controller for window-related events, specifically handling the window closing event.
 */
public class WindowController implements WindowListener {
    private final ActionController controller;
    private final Viewer viewer;

    /**
     * Constructor for WindowController.
     *
     * @param controller The ActionController to handle window-related actions.
     * @param viewer     The Viewer associated with this controller.
     */
    public WindowController(ActionController controller, Viewer viewer) {
        this.controller = controller;
        this.viewer = viewer;
    }

   /**
   * Invoked when the window is opened. No action is performed in this case.
   *
   * @param e The WindowEvent representing the window opening event.
   */
    public void windowOpened(WindowEvent e) {
    }

    /**
    * Invoked when the window is closing. It triggers the "Exit" action to handle window closure.
    *
    * @param e The WindowEvent representing the window closing event.
    */
    public void windowClosing(WindowEvent e) {
        controller.getActionHandlers().get("Exit").handleAction("Exit", null);
    }

   /**
   * Invoked when the window is closed. No action is performed in this case.
   *
   * @param e The WindowEvent representing the window closed event.
   */
    public void windowClosed(WindowEvent e) {
    }

    /**
    * Invoked when the window is iconified (minimized). No action is performed in this case.
    *
    * @param e The WindowEvent representing the window iconification event.
    */
    public void windowIconified(WindowEvent e) {
    }

    /**
    * Invoked when the window is deiconified (restored from a minimized state). No action is performed in this case.
    *
    * @param e The WindowEvent representing the window deiconification event.
    */
    public void windowDeiconified(WindowEvent e) {
    }

    /**
     * Invoked when the window is activated. No action is performed in this case.
     *
     * @param e The WindowEvent representing the window activation event.
     */
    public void windowActivated(WindowEvent e) {
    }

    /**
    * Invoked when the window is deactivated. No action is performed in this case.
    *
    * @param e The WindowEvent representing the window deactivation event.
    */
    public void windowDeactivated(WindowEvent e) {
    }
}
