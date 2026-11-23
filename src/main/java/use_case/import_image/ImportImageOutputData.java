package use_case.import_image;

import entity.Image;

public class ImportImageOutputData {
    private final Image importedImage;

    public ImportImageOutputData(Image image) {
        this.importedImage = image;
    }

    public Image getImage() {
        return importedImage;
    }
}
