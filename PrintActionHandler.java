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
        Font font = viewer.getCurrentTextAreaFont();
        String data = viewer.getCurrentTextAreaContent();
        Color textColor = viewer.getCurrentTextAreaColor();
        Print document = new Print(data, font, textColor);
        document.printDocument();
    }
}
