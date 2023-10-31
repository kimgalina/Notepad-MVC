import java.awt.event.ActionEvent;

/**
 * The NewDocumentActionHandler class implements the ActionHandler interface and is responsible for
 * handling the action of creating a new document in the application. When this action is triggered, it
 * opens a new tab in the Viewer, allowing the user to start working on a new document.
 */
public class NewDocumentActionHandler implements ActionHandler {
    private Viewer viewer;

    /**
     * Constructor for the NewDocumentActionHandler class.
     *
     * @param viewer The main viewer component of the application where the new document will be created.
     */
    public NewDocumentActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    /**
     * Handles the action of creating a new document.
     *
     * @param command The action command associated with the "New Document" action (e.g., "New_Document").
     * @param event   The ActionEvent object representing the user's action.
     */
    @Override
    public void handleAction(String command, ActionEvent event) {
        viewer.createNewTab();
    }
}
