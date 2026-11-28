package interface_adapter.import_image;

import interface_adapter.canvas.CanvasState;
import interface_adapter.canvas.CanvasViewModel;
import use_case.import_image.ImportImageOutputBoundary;
import use_case.import_image.ImportImageOutputData;

public class ImportImagePresenter implements ImportImageOutputBoundary {

    private final CanvasViewModel canvasViewModel;

    public ImportImagePresenter(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }


    @Override
    public void prepareSuccessView(ImportImageOutputData outputData) {
        final CanvasState canvasState = canvasViewModel.getState();
        canvasState.setCanvas(outputData.getUpdatedCanvas());
        canvasState.setErrorMessage("");
        canvasViewModel.notifyStateChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final CanvasState canvasState = canvasViewModel.getState();
        canvasState.setErrorMessage(errorMessage);
        canvasViewModel.notifyStateChanged();

    }
}
