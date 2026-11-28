package use_case.free_draw;

/**
 * Input Boundary for the Free Draw Use Case.
 */
public interface FreeDrawInputBoundary {

    /**
     * Executes the Free Draw Use Case.
     * @param freeDrawInputData the input data for this use case
     */
    void execute(FreeDrawInputData freeDrawInputData);
}
