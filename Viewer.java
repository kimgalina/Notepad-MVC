import javax.swing.JToolBar;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
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
import javax.swing.text.PlainDocument;

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

import javax.swing.JList;
import javax.swing.JDialog;
import javax.swing.border.TitledBorder;
import javax.swing.text.Document;

public class Viewer {

    private JFileChooser fileChooser;
    private JFrame frame;
    private ActionController controller;
    private WindowController windowController;
    private JTabbedPane tabPane;
    private Font contentFont;
    private Font submenuFont;
    private Font menuFont;
    private Font dialogFont;
    private JTextArea currentContent;
    private JMenuItem viewItemZoomIn;
    private JMenuItem viewItemZoomOut;
    private JMenuItem viewItemZoomDefault;
    private JCheckBox statusBarBox;
    private Font fontZoom;
    private JPanel statusPanel;
    private JLabel statusLabel;
    private JDialog goDialog;
    private JDialog findDialog;
    private JDialog fontDialog;

    public Viewer() {
        frame = getFrame();
        controller = new ActionController(this);
        windowController = new WindowController(controller,this);
        contentFont = new Font("Consolas", Font.PLAIN, 22);
        menuFont = new Font("Tahoma", Font.BOLD, 20);
        submenuFont = new Font("Tahoma", Font.PLAIN, 16);
        dialogFont = new Font("Tahoma", Font.PLAIN, 12);
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
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
        frame.setIconImage(new ImageIcon("images/notepad.png").getImage());
    }

    public void createNewTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea content = new JTextArea();
        content.setFont(contentFont);
        content.getDocument().addDocumentListener(controller);
        JScrollPane scrollPane = new JScrollPane(content);

        panel.add(scrollPane, BorderLayout.CENTER);

