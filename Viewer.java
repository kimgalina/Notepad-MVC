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
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JColorChooser;
import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JOptionPane;
import java.awt.GraphicsEnvironment;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;

import java.awt.Component;
import javax.swing.JViewport;

public class Viewer {
    private JFileChooser fileChooser;
    private JFrame frame;
    private ActionController controller;
    private WindowController windowController;
    private JTabbedPane tabPane;
    private Font contentFont;
    private Font submenuFont;
    private Font menuFont;
    private JPanel mainPanel;

    public Viewer() {
        mainPanel = new JPanel(new BorderLayout());
        controller = new ActionController(this,mainPanel);
        windowController = new WindowController(controller);
        contentFont = new Font("Consolas", Font.PLAIN, 25);
        menuFont = new Font("Tahoma", Font.BOLD, 20);
        submenuFont = new Font("Tahoma", Font.PLAIN, 16);
        tabPane = new JTabbedPane();
    }

    public void startApplication() {
        JPanel topPanel = new JPanel(new BorderLayout());

        JTextArea content = new JTextArea();
        content.setFont(contentFont);
        JScrollPane scrollPane = new JScrollPane(content);

        JMenuBar menuBar = getJMenuBar(menuFont, submenuFont,controller);
        JToolBar toolBar = getToolBar(controller);

        topPanel.add(menuBar);
        topPanel.add(toolBar, BorderLayout.NORTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);


        tabPane.addTab("Untitled", mainPanel);

        frame = new JFrame("Notepad MVC");
        frame.setLocation(300, 100);
        frame.setSize(1000, 800);

        frame.add(tabPane);
        frame.addWindowListener(windowController);
        frame.setVisible(true);
    }

    public void createNewTab() {

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout());

        ActionController newController = new ActionController(this,panel);

        JTextArea content = new JTextArea();
        content.setFont(contentFont);
        JScrollPane scrollPane = new JScrollPane(content);

        JMenuBar menuBar = getJMenuBar(menuFont, submenuFont,newController);
        JToolBar toolBar = getToolBar(newController);

