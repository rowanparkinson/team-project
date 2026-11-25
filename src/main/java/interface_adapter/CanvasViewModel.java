package interface_adapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CanvasViewModel {

    public static final String VIEW_NAME = "canvas";
    private final List<CanvasViewModelListener> listeners = new ArrayList<>();


    private CanvasState state = new CanvasState();
    public CanvasState getState() {
        return this.state;
    }
    public void setState(CanvasState state) {
        this.state = state;
    }

    public void addListener(CanvasViewModelListener listener) {
        listeners.add(listener);
    }
    public void notifyStateChanged() {
        for (CanvasViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }

    public void setTitle(String title) {
        state.setTitle(title);
    }
    public void setCanvasWidth(int width) {
        state.setCanvasWidth(width);
    }
    public void setCanvasHeight(int height) {
        state.setCanvasHeight(height);
    }
    public void setCanvasColour(Color color) {
        state.setCanvasColour(color);
    }
}
