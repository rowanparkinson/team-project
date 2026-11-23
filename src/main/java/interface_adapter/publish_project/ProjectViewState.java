package interface_adapter.publish_project;

public class ProjectViewState {
    private long projectId;
    private String name;
    private String description;
    private String message = "";

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long id) {
        this.projectId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
