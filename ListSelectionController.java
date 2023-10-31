import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JList;
import javax.swing.JTextField;

import java.awt.Container;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 * The ListSelectionController class handles list selection events for font, style, and size lists in the font selection dialog.
 */
public class ListSelectionController implements ListSelectionListener {
    private Viewer viewer;
    private Font font;

    /**
    * Constructs a ListSelectionController with the specified viewer.
    *
    * @param viewer The viewer associated with the font selection dialog.
    */
    public ListSelectionController(Viewer viewer) {
        this.viewer = viewer;
        font = viewer.getCurrentContent().getFont();
    }

  /**
   * Handles list selection events.
   *
   * @param e The ListSelectionEvent representing the list selection event.
   */
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            JList<?> sourceList = (JList<?>) e.getSource();
            String listName = sourceList.getName();

            switch (listName) {
                case "FontsList":
                    String selectedFont = (String) sourceList.getSelectedValue();
                    updateTextField(selectedFont, "fontTextField");
                    font = new Font(selectedFont, font.getStyle(), font.getSize());
                    getSampleLable().setFont(font);
                    break;
                case "StylesList":
                    String selectedStyle = (String) sourceList.getSelectedValue();
                    updateTextField(selectedStyle, "styleTextField");
                    font = new Font(font.getFontName(), getStyle(selectedStyle), font.getSize());
                    getSampleLable().setFont(font);
                    break;
                case "SizesList":
                    Integer selectedSize = (Integer) sourceList.getSelectedValue();
                    updateTextField(String.valueOf(selectedSize), "sizeTextField");
                    font = new Font(font.getFontName(), font.getStyle(), selectedSize);
                    getSampleLable().setFont(font);
                    break;
            }
        }
    }

   /**
    * Updates the text in a JTextField component with the specified value.
    *
    * @param value          The value to set in the JTextField.
    * @param textFieldName  The name of the JTextField to update.
    */
    private void updateTextField(String value, String textFieldName) {
        JTextField textField = getTextField(textFieldName);
        textField.setText(value);
    }

    /**
    * Retrieves a JTextField component with the specified name from the font dialog's content pane.
    *
    * @param name  The name of the JTextField to retrieve.
    * @return      The JTextField component if found; otherwise, returns null.
    */
    private JTextField getTextField(String name) {
        Container contentPane = viewer.getFontDialog().getContentPane();
        Component[] components = contentPane.getComponents();

        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;

                if (textField.getName().equals(name)) {
                    return textField;
                }
            }
        }
        return null;
    }

    /**
    * Retrieves the sample JLabel component from the font dialog's content pane.
    *
    * @return  The sample JLabel component if found; otherwise, returns null.
    */
    private JLabel getSampleLable() {
      Container contentPane = viewer.getFontDialog().getContentPane();
      Component[] components = contentPane.getComponents();
      JPanel panel = null;

      for (Component component : components) {
          if (component instanceof JPanel) {
              if("samplePanel".equals(component.getName())){
                  panel = (JPanel) component;
                  break;
              }
          }
      }

      for(Component component : panel.getComponents()) {
          if (component instanceof JLabel) {
              return (JLabel) component;
          }
      }

      return null;
    }

    /**
    * Converts a selected font style string to a Font style constant.
    *
    * @param selectedStyle  The selected font style as a string.
    * @return              The corresponding Font style constant (Font.PLAIN, Font.ITALIC, Font.BOLD, or Font.BOLD | Font.ITALIC).
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
