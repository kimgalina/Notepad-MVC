import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.HashMap;

/**
 * The ActionController class is responsible for handling various actions triggered by the user interface,
 * such as menu item clicks, button presses, and other user interactions. It implements the ActionListener
 * interface to respond to action events. This class maintains a collection of action handlers to execute
 * specific actions based on the event's action command.
 *
 * @author Template Design Method Pattern command
 * @version 1.0
 */
 public class ActionController implements ActionListener {
    private Viewer viewer;
    private TabsController tabsController;
    private FindDialogController findController;
    private Map<String, ActionHandler> actionHandlers;

    /**
     * Constructor for the ActionController class.
     *
     * @param viewer          The main viewer component of the application.
     * @param tabsController  The controller for managing tabs and documents.
     * @param findController  The controller for the find dialog and search functionality.
     */
    public ActionController(Viewer viewer, TabsController tabsController, FindDialogController findController) {
        this.viewer = viewer;
        this.tabsController = tabsController;
        this.findController = findController;
        actionHandlers = new HashMap<>();
        initializeActionHandlers();
    }

    /**
     * Handles the actionPerformed event when a user triggers an action.
     *
     * @param event The ActionEvent object representing the user's action.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        viewer.setCurrentContent();
        viewer.setViewItemZoomIn(viewer.canZoomIn());
        viewer.setViewItemZoomOut(viewer.canZoomOut());
        viewer.setStatusPanelToVisible(viewer.getStatusBarBox().isSelected());

        String command = event.getActionCommand();

        if (actionHandlers.containsKey(command)) {
            actionHandlers.get(command).handleAction(command, event);
        }
    }

    /**
     * Retrieve the map of action handlers associated with specific action commands.
     *
     * @return A map of action handlers.
     */
    public Map<String, ActionHandler> getActionHandlers() {
        return actionHandlers;
    }

    /**
    * Check if a tab has unsaved changes.
    *
    * @param tabIndex The index of the tab to check for unsaved changes.
    * @return True if there are unsaved changes, false otherwise.
    */
    public boolean hasUnsavedChanges(int tabIndex) {
        Boolean hasChanges = tabsController.getUnsavedChangesPerTab().get(tabIndex);

        if (hasChanges != null) {
            return hasChanges;
        }

        return false;
    }

    /**
    * Initialize the action handlers and map them to their corresponding action commands.
    */
    private void initializeActionHandlers() {
        NewDocumentActionHandler newDocumentActionHandler = new NewDocumentActionHandler(viewer);
        OpenDocumentActionHandler openDocumentActionHandler = new OpenDocumentActionHandler(viewer, tabsController);
        SaveDocumentActionHandler saveDocumentActionHandler = new SaveDocumentActionHandler(viewer, tabsController);
        PrintActionHandler printActionHandler = new PrintActionHandler(viewer);
        ExitActionHandler exitActionHandler = new ExitActionHandler(this, viewer);
        EditActionHandler editActionHandler = new EditActionHandler(viewer);
        GoAndFindActionHandler goAndFindActionHandler = new GoAndFindActionHandler(viewer, findController);
        FormatActionHandler formatActionHandler = new FormatActionHandler(viewer);
        ViewActionHandler viewActionHandler = new ViewActionHandler(viewer);
        HelpActionHandler helpActionHandler = new HelpActionHandler(viewer);

        actionHandlers.put("New_Document", newDocumentActionHandler);
        actionHandlers.put("Open_Document", openDocumentActionHandler);

        actionHandlers.put("Save", saveDocumentActionHandler);
        actionHandlers.put("Save_As", saveDocumentActionHandler);

        actionHandlers.put("Print", printActionHandler);

        actionHandlers.put("Copy", editActionHandler);
        actionHandlers.put("Cut", editActionHandler);
        actionHandlers.put("Paste", editActionHandler);
        actionHandlers.put("Clear", editActionHandler);
        actionHandlers.put("Select_All", editActionHandler);

        actionHandlers.put("Find", goAndFindActionHandler);
        actionHandlers.put("Find_Next", goAndFindActionHandler);
        actionHandlers.put("Find_Prev", goAndFindActionHandler);
        actionHandlers.put("Go", goAndFindActionHandler);
        actionHandlers.put("Time_And_Date", goAndFindActionHandler);

        actionHandlers.put("Font", formatActionHandler);
        actionHandlers.put("Choose_Color", formatActionHandler);
        actionHandlers.put("Word_Wrap", formatActionHandler);

        actionHandlers.put("ZOOM_IN", viewActionHandler);
        actionHandlers.put("ZOOM_OUT", viewActionHandler);
        actionHandlers.put("ZOOM_DEFAULT", viewActionHandler);
        actionHandlers.put("View_Help", helpActionHandler);
        actionHandlers.put("About", helpActionHandler);
        actionHandlers.put("Change_Theme", viewActionHandler);

        actionHandlers.put("Exit", exitActionHandler);
        actionHandlers.put("CloseTab", exitActionHandler);
    }
}
