package interface_adapter.publish_project;

/**
 * The state for the Canvas View Model.
 */
public class CanvasViewState {
    private String projectId;
    private String publishName;
    private String publishDescription;
    private String publishMessage = "";

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String id) {
        this.projectId = id;
    }

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String name) {
        this.publishName = publishName;
    }

    public String getPublishDescription() {
        return publishDescription;
    }

    public void setPublishDescription(String description) {
        this.publishDescription = publishDescription;
    }

    public String getPublishMessage() {
        return publishMessage;
    }

    public void setPublishMessage(String message) {
        this.publishMessage = message;
    }
}
