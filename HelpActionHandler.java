import java.awt.event.ActionEvent;
public class HelpActionHandler implements ActionHandler {
    private Viewer viewer;

    public HelpActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

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
