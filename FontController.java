import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Font;
import java.awt.Container;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * The FontController class handles font-related actions in the Viewer.
 */
public class FontController implements ActionListener {
    private Viewer viewer;

  /**
   * Constructs a new FontController associated with the given Viewer.
   *
   * @param viewer The Viewer instance to which this controller is associated.
   */
    public FontController(Viewer viewer) {
        this.viewer = viewer;
    }

  /**
   * Handles the actionPerformed event for the FontController.
   *
   * @param event The ActionEvent representing the user's action.
   */
   @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        switch (command) {
            case "Ok":
                processOkClick();
                break;
            case "Cancel":
                viewer.hideFontDialog();
                break;

        }
    }

  /**
   * Processes user clicks on the "Ok" button to set the new font for the TextArea.
   */
    private void processOkClick() {
        try {
            String fontName = getTextField("fontTextField");
            String fontStyle = getTextField("styleTextField");
            Integer fontSize = Integer.parseInt(getTextField("sizeTextField"));
            Font font = new Font(fontName, getStyle(fontStyle), fontSize);
            viewer.setNewFontForTextArea(font);
            viewer.hideFontDialog();
        } catch (NumberFormatException e) {
            viewer.showError("Write correct size!");
        }
    }

   /**
    * Retrieves the text value from a JTextField with the given name.
    *
    * @param name The name of the JTextField to retrieve text from.
    * @return The text content of the specified JTextField.
    */
    private String getTextField(String name) {
        Container contentPane = viewer.getFontDialog().getContentPane();
        Component[] components = contentPane.getComponents();

        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;

                if (textField.getName().equals(name)) {
                    return textField.getText();
                }
            }
        }
        return null;
    }

    /**
    * Maps a font style name to the corresponding Font constant.
    *
    * @param selectedStyle The selected font style name ("Regular," "Italic," "Bold," "Bold Italic").
    * @return The Font constant representing the selected style.
    */
    private int getStyle(String selectedStyle) {
        switch (selectedStyle) {
            case "Regular":
                return Font.PLAIN;
            case "Italic":
                return Font.ITALIC;
            case "Bold":
                return Font.BOLD;
            case "Bold Italic":
                return Font.BOLD | Font.ITALIC;
            default:
                return Font.PLAIN;
      }
  }

}
