import javax.swing.JTextArea;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;

public class TextFinder {

    private int pos = 0;
    private int foundPos = 0;

    public boolean find(String search, JTextArea textArea, boolean isNext, boolean isCaseSensitive) throws BadLocationException {
        textArea.requestFocus();

        Document document = textArea.getDocument();
        search = isCaseSensitive ? search : search.toLowerCase();

        boolean isFound = false;
        isFound = isNext ? findNext(search, document, isCaseSensitive)
                         : findPrevious(search, document, isCaseSensitive);

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
        }
        System.out.println(pos);
        return isFound;
    }

    private boolean findNext(String search, Document document, boolean isCaseSensitive) throws BadLocationException {
        int searchLength = search.length();
        boolean isFound = false;

        while (pos + searchLength <= document.getLength()) {
            String match = document.getText(pos, searchLength);
            match = isCaseSensitive ? match : match.toLowerCase();
            if (match.equals(search)) {
                isFound = true;
                break;
            }
            pos++;
        }
        return isFound;
    }

    private boolean findPrevious(String search, Document document, boolean isCaseSensitive) throws BadLocationException {
        int searchLength = search.length();
        boolean isFound = false;

        while (pos - searchLength >= 0) {
            String match = document.getText(pos - searchLength, searchLength);
            match = isCaseSensitive ? match : match.toLowerCase();
            if (match.equals(search)) {
                isFound = true;
                break;
            }
            pos--;
        }
        return isFound;
    }
}
