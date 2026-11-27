package use_case.import_image;

import entity.Image;
import entity.Project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ImportImageInteractor implements ImportImageInputBoundary {
    private final ImportImageDataAccessInterface imageDataAccessObject;
    private final LocalDataAccessInterface localDataAccessObject;
    private final ImportImageOutputBoundary importImagePresenter;

    public ImportImageInteractor(ImportImageDataAccessInterface imageDataAccessObject,
                                 LocalDataAccessInterface localDataAccessObject,
                                 ImportImageOutputBoundary presenter) {
        this.imageDataAccessObject = imageDataAccessObject;
        this.localDataAccessObject = localDataAccessObject;
        this.importImagePresenter = presenter;
    }

    @Override
    public void execute(ImportImageInputData inputData) {
        File selectedFile = inputData.getSelectedFile();
        String projectId = inputData.getProjectID();

        if (!imageDataAccessObject.isValidImage(selectedFile)) {
            importImagePresenter.prepareFailView("Invalid image file.");
        }

        else {
            try {
                BufferedImage loadedImage = imageDataAccessObject.readImage(selectedFile);
                Path imagePath = imageDataAccessObject.saveImage(loadedImage, projectId);

                // edit findProject if Alex's implementation changes
                Project currentProject = localDataAccessObject.findProject(projectId);

                String imageName = "Layer " + (currentProject.getCanvas().getLayers().size() + 1);

                Image image = new Image(imageName, imagePath.toString(), loadedImage);

                currentProject.getCanvas().addLayer(image);

                ImportImageOutputData outputData = new ImportImageOutputData(currentProject.getCanvas());
                importImagePresenter.prepareSuccessView(outputData);

            } catch (IOException e) {
                importImagePresenter.prepareFailView("Failed to read or save image file.");
            }
        }
    }
}
