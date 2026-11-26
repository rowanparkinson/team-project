package interface_adapter.free_draw;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Free Draw View Model.
 */
public class FreeDrawState {
    // What the user has currently chosen in the toolbar
    private String activeLayerId;
    private String currentColour = "#000000";
    private double currentWidth = 3.0;

    // The stroke that was just added, so the View can paint it
    private String lastStrokeColour;
    private double lastStrokeWidth;
    private List<Point2D.Double> lastStrokePoints = new ArrayList<>();

    private boolean hasUnsavedChanges;
    private String error;

    public String getActiveLayerId() {
        return activeLayerId;
    }

    public void setActiveLayerId(String activeLayerId) {
        this.activeLayerId = activeLayerId;
    }

    public String getCurrentColour() {
        return currentColour;
    }

    public void setCurrentColour(String currentColour) {
        this.currentColour = currentColour;
    }

    public double getCurrentWidth() {
        return currentWidth;
    }

    public void setCurrentWidth(double currentWidth) {
        this.currentWidth = currentWidth;
    }

    public String getLastStrokeColour() {
        return lastStrokeColour;
    }

    public double getLastStrokeWidth() {
        return lastStrokeWidth;
    }

    public List<Point2D.Double> getLastStrokePoints() {
        return lastStrokePoints;
    }

    public void setLastStroke(String colour, double width, List<Point2D.Double> points) {
        this.lastStrokeColour = colour;
        this.lastStrokeWidth = width;
        this.lastStrokePoints = new ArrayList<>(points);
    }

    public boolean hasUnsavedChanges() {
        return hasUnsavedChanges;
    }

    public void setHasUnsavedChanges(boolean hasUnsavedChanges) {
        this.hasUnsavedChanges = hasUnsavedChanges;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
