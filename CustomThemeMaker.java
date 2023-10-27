import java.awt.Color;
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
            return new Color(24, 24, 24);
        }
    }

    public static Color getBackgroundColor(boolean isLightTheme)  {
        if (isLightTheme) {
            return new Color(40, 44, 52);
        } else {
            return new Color(240, 240, 240);
        }
    }

    public static Color getSecondBackgroundColor(boolean isLightTheme)  {
        if (isLightTheme) {
            return new Color(33, 37, 43);
        } else {
            return new Color(220, 220, 220);
        }
    }

    public static Color getAlternativeColor(boolean isLightTheme)  {
        if (isLightTheme) {
            return new Color(62, 68, 81);
        } else {
            return new Color(163, 184, 204);
        }
    }

    public static Color getFullTransparentColor(boolean isLightTheme)  {
        return new Color(0, 0, 0, 0);
    }

    public void refreshTheme() {
        UIManager.put("ScrollBar.background", backgroundColor);

        UIManager.put("Button.foreground", textColor);
        UIManager.put("Button.select", secondBackgroundColor);
        UIManager.put("Button.focus", fullTransparentColor);

        UIManager.put("Menu.foreground", textColor);
        UIManager.put("MenuItem.foreground", textColor);

        UIManager.put("TextArea.background", backgroundColor);
        UIManager.put("TextArea.foreground", textColor);
        UIManager.put("TextArea.selectionBackground", alternativeColor);
        UIManager.put("TextArea.selectionForeground", textColor);
        UIManager.put("TextField.background", backgroundColor);
        UIManager.put("TextField.foreground", textColor);

        UIManager.put("TabbedPane.background", backgroundColor);
        UIManager.put("TabbedPane.foreground", textColor);

        UIManager.put("Dialog.foreground", textColor);

        UIManager.put("List.background", backgroundColor);
        UIManager.put("List.foreground", textColor);

        UIManager.put("Label.foreground", textColor);

        UIManager.put("ComboBox.background", secondBackgroundColor);
        UIManager.put("ComboBox.foreground", textColor);
        UIManager.put("CheckBoxMenuItem.foreground", textColor);
        UIManager.put("CheckBox.foreground", textColor);

        UIManager.put("RadioButton.foreground", textColor);

        UIManager.put("TitledBorder.titleColor", textColor);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ColorUIResource getPrimary1() {
        return new ColorUIResource(textColor);
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
        return new ColorUIResource(textColor);
    }

    @Override
    public ColorUIResource getSecondary2() {
        return new ColorUIResource(Color.GRAY);
    }

    @Override
    public ColorUIResource getSecondary3() {
        return new ColorUIResource(secondBackgroundColor);
    }
}
