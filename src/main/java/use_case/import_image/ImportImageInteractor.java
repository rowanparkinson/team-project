package use_case.import_image;

public class ImportImageInteractor implements ImportImageInputBoundary {
    private final ImportImageDataAccessInterface imageDataAccess;
    private final ImportImageOutputBoundary presenter;

    public ImportImageInteractor(ImportImageDataAccessInterface imageDataAccess,
                                 ImportImageOutputBoundary presenter) {
        this.imageDataAccess = imageDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ImportImageInputData inputData) {

    }
}
