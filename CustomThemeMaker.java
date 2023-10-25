import java.awt.Color;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.ColorUIResource;
import javax.swing.UIManager;

public class CustomThemeMaker extends DefaultMetalTheme {

    private Color textColor;
    private Color backgroundColor;
    private Color secondBackgroundColor;
    private Color alternativeColor;
    private Color fullTransparentColor;

    public CustomThemeMaker(boolean isLightTheme) {
        textColor = getTextColor(isLightTheme);
        backgroundColor = getBackgroundColor(isLightTheme);
        secondBackgroundColor = getSecondBackgroundColor(isLightTheme);
        alternativeColor = getAlternativeColor(isLightTheme);
        fullTransparentColor = getFullTransparentColor(isLightTheme);
    }

    public static Color getTextColor(boolean isLightTheme)  {
        if (isLightTheme) {
            return new Color(205, 205, 205);
        } else {
            return Color.BLACK;
        }
    }

    public static Color getBackgroundColor(boolean isLightTheme)  {
        if (isLightTheme) {
            return new Color(40, 44, 52);
        } else {
            return Color.WHITE;
        }
    }

    public static Color getSecondBackgroundColor(boolean isLightTheme)  {
        if (isLightTheme) {
            return new Color(33, 37, 43);
        } else {
            return Color.WHITE;
        }
    }

    public static Color getAlternativeColor(boolean isLightTheme)  {
        if (isLightTheme) {
            return new Color(62, 68, 81);
        } else {
            return Color.GRAY;
        }
    }

    public static Color getFullTransparentColor(boolean isLightTheme)  {
        return new Color(0, 0, 0, 0);
    }

    public void refreshTheme() {
        //UIManager.put("Panel.background", backgroundColor); //frame panel color
        //UIManager.put("Panel.foreground", textColor); //frame panel text color
        //
        UIManager.put("ScrollBar.background", backgroundColor); //scroll background
        // //UIManager.put("ScrollBar.foreground", Color.GREEN); //nw
        // //UIManager.put("ScrollBar.thumb", Color.RED);
        //
        // UIManager.put("Button.background", backgroundColor);
        UIManager.put("Button.foreground", textColor);
        UIManager.put("Button.select", secondBackgroundColor);
        UIManager.put("Button.focus", fullTransparentColor);
        //
        // UIManager.put("MenuBar.background", secondBackgroundColor); //menubar color
        UIManager.put("Menu.foreground", textColor); //menubar text color
        // UIManager.put("MenuItem.background", secondBackgroundColor); //t half works VIEW not working
        UIManager.put("MenuItem.foreground", textColor); //t half works VIEW not working
        //
        // UIManager.put("ToolBar.background", backgroundColor); //tool bar color
        UIManager.put("TextArea.background", backgroundColor); //font color in notepad tabs
        UIManager.put("TextArea.foreground", textColor); //text color in notepad tabs
        UIManager.put("TextArea.selectionBackground", alternativeColor);
        UIManager.put("TextArea.selectionForeground", textColor);
        //
        UIManager.put("TextField.background", backgroundColor); //background color in fields
        UIManager.put("TextField.foreground", textColor); //font color in fields
        //
        UIManager.put("TabbedPane.background", backgroundColor); //text color of tabs
        UIManager.put("TabbedPane.foreground", textColor); //status bar font color and tabs in colorchooser
        // UIManager.put("TabbedPane.selected", secondBackgroundColor); //color of selected tab
        //
        // UIManager.put("Dialog.background", backgroundColor); //test
        UIManager.put("Dialog.foreground", textColor);
        //
        UIManager.put("List.background", backgroundColor); //background fileopener main window
        UIManager.put("List.foreground", textColor); //font color fileopener main window
        //
        // UIManager.put("Label.background", Color.RED); //nw
        UIManager.put("Label.foreground", textColor); //others font color below main window
        //
        // UIManager.put("EditorPane.background", backgroundColor);
        // UIManager.put("EditorPane.foreground", textColor);
        //
        UIManager.put("ComboBox.background", secondBackgroundColor);
        UIManager.put("ComboBox.foreground", textColor);
        UIManager.put("CheckBoxMenuItem.foreground", textColor);
        UIManager.put("CheckBox.foreground", textColor);

        //
        // UIManager.put("RadioButton.background", backgroundColor); //changes radiobuttons in colorchooser
        UIManager.put("RadioButton.foreground", textColor); //changes radiobuttons text in colorchooser

        UIManager.put("TitledBorder.titleColor", textColor);
        //
        //
        // //font chooser borders --- titleborder need dorabotka
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ColorUIResource getPrimary1() {
        return new ColorUIResource(secondBackgroundColor);
    }

    @Override
    public ColorUIResource getPrimary2() {
        return new ColorUIResource(alternativeColor);
    }

    @Override
    public ColorUIResource getPrimary3() {
        return new ColorUIResource(alternativeColor);
    }

    @Override
    public ColorUIResource getSecondary1() {
        return new ColorUIResource(Color.GRAY);
    }

    @Override
    public ColorUIResource getSecondary2() {
        return new ColorUIResource(secondBackgroundColor);
    }

    @Override
    public ColorUIResource getSecondary3() {
        return new ColorUIResource(secondBackgroundColor);
    }
}
