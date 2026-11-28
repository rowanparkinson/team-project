package use_case.free_draw;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Output Data for the Free Draw Use Case.
 */
public class FreeDrawOutputData {

    private final String layerId;
    private final String colour;
    private final double width;
    private final List<Point2D.Double> points;

    public FreeDrawOutputData(String layerId, String colour, double width, List<Point2D.Double> points) {
        this.layerId = layerId;
        this.colour = colour;
        this.width = width;
        this.points = points;
    }

    public String getLayerId() {
        return layerId;
    }

    public String getColour() {
        return colour;
    }

    public double getWidth() {
        return width;
    }

    public List<Point2D.Double> getPoints() {
        return points;
    }
}
