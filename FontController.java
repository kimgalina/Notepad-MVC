import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Font;
import java.awt.Container;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FontController implements ActionListener {
    private Viewer viewer;

    public FontController(Viewer viewer) {
        this.viewer = viewer;
    }

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
