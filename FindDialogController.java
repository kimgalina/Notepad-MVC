import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;

public class FindDialogController implements ActionListener {
    private Viewer viewer;
    private String searchValue;
    private boolean isNext;
    private boolean isCaseSensitive;
    private int pos;
    private int foundPos;
    private boolean isPrevNext;

    public FindDialogController(Viewer viewer) {
        this.viewer = viewer;
        isNext = true;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("Find")) {
            searchValue = viewer.getSearchField().getText();
            isCaseSensitive = viewer.getCaseSensitiveButton().isSelected();
            isNext = viewer.getDownButton().isSelected();
            find(isNext);

        } else if (command.equals("Cancel")) {
            viewer.closeFindDialog();
        }
    }

    public void find(boolean isNext) {
        JTextArea textArea = viewer.getCurrentContent();
        textArea.requestFocus();

        String search = isCaseSensitive ? searchValue : searchValue.toLowerCase();

        if (isDirectionChanged(isNext)) {
            updatePosition(isNext, search.length());
        }

        boolean isFound = false;
        isFound = isNext ? findNext(search, textArea.getDocument(), isCaseSensitive)
                         : findPrevious(search, textArea.getDocument(), isCaseSensitive);

        handleResult(isFound, isNext, textArea);
    }

    public String getSearchValue() {
        return searchValue;
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public boolean isNext() {
        return isNext;
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

    private void handleResult(boolean isFound, boolean isNext, JTextArea textArea) {
        int searchLength = searchValue.length();
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
            viewer.showError("Text \"" + searchValue + "\" is not found");
        }
    }

    private boolean isDirectionChanged(boolean isNext) {
        return isPrevNext != isNext;
    }
}
