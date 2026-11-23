package interface_adapter.publish_project;

import java.util.ArrayList;
import java.util.List;

public class ProjectViewModel {
    private final ProjectViewState projectViewState;
    private final List<ProjectViewModelListener> listners = new ArrayList<>();

    public ProjectViewModel(ProjectViewState projectViewState) {
        this.projectViewState = projectViewState;
    }

    public ProjectViewState getState() {
        return projectViewState;
    }

    public void addListener(ProjectViewModelListener listener) {
        listners.add(listener);
    }

    public void notifyChange() {
        for (ProjectViewModelListener listener : listners) {
            listener.stateChanged();
        }
    }
}
