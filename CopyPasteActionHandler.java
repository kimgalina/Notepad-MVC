import javax.swing.JTextArea;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class CopyPasteActionHandler implements ActionHandler {
    private Viewer viewer;

    public CopyPasteActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void handleAction(String command, ActionEvent event) {
        switch (command) {
            case "Paste":
                pasteText();
                break;
            case "Copy":
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