        tabPane.addTab(null, panel);
        int tabIndex = tabPane.indexOfComponent(panel);
        tabPane.setTabComponentAt(tabIndex, createCustomTabComponent("Untitled.txt"));
    }

    public JTabbedPane getTabPane() {
        return tabPane;
    }

    public int getCurrentTabIndex() {
        return tabPane.getSelectedIndex();
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

    public void openFindDialog() {
        findDialog = createDialog("Find", false, 480, 170);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel label = new JLabel(new ImageIcon("images/find.png"));
        label.setBounds(5, 15, 50, 30);
        label.setFont(dialogFont);

        JTextField textField = new JTextField();
        textField.setBounds(60, 20, 270, 20);

        JLabel directionLabel = new JLabel("Direction");
        directionLabel.setBounds(215, 60, 70, 20);
        directionLabel.setFont(dialogFont);

        ButtonGroup direction = new ButtonGroup();
        JRadioButton upButton = createRadioButton("Up", false, 180, 85, 60, 20);
        JRadioButton downButton = createRadioButton("Down", true, 240, 85, 60, 20);
        direction.add(upButton);
        direction.add(downButton);

        JCheckBox caseSensitiveButton = new JCheckBox("Case sensitive");
        caseSensitiveButton.setBounds(30, 80, 100, 25);
        caseSensitiveButton.setFont(dialogFont);
        caseSensitiveButton.setFocusable(false);

        FindDialogController dialogController = new FindDialogController(this, textField, downButton, caseSensitiveButton);

        JButton findButton = createDialogButton("Find", "Find", 350, 20, 90, 25);
        findButton.setEnabled(false);
        findButton.addActionListener(dialogController);

        TextFieldController textFieldController = new TextFieldController(textField, findButton);
        textField.getDocument().addDocumentListener(textFieldController);

        JButton cancelButton = createDialogButton("Cancel", "Cancel", 350, 78, 90, 25);
        cancelButton.addActionListener(dialogController);

        panel.add(label);
        panel.add(textField);
        panel.add(directionLabel);
        panel.add(upButton);
        panel.add(downButton);
        panel.add(findButton);
        panel.add(cancelButton);
        panel.add(caseSensitiveButton);

        findDialog.add(panel);
        findDialog.setVisible(true);
    }

    private JRadioButton createRadioButton(String name, boolean isSelected, int x, int y, int width, int height) {
        JRadioButton radioButton = new JRadioButton(name, isSelected);
        radioButton.setBounds(x, y, width, height);
        radioButton.setFont(dialogFont);
        radioButton.setFocusable(false);
        return radioButton;
    }

    public void closeFindDialog() {
        findDialog.dispose();
    }

    public void openGoDialog() {
        goDialog = createDialog("Go to the line", true, 300, 150);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel label = new JLabel("The line number:");
        label.setBounds(15, 10, 200, 20);
        label.setFont(dialogFont);

        JTextField textField = new JTextField();
        textField.setBounds(15, 35, 250, 20);
        filterInput(textField);

        GoDialogController dialogController = new GoDialogController(this, textField);

        JButton goToButton = createDialogButton("Go", "Go", 70, 70, 90, 25);
        goToButton.addActionListener(dialogController);

        JButton cancelButton = createDialogButton("Cancel", "Cancel", 175, 70, 90, 25);
        cancelButton.addActionListener(dialogController);

        panel.add(label);
        panel.add(textField);
        panel.add(goToButton);
        panel.add(cancelButton);

        goDialog.add(panel);
        goDialog.setVisible(true);
    }

    public void closeGoDialog() {
        goDialog.dispose();
    }

    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(
            new JFrame(),
            errorMessage,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
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
        updateText(text);
        int tabIndex = tabPane.indexOfComponent(getCurrentPanel());
        renameTab(tabName, tabIndex);
    }

    public void updateText(String text) {
        currentContent.setText(text);
    }

    public void updateTextColor(Color color) {
        currentContent.setForeground(color);
    }

    public JDialog getFontDialog() {
        return fontDialog;
    }

    public void openFontDialog() {
        if (fontDialog != null) {
            fontDialog.setVisible(true);
            return;
        }

        FontController fontController = new FontController(this);
        ListSelectionController ListSelectionController = new ListSelectionController(this);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String[] styles = {"Regular", "Italic", "Bold", "Bold Italic"};
        Integer[] sizes = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72};

        int x = frame.getX();
        int y = frame.getY();
        fontDialog = new JDialog(frame, "Font", true);
        fontDialog.setSize(500, 500);
        fontDialog.setLocation(x + 150, y + 150);
        fontDialog.setLayout(null);
        fontDialog.setResizable(false);

        JLabel fontLabel = createLabel("Font:", 15, 10);
        String currentFont = currentContent.getFont().getFontName();
        JTextField fontTextField = createTextField("fontTextField", currentFont, 15, 27, 200, 30);
        JList<String> fontsList = new JList<>(fonts);
        fontsList.setSelectedValue(currentFont, true);
        fontsList.setName("FontsList");
        fontsList.addListSelectionListener(ListSelectionController);
        JScrollPane fontsScrollPane = new JScrollPane(fontsList);
        fontsScrollPane.setBounds(15, 57, 200, 150);

        JLabel styleLabel = createLabel("Style:", 235, 10);
        String currentStyle = getFontStyle(currentContent.getFont().getStyle());
        JTextField styleTextField = createTextField("styleTextField", currentStyle, 235, 27, 150, 30);
        JList<String> stylesList = new JList<>(styles);
        stylesList.setSelectedValue(currentStyle, true);
        stylesList.setName("StylesList");
        stylesList.addListSelectionListener(ListSelectionController);
        JScrollPane stylesScrollPane = new JScrollPane(stylesList);
        stylesScrollPane.setBounds(235, 57, 150, 150);

        JLabel sizeLabel = createLabel("Size:", 405, 10);
        int currentSize = currentContent.getFont().getSize();
        JTextField sizeTextField = createTextField("sizeTextField", String.valueOf(currentSize), 405, 27, 70, 30);
        JList<Integer> sizesList = new JList<>(sizes);
        sizesList.setSelectedValue(currentSize, true);
        sizesList.setName("SizesList");
        sizesList.addListSelectionListener(ListSelectionController);
        JScrollPane sizesScrollPane = new JScrollPane(sizesList);
        sizesScrollPane.setBounds(405, 57, 70, 150);

        JPanel panel = new JPanel();
        panel.setBounds(235, 217, 240, 100);
        panel.setLayout(new BorderLayout());
        panel.setName("samplePanel");
        TitledBorder border = BorderFactory.createTitledBorder("Sample");
        panel.setBorder(border);
        JLabel label = new JLabel("AaBbCc");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(currentContent.getFont());
        panel.add(label);

        JButton buttonOk = new JButton("Ok");
        buttonOk.setBounds(260, 420, 100, 30);
        buttonOk.addActionListener(fontController);
        buttonOk.setActionCommand("Ok");

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setBounds(380, 420, 100, 30);
        buttonCancel.addActionListener(fontController);
        buttonCancel.setActionCommand("Cancel");

        fontDialog.add(buttonOk);
        fontDialog.add(buttonCancel);
        fontDialog.add(fontLabel);
        fontDialog.add(fontTextField);
        fontDialog.add(fontsScrollPane);
        fontDialog.add(styleLabel);
        fontDialog.add(stylesScrollPane);
        fontDialog.add(styleTextField);
        fontDialog.add(sizeLabel);
        fontDialog.add(sizeTextField);
        fontDialog.add(sizesScrollPane);
        fontDialog.add(panel);
        fontDialog.setVisible(true);
    }

    public void setNewFontForTextArea(Font font) {
        currentContent.setFont(font);
    }

    public void hideFontDialog() {
        fontDialog.setVisible(false);
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
            if(controller.hasUnsavedChanges(currentTabIndex)) {
                showCloseTabMessage(currentTabIndex);
            } else {
                 deleteTab(currentTabIndex);
            }
       } else if(currentTabIndex == 0) { // checking if there are other tabs
           int tabCount = tabPane.getTabCount();
           if(controller.hasUnsavedChanges(currentTabIndex) && tabCount != 1) {
                showCloseTabMessage(currentTabIndex);

           } else if(controller.hasUnsavedChanges(currentTabIndex)) {
                showExitMessage();
           } else if(!controller.hasUnsavedChanges(currentTabIndex) && tabCount > 1){
                deleteTab(currentTabIndex);
           } else {
                System.exit(0);
           }
       }
   }

    public int showCloseTabMessage(int currentTabIndex) {
        int result = JOptionPane.showConfirmDialog(frame, "Do you want to save changes ? ", "Notepad MVC",
                                                   JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);

        if(result == JOptionPane.YES_OPTION) {
            controller.saveDocument();
            deleteTab(currentTabIndex);

        } else if (result == JOptionPane.NO_OPTION) {
            deleteTab(currentTabIndex);
        }
        return result;
    }

    public void showExitMessage() {
        int result = JOptionPane.showConfirmDialog(frame, "Do you want to save changes ? ", "Notepad MVC",
                                                   JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        if(result == JOptionPane.YES_OPTION) {
            controller.saveDocument();
            System.exit(0);
        } else if (result == JOptionPane.NO_OPTION) {
            System.exit(0);
        } else if (result == JOptionPane.CANCEL_OPTION) {
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }

    private void filterInput(JTextField textField) {
        PlainDocument doc = (PlainDocument) textField.getDocument();
        IntegerFilter filter = new IntegerFilter(this);
        doc.setDocumentFilter(filter);
    }

    private JButton createDialogButton(String name, String command, int x, int y, int width, int height) {
        JButton button = new JButton(name);

        button.setBounds(x, y, width, height);
        button.setFont(dialogFont);
        button.setFocusable(false);
        button.setActionCommand(command);

        return button;
    }

    private JDialog createDialog(String title, boolean isModal, int width, int height) {
        JDialog dialog = new JDialog(frame, title, isModal);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocation(frame.getX() + 150, frame.getY() + 150);
        dialog.setSize(width, height);

        return dialog;
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 50, 15);
        return label;
    }

    private JTextField createTextField(String name, String text, int x, int y, int width, int height) {
        JTextField textField = new JTextField(text);
        textField.setBounds(x, y, width, height);
        textField.setName(name);
        return textField;
    }

    private String getFontStyle(int style) {
        if (style == Font.PLAIN) {
            return "Regular";
        } else if (style == Font.ITALIC) {
            return "Italic";
        } else if (style == Font.BOLD) {
            return "Bold";
        } else {
            return "Bold Italic";
        }
    }

    private void deleteTab(int tabIndex) {
        tabPane.removeTabAt(tabIndex);
        controller.removeFromList(controller.getUnsavedChangesPerTab(), tabIndex);
        controller.removeFromList(controller.getFilesPerTabs(), tabIndex);
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
        viewItemZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.CTRL_MASK));
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
        frame.setLocation(300, 15);
        frame.setSize(1000, 800);

        return frame;
    }

}
