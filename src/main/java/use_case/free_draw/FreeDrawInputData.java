package use_case.free_draw;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The Input Data for the Free Draw Use Case.
 */
public class FreeDrawInputData {

    private final String layerId;
    private final String colour;
    private final double width;
    private final List<Point2D.Double> points;

    public FreeDrawInputData(String layerId, String colour, double width, List<Point2D.Double> points) {
        this.layerId = layerId;
        this.colour = colour;
        this.width = width;
        this.points = points == null ? new ArrayList<>() : new ArrayList<>(points);
    }

    String getLayerId() {
        return layerId;
    }

    String getColour() {
        return colour;
    }

    double getWidth() {
        return width;
    }

    List<Point2D.Double> getPoints() {
        return points;
    }
}
