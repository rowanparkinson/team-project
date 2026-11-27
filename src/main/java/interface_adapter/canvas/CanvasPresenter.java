package interface_adapter.canvas;

import use_case.import_image.ImportImageOutputBoundary;
import use_case.import_image.ImportImageOutputData;

public class CanvasPresenter implements ImportImageOutputBoundary {

    private final CanvasViewModel canvasViewModel;

    public CanvasPresenter(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }


    @Override
    public void prepareImportImageSuccessView(ImportImageOutputData outputData) {
        final CanvasState canvasState = canvasViewModel.getState();
        canvasState.setCanvas(outputData.getUpdatedCanvas());
        canvasState.setErrorMessage("");
        canvasViewModel.notifyStateChanged();
    }

    @Override
    public void prepareImportImageFailView(String errorMessage) {
        final CanvasState canvasState = canvasViewModel.getState();
        canvasState.setErrorMessage(errorMessage);
        canvasViewModel.notifyStateChanged();

    }
}
