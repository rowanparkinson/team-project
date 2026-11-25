package use_case.publish_project;

/**
 * The Output Data for the Publish Project Use Case.
 */
public class PublishProjectOutputData {
    private final String name;

    public PublishProjectOutputData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
