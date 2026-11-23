package interface_adapter.publish_project;

import use_case.publish_project.PublishProjectOutputBoundary;
import use_case.publish_project.PublishProjectOutputData;

public class PublishProjectPresenter implements PublishProjectOutputBoundary {
    private final ProjectViewModel viewModel;

    public PublishProjectPresenter(ProjectViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(PublishProjectOutputData outputData) {
        ProjectViewState state = viewModel.getState();
        state.setName(outputData.getName());
        state.setMessage(outputData.getName() + " has been published!");
        viewModel.notifyChange();
    }

    @Override
    public void prepareFailView(String error) {
        ProjectViewState state = viewModel.getState();
        state.setMessage(error);
        viewModel.notifyChange();
    }
}
