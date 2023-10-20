import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JList;
import javax.swing.JTextField;

import java.awt.Container;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class ListSelectionController implements ListSelectionListener {
    private Viewer viewer;
    private Font font;

    public ListSelectionController(Viewer viewer) {
        this.viewer = viewer;
        font = viewer.getCurrentContent().getFont();
    }

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

    private void updateTextField(String value, String textFieldName) {
        JTextField textField = getTextField(textFieldName);
        textField.setText(value);
    }

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

    private JLabel getSampleLable() {
      Container contentPane = viewer.getFontDialog().getContentPane();
      Component[] components = contentPane.getComponents();
      JPanel panel = null;

      for (Component component : components) {
          if (component instanceof JPanel) {
              panel = (JPanel) component;
          }
      }

      for(Component component : panel.getComponents()) {
          if (component instanceof JLabel) {
              return (JLabel) component;
          }
      }

      return null;
    }

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
