import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * The GoAndFindActionHandler class implements the ActionHandler interface and is responsible for
 * handling actions related to navigating and searching within the application's text content.
 * It provides functionality to open the "Go To" dialog, insert the current time and date, and
 * initiate the find and find next/previous operations.
 */
public class GoAndFindActionHandler implements ActionHandler {
    private Viewer viewer;
    private FindDialogController findController;

    /**
     * Constructor for the GoAndFindActionHandler class.
     *
     * @param viewer        The main viewer component of the application.
     * @param findController The controller for managing find and replace operations.
     */
    public GoAndFindActionHandler(Viewer viewer, FindDialogController findController) {
        this.viewer = viewer;
        this.findController = findController;
    }

    /**
     * Handles various actions based on the provided command.
     *
     * @param command The action command, which can be "Go," "Time_And_Date," "Find," "Find_Next," or "Find_Prev."
     * @param event   The ActionEvent object representing the user's action.
     */
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

    /**
    * Inserts the current time and date into the text content.
    */
    private void pasteTimeAndDate() {
        LocalDateTime currentDate = LocalDateTime.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
        JTextArea textArea = viewer.getCurrentContent();
        textArea.insert(formattedDate, textArea.getCaretPosition());
    }

    /**
     * Initiates the find or find next/previous operation based on the user's input.
     *
     * @param isNext A boolean indicating whether to perform a "Find Next" operation (true) or "Find Previous" operation (false).
     */
    private void find(boolean isNext) {
        if (findController.getSearchValue() == null) {
            viewer.openFindDialog();
        } else {
            findController.find(isNext);
        }
    }
}
