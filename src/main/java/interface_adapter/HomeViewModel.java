package interface_adapter;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel {
    public static final String VIEW_NAME = "Home";
    private final List<HomeViewModelListener> listeners = new ArrayList<>();
    private HomeState state = new HomeState();

    public HomeState getState() {
        return this.state;
    }

    public void setState(HomeState state) {
        this.state = state;
    }

    public void addListener(HomeViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (HomeViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}

