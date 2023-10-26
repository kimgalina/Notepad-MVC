import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.HashMap;

public class ActionController implements ActionListener {
    private Viewer viewer;
    private TabsController tabsController;
    private FindDialogController findController;
    private Map<String, ActionHandler> actionHandlers;


    public ActionController(Viewer viewer, TabsController tabsController, FindDialogController findController) {
        this.viewer = viewer;
        this.tabsController = tabsController;
        this.findController = findController;
        actionHandlers = new HashMap<>();
        initializeActionHandlers();
    }


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

    public Map<String, ActionHandler> getActionHandlers() {
        return actionHandlers;
    }

    public boolean hasUnsavedChanges(int tabIndex) {
        Boolean hasChanges = tabsController.getUnsavedChangesPerTab().get(tabIndex);
        if(hasChanges != null){
            System.out.println("hasChanges = " + hasChanges);
            return hasChanges;
        }
        return false;
    }

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
        actionHandlers.put("View_Help", viewActionHandler);
        actionHandlers.put("About", viewActionHandler);
        actionHandlers.put("Change_Theme", viewActionHandler);

        actionHandlers.put("Exit", exitActionHandler);
        actionHandlers.put("CloseTab", exitActionHandler);
    }
}
