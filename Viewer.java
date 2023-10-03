import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.JRadioButtonMenuItem;
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
        JMenu editMenu = getEditMenu(controller);
        JMenu formatMenu = getFormatMenu(controller);
        JMenu viewMenu = getViewMenu(controller);
        JMenu helpMenu = getHelpMenu(controller);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private JMenu getFileMenu(ActionController controller) {
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
        fileMenu.addSeparator();;
        fileMenu.add(printDocument);
        fileMenu.addSeparator();;
        fileMenu.add(exitProgram);
        return fileMenu;
    }

    private JMenu getEditMenu(ActionController controller) {
        JMenuItem cutDocument = new JMenuItem( "Cut" , new ImageIcon("images/cut.gif"));
        cutDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cutDocument.addActionListener(controller);
        cutDocument.setActionCommand("Cut");

        JMenuItem copyDocument = new JMenuItem( "Copy" , new ImageIcon("images/copy.gif"));
        copyDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copyDocument.addActionListener(controller);
        copyDocument.setActionCommand("Copy");

        JMenuItem pasteDocument = new JMenuItem( "Paste" , new ImageIcon("images/past.gif"));
        pasteDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        pasteDocument.addActionListener(controller);
        pasteDocument.setActionCommand("Paste");

        JMenuItem clearDocument = new JMenuItem( "Clear" , new ImageIcon("images/delit.gif"));
        clearDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        clearDocument.addActionListener(controller);
        clearDocument.setActionCommand("Clear");

        JMenuItem findDocument = new JMenuItem( "Find" , new ImageIcon("images/find.gif"));
        findDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        findDocument.addActionListener(controller);
        findDocument.setActionCommand("Find");

        JMenuItem findMoreDocument = new JMenuItem( "Find more" , new ImageIcon("images/findMore.gif"));
        findMoreDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK));
        findMoreDocument.addActionListener(controller);
        findMoreDocument.setActionCommand("Find_More");

        JMenuItem goDocument = new JMenuItem( "Go" , new ImageIcon("images/go.gif"));
        goDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        goDocument.addActionListener(controller);
        goDocument.setActionCommand("Go");

        JMenuItem selectAllDocument = new JMenuItem( "Select all" , new ImageIcon("images/marcker.gif"));
        selectAllDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectAllDocument.addActionListener(controller);
        selectAllDocument.setActionCommand("Select_All");

        JMenuItem timeAndDateDocument = new JMenuItem( "Time and  date" , new ImageIcon("images/time.gif"));
        timeAndDateDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.CTRL_MASK));
        timeAndDateDocument.addActionListener(controller);
        timeAndDateDocument.setActionCommand("Time_And_Date");

        JMenu editMenu = new JMenu("Edit");
        editMenu.add(cutDocument);
        editMenu.add(copyDocument);
        editMenu.add(pasteDocument);
        editMenu.add(clearDocument);
        editMenu.add(findDocument);
        editMenu.add(findMoreDocument);
        editMenu.add(goDocument);
        editMenu.add(selectAllDocument);
        editMenu.add(timeAndDateDocument);

        return editMenu;
    }

    private JMenu getFormatMenu(ActionController controller) {
        JRadioButtonMenuItem wordSpase = new JRadioButtonMenuItem("Word space");
        wordSpase.setSelected(true);
        wordSpase.addActionListener(controller);
        wordSpase.setActionCommand("Word_Space");

        JMenuItem fontDocument = new JMenuItem( "Font" , new ImageIcon("images/font.gif"));
        fontDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        fontDocument.addActionListener(controller);
        fontDocument.setActionCommand("Font");

        JMenu formatMenu = new JMenu("Format");
        formatMenu.add(wordSpase);
        formatMenu.addSeparator();
        formatMenu.add(fontDocument);

        return formatMenu;
    }

    private JMenu getViewMenu(ActionController controller) {
        JRadioButtonMenuItem statusSpase = new JRadioButtonMenuItem("Status space");
        statusSpase.setSelected(false);
        statusSpase.addActionListener(controller);
        statusSpase.setActionCommand("Status_Space");

        JMenu viewMenu = new JMenu("View");
        viewMenu.add(statusSpase);

        return viewMenu;
    }

    private JMenu getHelpMenu(ActionController controller) {
        JMenuItem viewHelpDocument = new JMenuItem( "View Help" , new ImageIcon("images/font.gif"));
        viewHelpDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        viewHelpDocument.addActionListener(controller);
        viewHelpDocument.setActionCommand("View_Help");

        JMenuItem aboutDocument = new JMenuItem( "About" , new ImageIcon("images/font.gif"));
        aboutDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        aboutDocument.addActionListener(controller);
        aboutDocument.setActionCommand("About");

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(viewHelpDocument);
        helpMenu.add(aboutDocument);

        return helpMenu;
    }
}
