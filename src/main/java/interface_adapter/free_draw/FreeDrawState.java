package interface_adapter.free_draw;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The state for the Free Draw View Model.
 */
public class FreeDrawState {
    // What the user has currently chosen in the toolbar
    private String activeLayerId;
    private String currentColour = "#000000";
    private double currentWidth = 3.0;

    // Every stroke that has been drawn
    private final List<DrawnStroke> drawnStrokes = new ArrayList<>();

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

    public List<DrawnStroke> getDrawnStrokes() {
        return Collections.unmodifiableList(drawnStrokes);
    }

    public void addDrawnStroke(String colour, double width, List<Point2D.Double> points) {
        drawnStrokes.add(new DrawnStroke(colour, width, points));
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

    /**
     * The display data for one stroke
     */
    public static class DrawnStroke {
        private final String colour;
        private final double width;
        private final List<Point2D.Double> points;

        public DrawnStroke(String colour, double width, List<Point2D.Double> points) {
            this.colour = colour;
            this.width = width;
            this.points = new ArrayList<>(points);
        }

        public String getColour() {
            return colour;
        }

        public double getWidth() {
            return width;
        }

        public List<Point2D.Double> getPoints() {
            return Collections.unmodifiableList(points);
        }
    }
}
