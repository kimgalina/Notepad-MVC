import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.awt.Desktop;
import java.net.URI;

/**
 * The HelpMouseListener class is responsible for handling mouse click events on a help link.
 */
public class HelpMouseListener extends MouseAdapter {

  /**
   * Handles the mouseClicked event when the user clicks on the help link.
   *
   * @param e The MouseEvent representing the mouse click event.
   */
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

    /**
    * Opens the specified web page in the default web browser.
    *
    * @param url The URL of the web page to open.
    * @throws IOException       If an I/O error occurs while opening the web page.
    * @throws URISyntaxException If the URL is not formatted properly.
    */
    private void openWebPage(String url) throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URI(url));
    }

}
