import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class IntegerFilter extends DocumentFilter {
    private Viewer viewer;

    public IntegerFilter(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        if (isDigit(text)) {
            super.insertString(fb, offset, text, attr);
        } else {
            viewer.showError("The string can't be entered");
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (isDigit(text)) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            viewer.showError("The string can't be entered");
        }
    }

    private boolean isDigit(String number) {
        return number.matches("\\d+");
    }
}
