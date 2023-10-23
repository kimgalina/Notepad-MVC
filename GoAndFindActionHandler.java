import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextArea;

public class GoAndFindActionHandler implements ActionHandler {
    private Viewer viewer;

    public GoAndFindActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void handleAction(String command) {
        switch (command) {
            case "Go":
                viewer.openGoDialog();
                break;
            case "Time_And_Date":
                pasteTimeAndDate();
                break;
            case "Find":

                break;
            case "Find more":

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
