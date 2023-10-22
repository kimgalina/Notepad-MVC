import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;

public class ListChangeListener implements PropertyChangeListener {
    private int elementIndex;
    private Viewer viewer;

    public ListChangeListener(Viewer viewer) {
        this.viewer = viewer;
    }
    public void setElementIndex(int index) {
        elementIndex = index;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("List was changed");

        String propertyName = evt.getPropertyName();
        Object newValue = evt.getNewValue();
        if ("add".equals(propertyName)) {
            System.out.println("Element added in index " + elementIndex + " : " + newValue);

        } else if ("set".equals(propertyName) && newValue.equals(true)) {
            JButton closeBtn = viewer.getCloseBtnFromTab(elementIndex);
            closeBtn.setText("\u2022");  

        } else if ("set".equals(propertyName) && newValue.equals(false)) {
            JButton closeBtn = viewer.getCloseBtnFromTab(elementIndex);
            closeBtn.setText("\u00d7");
        }

    }
}
