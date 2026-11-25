package interface_adapter.publish_project;

import use_case.publish_project.PublishProjectInputBoundary;
import use_case.publish_project.PublishProjectInputData;

/**
 * The controller for the Publish Project Use Case.
 */
public class PublishProjectController {
    private final PublishProjectInputBoundary publishProjectInputBoundary;

    public PublishProjectController(PublishProjectInputBoundary publishProjectInputBoundary) {
        this.publishProjectInputBoundary = publishProjectInputBoundary;
    }

    /**
     * Executes the Login Use Case.
     * @param projectId the project id of the publishing project
     * @param name the password of the publishing project
     * @param description the description of the publishing project
     */
    public void execute(String projectId, String name, String description) {
        PublishProjectInputData inputData = new PublishProjectInputData(projectId, name, description);
        publishProjectInputBoundary.execute(inputData);
    }
}
