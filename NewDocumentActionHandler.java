import java.awt.event.ActionEvent;

public class NewDocumentActionHandler implements ActionHandler {
    private Viewer viewer;

    public NewDocumentActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void handleAction(String command, ActionEvent event) {
        viewer.createNewTab();
    }
}
