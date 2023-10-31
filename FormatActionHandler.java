import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionEvent;

/**
 * The FormatActionHandler class implements the ActionHandler interface and is responsible for
 * handling actions related to text formatting and appearance settings within the application.
 * It provides functionality to open the font dialog, toggle word wrap, and choose text color.
 */
public class FormatActionHandler implements ActionHandler {
    private Viewer viewer;

    /**
     * Constructor for the FormatActionHandler class.
     *
     * @param viewer The main viewer component of the application.
     */
    public FormatActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    /**
     * Handles various actions based on the provided command.
     *
     * @param command The action command, which can be "Font," "Word_Wrap," or "Choose_Color."
     * @param event   The ActionEvent object representing the user's action.
     */
    @Override
    public void handleAction(String command, ActionEvent event) {
        switch (command) {
            case "Font":
                viewer.openFontDialog();
                break;
            case "Word_Wrap":
                makeWordWrap();
                break;
            case "Choose_Color":
                Color color = viewer.openColorChooser();
                viewer.updateTextColor(color);
                break;
        }
    }

    /**
     * Toggles the word wrap setting for the text area.
     */
    private void makeWordWrap() {
        JTextArea textArea = viewer.getCurrentContent();

        if (textArea.getLineWrap()) {
            textArea.setLineWrap(false);
        } else {
            textArea.setLineWrap(true);
        }
    }
}
