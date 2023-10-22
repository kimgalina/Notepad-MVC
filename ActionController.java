import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Files;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.nio.charset.UnmappableCharacterException;
import java.nio.charset.Charset;



public class ActionController implements ActionListener {
    private Viewer viewer;
    private TabsController tabsController;
    private String contentText;


    public ActionController(Viewer viewer, TabsController tabsController) {
        this.viewer = viewer;
        this.tabsController = tabsController;
    }



    @Override
    public void actionPerformed(ActionEvent event) {
        viewer.setCurrentContent();
        viewer.setViewItemZoomIn(viewer.canZoomIn());
        viewer.setViewItemZoomOut(viewer.canZoomOut());
        viewer.setStatusPanelToVisible(viewer.getStatusBarBox().isSelected());

        String command = event.getActionCommand();
        if(map.containsKey(command)){
            map.get(command).actionPerformed();
        }

        if (command.equals("New_Document")) {
            viewer.createNewTab();

        } else if (command.equals("Open_Document")) {
            openDocument();

        } else if (command.equals("Save")) {
            saveDocument();

        } else if(command.equals("Save_As")) {
            saveDocumentAs();

        } else if(command.equals("Print")) {
            Font font = viewer.getCurrentTextAreaFont();
            String data = viewer.getCurrentTextAreaContent();
            Print document = new Print(data, font);
            document.printDocument();

        } else if(command.equals("Exit")) {
            exitProgram();

        } else if(command.equals("Cut")) {
            copyOrCutText(command);

        } else if(command.equals("Copy")) {
            copyOrCutText(command);

        } else if(command.equals("Paste")) {
            pasteText();

        } else if(command.equals("Clear")) {
            viewer.updateText("");

        } else if(command.equals("Find")) {
            System.out.println(command);

        } else if(command.equals("Find more")) {
            System.out.println(command);

        } else if(command.equals("Go")) {
            viewer.openGoDialog();

        } else if(command.equals("Select_All")) {
            viewer.getCurrentContent().selectAll();

        } else if(command.equals("Time_And_Date")) {
            pasteTimeAndDate();

        } else if(command.equals("Word_Wrap")) {
            makeWordWrap();

        } else if(command.equals("Font")) {
            viewer.openFontDialog();

        } else if(command.equals("ZOOM_IN")) {
            viewer.zoomIn();

        } else if(command.equals("ZOOM_OUT")) {
            viewer.zoomOut();

        } else if(command.equals("ZOOM_DEFAULT")) {
            viewer.zoomDefault();

        } else if(command.equals("View_Help")) {
            viewer.getMessageAbout();

        } else if(command.equals("About")) {
            viewer.getMessageAbout();

        } else if (command.equals("Choose_Color")) {
            Color color = viewer.openColorChooser();
            viewer.updateTextColor(color);

        } else if (command.equals("CloseTab")) {
            viewer.closeCurrentTab();
        }
    }



    public boolean hasUnsavedChanges(int tabIndex) {
        Boolean hasChanges = tabsController.getUnsavedChangesPerTab().get(tabIndex);
        if(hasChanges != null){
            System.out.println("hasChanges = " + hasChanges);
            return hasChanges;
        }
        return false;
    }



    public void exitProgram() {
        JTabbedPane tabPane = viewer.getTabPane();
        int tabCount = tabPane.getTabCount();
        for(int i = 0; i < tabCount; i++) {
            int currentTabIndex = viewer.getCurrentTabIndex();

            if(hasUnsavedChanges(currentTabIndex)) {
                int result = viewer.showCloseTabMessage(currentTabIndex);

                if(result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION || result == -1) {
                    return;
                }
            } else {
                viewer.deleteTab(currentTabIndex);

            }
        }
        System.exit(0);
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
              return 0;
          } catch (IOException e) {
              viewer.showError(e.toString());
              return -1;
          }
        } else {
             return saveDocumentAs();
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

    private <T> void fillList(List<T> list, int size, int index, T value) {
        for(int i = index; i < size ; i++) {
            list.add(i, value);
        }
    }



    private void pasteTimeAndDate() {
        LocalDateTime currentDate = LocalDateTime.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
        JTextArea textArea = viewer.getCurrentContent();
        textArea.insert(formattedDate, textArea.getCaretPosition());
    }

    private void openDocument() {
        File file = viewer.getFile();
        if(file != null) {
            JTabbedPane tabPane = viewer.getTabPane();
            int newTabIndex = viewer.createNewTab();
            tabsController.setValueInToList(tabsController.getFilesPerTabs(), newTabIndex, file);

            String filePath = file.getAbsolutePath();
            String contentText = readFile(filePath);
            String fileName = getFileNameFromPath(filePath);

            viewer.update(contentText, fileName, newTabIndex);
            tabsController.setValueInToList(tabsController.getUnsavedChangesPerTab(), newTabIndex, false);
        }
    }

    private int saveDocumentAs() {
        int currentTabIndex = viewer.getCurrentTabIndex();
        String fileName = "";
        File currentOpenFile = tabsController.getFilesPerTabs().get(currentTabIndex);
        if (currentOpenFile == null) {
            fileName = "Untitled.txt";
        } else  {
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
                return 0;
            } catch (IOException e) {
                viewer.showError(e.toString());
                return -1;
            }
        }
        return -1;
    }

    private void copyOrCutText(String command) {
        JTextArea textArea = viewer.getCurrentContent();
        String selectedText = textArea.getSelectedText();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(selectedText);
        clipboard.setContents(stringSelection, null);

        if (command.equals("Cut")) {
            textArea.cut();
        }
    }

    private void pasteText() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = clipboard.getContents(null);

        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                String textToPaste = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                JTextArea textArea = viewer.getCurrentContent();
                int pos = textArea.getCaretPosition();
                textArea.insert(textToPaste, pos);
            } catch (UnsupportedFlavorException | IOException e) {
                viewer.showError(e.getMessage());
            }
        }
    }

    private String getFileNameFromPath(String path) {
        String[] directories = path.split("\\\\");
        return directories[directories.length - 1];
    }

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


}
