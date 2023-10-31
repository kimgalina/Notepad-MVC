import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * This class serves as a listener for a JTextArea component and a JMenu to enable or disable specific menu items based on the text area's content and selection.
 */
public class TextAreaListener implements DocumentListener, CaretListener {

    private JTextArea textArea;
    private JMenu editMenu;

  /**
   * Constructor for TextAreaListener.
   *
   * @param textArea The JTextArea component to be monitored.
   * @param editMenu The JMenu associated with the menu items.
   */
    public TextAreaListener(JTextArea textArea, JMenu editMenu) {
        this.textArea = textArea;
        this.editMenu = editMenu;
    }

    /**
     * Called when the text caret position in the JTextArea changes. It updates specific menu items based on whether text is selected.
     *
     * @param e The CaretEvent indicating the change in the caret position.
     */
    @Override
    public void caretUpdate(CaretEvent e) {
        boolean isTextSelected = e.getDot() != e.getMark();
        updateMenuItems("C", isTextSelected);
    }

    /**
    * Called when text is inserted into the document. It updates the "Find" menu item based on whether the text area is empty.
    *
    * @param e The DocumentEvent indicating text insertion.
    */
    @Override
    public void insertUpdate(DocumentEvent e) {
        boolean isTextEmpty = textArea.getText().equals("");
        updateMenuItems("Find", !isTextEmpty);
    }

    /**
    * Called when text is removed from the document. It updates the "Find" menu item based on whether the text area is empty.
    *
    * @param e The DocumentEvent indicating text removal.
    */
    @Override
    public void removeUpdate(DocumentEvent e) {
        boolean isTextEmpty = textArea.getText().equals("");
        updateMenuItems("Find", !isTextEmpty);
    }

    /**
    * Called when changes occur in the document. It updates the "Find" menu item based on whether the text area is empty.
    *
    * @param e The DocumentEvent indicating document changes.
    */
    @Override
    public void changedUpdate(DocumentEvent e) {
        boolean isTextEmpty = textArea.getText().equals("");
        updateMenuItems("Find", !isTextEmpty);
    }

  /**
   * Helper method to enable or disable specific menu items within the associated JMenu.
   *
   * @param menuItemName The name or identifier of the menu item to enable or disable.
   * @param enable       A boolean flag to enable (true) or disable (false) the menu item.
   */
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
