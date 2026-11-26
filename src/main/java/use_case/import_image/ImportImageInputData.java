package use_case.import_image;

import java.io.File;

public class ImportImageInputData {
    private final File selectedFile;
    private final long currentProjectId;

    public ImportImageInputData(File selectedFile, long currentProjectId) {
        this.selectedFile = selectedFile;
        this.currentProjectId = currentProjectId;
    }

    long getProjectID() { return currentProjectId; }

    File getSelectedFile() { return selectedFile; }
}
