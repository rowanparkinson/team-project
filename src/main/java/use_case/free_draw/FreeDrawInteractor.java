package use_case.free_draw;

import entity.Drawing;
import entity.Layer;
import entity.Project;
import entity.Stroke;

import java.awt.geom.Point2D;

/**
 * The Free Draw Interactor.
 */
public class FreeDrawInteractor implements FreeDrawInputBoundary {
    private final FreeDrawDataAccessInterface projectDataAccessObject;
    private final FreeDrawOutputBoundary freeDrawPresenter;

    public FreeDrawInteractor(FreeDrawDataAccessInterface freeDrawDataAccessInterface,
                              FreeDrawOutputBoundary freeDrawOutputBoundary) {
        this.projectDataAccessObject = freeDrawDataAccessInterface;
        this.freeDrawPresenter = freeDrawOutputBoundary;
    }

    @Override
    public void execute(FreeDrawInputData freeDrawInputData) {
        final Project project = projectDataAccessObject.getCurrentProject();
        final Drawing drawing = findDrawing(project, freeDrawInputData.getLayerId());

        if (drawing == null) {
            freeDrawPresenter.prepareFailView("Please select a drawing layer.");
        }
        else if (freeDrawInputData.getPoints().isEmpty()) {
            freeDrawPresenter.prepareFailView("A stroke needs at least one point.");
        }
        else {
            try {
                final Stroke stroke = new Stroke(freeDrawInputData.getColour(), freeDrawInputData.getWidth());
                for (Point2D.Double point : freeDrawInputData.getPoints()) {
                    stroke.addPoint(point.x, point.y);
                }
                drawing.addStroke(stroke);
                projectDataAccessObject.save(project);

                final FreeDrawOutputData freeDrawOutputData = new FreeDrawOutputData(
                        drawing.getLayerId(), stroke.getColour(), stroke.getWidth(), stroke.getPoints());
                freeDrawPresenter.prepareSuccessView(freeDrawOutputData);
            }
            catch (IllegalArgumentException ex) {
                // Stroke rejects invalid colours and non-positive widths
                freeDrawPresenter.prepareFailView(ex.getMessage());
            }
        }
    }

    /**
     * Finds the drawing layer with the given id on the project's canvas.
     * @return the drawing, or null if there is no layer with that id or it isn't a drawing
     */
    private Drawing findDrawing(Project project, String layerId) {
        if (project == null || layerId == null) {
            return null;
        }
        for (Layer layer : project.getCanvas().getLayers()) {
            if (layerId.equals(layer.getLayerId()) && layer instanceof Drawing) {
                return (Drawing) layer;
            }
        }
        return null;
    }
}
