import javax.swing.JTextArea;
import java.awt.event.ActionEvent;

/**
 * The ViewActionHandler class implements the ActionHandler interface and is responsible for
 * handling actions related to the view and appearance settings within the application.
 * It provides functionality to zoom in, zoom out, reset the zoom level, and change the application's theme.
 */
public class ViewActionHandler implements ActionHandler {
    private Viewer viewer;

    /**
     * Constructor for the ViewActionHandler class.
     *
     * @param viewer The main viewer component of the application.
     */
    public ViewActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    /**
     * Handles various actions based on the provided command.
     *
     * @param command The action command, which can be "ZOOM_IN," "ZOOM_OUT," "ZOOM_DEFAULT," or "Change_Theme."
     * @param event   The ActionEvent object representing the user's action.
     */
    @Override
    public void handleAction(String command, ActionEvent event) {
        switch (command) {
            case "ZOOM_IN":
                viewer.zoomIn();
                break;
            case "ZOOM_OUT":
                viewer.zoomOut();
                break;
            case "ZOOM_DEFAULT":
                viewer.zoomDefault();
                break;
            case "Change_Theme":
                viewer.changeTheme();
                break;
        }
    }
}
