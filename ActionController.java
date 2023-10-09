import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.io.File;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

public class ActionController implements ActionListener {

    private Viewer viewer;
    private String contentText;

    public ActionController(Viewer viewer) {
        this.viewer = viewer;
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if(command.equals("Open_Document")){
            File file = viewer.getFile();
            String fileName = "fileName";//////
            contentText = readFile(file);
            viewer.update(contentText,fileName);
        } else if (command.equals("Choose_Color")) {
            openColorChooser();
        }else if(command.equals("New_Document")){

            System.out.println(command);
        }
        else if(command.equals("Save")){
            System.out.println(command);
        }
        else if(command.equals("Save_As")){
            System.out.println(command);
        }
        else if(command.equals("Print")){
            System.out.println(command);

        }else if(command.equals("Exit")){
            System.out.println(command);

        }else if(command.equals("Cut")){
            System.out.println(command);

        }else if(command.equals("Copy")){
            System.out.println(command);

        }else if(command.equals("Paste")){
            System.out.println(command);

        }else if(command.equals("Clear")){
            System.out.println(command);

        }else if(command.equals("Find")){
            System.out.println(command);

        }else if(command.equals("Find more")){
            System.out.println(command);

        }else if(command.equals("Go")){
            System.out.println(command);

        }else if(command.equals("Select all")){
            System.out.println(command);

        }else if(command.equals("Time and  date")){
            System.out.println(command);

        }else if(command.equals("Word_Space")){
            System.out.println(command);

        }else if(command.equals("Format")){
            System.out.println(command);

        }else if(command.equals("Status_Space")){
            System.out.println(command);

        }else if(command.equals("View_Help")){
            System.out.println(command);

        }else if(command.equals("About")){
            System.out.println(command);

        }
    }

    private void openColorChooser() {
        Color color = JColorChooser.showDialog(new JFrame(), "Chooser", Color.BLACK);
        viewer.updateTextColor(color);
    }

    private String readFile(File fileName){
        //using nio to read the file and form content of the entire file to a string
        String content = "I love Java";
        return content;
    }
}
