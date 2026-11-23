package use_case.publish_project;

public class PublishProjectInputData {
    private final long projectId;
    private final String name;
    private final String description;

    public PublishProjectInputData(long projectId, String name, String description) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
