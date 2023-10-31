import javax.swing.JTextArea;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.awt.event.ActionEvent;

/**
 * The EditActionHandler class implements action handling for edit-related operations in a text viewer application.
 */
public class EditActionHandler implements ActionHandler {
    private Viewer viewer;

    /**
    * Constructs an EditActionHandler with the associated viewer.
    *
    * @param viewer The viewer to be associated with this action handler.
    */
    public EditActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    /**
    * Handles an action based on the given command.
    *
    * @param command The command representing the action to be performed.
    * @param event   The ActionEvent associated with the action.
    */
    @Override
    public void handleAction(String command, ActionEvent event) {
        switch (command) {
            case "Paste":
                pasteText();
                break;
            case "Copy":
                copyOrCut(command);
                break;
            case "Cut":
                copyOrCut(command);
                break;
            case "Clear":
                viewer.updateText("");
                break;
            case "Select_All":
                viewer.getCurrentContent().selectAll();
                break;
        }
    }

    /**
     * Copies or cuts the selected text from the JTextArea and stores it in the system clipboard.
     *
     * @param command The command indicating whether to copy or cut the text.
     */
    private void copyOrCut(String command) {
        JTextArea textArea = viewer.getCurrentContent();
        String selectedText = textArea.getSelectedText();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(selectedText);
        clipboard.setContents(stringSelection, null);

        if (command.equals("Cut")) {
            textArea.cut();
        }
    }

    /**
    * Pastes text from the system clipboard into the JTextArea.
    */
    private void pasteText() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = clipboard.getContents(null);

        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                String textToPaste = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                JTextArea textArea = viewer.getCurrentContent();
                int pos = textArea.getCaretPosition();
                textArea.insert(textToPaste, pos);
            } catch (UnsupportedFlavorException | IOException e) {
                viewer.showError(e.getMessage());
            }
        }
    }
}
