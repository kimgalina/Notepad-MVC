import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;

/**
 * The FindDialogController class handles user interactions and text search operations for the Find dialog in a text viewer application.
 */
public class FindDialogController implements ActionListener {
    private Viewer viewer;
    private String searchValue;
    private boolean isNext;
    private boolean isCaseSensitive;
    private int pos;
    private int foundPos;
    private boolean isPrevNext;

   /**
    * Constructs a FindDialogController associated with the provided viewer.
    *
    * @param viewer The viewer to be associated with this FindDialogController.
    */
    public FindDialogController(Viewer viewer) {
        this.viewer = viewer;
        isNext = true;
    }

   /**
    * Handles the user's actions based on the provided ActionEvent.
    *
    * @param event The ActionEvent representing the user's action.
    */
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

    /**
    * Initiates a search operation in the text based on the specified direction.
    *
    * @param isNext Specifies whether the search should be performed in the forward direction.
    */
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

    /**
    * Gets the search value entered by the user.
    *
    * @return The search value as a String.
    */
    public String getSearchValue() {
        return searchValue;
    }

    /**
     * Checks if the search is case-sensitive.
     *
     * @return true if the search is case-sensitive, false otherwise.
     */
    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    /**
    * Checks if the search is in the next direction.
    *
    * @return true if searching forward, false if searching backward.
    */
    public boolean isNext() {
        return isNext;
    }

    /**
    * Updates the current search position based on the search direction and search length.
    *
    * @param isNext       Specifies whether the search should be performed in the forward direction.
    * @param searchLength The length of the search query.
    */
    private void updatePosition(boolean isNext, int searchLength) {
        if (isNext) {
            pos += searchLength;
        } else {
            pos -= searchLength;
        }
    }

    /**
    * Searches for the next occurrence of the specified text in the document.
    *
    * @param search         The text to search for.
    * @param document       The document to search within.
    * @param isCaseSensitive Specifies whether the search should be case-sensitive.
    * @return true if a match is found, false otherwise.
    */
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

    /**
    * Searches for the previous occurrence of the specified text in the document.
    *
    * @param search         The text to search for.
    * @param document       The document to search within.
    * @param isCaseSensitive Specifies whether the search should be case-sensitive.
    * @return true if a match is found, false otherwise.
    */
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

    /**
    * Handles the result of a search operation by selecting the matched text and updating the position.
    *
    * @param isFound      Indicates whether a match was found.
    * @param isNext       Specifies whether the search was in the forward direction.
    * @param textArea     The JTextArea where the search is performed.
    */
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

    /**
    * Checks if the search direction has changed.
    *
    * @param isNext Specifies whether the search is in the forward direction.
    * @return true if the search direction has changed, false otherwise.
    */
    private boolean isDirectionChanged(boolean isNext) {
        return isPrevNext != isNext;
    }
}
