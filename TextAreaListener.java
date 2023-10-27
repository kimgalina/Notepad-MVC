import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class TextAreaListener implements DocumentListener, CaretListener {

    private JTextArea textArea;
    private JMenu editMenu;

    public TextAreaListener(JTextArea textArea, JMenu editMenu) {
        this.textArea = textArea;
        this.editMenu = editMenu;
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        boolean isTextSelected = e.getDot() != e.getMark();
        updateMenuItems("C", isTextSelected);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        boolean isTextEmpty = textArea.getText().equals("");
        updateMenuItems("Find", !isTextEmpty);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        boolean isTextEmpty = textArea.getText().equals("");
        updateMenuItems("Find", !isTextEmpty);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        boolean isTextEmpty = textArea.getText().equals("");
        updateMenuItems("Find", !isTextEmpty);
    }

    private void updateMenuItems(String menuItemName, boolean enable) {
        for (int i = 0; i < editMenu.getItemCount(); i++) {
            JMenuItem menuItem = editMenu.getItem(i);

            if (menuItem != null) {
                if (menuItem.getText().contains(menuItemName)) {
                    menuItem.setEnabled(enable);
                }
            }
        }
    }
}
