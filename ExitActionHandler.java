import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionEvent;

/**
 * The ExitActionHandler class implements the ActionHandler interface and is responsible for
 * handling the actions related to exiting the application and closing individual tabs.
 * It provides functionality to prompt the user for unsaved changes and exit the application safely.
 */
public class ExitActionHandler implements ActionHandler {
    private Viewer viewer;
    private ActionController actionController;

    /**
     * Constructor for the ExitActionHandler class.
     *
     * @param actionController The controller responsible for handling application actions.
     * @param viewer           The main viewer component of the application.
     */
    public ExitActionHandler(ActionController actionController, Viewer viewer) {
        this.viewer = viewer;
        this.actionController = actionController;
    }

    /**
     * Handles the "Exit" and "CloseTab" actions based on the provided command.
     *
     * @param command The action command, which can be "Exit" or "CloseTab."
     * @param event   The ActionEvent object representing the user's action.
     */
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

    /**
    * Safely exits the application, handling unsaved changes and user confirmation.
    */
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
