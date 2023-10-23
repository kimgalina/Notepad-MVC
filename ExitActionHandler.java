import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;


public class ExitActionHandler implements ActionHandler {
    private Viewer viewer;
    private ActionController actionController;
    private TabsController tabsController;

    public ExitActionHandler(ActionController actionController, Viewer viewer, TabsController tabsController) {
        this.viewer = viewer;
        this.tabsController = tabsController;
        this.actionController = actionController;
    }

    @Override
    public void handleAction(String command) {

        switch (command) {
            case "Exit":
                exit();
                break;
            case "CloseTab":
                viewer.closeCurrentTab();
                break;
        }
    }

    private void exit() {
        JTabbedPane tabPane = viewer.getTabPane();
        int tabCount = tabPane.getTabCount();
        for(int i = 0; i < tabCount; i++) {
            int currentTabIndex = viewer.getCurrentTabIndex();

            if(actionController.hasUnsavedChanges(currentTabIndex)) {
                int result = viewer.showCloseTabMessage(currentTabIndex);

                if(result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION || result == -1) {
                    return;
                }
            } else {
                viewer.deleteTab(currentTabIndex);

            }
        }
        System.exit(0);
    }
}
