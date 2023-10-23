import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javax.swing.JButton;

public class TabsController implements DocumentListener {
    private List<Boolean> unsavedChangesPerTab;
    private List<File> filesPerTabs;
    private Viewer viewer;

    public TabsController(Viewer viewer) {
        unsavedChangesPerTab = new ArrayList<>();
        filesPerTabs = new ArrayList<>();
        this.viewer = viewer;
    }

    public List<Boolean> getUnsavedChangesPerTab() {
        return unsavedChangesPerTab;
    }

    public List<File> getFilesPerTabs() {
        return filesPerTabs;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        int currentTabIndex = viewer.getCurrentTabIndex();
        setValueInToList(unsavedChangesPerTab, currentTabIndex, true);
        JButton closeBtn = viewer.getCloseBtnFromTab(currentTabIndex);
        closeBtn.setText("\u2022");

    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        int currentTabIndex = viewer.getCurrentTabIndex();
        setValueInToList(unsavedChangesPerTab, currentTabIndex, true);
        JButton closeBtn = viewer.getCloseBtnFromTab(currentTabIndex);
        closeBtn.setText("\u2022");

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        System.out.println("changed content");
    }

    public <T> void fillList(List<T> list, int size, int index, T value) {
        if(index == list.size()) {
            list.add(index, value);

        } else { // index > list.size()
            for(int i = list.size(); i < size ; i++) {
                list.add(i, null);
            }
            list.set(index, value);
        }
    }

    public <T> void setValueInToList(List<T> list, int currentTabIndex, T value) {
        if(currentTabIndex < list.size()) {
            list.set(currentTabIndex, value);
        } else {
            fillList(list, currentTabIndex + 1, currentTabIndex, value);

        }
    }

}
