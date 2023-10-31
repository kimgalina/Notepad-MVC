import java.awt.event.ActionEvent;

/**
 * The HelpActionHandler class implements the ActionHandler interface and is responsible for
 * handling actions related to providing help and information within the application.
 * It provides functionality to open help dialogs and display information about the application.
 */
public class HelpActionHandler implements ActionHandler {
    private Viewer viewer;

    /**
     * Constructor for the HelpActionHandler class.
     *
     * @param viewer The main viewer component of the application.
     */
    public HelpActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    /**
     * Handles various actions based on the provided command.
     *
     * @param command The action command, which can be "View_Help" or "About."
     * @param event   The ActionEvent object representing the user's action.
     */
    @Override
    public void handleAction(String command, ActionEvent event) {
        switch (command) {
            case "View_Help":
                viewer.openHelpDialog();
                break;
            case "About":
                viewer.openHelpDialog();
                break;
        }
    }
}
