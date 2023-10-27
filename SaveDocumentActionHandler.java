import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class SaveDocumentActionHandler implements ActionHandler {
    private Viewer viewer;
    private TabsController tabsController;

    public SaveDocumentActionHandler(Viewer viewer, TabsController tabsController) {
        this.viewer = viewer;
        this.tabsController = tabsController;
    }

    @Override
    public void handleAction(String command, ActionEvent event) {
        if ("Save".equals(command)) {
            saveDocument();
        } else {
            saveDocumentAs();
        }
    }

    public int saveDocument() {
        int currentTabIndex = viewer.getCurrentTabIndex();
        File currentOpenFile = tabsController.getFilesPerTabs().get(currentTabIndex);

        if (currentOpenFile != null) {
          try {
              String fileName = getFileNameFromPath(currentOpenFile.getAbsolutePath());
              String content = viewer.getCurrentContent().getText();
              Files.write(currentOpenFile.toPath(), content.getBytes("UTF-8"));
              tabsController.setValueInToList(tabsController.getUnsavedChangesPerTab(), currentTabIndex, false);
              deleteDotInTab(currentTabIndex);
              return 0;
          } catch (IOException e) {
              viewer.showError(e.toString());
              return -1;
          }
        } else {
             return saveDocumentAs();
        }
    }

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
                deleteDotInTab(currentTabIndex);
                return 0;
            } catch (IOException e) {
                viewer.showError(e.toString());
                return -1;
            }
        }

        return -1;
    }

    private String getFileNameFromPath(String path) {
        String[] directories = path.split("\\\\");
        return directories[directories.length - 1];
    }
    
    private void deleteDotInTab(int currentTabIndex) {
        JButton closeBtn = viewer.getCloseBtnFromTab(currentTabIndex);
        closeBtn.setText("\u00d7");
    }
}
