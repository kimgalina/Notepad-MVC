import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JTextArea;
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

import java.util.List;
import java.nio.charset.UnmappableCharacterException;
import java.nio.charset.Charset;

public class ActionController implements ActionListener {
    private Viewer viewer;
    private String contentText;
    private File currentOpenFile;

    public ActionController(Viewer viewer) {
        this.viewer = viewer;
    }

    public void actionPerformed(ActionEvent event) {
        viewer.setCurrentContent();
        viewer.setViewItemZoomIn(viewer.canZoomIn());
        viewer.setViewItemZoomOut(viewer.canZoomOut());
        viewer.setStatusPanelToVisible(viewer.getStatusBarBox().isSelected());

        String command = event.getActionCommand();
        if (command.equals("New_Document")) {
            createNewDocument();

        } else if (command.equals("Open_Document")) {
            openDocument();

        } else if (command.equals("Save")) {
            saveDocument();

        } else if(command.equals("Save_As")) {
            saveDocumentAs();

        } else if(command.equals("Print")) {
            System.out.println(command);

        } else if(command.equals("Exit")) {
            exitProgram();


        } else if(command.equals("Cut")) {
            copyOrCutText(command);

        } else if(command.equals("Copy")) {
            copyOrCutText(command);

        } else if(command.equals("Paste")) {
            pasteText();

        } else if(command.equals("Clear")) {
            System.out.println(command);

        } else if(command.equals("Find")) {
            System.out.println(command);

        } else if(command.equals("Find more")) {
            System.out.println(command);

        } else if(command.equals("Go")) {
            System.out.println(command);

        } else if(command.equals("Select_All")) {
            viewer.getCurrentContent().selectAll();

        } else if(command.equals("Time and date")) {
            System.out.println(command);

        } else if(command.equals("Word_Space")) {
            System.out.println(command);

        } else if(command.equals("Font")) {
            viewer.openFontChooser();

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
    public void exitProgram() {
        if(!hasUnsavedChanges()) {
            System.exit(0);
        } else {
            viewer.showExitMessage();
        }
    }

    public boolean hasUnsavedChanges() {
        try {
            String currentContent = viewer.getCurrentContent().getText();
            if(currentOpenFile != null) {
                viewer.setCurrentContent();
                String fileContent = readFile(currentOpenFile.getAbsolutePath());
                return (!fileContent.equals(currentContent));
            } else if (!currentContent.equals("")) {
                return true;
            }
        }
        catch(NullPointerException e) {
            return false;
        }
        return false;
    }

    public void saveDocument() {
        if (currentOpenFile != null) {
          try {
              String fileName = getFileNameFromPath(currentOpenFile.getAbsolutePath());
              String content = viewer.getCurrentContent().getText();
              Files.write(currentOpenFile.toPath(), content.getBytes());
          } catch (IOException e) {
              viewer.showError(e.toString());
          }
        } else {
            saveDocumentAs();
        }
    }

    private void createNewDocument() {
        viewer.createNewTab();
    }

    private void openDocument() {
        File file = viewer.getFile();
        if(file != null) {
            currentOpenFile = file;
            String filePath = file.getAbsolutePath();
            String contentText = readFile(filePath);
            String fileName = getFileNameFromPath(filePath);
            viewer.update(contentText, fileName);
        }
    }

    private void saveDocumentAs() {
        String fileName = "";

        if (currentOpenFile == null) {
            fileName = "Untitled.txt";
        } else  {
            fileName = getFileNameFromPath(currentOpenFile.getAbsolutePath());
        }

        File selectedFile = viewer.getNewFileSaveLocation(fileName);
        if(selectedFile != null) {
            try {
                Path filePath = selectedFile.toPath();
                Files.write(filePath, viewer.getCurrentContent().getText().getBytes());
                currentOpenFile = selectedFile;
                viewer.update(viewer.getCurrentContent().getText(), getFileNameFromPath(selectedFile.getAbsolutePath()));
            } catch (IOException e) {
                viewer.showError(e.toString());
            }
        }
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
