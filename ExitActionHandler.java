import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionEvent;

public class ExitActionHandler implements ActionHandler {
    private Viewer viewer;
    private ActionController actionController;

    public ExitActionHandler(ActionController actionController, Viewer viewer) {
        this.viewer = viewer;
        this.actionController = actionController;
    }

    @Override
    public void handleAction(String command, ActionEvent event) {

        switch (command) {
            case "Exit":
                exit();
                break;
            case "CloseTab":
                viewer.closeCurrentTab((JButton) event.getSource());
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
