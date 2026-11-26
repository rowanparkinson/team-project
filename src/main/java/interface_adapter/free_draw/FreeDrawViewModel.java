package interface_adapter.free_draw;

import java.util.ArrayList;
import java.util.List;

/**
 * The View Model for the Free Draw (canvas) View.
 */
public class FreeDrawViewModel {

    public static final String VIEW_NAME = "canvas";

    private final List<FreeDrawViewModelListener> listeners = new ArrayList<>();

    private FreeDrawState state = new FreeDrawState();

    public FreeDrawState getState() {
        return this.state;
    }

    public void setState(FreeDrawState state) {
        this.state = state;
    }

    public void addListener(FreeDrawViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (FreeDrawViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}
