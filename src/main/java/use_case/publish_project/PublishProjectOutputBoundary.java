package use_case.publish_project;

/**
 * Output Boundary for actions which are related to publishing project.
 */
public interface PublishProjectOutputBoundary {

    /**
     * Prepares the success view for the Publishing Project Use Case.
     * @param outputData the output data.
     */
    public void prepareSuccessView(PublishProjectOutputData outputData);

    /**
     * Prepares the fail view for the Publishing Project Use Case.
     * @param error explains the failure.
     */
    public void prepareFailView(String error);
}
