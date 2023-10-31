import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JTextArea;

import javax.swing.text.BadLocationException;

/**
 * The GoDialogController class handles actions related to the Go To Line feature in the Viewer.
 */
public class GoDialogController implements ActionListener {
    private Viewer viewer;
    private JTextField textField;

    /**
    * Constructs a new GoDialogController associated with the given Viewer and JTextField.
    *
    * @param viewer   The Viewer instance to which this controller is associated.
    * @param textField The JTextField used to input the target line number.
    */
    public GoDialogController(Viewer viewer, JTextField textField) {
        this.viewer = viewer;
        this.textField = textField;
    }


    /**
     * Handles the actionPerformed event for the GoDialogController.
     *
     * @param event The ActionEvent representing the user's action.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("Go")) {
            goToLine();
        } else if (command.equals("Cancel")) {
            viewer.closeGoDialog();
        }
    }

    /**
    * Processes the user's request to navigate to a specific line in the text content.
    * Retrieves the line number from the JTextField and moves the caret to the beginning of that line.
    */
    private void goToLine() {
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
