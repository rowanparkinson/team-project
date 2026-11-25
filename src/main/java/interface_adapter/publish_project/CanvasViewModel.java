package interface_adapter.publish_project;

import java.util.ArrayList;
import java.util.List;

public class CanvasViewModel {
    private final CanvasViewState canvasViewState;
    private final List<CanvasViewModelListener> listners = new ArrayList<>();

    public CanvasViewModel(CanvasViewState canvasViewState) {
        this.canvasViewState = canvasViewState;
    }

    public CanvasViewState getState() {
        return canvasViewState;
    }

    public void addListener(CanvasViewModelListener listener) {
        listners.add(listener);
    }

    public void notifyStateChanged() {
        for (CanvasViewModelListener listener : listners) {
            listener.stateChanged();
        }
    }
}
