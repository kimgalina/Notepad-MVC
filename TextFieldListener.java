import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * This class serves as a listener for a JTextField and a JButton. It enables or disables the JButton based on changes in the text field's content.
 */
public class TextFieldListener implements DocumentListener {
    private JTextField textField;
    private JButton button;

    /**
     * Constructor for TextFieldListener.
     *
     * @param textField The JTextField component to be monitored.
     * @param button    The JButton to be enabled or disabled based on text field content.
     */
    public TextFieldListener(JTextField textField, JButton button) {
        this.textField = textField;
        this.button = button;
    }

   /**
    * Called when text is inserted into the document. It enables the associated JButton.
    *
    * @param e The DocumentEvent indicating text insertion.
    */
    @Override
    public void insertUpdate(DocumentEvent e) {
        enableButton();
    }

   /**
    * Called when text is removed from the document. It enables the associated JButton.
    *
    * @param e The DocumentEvent indicating text removal.
    */
    @Override
    public void removeUpdate(DocumentEvent e) {
        enableButton();
    }

  /**
   * Called when changes occur in the document. It enables the associated JButton.
   *
   * @param e The DocumentEvent indicating document changes.
   */
    @Override
    public void changedUpdate(DocumentEvent e) {
        enableButton();
    }

    /**
     * Enables or disables the associated JButton based on the content of the JTextField.
     */
    private void enableButton() {
        String value = textField.getText();
        button.setEnabled(!value.isEmpty());
    }
}
