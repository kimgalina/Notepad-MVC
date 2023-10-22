import java.util.ArrayList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeEvent;
import java.util.List;

class ListWithListener<E> extends ArrayList<E> {
    private List<ListChangeListener> listeners = new ArrayList<>();

    @Override
    public boolean add(E e) {
        boolean result = super.add(e);
        fireListChanged("add", e , size());
        return result;
    }

    @Override
    public void add(int index, E element) {
       super.add(index, element);
       fireListChanged("add", element, index);
    }
    
    @Override
    public E set(int index, E element) {
        fireListChanged("set", element, index);
        return super.set(index, element);
    }

    public void addListChangeListener(ListChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListChangeListener(ListChangeListener listener) {
        listeners.remove(listener);
    }

    private void fireListChanged(String command, E element, int index) {
        for (ListChangeListener listener : listeners) {
            PropertyChangeEvent event = new PropertyChangeEvent(this, command, null, element);// source , propertyName , oldValue , newValue
            listener.setElementIndex(index);
            listener.propertyChange(event);

        }
    }


}
