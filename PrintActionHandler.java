import java.awt.Font;
import java.util.List;
import java.awt.event.ActionEvent;

public class PrintActionHandler implements ActionHandler {
    private Viewer viewer;
    private List<String> data;

    public PrintActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void handleAction(String command, ActionEvent event) {
        Font font = viewer.getCurrentTextAreaFont();
        try {
            String textPageNumber = "Page ";
            data = viewer.getListTextFromTextAreaContent();

            Print document = new Print(data, font, textPageNumber);
            document.printDocument();
            if (document.isPrinted()) {
                viewer.showDialogFinishPrintDocument();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            data.clear();
            data = null;
        }
    }
}
