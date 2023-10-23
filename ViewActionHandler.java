import javax.swing.JTextArea;

public class ViewActionHandler implements ActionHandler {
    private Viewer viewer;

    public ViewActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void handleAction(String command) {
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
            case "View_Help":
                viewer.openHelpDialog();
                break;
            case "About":
                viewer.openHelpDialog();
                break;
            case "Change_Theme":
                viewer.changeTheme();
                break;
        }
    }

    private void makeWordWrap() {
        JTextArea textArea = viewer.getCurrentContent();
        if(textArea.getLineWrap()) {
            textArea.setLineWrap(false);
        } else {
            textArea.setLineWrap(true);
        }
    }
}
