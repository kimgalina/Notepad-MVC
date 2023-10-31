import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javax.swing.JButton;

/**
 * TabsController manages unsaved changes and associated files for each tab in the Viewer.
 */
public class TabsController implements DocumentListener {
    private List<Boolean> unsavedChangesPerTab;
    private List<File> filesPerTabs;
    private Viewer viewer;
    private JButton closeBtn;
    private boolean isFileOpening;

    /**
    * Constructs a TabsController for the given Viewer.
    *
    * @param viewer The Viewer instance this controller is associated with.
    */
    public TabsController(Viewer viewer) {
        unsavedChangesPerTab = new ArrayList<>();
        filesPerTabs = new ArrayList<>();
        this.viewer = viewer;
        isFileOpening = false;
    }


    /**
     * Gets the list of unsaved changes per tab.
     *
     * @return A list containing Boolean values indicating unsaved changes for each tab.
     */
    public List<Boolean> getUnsavedChangesPerTab() {
        return unsavedChangesPerTab;
    }

    /**
    * Gets the list of associated files per tab.
    *
    * @return A list of File objects representing the files associated with each tab.
    */
    public List<File> getFilesPerTabs() {
        return filesPerTabs;
    }

    /**
    * Sets the flag indicating whether a file is being opened in the Viewer.
    *
    * @param value True if a file is being opened, false otherwise.
    */
    public void setIsFileOpening(boolean value) {
        isFileOpening = value;
    }

   /**
    * Handles an insert update in the document for the current tab.
    *
    * @param e The DocumentEvent representing the insert update.
    */
    @Override
    public void insertUpdate(DocumentEvent e) {
        int currentTabIndex = viewer.getCurrentTabIndex();
        if(!isFileOpening && !unsavedChangesPerTab.get(currentTabIndex)) {
            setValueInToList(unsavedChangesPerTab, currentTabIndex, true);
            viewer.setDotInTab(currentTabIndex);
        }
    }

   /**
    * Handles a remove update in the document for the current tab.
    *
    * @param e The DocumentEvent representing the remove update.
    */
    @Override
    public void removeUpdate(DocumentEvent e) {
        int currentTabIndex = viewer.getCurrentTabIndex();
        File currentOpenFile = filesPerTabs.get(currentTabIndex);

        if(!unsavedChangesPerTab.get(currentTabIndex) && currentOpenFile != null) {
            setValueInToList(unsavedChangesPerTab, currentTabIndex, true);
            viewer.setDotInTab(currentTabIndex);

        } else if (currentOpenFile == null) {
            viewer.setCurrentContent();
            if(viewer.getCurrentTextAreaContent().equals("")) {
                setValueInToList(unsavedChangesPerTab, currentTabIndex, false);
                viewer.removeDotInTab(currentTabIndex);
            }
        }
    }

   /**
    * Handles a changed update in the document for the current tab.
    *
    * @param e The DocumentEvent representing the changed update.
    */
    @Override
    public void changedUpdate(DocumentEvent e) {
        System.out.println("changed content");
    }


    /**
     * Fills a list with a specific value up to the specified index.
     *
     * @param list  The list to be filled.
     * @param size  The desired size of the list.
     * @param index The index up to which the list should be filled.
     * @param value The value to fill in the list.
     * @param <T>   The type of elements in the list.
     */
    public <T> void fillList(List<T> list, int size, int index, T value) {
        if(index == list.size()) {
            list.add(index, value);

        } else {
            for(int i = list.size(); i < size ; i++) {
                list.add(i, null);
            }

            list.set(index, value);
        }
    }

    /**
     * Sets a specific value in the list at the given index, expanding the list if necessary.
     *
     * @param list           The list in which to set the value.
     * @param currentTabIndex The index at which to set the value.
     * @param value          The value to set.
     * @param <T>            The type of elements in the list.
     */
    public <T> void setValueInToList(List<T> list, int currentTabIndex, T value) {
        if(currentTabIndex < list.size()) {
            list.set(currentTabIndex, value);
        } else {
            fillList(list, currentTabIndex + 1, currentTabIndex, value);
        }
    }

}
