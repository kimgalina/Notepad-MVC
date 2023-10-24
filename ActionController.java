import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.HashMap;

public class ActionController implements ActionListener {
    private Viewer viewer;
    private TabsController tabsController;
    private Map<String, ActionHandler> actionHandlers;


    public ActionController(Viewer viewer, TabsController tabsController) {
        this.viewer = viewer;
        this.tabsController = tabsController;
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
        actionHandlers.put("New_Document", new NewDocumentActionHandler(viewer));
        actionHandlers.put("Open_Document", new OpenDocumentActionHandler(viewer, tabsController));
        actionHandlers.put("Save", new SaveDocumentActionHandler(viewer, tabsController));
        actionHandlers.put("Save_As", new SaveDocumentActionHandler(viewer, tabsController));
        actionHandlers.put("Print", new PrintActionHandler(viewer));
        actionHandlers.put("Exit", new ExitActionHandler(this, viewer));
        actionHandlers.put("Copy", new CopyPasteActionHandler(viewer));
        actionHandlers.put("Cut", new CopyPasteActionHandler(viewer));
        actionHandlers.put("Paste", new CopyPasteActionHandler(viewer));
        actionHandlers.put("Clear", new CopyPasteActionHandler(viewer));
        actionHandlers.put("Select_All", new CopyPasteActionHandler(viewer));
        actionHandlers.put("Find", new GoAndFindActionHandler(viewer));
        actionHandlers.put("Find more", new GoAndFindActionHandler(viewer));
        actionHandlers.put("Go", new GoAndFindActionHandler(viewer));
        actionHandlers.put("Time_And_Date", new GoAndFindActionHandler(viewer));
        actionHandlers.put("Font", new FormatActionHandler(viewer));
        actionHandlers.put("Word_Wrap", new FormatActionHandler(viewer));
        actionHandlers.put("ZOOM_IN", new ViewActionHandler(viewer));
        actionHandlers.put("ZOOM_OUT", new ViewActionHandler(viewer));
        actionHandlers.put("ZOOM_DEFAULT", new ViewActionHandler(viewer));
        actionHandlers.put("View_Help", new ViewActionHandler(viewer));
        actionHandlers.put("About", new ViewActionHandler(viewer));
        actionHandlers.put("Change_Theme", new ViewActionHandler(viewer));
        actionHandlers.put("CloseTab", new ExitActionHandler(this, viewer));
        actionHandlers.put("Choose_Color", new FormatActionHandler(viewer));
    }
}
