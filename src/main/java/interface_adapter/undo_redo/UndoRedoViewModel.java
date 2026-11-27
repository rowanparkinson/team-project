package interface_adapter.undo_redo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UndoRedoViewModel {
    private final PropertyChangeSupport support =
            new PropertyChangeSupport(this);

    private boolean canUndo;
    private boolean canRedo;
    private String message = "";

    public boolean canUndo() {
        return canUndo;
    }

    public boolean canRedo() {
        return canRedo;
    }

    public String getMessage() {
        return message;
    }
    public void update(boolean canUndo, boolean canRedo, String message) {
        this.canUndo = canUndo;
        this.canRedo = canRedo;
        this.message = message;

        support.firePropertyChange("state", null, this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
