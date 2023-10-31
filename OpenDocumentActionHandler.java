import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.nio.charset.StandardCharsets;
import javax.swing.JTabbedPane;
import java.nio.charset.UnmappableCharacterException;
import java.io.IOException;
import java.awt.event.ActionEvent;

/**
 * The OpenDocumentActionHandler class implements the ActionHandler interface and is responsible for
 * handling the action of opening an existing document in the application. When this action is triggered,
 * it opens the selected document, loads its content, and displays it in a new tab in the Viewer.
 */
public class OpenDocumentActionHandler implements ActionHandler {
    private Viewer viewer;
    private TabsController tabsController;

    /**
     * Constructor for the OpenDocumentActionHandler class.
     *
     * @param viewer          The main viewer component of the application where the document will be displayed.
     * @param tabsController  The controller for managing tabs and documents.
     */
    public OpenDocumentActionHandler(Viewer viewer, TabsController tabsController) {
        this.viewer = viewer;
        this.tabsController = tabsController;
    }

    /**
     * Handles the action of opening an existing document.
     *
     * @param command The action command associated with the "Open Document" action (e.g., "Open_Document").
     * @param event   The ActionEvent object representing the user's action.
     */
    @Override
    public void handleAction(String command, ActionEvent event) {
        File file = viewer.getFile();

        if(file != null) {
            JTabbedPane tabPane = viewer.getTabPane();
            int newTabIndex = viewer.createNewTab();
            tabsController.setValueInToList(tabsController.getFilesPerTabs(), newTabIndex, file);

            String filePath = file.getAbsolutePath();
            String contentText = readFile(filePath);
            String fileName = getFileNameFromPath(filePath);

            tabsController.setIsFileOpening(true);
            viewer.update(contentText, fileName, newTabIndex);
            tabsController.setValueInToList(tabsController.getUnsavedChangesPerTab(), newTabIndex, false);
            tabsController.setIsFileOpening(false);
        }
    }

    /**
     * Reads the content of a file given its path.
     *
     * @param filePath The path to the file to be read.
     * @return The content of the file as a string.
     */
    private String readFile(String filePath) {
        StringBuilder fileContent = new StringBuilder();

        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            for (String line : lines) {
                fileContent.append(line).append("\n");
                line = null;
            }
        } catch (UnmappableCharacterException e) {
            viewer.showError(e.toString());
            System.out.println("Can't encode this type of symbols");
        } catch (IOException e) {
            viewer.showError(e.toString());
        }

        return fileContent.toString();
    }

    /**
     * Reads the content of the currently open file in the active tab.
     *
     * @return The content of the currently open file as a string.
     */
    public String readCurrentFile() {
        int tabIndex = viewer.getCurrentTabIndex();
        File currentOpenFile = tabsController.getFilesPerTabs().get(tabIndex);
        return readFile(currentOpenFile.getAbsolutePath());
    }

    /**
     * Extracts the file name from a given file path.
     *
     * @param path The file path from which the file name should be extracted.
     * @return The file name extracted from the path.
     */
    private String getFileNameFromPath(String path) {
        String[] directories = path.split("\\\\");
        return directories[directories.length - 1];
    }
}
