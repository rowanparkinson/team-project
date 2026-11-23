package interface_adapter.import_image;

import use_case.import_image.ImportImageInputBoundary;
import use_case.import_image.ImportImageInputData;

import java.io.File;

public class ImportImageController {

    private final ImportImageInputBoundary importImageUseCaseInteractor;
    private final String currentProjectId;

    public ImportImageController(ImportImageInputBoundary importImageUseCaseInteractor,
                                 String currentProjectId) {
        this.importImageUseCaseInteractor = importImageUseCaseInteractor;
        this.currentProjectId = currentProjectId;
    }

    public void execute(File selectedFile) {
        ImportImageInputData inputData = new ImportImageInputData(selectedFile, currentProjectId);
        importImageUseCaseInteractor.execute(inputData);

    }
}
