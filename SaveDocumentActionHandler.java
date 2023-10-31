import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

/**
 * The SaveDocumentActionHandler class implements the ActionHandler interface and is responsible for
 * handling the action of saving a document in the application. It provides functionality for both
 * regular "Save" and "Save As" actions, allowing the user to save the current document to the file system.
 */
public class SaveDocumentActionHandler implements ActionHandler {
    private Viewer viewer;
    private TabsController tabsController;

    /**
     * Constructor for the SaveDocumentActionHandler class.
     *
     * @param viewer          The main viewer component of the application where the document is being saved.
     * @param tabsController  The controller for managing tabs and documents.
     */
    public SaveDocumentActionHandler(Viewer viewer, TabsController tabsController) {
        this.viewer = viewer;
        this.tabsController = tabsController;
    }

    /**
    * Handles the "Save" or "Save As" action based on the provided command.
    *
    * @param command The action command, which can be "Save" or "Save As."
    * @param event   The ActionEvent object representing the user's action.
    */
    @Override
    public void handleAction(String command, ActionEvent event) {
        if ("Save".equals(command)) {
            saveDocument();
        } else {
            saveDocumentAs();
        }
    }

    /**
     * Saves the current document using its existing file path (if available).
     *
     * @return 0 if the save operation is successful, -1 if an error occurs.
     */
    public int saveDocument() {
        int currentTabIndex = viewer.getCurrentTabIndex();
        File currentOpenFile = tabsController.getFilesPerTabs().get(currentTabIndex);

        if (currentOpenFile != null) {
          try {
              String fileName = getFileNameFromPath(currentOpenFile.getAbsolutePath());
              String content = viewer.getCurrentContent().getText();
              Files.write(currentOpenFile.toPath(), content.getBytes("UTF-8"));
              tabsController.setValueInToList(tabsController.getUnsavedChangesPerTab(), currentTabIndex, false);
              viewer.removeDotInTab(currentTabIndex);
              return 0;
          } catch (IOException e) {
              viewer.showError(e.toString());
              return -1;
          }

        } else {
             return saveDocumentAs();
        }
    }

    /**
    * Saves the current document with a new file name or at a different location.
    *
    * @return 0 if the save operation is successful, -1 if an error occurs.
    */
    public int saveDocumentAs() {
        int currentTabIndex = viewer.getCurrentTabIndex();
        String fileName = "";
        File currentOpenFile = tabsController.getFilesPerTabs().get(currentTabIndex);

        if (currentOpenFile == null) {
            fileName = "Untitled.txt";
        } else {
            fileName = getFileNameFromPath(currentOpenFile.getAbsolutePath());
        }

        File selectedFile = viewer.getNewFileSaveLocation(fileName);

        if(selectedFile != null) {
            try {
                Path filePath = selectedFile.toPath();
                Files.write(filePath, viewer.getCurrentContent().getText().getBytes("UTF-8"));
                tabsController.setValueInToList(tabsController.getFilesPerTabs(), currentTabIndex, selectedFile);

                viewer.update(viewer.getCurrentContent().getText(), getFileNameFromPath(selectedFile.getAbsolutePath()), currentTabIndex);

                tabsController.setValueInToList(tabsController.getUnsavedChangesPerTab(), currentTabIndex, false);
                viewer.removeDotInTab(currentTabIndex);
                return 0;
            } catch (IOException e) {
                viewer.showError(e.toString());
                return -1;
            }
        }

        return -1;
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
