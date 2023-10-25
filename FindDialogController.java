import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

import javax.swing.text.Document;
import javax.swing.text.BadLocationException;

public class FindDialogController implements ActionListener {

    private Viewer viewer;
    private int pos = 0;
    private int foundPos = 0;

    public FindDialogController(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("Find")) {
            find(viewer.getDownButton().isSelected());
        } else if (command.equals("Cancel")) {
            viewer.closeFindDialog();
        }
    }

    public void find(boolean isNext) {
        String search = viewer.getSearchField().getText();
        JTextArea textArea = viewer.getCurrentContent();
        textArea.requestFocus();

        boolean isCaseSensitive = viewer.getCaseSensitiveButton().isSelected();
        search = isCaseSensitive ? search : search.toLowerCase();

        boolean isFound = false;
        isFound = isNext ? findNext(search, textArea.getDocument(), isCaseSensitive)
                         : findPrevious(search, textArea.getDocument(), isCaseSensitive);

        if (isFound) {
            if (isNext) {
                textArea.select(pos, pos + search.length());
                pos += search.length();
            } else {
                textArea.select(pos - search.length(), pos);
                pos -= search.length();
            }
            foundPos = pos;
        } else {
            pos = foundPos == 0 ? 0 : foundPos;
            viewer.showError("Text is not found");
        }
    }

    private boolean findNext(String search, Document document, boolean isCaseSensitive) {
        int searchLength = search.length();
        boolean isFound = false;

        try {
            while (pos + searchLength <= document.getLength()) {
                String match = document.getText(pos, searchLength);
                match = isCaseSensitive ? match : match.toLowerCase();
                if (match.equals(search)) {
                    isFound = true;
                    break;
                }
                pos++;
            }
        } catch (BadLocationException e) {
            viewer.showError("Error");
        }
        return isFound;
    }

    private boolean findPrevious(String search, Document document, boolean isCaseSensitive) {
        int searchLength = search.length();
        boolean isFound = false;
        try {
            while (pos - searchLength >= 0) {
                String match = document.getText(pos - searchLength, searchLength);
                match = isCaseSensitive ? match : match.toLowerCase();
                if (match.equals(search)) {
                    isFound = true;
                    break;
                }
                pos--;
            }
        } catch (BadLocationException e) {
            viewer.showError("Error");
        }
        return isFound;
    }
}