        topPanel.add(menuBar);
        topPanel.add(toolBar, BorderLayout.NORTH);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        tabPane.addTab("Untitled", panel);

    }

    public Color openColorChooser() {
        return JColorChooser.showDialog(frame, "Color Chooser", Color.BLACK);
    }

    public void updateTextColor(Color color) {
        //content.setForeground(color);
    }

    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(new JFrame(),
        errorMessage,
        "Error",
        JOptionPane.ERROR_MESSAGE);
    }

    public File getFile(){
        if(fileChooser == null) {
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

    public void update(String text, String tabName, JTextArea content, JPanel currentPanel) {
        content.setText(text);
        if(tabName != null) {
            int tabIndex = tabPane.indexOfComponent(currentPanel);
            tabPane.setTitleAt(tabIndex,tabName);
        }
    }

    public JTextArea findPanelsContent(JPanel currentPanel){
        for (Component component : currentPanel.getComponents()) {
            if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                JViewport viewport = scrollPane.getViewport();
                if (viewport.getView() instanceof JTextArea) {
                    JTextArea textArea = (JTextArea) viewport.getView();
                    return textArea;
                }
            }
        }
        System.out.println("TextArea not found");
        return null;
    }

    public void updateTextFont() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontComboBox = new JComboBox<>(fonts);
        JTextField sizeTextField = new JTextField(5);

        Object[] message = {
            "Выберите шрифт:", fontComboBox,
            "Введите размер шрифта:", sizeTextField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Выбор шрифта и размера", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String selectedFont = (String) fontComboBox.getSelectedItem();
            int fontSize = 24;

            try {
                fontSize = Integer.parseInt(sizeTextField.getText());
            } catch (NumberFormatException e) {
                showError("Font size is not correct. Using default size.");
            }

            Font newFont = new Font(selectedFont, Font.PLAIN, fontSize);
            //content.setFont(newFont);
        }
    }

    private JToolBar getToolBar(ActionController controller) {
        JToolBar toolBar = new JToolBar();

        JButton buttonNew = createButton("images/new.gif", "New_Document", controller);
        JButton buttonOpen = createButton("images/open.gif", "Open_Document", controller);
        JButton buttonSave = createButton("images/save.gif", "Save", controller);
        JButton buttonCut = createButton("images/cut.gif", "Cut", controller);
        JButton buttonCopy = createButton("images/copy.gif", "Copy", controller);
        JButton buttonPaste = createButton("images/paste.gif", "Paste", controller);
        JButton buttonColor = createButton("images/color.gif", "Choose_Color", controller);

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

    private JButton createButton(String iconPath, String actionCommand, ActionController controller) {
        JButton button = new JButton(new ImageIcon(iconPath));

        button.addActionListener(controller);
        button.setActionCommand(actionCommand);
        button.setFocusable(false);

        return button;
    }

    private JMenuBar getJMenuBar(Font menuFont, Font submenuFont, ActionController controller) {
        JMenu fileMenu = getFileMenu(menuFont, submenuFont, controller);
        JMenu editMenu = getEditMenu(menuFont, submenuFont, controller);
        JMenu formatMenu = getFormatMenu(menuFont, submenuFont, controller);
        JMenu viewMenu = getViewMenu(menuFont, submenuFont, controller);
        JMenu helpMenu = getHelpMenu(menuFont, submenuFont, controller);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private JMenu getFileMenu(Font menuFont, Font submenuFont, ActionController controller) {

        JMenuItem newDocument = createMenuItem("New Document", "images/new.gif", "New_Document", submenuFont, controller);
        newDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

        JMenuItem openDocument = createMenuItem("Open Document", "images/open.gif", "Open_Document", submenuFont, controller);
        openDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        JMenuItem saveDocument = createMenuItem("Save", "images/save.gif", "Save", submenuFont, controller);
        saveDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        JMenuItem saveAsDocument = createMenuItem("Save As ..." , "images/save_as.gif", "Save_As", submenuFont, controller);

        JMenuItem printDocument = createMenuItem("Print ", "images/print.gif", "Print", submenuFont, controller);
        printDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        JMenuItem exitProgram = createMenuItem("Exit", "", "Exit", submenuFont, controller);

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

    private JMenu getEditMenu(Font menuFont, Font submenuFont, ActionController controller) {
        JMenuItem cutDocument = createMenuItem("Cut", "images/cut.gif", "Cut", submenuFont, controller);
        cutDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        JMenuItem copyDocument = createMenuItem("Copy", "images/copy.gif", "Copy", submenuFont, controller);
        copyDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        JMenuItem pasteDocument = createMenuItem("Paste", "images/past.gif", "Paste", submenuFont, controller);
        pasteDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        JMenuItem clearDocument = createMenuItem("Clear", "images/delit.gif", "Clear", submenuFont, controller);
        clearDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));

        JMenuItem findDocument = createMenuItem("Find", "images/find.gif", "Find", submenuFont, controller);
        findDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));

        JMenuItem findMoreDocument = createMenuItem("Find more", "images/findMore.gif", "Find_More", submenuFont, controller);
        findMoreDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK));

        JMenuItem goDocument = createMenuItem("Go", "images/go.gif", "Go", submenuFont,controller);
        goDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));

        JMenuItem selectAllDocument = createMenuItem("Select all", "images/marcker.gif", "Select_All", submenuFont, controller);
        selectAllDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        JMenuItem timeAndDateDocument = createMenuItem("Time and  date", "images/time.gif", "Time_And_Date", submenuFont, controller);
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

    private JMenu getFormatMenu(Font menuFont, Font submenuFont, ActionController controller) {
        JRadioButtonMenuItem wordSpase = new JRadioButtonMenuItem("Word space");
        wordSpase.setSelected(true);
        wordSpase.addActionListener(controller);
        wordSpase.setActionCommand("Word_Space");
        wordSpase.setFont(submenuFont);

        JMenuItem fontDocument = createMenuItem("Font", "images/font.gif", "Font", submenuFont,controller);
        fontDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));

        JMenu formatMenu = new JMenu("Format");
        formatMenu.add(wordSpase);
        formatMenu.addSeparator();
        formatMenu.add(fontDocument);
        formatMenu.setFont(menuFont);

        return formatMenu;
    }

    private JMenu getViewMenu(Font menuFont, Font submenuFont, ActionController controller) {
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

    private JMenu getHelpMenu(Font menuFont, Font submenuFont, ActionController controller) {
        JMenuItem viewHelpDocument = createMenuItem("View Help", "images/font.gif", "View_Help", submenuFont,controller);
        viewHelpDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));

        JMenuItem aboutDocument = createMenuItem("About", "images/font.gif", "About", submenuFont,controller);
        aboutDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(viewHelpDocument);
        helpMenu.add(aboutDocument);
        helpMenu.setFont(menuFont);

        return helpMenu;
    }

    private JMenuItem createMenuItem(String name, String pathToIcon, String actionCommand, Font submenuFont, ActionController controller) {
        JMenuItem menuItem = new JMenuItem(name, new ImageIcon(pathToIcon));
        menuItem.addActionListener(controller);
        menuItem.setActionCommand(actionCommand);
        menuItem.setFont(submenuFont);

        return menuItem;
    }

}
