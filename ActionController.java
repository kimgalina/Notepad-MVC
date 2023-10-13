import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.charset.Charset;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Files;

import java.util.List;

public class ActionController implements ActionListener {
    private Viewer viewer;
    private String contentText;
    private File CurrentOpenFile;

    public ActionController(Viewer viewer) {
        this.viewer = viewer;
    }

    public void actionPerformed(ActionEvent event) {
        viewer.setCurrentContent();

        String command = event.getActionCommand();
        if (command.equals("Open_Document")) {
            openDocument();

        } else if (command.equals("Choose_Color")) {
            Color color = viewer.openColorChooser();
            viewer.updateTextColor(color);

        } else if (command.equals("New_Document")) {
            createNewDocument();

        } else if (command.equals("Save")) {
            saveDocument();

        } else if(command.equals("Save_As")) {
            saveDocumentAs();

        } else if(command.equals("Print")) {
            System.out.println(command);

        } else if(command.equals("Exit")) {
            System.out.println(command);

        } else if(command.equals("Cut")) {
            System.out.println(command);

        } else if(command.equals("Copy")) {
            System.out.println(command);

        } else if(command.equals("Paste")) {
            System.out.println(command);

        } else if(command.equals("Clear")) {
            System.out.println(command);

        } else if(command.equals("Find")) {
            System.out.println(command);

        } else if(command.equals("Find more")) {
            System.out.println(command);

        } else if(command.equals("Go")) {
            System.out.println(command);

        } else if(command.equals("Select all")) {
            System.out.println(command);

        } else if(command.equals("Time and date")) {
            System.out.println(command);

        } else if(command.equals("Word_Space")) {
            System.out.println(command);

        } else if(command.equals("Font")) {
            viewer.openFontChooser();

        } else if(command.equals("Status_Space")) {
            System.out.println(command);

        } else if(command.equals("View_Help")) {
            System.out.println(command);

        } else if(command.equals("About")) {
            System.out.println(command);

        }
    }

    private void setCurrentPanel() {

    }

    private void openDocument() {
        File file = viewer.getFile();
        CurrentOpenFile = file;
        String filePath = file.getAbsolutePath();
        String contentText = readFile(filePath);
        String fileName = getFileNameFromPath(filePath);
        viewer.update(contentText, fileName);
    }

    private String getFileNameFromPath(String path) {
        String[] directories = path.split("\\\\");
        return directories[directories.length - 1];
    }

    private String readFile(String filePath) {
        int bytesCount;
        String fileContent = "";
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
            fileContent = String.join("\n", lines);
        } catch(IOException e) {
            viewer.showError(e.toString());
        }
        return fileContent;
    }

    private void createNewDocument() {
        viewer.createNewTab();
    }

    private void saveDocument() {
        if (CurrentOpenFile != null) {
          try {
              String fileName = getFileNameFromPath(CurrentOpenFile.getAbsolutePath());
              String content = viewer.getCurrentContent().getText();
              Files.write(CurrentOpenFile.toPath(), content.getBytes());
          } catch (IOException e) {
              viewer.showError(e.toString());
          }
        } else {
            saveDocumentAs();
        }
    }

    private void saveDocumentAs() {
        String fileName = "";
        if (CurrentOpenFile == null) {
            fileName = "Untitled.txt";
        } else  {
            fileName = getFileNameFromPath(CurrentOpenFile.getAbsolutePath());
        }
        File selectedFile = viewer.getNewFileSaveLocation(fileName);
        try {
            Path filePath = selectedFile.toPath();
            Files.write(filePath, viewer.getCurrentContent().getText().getBytes());
            CurrentOpenFile = selectedFile;
            viewer.update(viewer.getCurrentContent().getText(), getFileNameFromPath(selectedFile.getAbsolutePath()));
        } catch (IOException e) {
            viewer.showError(e.toString());
        }
    }
}
