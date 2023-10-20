import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Font;
import java.awt.Container;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;

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
                viewer.hideFontDialog();
                break;
            case "Cancel":
                viewer.hideFontDialog();
                break;

        }
    }

    private void processOkClick() {
        Container contentPane = viewer.getFontDialog().getContentPane();
        Component[] components = contentPane.getComponents();
        JPanel panel = null;

        for (Component component : components) {
            if (component instanceof JPanel) {
                panel = (JPanel) component;
                break;
            }
        }

        for(Component component : panel.getComponents()) {
            if (component instanceof JLabel) {
                Font font = component.getFont();
                viewer.getCurrentContent().setFont(font);
                break;
            }
        }
    }

}
