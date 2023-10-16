import javax.swing.JToolBar;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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
import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;

import java.awt.Component;
import javax.swing.JViewport;

import javax.swing.JComponent;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Container;

public class Viewer {

    private JFileChooser fileChooser;
    private JFrame frame;
    private ActionController controller;
    private WindowController windowController;
    private JTabbedPane tabPane;
    private Font contentFont;
    private Font submenuFont;
    private Font menuFont;
    private JTextArea currentContent;
    private JMenuItem viewItemZoomIn;
    private JMenuItem viewItemZoomOut;
    private JMenuItem viewItemZoomDefault;
    private JCheckBox statusBarBox;
    private Font fontZoom;
    private JPanel statusPanel;
    private JLabel statusLabel;

    public Viewer() {
        frame = getFrame();
        controller = new ActionController(this);
        windowController = new WindowController(controller,this);
        contentFont = new Font("Consolas", Font.PLAIN, 22);
        menuFont = new Font("Tahoma", Font.BOLD, 20);
        submenuFont = new Font("Tahoma", Font.PLAIN, 16);
        tabPane = new JTabbedPane();
    }

    public void startApplication() {
        JMenuBar menuBar = getJMenuBar(menuFont, submenuFont, controller);
        JToolBar toolBar = getToolBar(controller);

        createNewTab();
        initStatusPanel();

        frame.setJMenuBar(menuBar);
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(statusPanel, BorderLayout.SOUTH);
        frame.add(tabPane);
        frame.addWindowListener(windowController);
        frame.setVisible(true);
        frame.setIconImage(new ImageIcon("images/notepad.png").getImage());
    }

    public void createNewTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea content = new JTextArea();
        content.setFont(contentFont);
        JScrollPane scrollPane = new JScrollPane(content);

        panel.add(scrollPane, BorderLayout.CENTER);

