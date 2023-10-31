import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

/**
 * The CaretController class implements a CaretListener for a JTextArea, allowing
 * it to track and update the current caret position and display the line and
 * column numbers in a viewer.
 */
public class CaretController implements CaretListener {
    private final Viewer viewer;

    /**
    * Constructs a CaretController associated with a Viewer.
    *
    * @param viewer The Viewer instance to update with caret position information.
    */
    public CaretController(Viewer viewer) {
        this.viewer = viewer;
    }

    /**
    * Called when the caret position changes. Updates the current line and
    * column based on the caret position and notifies the associated Viewer.
    *
    * @param e The CaretEvent containing information about the caret position.
    */
    @Override
    public void caretUpdate(CaretEvent e) {
        int line = 0;
        int column = 0;

        JTextArea textArea = (JTextArea) e.getSource();

        try {
            line = textArea.getLineOfOffset(textArea.getCaretPosition());
            column = (textArea.getCaretPosition() - textArea.getLineStartOffset(line));
            line = line + 1;
            column = column + 1;
        } catch (BadLocationException exp) {
            System.err.println(exp.getMessage());
        }

        viewer.setLabelByTextAreaLines(line, column);
    }
}
