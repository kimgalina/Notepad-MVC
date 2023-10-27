import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JTextArea;

import javax.swing.text.BadLocationException;

public class GoDialogController implements ActionListener {
    private Viewer viewer;
    private JTextField textField;

    public GoDialogController(Viewer viewer, JTextField textField) {
        this.viewer = viewer;
        this.textField = textField;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("Go")) {
            goToTheLine();
        } else if (command.equals("Cancel")) {
            viewer.closeGoDialog();
        }
    }

    private void goToTheLine() {
        JTextArea textArea = viewer.getCurrentContent();

        try {
            int line = Integer.parseInt(textField.getText());
            int lineStartOffset = textArea.getLineStartOffset(line - 1);
            viewer.closeGoDialog();
            textArea.setCaretPosition(lineStartOffset);
            textArea.requestFocusInWindow();
        } catch (BadLocationException | NumberFormatException e) {
            viewer.showError("Line number exceeds total number of lines");
        }
    }
}
