import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.awt.Desktop;
import java.net.URI;


public class HelpMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Desktop.isDesktopSupported()) {
            try {
                openWebPage("https://labs.o.kg:3443/kimgalina/notepad-mvc-pattern-template-method-design-pattern/-/tree/dev?ref_type=heads");
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void openWebPage(String url) throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URI(url));
    }

}
