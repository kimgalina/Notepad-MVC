
public class HelpActionHandler implements ActionHandler {
    private Viewer viewer;

    public HelpActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void handleAction(String command) {
        switch (command) {
            case "View_Help":
                viewer.getMessageAbout();
                break;
            case "About":
                viewer.getMessageAbout();
                break;
        }
    }
}
