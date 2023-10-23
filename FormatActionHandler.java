import javax.swing.JTextArea;
import java.awt.Color;

public class FormatActionHandler implements ActionHandler {
    private Viewer viewer;

    public FormatActionHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void handleAction(String command) {
        switch (command) {
            case "Font":
                viewer.openFontDialog();
                break;
            case "Word_Wrap":
                makeWordWrap();
                break;
            case "Choose_Color":
                Color color = viewer.openColorChooser();
                viewer.updateTextColor(color);
                break;
        }
    }

    private void makeWordWrap() {
        JTextArea textArea = viewer.getCurrentContent();
        if(textArea.getLineWrap()) {
            textArea.setLineWrap(false);
        } else {
            textArea.setLineWrap(true);
        }
    }
}
