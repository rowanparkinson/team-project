package use_case.publish_project;

/**
 * Input Boundary for actions which are related to publishing project.
 */
public interface PublishProjectInputBoundary {

    /**
     * Executes the publishing project use case.
     * @param inputData the input data
     */
    public void execute(PublishProjectInputData inputData);
}
