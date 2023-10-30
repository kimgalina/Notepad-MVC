import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
                find(true);
                break;
            case "Find_Prev":
                find(false);
                break;
        }
    }

    private void pasteTimeAndDate() {
        LocalDateTime currentDate = LocalDateTime.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
        JTextArea textArea = viewer.getCurrentContent();
        textArea.insert(formattedDate, textArea.getCaretPosition());
    }

    private void find(boolean isNext) {
        if (findController.getSearchValue() == null) {
            viewer.openFindDialog();
        } else {
            findController.find(isNext);
        }
    }
}
