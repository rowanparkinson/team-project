package interface_adapter.free_draw;

import use_case.free_draw.FreeDrawInputBoundary;
import use_case.free_draw.FreeDrawInputData;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Controller for the Free Draw Use Case.
 */
public class FreeDrawController {
    private final FreeDrawInputBoundary freeDrawUseCaseInteractor;

    public FreeDrawController(FreeDrawInputBoundary freeDrawUseCaseInteractor) {
        this.freeDrawUseCaseInteractor = freeDrawUseCaseInteractor;
    }

    /**
     * Executes the Free Draw Use Case. Called by the View when the user releases the mouse.
     * @param layerId the id of the active drawing layer
     * @param colour the stroke colour as a hex code, e.g. "#FF0000"
     * @param width the stroke thickness
     * @param points the mouse positions recorded while the user was dragging
     */
    public void execute(String layerId, String colour, double width, List<Point2D.Double> points) {
        final FreeDrawInputData freeDrawInputData = new FreeDrawInputData(layerId, colour, width, points);

        freeDrawUseCaseInteractor.execute(freeDrawInputData);
    }
}
