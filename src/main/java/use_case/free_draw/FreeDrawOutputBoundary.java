package use_case.free_draw;

/**
 * The Output Boundary for the Free Draw Use Case.
 */
public interface FreeDrawOutputBoundary {

    /**
     * Prepares the success view for the Free Draw Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(FreeDrawOutputData outputData);

    /**
     * Prepares the failure view for the Free Draw Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
