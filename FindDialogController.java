import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.Document;
import javax.swing.text.BadLocationException;

public class FindDialogController implements ActionListener {

    private Viewer viewer;
    private JTextField searchField;
    private JRadioButton upButton;
    private JRadioButton downButton;
    private int currentPosition = 0;

    public FindDialogController(Viewer viewer, JTextField searchField, JRadioButton upButton, JRadioButton downButton) {
        this.viewer = viewer;
        this.searchField = searchField;
        this.upButton = upButton;
        this.downButton = downButton;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("Find")) {
            find(downButton.isSelected());

        } else if (command.equals("Cancel")) {
            viewer.closeFindDialog();
        }
    }

    private void find(boolean isForward) {
        JTextArea textArea = viewer.getCurrentContent();
        String searchText = searchField.getText();
        Document document = textArea.getDocument();
        int textLength = document.getLength();
        String text;
        try {
            text = document.getText(0, textLength);
        } catch (BadLocationException e) {
            e.printStackTrace();
            return;
        }

        Pattern pattern = Pattern.compile(Pattern.quote(searchText));
        Matcher matcher = pattern.matcher(text);

        int start = currentPosition;
        int end = currentPosition;

        if (isForward) {
            if (matcher.find(currentPosition)) {
                start = matcher.start();
                end = matcher.end();
            }
        } else {
            if (currentPosition == 0) {
                currentPosition = textLength;
            }
            while (matcher.find()) {
                int tempStart = matcher.start();
                if (tempStart >= currentPosition) {
                    break;
                }
                start = tempStart;
                end = matcher.end();
            }
        }

        if (start != end) {
            textArea.setSelectionStart(start);
            textArea.setSelectionEnd(end);
            currentPosition = isForward ? end : start;
        } else {
            viewer.showError("Text not found");
        }
    }

}
