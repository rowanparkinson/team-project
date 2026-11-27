package use_case.import_image;

public interface ImportImageOutputBoundary {

    void prepareImportImageSuccessView(ImportImageOutputData outputData);

    void prepareImportImageFailView(String errorMessage);
}
