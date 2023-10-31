import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Font;
import java.awt.Container;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 * The HelpController class handles actions related to the Help feature in the Viewer.
 */
public class HelpController implements ActionListener {
    private Viewer viewer;

    /**
    * Constructs a new HelpController associated with the given Viewer.
    *
    * @param viewer The Viewer instance to which this controller is associated.
    */
    public HelpController(Viewer viewer) {
        this.viewer = viewer;
    }


    /**
     * Handles the actionPerformed event for the HelpController.
     *
     * @param event The ActionEvent representing the user's action.
     */
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command == "Ok") {
            viewer.hideHelpDialog();
        }

    }

}
