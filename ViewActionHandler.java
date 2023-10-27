import javax.swing.JTextArea;
import java.awt.event.ActionEvent;

public class ViewActionHandler implements ActionHandler {
    private Viewer viewer;

    public ViewActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void handleAction(String command, ActionEvent event) {
        switch (command) {
            case "ZOOM_IN":
                viewer.zoomIn();
                break;
            case "ZOOM_OUT":
                viewer.zoomOut();
                break;
            case "ZOOM_DEFAULT":
                viewer.zoomDefault();
                break;
            case "Change_Theme":
                viewer.changeTheme();
                break;
        }
    }
}
