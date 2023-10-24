import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

import javax.swing.text.BadLocationException;

public class FindDialogController implements ActionListener {

    private Viewer viewer;
    private JTextField searchField;
    private JRadioButton downButton;
    private JCheckBox caseSensitiveButton;
    private TextFinder textFinder;

    public FindDialogController(Viewer viewer, JTextField searchField, JRadioButton downButton, JCheckBox caseSensitiveButton) {
        this.viewer = viewer;
        this.searchField = searchField;
        this.downButton = downButton;
        this.caseSensitiveButton = caseSensitiveButton;
        textFinder = new TextFinder();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("Find")) {
            find();
        } else if (command.equals("Cancel")) {
            viewer.closeFindDialog();
        }
    }

    private void find() {
        String search = searchField.getText();
        JTextArea textArea = viewer.getCurrentContent();
        boolean isNext = downButton.isSelected();
        boolean isCaseSensitive = caseSensitiveButton.isSelected();
        
        boolean isFound = false;
        try {
            isFound = textFinder.find(search, textArea, isNext, isCaseSensitive);
        } catch (BadLocationException e) {
            viewer.showError("Error");
        }
        if (!isFound) {
            viewer.showError("Text is not found");
        }
    }
}
