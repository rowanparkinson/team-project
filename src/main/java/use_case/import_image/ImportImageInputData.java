package use_case.import_image;

import java.io.File;

public class ImportImageInputData {
    private final File selectedFile;
    private final String currentProjectId;

    public ImportImageInputData(File selectedFile, String currentProjectId) {
        this.selectedFile = selectedFile;
        this.currentProjectId = currentProjectId;
    }

    String getProjectID() { return currentProjectId; }

    File getSelectedFile() { return selectedFile; }
}
