package interface_adapter.publish_project;

import use_case.publish_project.PublishProjectOutputBoundary;
import use_case.publish_project.PublishProjectOutputData;

/**
 * The Presenter for the Publish Project Use Case.
 */
public class PublishProjectPresenter implements PublishProjectOutputBoundary {
    private final CanvasViewModel viewModel;

    public PublishProjectPresenter(CanvasViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(PublishProjectOutputData outputData) {
        CanvasViewState state = viewModel.getState();
        state.setPublishName(outputData.getName());
        state.setPublishMessage(outputData.getName() + " has been published!");
        viewModel.notifyStateChanged();
    }

    @Override
    public void prepareFailView(String error) {
        CanvasViewState state = viewModel.getState();
        state.setPublishMessage(error);
        viewModel.notifyStateChanged();
    }
}
