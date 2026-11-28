package use_case.publish_project;

import entity.Project;

public interface LoadedProjectDataAccessInterface {

    Project getProject(String projectId);
    boolean containsProject(String projectId);
    void saveProject(Project project);
    void unloadProject(String projectId);
}
