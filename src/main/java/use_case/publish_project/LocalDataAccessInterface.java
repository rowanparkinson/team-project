package use_case.publish_project;

import entity.Project;

/**
 * DAO interface getting project from local data for the Publish Project Use Case.
 */
public interface LocalDataAccessInterface {

    /**
     * Convert the saved data into project and return it.
     * @param projectId the project id of the project for publishing
     * @return project of the matching id saved in the local data
     */
    public Project findProject(String projectId);
}
