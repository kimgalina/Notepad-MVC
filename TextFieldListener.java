import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.JTextField;
import javax.swing.JButton;

public class TextFieldListener implements DocumentListener {

    private JTextField textField;
    private JButton button;

    public TextFieldListener(JTextField textField, JButton button) {
        this.textField = textField;
        this.button = button;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        enableButton();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        enableButton();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        enableButton();
    }

    private void enableButton() {
        String value = textField.getText();
        button.setEnabled(!value.isEmpty());
    }

}
