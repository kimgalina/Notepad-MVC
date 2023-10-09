import javax.swing.JToolBar;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import java.io.File;


public class Viewer {

    JTextArea content ;
    JFileChooser fileChooser;
    JFrame frame;
    ActionController controller;
    WindowController windowController;

    public Viewer(){
        controller = new ActionController(this);
        windowController = new WindowController(controller);
    }

    public void startApplication(){
        Font contentFont = new Font("Consolas", Font.PLAIN,25);
        Font menuFont = new Font("Times New Roman",Font.BOLD,23);
        Font submenuFont = new Font("Times New Roman",Font.PLAIN,20);

        content = new JTextArea();
        content.setFont(contentFont);

        JMenuBar menuBar = getJMenuBar(menuFont,submenuFont);
        JScrollPane scrollPane = new JScrollPane(content);
        JToolBar toolBar = getToolBar();

        frame = new JFrame("Notepad MVC");
        frame.setLocation(300,100);
        frame.setSize(1000,800);

        frame.setJMenuBar(menuBar);
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(scrollPane);
        frame.addWindowListener(windowController);
        frame.setVisible(true);
    }

    public File getFile(){
        if(fileChooser == null){
            fileChooser = new JFileChooser();
        }

        int returnVal = fileChooser.showOpenDialog(new JFrame());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
           File file = fileChooser.getSelectedFile();
           return file;
       } else {

       }
        return null;
    }

    public void update(String text, String frameName){
        content.setText(text);
        if(frameName!= null){
            frame.setTitle(frameName);
        }
    }

    public void updateTextColor(Color color) {
        content.setForeground(color);
    }

    private JToolBar getToolBar() {
        JToolBar toolBar = new JToolBar();

        JButton buttonNew = createButton("images/new.gif", "New_Document");
        JButton buttonOpen = createButton("images/open.gif", "Open_Document");
        JButton buttonSave = createButton("images/save.gif", "Save");
        JButton buttonCut = createButton("images/cut.gif", "Cut");
        JButton buttonCopy = createButton("images/copy.gif", "Copy");
        JButton buttonPaste = createButton("images/paste.gif", "Paste");
        JButton buttonColor = createButton("images/color.gif", "Choose_Color");

        toolBar.add(buttonNew);
        toolBar.add(buttonOpen);
        toolBar.add(buttonSave);
        toolBar.addSeparator();
        toolBar.add(buttonCut);
        toolBar.add(buttonCopy);
        toolBar.add(buttonPaste);
        toolBar.add(buttonColor);
        toolBar.setFloatable(true);
        toolBar.setRollover(true);

        return toolBar;
    }

    private JButton createButton(String iconPath, String actionCommand) {
        JButton button = new JButton(new ImageIcon(iconPath));

        button.addActionListener(controller);
        button.setActionCommand(actionCommand);
        button.setFocusable(false);

        return button;
    }

    private JMenuBar getJMenuBar(Font menuFont,Font submenuFont){
        JMenu fileMenu = getFileMenu(menuFont,submenuFont);
        JMenu editMenu = getEditMenu(menuFont,submenuFont);
        JMenu formatMenu = getFormatMenu(menuFont,submenuFont);
        JMenu viewMenu = getViewMenu(menuFont,submenuFont);
        JMenu helpMenu = getHelpMenu(menuFont,submenuFont);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private JMenuItem createMenuItem(String name, String pathToIcon,String actionCommand,Font submenuFont){
        JMenuItem menuItem = new JMenuItem( name, new ImageIcon(pathToIcon));
        menuItem.addActionListener(controller);
        menuItem.setActionCommand(actionCommand);
        menuItem.setFont(submenuFont);
        return menuItem;
    }

    private JMenu getFileMenu(Font menuFont, Font submenuFont) {

        JMenuItem newDocument = createMenuItem("New Document","images/new.gif","New_Document",submenuFont);
        newDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

        JMenuItem openDocument = createMenuItem( "Open Document" ,"images/open.gif","Open_Document",submenuFont);
        openDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.CTRL_MASK));

        JMenuItem saveDocument = createMenuItem( "Save" ,"images/save.gif","Save",submenuFont);
        saveDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        JMenuItem saveAsDocument = createMenuItem( "Save As ..." ,"images/save_as.gif","Save_As",submenuFont);

        JMenuItem printDocument = createMenuItem( "Print ","images/print.gif","Print",submenuFont);
        printDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        JMenuItem exitProgram = createMenuItem( "Exit","","Exit",submenuFont);

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(newDocument);
        fileMenu.add(openDocument);
        fileMenu.add(saveDocument);
        fileMenu.add(saveAsDocument);
        fileMenu.addSeparator();
        fileMenu.add(printDocument);
        fileMenu.addSeparator();
        fileMenu.add(exitProgram);
        fileMenu.setFont(menuFont);
        return fileMenu;
    }

    private JMenu getEditMenu(Font menuFont,Font submenuFont) {
        JMenuItem cutDocument = createMenuItem( "Cut" ,"images/cut.gif","Cut",submenuFont);
        cutDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        JMenuItem copyDocument = createMenuItem( "Copy" ,"images/copy.gif","Copy",submenuFont);
        copyDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        JMenuItem pasteDocument = createMenuItem( "Paste" ,"images/past.gif","Paste",submenuFont);
        pasteDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        JMenuItem clearDocument = createMenuItem( "Clear" ,"images/delit.gif","Clear",submenuFont);
        clearDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));

        JMenuItem findDocument = createMenuItem( "Find" ,"images/find.gif","Find",submenuFont);
        findDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));

        JMenuItem findMoreDocument = createMenuItem( "Find more" , "images/findMore.gif","Find_More",submenuFont);
        findMoreDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK));

        JMenuItem goDocument = createMenuItem( "Go" ,"images/go.gif","Go",submenuFont);
        goDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));

        JMenuItem selectAllDocument = createMenuItem( "Select all" ,"images/marcker.gif","Select_All",submenuFont);
        selectAllDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        JMenuItem timeAndDateDocument = createMenuItem( "Time and  date" ,"images/time.gif","Time_And_Date",submenuFont);
        timeAndDateDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.CTRL_MASK));

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
        editMenu.setFont(menuFont);

        return editMenu;
    }

    private JMenu getFormatMenu(Font menuFont,Font submenuFont) {
        JRadioButtonMenuItem wordSpase = new JRadioButtonMenuItem("Word space");
        wordSpase.setSelected(true);
        wordSpase.addActionListener(controller);
        wordSpase.setActionCommand("Word_Space");
        wordSpase.setFont(submenuFont);

        JMenuItem fontDocument = createMenuItem( "Font" ,"images/font.gif","Font",submenuFont);
        fontDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));

        JMenu formatMenu = new JMenu("Format");
        formatMenu.add(wordSpase);
        formatMenu.addSeparator();
        formatMenu.add(fontDocument);
        formatMenu.setFont(menuFont);

        return formatMenu;
    }

    private JMenu getViewMenu(Font menuFont,Font submenuFont) {
        JRadioButtonMenuItem statusSpase = new JRadioButtonMenuItem("Status space");
        statusSpase.setSelected(false);
        statusSpase.addActionListener(controller);
        statusSpase.setActionCommand("Status_Space");
        statusSpase.setFont(submenuFont);

        JMenu viewMenu = new JMenu("View");
        viewMenu.add(statusSpase);
        viewMenu.setFont(menuFont);

        return viewMenu;
    }

    private JMenu getHelpMenu(Font menuFont,Font submenuFont) {
        JMenuItem viewHelpDocument = createMenuItem( "View Help" ,"images/font.gif","View_Help",submenuFont);
        viewHelpDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));

        JMenuItem aboutDocument = createMenuItem( "About" , "images/font.gif","About",submenuFont);
        aboutDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(viewHelpDocument);
        helpMenu.add(aboutDocument);
        helpMenu.setFont(menuFont);

        return helpMenu;
    }
}
