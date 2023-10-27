import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

public class CaretController implements CaretListener {
    private final Viewer viewer;

    public CaretController(Viewer viewer) {
        this.viewer = viewer;
    }

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
