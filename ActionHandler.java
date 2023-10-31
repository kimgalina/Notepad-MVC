import java.awt.event.ActionEvent;

/**
 * The ActionHandler interface defines the contract for classes that handle specific actions triggered
 * by the user interface, such as menu item clicks or button presses. Classes implementing this interface
 * should provide the logic to respond to these actions by implementing the handleAction method.
 */
public interface ActionHandler {

    /**
     * Handles a specific action based on the provided action command and ActionEvent.
     *
     * @param command The action command associated with the action.
     * @param event   The ActionEvent object representing the user's action.
     */
    void handleAction(String command, ActionEvent event);
}
