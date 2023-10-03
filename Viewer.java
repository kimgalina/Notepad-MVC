import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;


public class Viewer {

    public Viewer(){

        ActionController controller = new ActionController();
        WindowController windowController = new WindowController(controller);

        JMenuBar menuBar = getJMenuBar(controller);

        JFrame frame = new JFrame("Notepad MVC");
        frame.setLocation(300,100);
        frame.setSize(800,500);

        frame.setJMenuBar(menuBar);
        frame.addWindowListener(windowController);
        frame.setVisible(true);
    }

    private JMenuBar getJMenuBar(ActionController controller){
      JMenu fileMenu = getFileMenu(controller);
      JMenu editMenu = new JMenu("Help");
      JMenu formatMenu = new JMenu("Format");
      JMenu viewMenu = new JMenu("View");
      JMenu helpMenu = new JMenu("Help");

      JMenuBar menuBar = new JMenuBar();
      menuBar.add(fileMenu);
      menuBar.add(editMenu);
      menuBar.add(formatMenu);
      menuBar.add(viewMenu);
      menuBar.add(helpMenu);

      return menuBar;
    }

    private JMenu getFileMenu(ActionController controller){
        JMenuItem newDocument = new JMenuItem( "New Document" , new ImageIcon("images/new.gif"));
        newDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newDocument.addActionListener(controller);
        newDocument.setActionCommand("New_Document");

        JMenuItem openDocument = new JMenuItem( "Open Document" , new ImageIcon("images/open.gif"));
        openDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.CTRL_MASK));
        openDocument.addActionListener(controller);
        openDocument.setActionCommand("Open_Document");

        JMenuItem saveDocument = new JMenuItem( "Save" , new ImageIcon("images/save.gif"));
        saveDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveDocument.addActionListener(controller);
        saveDocument.setActionCommand("Save");

        JMenuItem saveAsDocument = new JMenuItem( "Save As ..." , new ImageIcon("images/save_as.gif"));
        saveAsDocument.addActionListener(controller);
        saveAsDocument.setActionCommand("Save_As");

        JMenuItem printDocument = new JMenuItem( "Print " , new ImageIcon("images/print.gif"));
        printDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        printDocument.addActionListener(controller);
        printDocument.setActionCommand("Print");

        JMenuItem exitProgram = new JMenuItem( "Exit");
        exitProgram.addActionListener(controller);
        exitProgram.setActionCommand("Exit");

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(newDocument);
        fileMenu.add(openDocument);
        fileMenu.add(saveDocument);
        fileMenu.add(saveAsDocument);
        fileMenu.add(new JSeparator());
        fileMenu.add(printDocument);
        fileMenu.add(new JSeparator());
        fileMenu.add(exitProgram);
        return fileMenu;
    }
}
