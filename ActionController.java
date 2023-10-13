import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Files;

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
            save();

        } else if(command.equals("Save_As")) {
            System.out.println(command);

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
            openFontChooser();

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

    private void openFontChooser() {
        viewer.updateTextFont();
    }

    private void openDocument() {
        File file = viewer.getFile();
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

        try(FileChannel fchannel = FileChannel.open(Paths.get(filePath), StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            do {
                bytesCount = fchannel.read(buffer);
                if(bytesCount != -1) {
                    buffer.rewind();
                    for(int i = 0; i < bytesCount; i++) {
                        fileContent += (char) buffer.get();
                    }
                }
            } while(bytesCount != -1);

        }catch(InvalidPathException e) {
            viewer.showError(e.toString());
        } catch(IOException e) {
            viewer.showError(e.toString());
        }

        return fileContent;
    }

    private void createNewDocument() {
        viewer.createNewTab();
    }

    private void save(){
        createNewFile();
    }
    private void createNewFile() {
        File newFile = viewer.selectNewFileLocation();
        Path newFilePath = Paths.get(newFile.getAbsolutePath());
        try{
            Files.createFile(newFilePath);
        }catch(IOException e){
            viewer.showError(e.toString());
        }
    }
}
