import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 * The IntegerFilter class is used to filter input in a text component to allow only integer values.
 */
public class IntegerFilter extends DocumentFilter {
    private Viewer viewer;


    /**
     * Constructs an IntegerFilter with a reference to the viewer.
     *
     * @param viewer The viewer instance to handle error messages.
     */
    public IntegerFilter(Viewer viewer) {
        this.viewer = viewer;
    }

    /**
     * Overrides the insertString method to filter and insert text only if it consists of digits.
     *
     * @param fb     The FilterBypass used for insertion.
     * @param offset The offset where the insertion should occur.
     * @param text   The text to insert.
     * @param attr   The AttributeSet.
     * @throws BadLocationException If the insertion location is invalid.
     */
    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        if (isDigit(text)) {
            super.insertString(fb, offset, text, attr);
        } else {
            viewer.showError("The string can't be entered");
        }
    }

    /**
     * Overrides the replace method to filter and replace text only if it consists of digits.
     *
     * @param fb     The FilterBypass used for replacement.
     * @param offset The offset where the replacement should occur.
     * @param length The length of text to replace.
     * @param text   The text to replace with.
     * @param attrs  The AttributeSet.
     * @throws BadLocationException If the replacement location is invalid.
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (isDigit(text)) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            viewer.showError("The string can't be entered");
        }
    }

    /**
     * Checks whether the provided string consists of digits.
     *
     * @param number The string to check.
     * @return True if the string consists of digits, otherwise false.
     */
    private boolean isDigit(String number) {
        return number.matches("\\d+");
    }
}
