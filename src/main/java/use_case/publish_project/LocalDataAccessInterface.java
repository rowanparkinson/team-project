package use_case.publish_project;

import entity.Project;

public interface LocalDataAccessInterface {
    public Project findProject(String projectId);
}
