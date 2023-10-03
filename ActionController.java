import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ActionController implements ActionListener {

    public ActionController() {

    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        System.out.println("command " + command);
    }
}
