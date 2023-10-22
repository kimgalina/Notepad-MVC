import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class MouseListener extends MouseAdapter {
    private String btnText;

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouse entered");
        JButton closeBtn = (JButton) e.getSource();
        btnText = closeBtn.getText();
        closeBtn.setText("\u00d7");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse exited");
        JButton closeBtn = (JButton) e.getSource();
        closeBtn.setText(btnText);
    }

}
