import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

import javax.swing.text.Document;
import javax.swing.text.BadLocationException;

public class FindDialogController implements ActionListener {
    private Viewer viewer;
    private int pos;
    private int foundPos;
    private boolean isPrevNext;

    public FindDialogController(Viewer viewer) {
        this.viewer = viewer;
        pos = 0;
        foundPos = 0;
        isPrevNext = false;
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

        int searchLength = search.length();

        if (isDirectionChanged(isNext)) {
            updatePosition(isNext, searchLength);
        }

        boolean isFound = false;
        isFound = isNext ? findNext(search, textArea.getDocument(), isCaseSensitive)
                         : findPrevious(search, textArea.getDocument(), isCaseSensitive);

        handleResult(isFound, isNext, searchLength, textArea);
    }

    private void updatePosition(boolean isNext, int searchLength) {
        if (isNext) {
            pos += searchLength;
        } else {
            pos -= searchLength;
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

    private void handleResult(boolean isFound, boolean isNext, int searchLength, JTextArea textArea) {
        if (isFound) {
            if (isNext) {
                textArea.select(pos, pos + searchLength);
                pos += searchLength;
            } else {
                textArea.select(pos - searchLength, pos);
                pos -= searchLength;
            }

            isPrevNext = isDirectionChanged(isNext) ? isNext : isPrevNext;
            foundPos = pos;
        } else {
            pos = foundPos == 0 ? 0 : foundPos;
            viewer.showError("Text is not found");
        }
    }

    private boolean isDirectionChanged(boolean isNext) {
        return isPrevNext != isNext;
    }
}
