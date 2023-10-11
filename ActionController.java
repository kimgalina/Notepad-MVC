import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JFrame;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;

public class ActionController implements ActionListener {

    private Viewer viewer;
    private String contentText;
    private File CurrentOpenFile;

    public ActionController(Viewer viewer) {
        this.viewer = viewer;
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if (command.equals("Open_Document")) {
            openDocument();

        } else if (command.equals("Choose_Color")) {
            openColorChooser();

        } else if (command.equals("New_Document")) {
            System.out.println(command);

        } else if (command.equals("Save")) {
            System.out.println(command);

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

        } else if(command.equals("Time and  date")) {
            System.out.println(command);

        } else if(command.equals("Word_Space")) {
            System.out.println(command);

        } else if(command.equals("Format")) {
            System.out.println(command);

        } else if(command.equals("Status_Space")) {
            System.out.println(command);

        } else if(command.equals("View_Help")) {
            System.out.println(command);

        } else if(command.equals("About")) {
            System.out.println(command);

        }
    }

    private void openColorChooser() {
        Color color = JColorChooser.showDialog(new JFrame(), "Chooser", Color.BLACK);
        viewer.updateTextColor(color);
    }
    private void openDocument() {
        File file = viewer.getFile();
        String filePath = file.getAbsolutePath();
        String contentText = readFile(filePath);
        viewer.update(contentText, filePath);
    }
    private String readFile(String filePath) {
        Path path = null;
        int bytesCount;
        String fileContent = "";
        try {
            path = Paths.get(filePath);
        }catch(InvalidPathException e){
            viewer.showError(e.toString());
        }

        try(FileChannel fchannel = FileChannel.open(path,StandardOpenOption.READ)) {
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

        } catch(IOException e) {
            viewer.showError(e.toString());
        }

        return fileContent;
    }
}
