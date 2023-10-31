import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 * The PrintActionHandler class implements the ActionHandler interface and is responsible for
 * handling the action of printing the content of the application's text area. It retrieves the
 * necessary data, such as font and text color, from the Viewer and initiates the printing process.
 */
public class PrintActionHandler implements ActionHandler {
    private Viewer viewer;
    private List<String> data;

    /**
     * Constructor for the PrintActionHandler class.
     *
     * @param viewer The main viewer component of the application from which to retrieve printing data.
     */
    public PrintActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    /**
     * Handles the action of printing the content of the text area.
     *
     * @param command The action command associated with the "Print" action.
     * @param event   The ActionEvent object representing the user's action.
     */
    @Override
    public void handleAction(String command, ActionEvent event) {
        Font font = viewer.getCurrentTextAreaFont();
        Color textColor = viewer.getCurrentTextAreaColor();
        try {
            data = viewer.getListTextFromTextAreaContent();

            Print document = new Print(data, font, textColor);
            document.printDocument();
            if (document.isPrinted()) {
                viewer.showDialogFinishPrintDocument();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            data.clear();
            data = null;
        }
    }
}
