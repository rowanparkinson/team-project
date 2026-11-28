package interface_adapter.free_draw;

import use_case.free_draw.FreeDrawOutputBoundary;
import use_case.free_draw.FreeDrawOutputData;

/**
 * The Presenter for the Free Draw Use Case.
 */
public class FreeDrawPresenter implements FreeDrawOutputBoundary {

    private final FreeDrawViewModel freeDrawViewModel;

    public FreeDrawPresenter(FreeDrawViewModel freeDrawViewModel) {
        this.freeDrawViewModel = freeDrawViewModel;
    }

    @Override
    public void prepareSuccessView(FreeDrawOutputData outputData) {
        final FreeDrawState freeDrawState = freeDrawViewModel.getState();
        freeDrawState.addDrawnStroke(outputData.getColour(), outputData.getWidth(), outputData.getPoints());
        freeDrawState.setHasUnsavedChanges(true);
        freeDrawState.setError(null);
        freeDrawViewModel.notifyStateChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final FreeDrawState freeDrawState = freeDrawViewModel.getState();
        freeDrawState.setError(error);
        freeDrawViewModel.notifyStateChanged();
    }
}
