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
        textColor = isLightTheme ? new Color(205, 205, 205) : new Color(24, 24, 24);
        backgroundColor =  isLightTheme ? new Color(40, 44, 52) : new Color(240, 240, 240);
        secondBackgroundColor =  isLightTheme ? new Color(33, 37, 43) : new Color(220, 220, 220);
        alternativeColor =  isLightTheme ? new Color(62, 68, 81) : new Color(163, 184, 204);
        fullTransparentColor = new Color(0, 0, 0, 0);
    }

    public Color getTextColor()  {
        return textColor;
    }

    public Color getBackgroundColor()  {
        return backgroundColor;
    }

    public Color getSecondBackgroundColor()  {
        return secondBackgroundColor;
    }

    public Color getAlternativeColor()  {
        return alternativeColor;
    }

    public Color getFullTransparentColor()  {
        return fullTransparentColor;
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
