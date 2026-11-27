package use_case.import_image;

public interface ImportImageOutputBoundary {

    void prepareSuccessView(ImportImageOutputData outputData);

    void prepareFailView(String errorMessage);
}
