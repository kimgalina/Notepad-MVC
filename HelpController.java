import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Font;
import java.awt.Container;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class HelpController implements ActionListener {
    private Viewer viewer;

    public HelpController(Viewer viewer) {
        this.viewer = viewer;
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command == "Ok") {
            viewer.hideHelpDialog();
        }

    }

}
