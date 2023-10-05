import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class ActionController implements ActionListener {

    private Viewer viewer;
    private String contentText;

    public ActionController(Viewer viewer) {
            this.viewer = viewer;
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        System.out.println("command " + command);

        if(command.equals("Open_Document")){
            File file = viewer.getFile();
            contentText = readFile(file);
            viewer.update(contentText);
        }
    }

    private String readFile(File fileName){
        return "I love you";
    }
}
