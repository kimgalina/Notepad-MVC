import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextArea;

public class GoAndFindActionHandler implements ActionHandler {

    private Viewer viewer;
    private FindDialogController findController;

    public GoAndFindActionHandler(Viewer viewer, FindDialogController findController) {
        this.viewer = viewer;
        this.findController = findController;
    }

    @Override
    public void handleAction(String command, ActionEvent event) {
        switch (command) {
            case "Go":
                viewer.openGoDialog();
                break;
            case "Time_And_Date":
                pasteTimeAndDate();
                break;
            case "Find":
                viewer.openFindDialog();
                break;
            case "Find_Next":
                findController.find(true);
                break;
            case "Find_Prev":
                findController.find(false);
                break;
        }
    }

    private void pasteTimeAndDate() {
        LocalDateTime currentDate = LocalDateTime.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
        JTextArea textArea = viewer.getCurrentContent();
        textArea.insert(formattedDate, textArea.getCaretPosition());
    }
}
