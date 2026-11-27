package use_case.import_image;

import entity.Canvas;

public class ImportImageOutputData {
    private final Canvas updatedCanvas;

    public ImportImageOutputData(Canvas updatedCanvas) {
        this.updatedCanvas = updatedCanvas;
    }

    public Canvas getUpdatedCanvas() {
        return updatedCanvas;
    }
}
