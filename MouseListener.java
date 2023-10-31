import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;


/**
 * The MouseListener class is used to handle mouse events for a JButton to change its text on mouse hover.
 */
public class MouseListener extends MouseAdapter {
    private String btnText;

    /**
    * Overrides the mouseEntered method to change the text of the JButton on mouse hover.
    *
    * @param e The MouseEvent representing the mouse enter event.
    */
    @Override
    public void mouseEntered(MouseEvent e) {
        JButton closeBtn = (JButton) e.getSource();
        btnText = closeBtn.getText();
        closeBtn.setText("\u00d7");
    }

    /**
    * Overrides the mouseExited method to restore the original text of the JButton on mouse exit.
    *
    * @param e The MouseEvent representing the mouse exit event.
    */
    @Override
    public void mouseExited(MouseEvent e) {
        JButton closeBtn = (JButton) e.getSource();
        closeBtn.setText(btnText);
    }

}
