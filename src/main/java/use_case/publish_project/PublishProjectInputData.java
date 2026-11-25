package use_case.publish_project;

public class PublishProjectInputData {
    private final String projectId;
    private final String name;
    private final String description;

    public PublishProjectInputData(String projectId, String name, String description) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
