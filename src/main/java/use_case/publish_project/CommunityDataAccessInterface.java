package use_case.publish_project;

import entity.Project;

/**
 * DAO interface recording project in community data for the Publish Project Use Case.
 */
public interface CommunityDataAccessInterface {

    /**
     * Convert the project into savable data and save it.
     * @param project the project to save
     * @param id the id of the project to save
     * @param name the name of the project to save
     * @param description the description of the project to save
     */
    void addToCommunity(Project project, String id, String name, String description);

    /**
     * Modify the community file and update the project according to its id.
     * @param project the project to save
     * @param id the id of the project to save
     * @param name the name of the project to save
     * @param description the description of the project to save
     */
    void modifyInCommunity(Project project, String id, String name, String description);
}
