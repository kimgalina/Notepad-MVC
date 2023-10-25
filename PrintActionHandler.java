import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class PrintActionHandler implements ActionHandler {
    private Viewer viewer;

    public PrintActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void handleAction(String command, ActionEvent event) {
        String data = "";
        try {
            Font font = viewer.getCurrentTextAreaFont();
            data = viewer.getCurrentTextAreaContent();
            Color textColor = viewer.getCurrentTextAreaColor();
            Print document = new Print(data, font, textColor);
            document.printDocument();

            if (document.isPrinted()) {
                viewer.showDialogFinishPrintDocument();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            data = null;
        }
    }
}
