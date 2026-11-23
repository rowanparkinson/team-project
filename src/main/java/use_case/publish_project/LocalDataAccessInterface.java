package use_case.publish_project;

import entity.Project;

public interface LocalDataAccessInterface {
    // TODO: Change Object into Project according to implementation of Use Case 10
    public Object findProject(long projectId);
}
