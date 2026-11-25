package interface_adapter.publish_project;

import use_case.publish_project.PublishProjectInputBoundary;
import use_case.publish_project.PublishProjectInputData;

public class PublishProjectController {
    private final PublishProjectInputBoundary publishProjectInputBoundary;

    public PublishProjectController(PublishProjectInputBoundary publishProjectInputBoundary) {
        this.publishProjectInputBoundary = publishProjectInputBoundary;
    }

    public void execute(String projectId, String name, String description) {
        PublishProjectInputData inputData = new PublishProjectInputData(projectId, name, description);
        publishProjectInputBoundary.execute(inputData);
    }
}