        tabPane.addTab(null, panel);
        int tabIndex = tabPane.indexOfComponent(panel);
        tabPane.setTabComponentAt(tabIndex, createCustomTabComponent("Untitled.txt"));
    }

    public void setCurrentContent() {
        JPanel currentPanel = getCurrentPanel();

        for (Component component : currentPanel.getComponents()) {
            if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                JViewport viewport = scrollPane.getViewport();
                if (viewport.getView() instanceof JTextArea) {
                    JTextArea textArea = (JTextArea) viewport.getView();
                    textArea.addCaretListener(new CaretController(this));
                    currentContent = textArea;
                    fontZoom = textArea.getFont();
                }
            }
        }
    }

    public JTextArea getCurrentContent() {
        return currentContent;
    }

    public Color openColorChooser() {
        return JColorChooser.showDialog(frame, "Color Chooser", Color.BLACK);
    }

    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(new JFrame(),
        errorMessage,
        "Error",
        JOptionPane.ERROR_MESSAGE);
    }

    public File getFile() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }
        int returnVal = fileChooser.showOpenDialog(new JFrame());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
           File file = fileChooser.getSelectedFile();
           return file;
        }
        return null;
    }

    public File getNewFileSaveLocation(String fileName){
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files (*.txt)", "txt");
        if (!fileName.equals("Untitled.txt")) {
          fileChooser.setSelectedFile(new File(fileName));
        }
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showSaveDialog(new JFrame());
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile;
        }
        return null;
    }

    public void update(String text, String tabName) {
        currentContent.setText(text);
        int tabIndex = tabPane.indexOfComponent(getCurrentPanel());
        renameTab(tabName, tabIndex);
    }

    public void updateTextColor(Color color) {
        currentContent.setForeground(color);
    }

    public void openFontChooser() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontComboBox = new JComboBox<>(fonts);
        JTextField sizeTextField = new JTextField(5);

        Object[] message = {
            "Choose font:", fontComboBox,
            "Enter font size:", sizeTextField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Font and size choosing", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String selectedFont = (String) fontComboBox.getSelectedItem();
            int fontSize = 24;

            try {
                fontSize = Integer.parseInt(sizeTextField.getText());
            } catch (NumberFormatException e) {
                showError("Font size is not correct. Using default size.");
            }

            Font newFont = new Font(selectedFont, Font.PLAIN, fontSize);
            currentContent.setFont(newFont);
          }
    }

    public void zoomIn() {
        if (canZoomIn()) {
            fontZoom = new Font(currentContent.getFont().getFontName(), currentContent.getFont().getStyle(), currentContent.getFont().getSize() + 2);
            currentContent.setFont(fontZoom);
        }

    }

    public boolean canZoomIn() {
        if (fontZoom.getSize() > 48) {
            setViewItemZoomIn(false);
            return false;
        } else {
            setViewItemZoomIn(true);
            return true;
        }
    }

    public void setViewItemZoomIn(boolean active) {
        viewItemZoomIn.setEnabled(active);
    }

    public void zoomOut() {
        if (canZoomOut()) {
            int size = currentContent.getFont().getSize();
            size = Math.max(size - 2, 8);
            fontZoom = new Font(currentContent.getFont().getFontName(), currentContent.getFont().getStyle(), size);
            currentContent.setFont(fontZoom);
        }
    }

    public boolean canZoomOut() {
        if (fontZoom.getSize() <= 8) {
            setViewItemZoomOut(false);
            return false;
        } else {
            setViewItemZoomOut(true);
            return true;
        }
    }

    public void setViewItemZoomOut(boolean active) {
        viewItemZoomOut.setEnabled(active);
    }

    public void zoomDefault() {
        if (canZoomDefault()) {
            currentContent.setFont(new java.awt.Font(currentContent.getFont().getFontName(), currentContent.getFont().getStyle(),
                    22));

        }
    }

    public boolean canZoomDefault() {
        if (currentContent.getFont().getSize() >= 22 || currentContent.getFont().getSize() <= 22) {
            return true;
        } else {
            return false;
        }
    }

    public JCheckBox getStatusBarBox() {
        return statusBarBox;
    }

    public void setLabelByTextAreaLines(int line, int column) {
        statusLabel.setText("  Line " + line + ", Column " + column);
    }

    public void setStatusPanelToVisible(boolean visible) {
        statusPanel.setVisible(visible);
    }

    public void getMessageAbout() {
        JOptionPane.showMessageDialog(frame,
                new MessageWithLink("<div>Notepad Template Method Design Pattern team<div>" +
                        "<a href=\"\">See the development process</a>"), "About Notepad",
                JOptionPane.INFORMATION_MESSAGE);
    }
    public void closeCurrentTab() {
        int currentTabIndex = tabPane.getSelectedIndex();
        if (currentTabIndex > 0) {
           tabPane.removeTabAt(currentTabIndex);
       } else if(currentTabIndex == 0 && true) { // and we have unsaved shanges
           showExitMessage();

       } else {
           controller.exitProgram();
       }
    }
    public void showExitMessage() {
        int result = JOptionPane.showConfirmDialog(frame, "Do you want to save changes ? ", "Notepad MVC",
                                                   JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        if(result == JOptionPane.YES_OPTION) {
            controller.saveDocument();
        } else if (result == JOptionPane.NO_OPTION) {
            controller.exitProgram();
        }

    }
    private JMenu getHelpMenu(Font menuFont, Font submenuFont, ActionController controller) {
        JMenuItem viewHelpDocument = createMenuItem("View Help", "images/font.gif", "View_Help", submenuFont, controller);
        viewHelpDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));

        JMenuItem aboutDocument = createMenuItem("About", "images/font.gif", "About", submenuFont, controller);
        aboutDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(viewHelpDocument);
        helpMenu.add(aboutDocument);
        helpMenu.setFont(menuFont);

        return helpMenu;
    }

    private void initStatusPanel() {
        statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 20));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusLabel = new JLabel();
        statusPanel.add(statusLabel);
        statusPanel.setVisible(false);
    }

    private String getNewFileName(){
        String content = currentContent.getText();
        if (content.length() != 0) {
            if(content.length() > 12){
                return content.substring(0,13) + ".txt";
            } else {
                return content + ".txt";
            }
        }
        return "Untitled.txt";
    }

    private JPanel getCurrentPanel() {
        int currentTabIndex = tabPane.getSelectedIndex();
        if (currentTabIndex != -1) {
            Component currentTab = tabPane.getComponentAt(currentTabIndex);
            if (currentTab instanceof JPanel) {
                JPanel panel = (JPanel) currentTab;
                return panel;
            }
        }
        return null;
    }

    private JComponent createCustomTabComponent(String tabTitle) {
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tabPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); //margin from top and bottom - 10
        tabPanel.setOpaque(false);
        tabPanel.add(Box.createRigidArea(new Dimension(10, 10)));// space between edge and tabName

        JLabel label = new JLabel(tabTitle);
        label.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tabPanel.add(label);

        tabPanel.add(Box.createRigidArea(new Dimension(40, 10)));// space between button and tabName

        JButton closeTabBtn = createCloseTabBtn();
        tabPanel.add(closeTabBtn);
        tabPanel.add(Box.createRigidArea(new Dimension(5, 5)));// space between edge and button
        return tabPanel;
    }

    private JButton createCloseTabBtn() {
        JButton closeButton = new JButton("\u00d7");
        closeButton.setFont(submenuFont);
        closeButton.setBorder(null);
        closeButton.setContentAreaFilled(false);
        closeButton.setActionCommand("CloseTab");
        closeButton.addActionListener(controller);
        return closeButton;
    }

    private void renameTab(String tabName, int tabIndex) {
        Component tabComponent = tabPane.getTabComponentAt(tabIndex);// taking tab with the index = tabIndex
        if (tabComponent instanceof Container) {
            Component[] components = ((Container) tabComponent).getComponents();

            for (Component component : components) {
                if (component instanceof JLabel) {
                    JLabel tabLabel = (JLabel) component;
                    tabLabel.setText(tabName);
                    break;
                }
            }
        }
    }


    private JToolBar getToolBar(ActionController controller) {
        JToolBar toolBar = new JToolBar();

        JButton buttonNew = createButton("images/new-document.png", "New_Document", controller);
        JButton buttonOpen = createButton("images/open.png", "Open_Document", controller);
        JButton buttonSave = createButton("images/save.png", "Save", controller);
        JButton buttonCut = createButton("images/cut.png", "Cut", controller);
        JButton buttonCopy = createButton("images/copy.png", "Copy", controller);
        JButton buttonPaste = createButton("images/paste.png", "Paste", controller);
        JButton buttonColor = createButton("images/color.png", "Choose_Color", controller);

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

        JMenuItem newDocument = createMenuItem("New Document", "images/new-document.png", "New_Document", submenuFont, controller);
        newDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

        JMenuItem openDocument = createMenuItem("Open Document", "images/open.png", "Open_Document", submenuFont, controller);
        openDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        JMenuItem saveDocument = createMenuItem("Save", "images/save.png", "Save", submenuFont, controller);
        saveDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        JMenuItem saveAsDocument = createMenuItem("Save As..." , "images/save_as.png", "Save_As", submenuFont, controller);

        JMenuItem printDocument = createMenuItem("Print", "images/print.png", "Print", submenuFont, controller);
        printDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        JMenuItem exitProgram = createMenuItem("Exit", "images/exit.png", "Exit", submenuFont, controller);

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
        JMenuItem cutDocument = createMenuItem("Cut", "images/cut.png", "Cut", submenuFont, controller);
        cutDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        JMenuItem copyDocument = createMenuItem("Copy", "images/copy.png", "Copy", submenuFont, controller);
        copyDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        JMenuItem pasteDocument = createMenuItem("Paste", "images/paste.png", "Paste", submenuFont, controller);
        pasteDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        JMenuItem clearDocument = createMenuItem("Clear", "images/clear.png", "Clear", submenuFont, controller);
        clearDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));

        JMenuItem findDocument = createMenuItem("Find", "images/find.png", "Find", submenuFont, controller);
        findDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));

        JMenuItem findMoreDocument = createMenuItem("Find more", "images/findMore.png", "Find_More", submenuFont, controller);
        findMoreDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK));

        JMenuItem goDocument = createMenuItem("Go", "images/go.png", "Go", submenuFont,controller);
        goDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));

        JMenuItem selectAllDocument = createMenuItem("Select all", "images/selectAll.png", "Select_All", submenuFont, controller);
        selectAllDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        JMenuItem timeAndDateDocument = createMenuItem("Time and date", "images/time.png", "Time_And_Date", submenuFont, controller);
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

        JMenuItem fontDocument = createMenuItem("Font", "images/font.png", "Font", submenuFont, controller);
        fontDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));

        JMenu formatMenu = new JMenu("Format");
        formatMenu.add(wordSpase);
        formatMenu.addSeparator();
        formatMenu.add(fontDocument);
        formatMenu.setFont(menuFont);

        return formatMenu;
    }

    private JMenu getViewMenu(Font menuFont, Font submenuFont, ActionController controller) {

        JMenu viewMenu = new JMenu("View");
        viewMenu.setFont(menuFont);
        JMenu viewZoom = new JMenu("Zoom");
        viewZoom.setFont(submenuFont);

        viewItemZoomIn = createMenuItem("Zoom In", null,
                "ZOOM_IN", submenuFont, controller);
        viewItemZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, ActionEvent.CTRL_MASK));
        viewItemZoomIn.setEnabled(true);

        viewItemZoomOut = createMenuItem("Zoom Out", null,
                "ZOOM_OUT", submenuFont, controller);
        viewItemZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
        viewItemZoomOut.setEnabled(true);

        viewItemZoomDefault = createMenuItem("Restore Default Zoom", null,
                "ZOOM_DEFAULT", submenuFont, controller);
        viewItemZoomDefault.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.CTRL_MASK));
        viewItemZoomDefault.setEnabled(true);

        statusBarBox = new JCheckBox("StatusBar");
        statusBarBox.setOpaque(false);
        statusBarBox.setFocusable(false);
        statusBarBox.setFont(submenuFont);
        statusBarBox.setPreferredSize(new Dimension(100, 20));
        statusBarBox.setSelected(false);

        statusBarBox.addActionListener(controller);
        viewMenu.add(viewZoom);
        viewMenu.addSeparator();
        viewZoom.add(viewItemZoomIn);
        viewZoom.add(viewItemZoomOut);
        viewZoom.addSeparator();
        viewZoom.add(viewItemZoomDefault);
        viewMenu.add(statusBarBox);

        return viewMenu;
    }

    private JMenuItem createMenuItem(String name, String pathToIcon, String actionCommand, Font submenuFont, ActionController controller) {
        JMenuItem menuItem = new JMenuItem(name, new ImageIcon(pathToIcon));
        menuItem.addActionListener(controller);
        menuItem.setActionCommand(actionCommand);
        menuItem.setFont(submenuFont);

        return menuItem;
    }

    private JFrame getFrame() {
        JFrame frame = new JFrame("Notepad MVC");
        frame.setLocation(300, 50);
        frame.setSize(1000, 650);

        return frame;
    }

}
