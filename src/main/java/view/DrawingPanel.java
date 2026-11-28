package view;

import interface_adapter.free_draw.FreeDrawController;
import interface_adapter.free_draw.FreeDrawState;
import interface_adapter.free_draw.FreeDrawViewModel;
import interface_adapter.free_draw.FreeDrawViewModelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The drawing surface in the middle of the Canvas View.
 * Records the mouse while the user drags, shows the stroke as it's drawn,
 * and sends the finished stroke to the Free Draw controller when the mouse is released.
 */
public class DrawingPanel extends JPanel implements FreeDrawViewModelListener {

    private final FreeDrawViewModel freeDrawViewModel;
    private FreeDrawController freeDrawController;

    private boolean drawingEnabled;

    // The stroke the user is drawing right now (before the mouse is released)
    private final List<Point2D.Double> currentPoints = new ArrayList<>();

    public DrawingPanel(FreeDrawViewModel freeDrawViewModel) {
        this.freeDrawViewModel = freeDrawViewModel;
        freeDrawViewModel.addListener(this);

        final MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!drawingEnabled) {
                    return;
                }
                currentPoints.clear();
                currentPoints.add(new Point2D.Double(e.getX(), e.getY()));
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!drawingEnabled || currentPoints.isEmpty()) {
                    return;
                }
                addPointIfMoved(e.getX(), e.getY());
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!drawingEnabled || currentPoints.isEmpty()) {
                    return;
                }
                addPointIfMoved(e.getX(), e.getY());
                finishStroke();
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    /**
     * Adds a point unless it's the same as the last one (e.g. releasing the mouse without moving).
     */
    private void addPointIfMoved(double x, double y) {
        final Point2D.Double last = currentPoints.get(currentPoints.size() - 1);
        if (last.x != x || last.y != y) {
            currentPoints.add(new Point2D.Double(x, y));
        }
    }

    public void setFreeDrawController(FreeDrawController freeDrawController) {
        this.freeDrawController = freeDrawController;
    }

    /**
     * Turns drawing on or off, e.g. when the user picks the Draw tool or another tool.
     */
    public void setDrawingEnabled(boolean drawingEnabled) {
        this.drawingEnabled = drawingEnabled;
        setCursor(drawingEnabled
                ? Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)
                : Cursor.getDefaultCursor());
    }

    /**
     * Sends the finished stroke to the use case. If it succeeds, the presenter adds it to the
     * state and stateChanged() repaints it as a saved stroke.
     */
    private void finishStroke() {
        final List<Point2D.Double> points = new ArrayList<>(currentPoints);
        currentPoints.clear();

        if (freeDrawController != null) {
            final FreeDrawState state = freeDrawViewModel.getState();
            freeDrawController.execute(state.getActiveLayerId(), state.getCurrentColour(),
                    state.getCurrentWidth(), points);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);   // paints the background colour
        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final FreeDrawState state = freeDrawViewModel.getState();

        // Strokes that have been saved by the use case
        for (FreeDrawState.DrawnStroke stroke : state.getDrawnStrokes()) {
            paintStroke(g2, stroke.getColour(), stroke.getWidth(), stroke.getPoints());
        }

        // The stroke being drawn right now, so the user sees it while dragging
        if (!currentPoints.isEmpty()) {
            paintStroke(g2, state.getCurrentColour(), state.getCurrentWidth(), currentPoints);
        }

        g2.dispose();
    }

    /**
     * Draws one stroke by connecting its points into a Path2D.
     */
    private void paintStroke(Graphics2D g2, String colour, double width, List<Point2D.Double> points) {
        if (points.isEmpty()) {
            return;
        }
        g2.setColor(Color.decode(colour));

        // A single click has no line to draw, it will show a dot
        if (points.size() == 1) {
            final Point2D.Double p = points.get(0);
            g2.fill(new Ellipse2D.Double(p.x - width / 2, p.y - width / 2, width, width));
            return;
        }

        final Path2D.Double path = new Path2D.Double();
        path.moveTo(points.get(0).x, points.get(0).y);
        for (int i = 1; i < points.size(); i++) {
            path.lineTo(points.get(i).x, points.get(i).y);
        }

        // Round caps and joins make the line look smooth
        g2.setStroke(new BasicStroke((float) width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(path);
    }

    @Override
    public void stateChanged() {
        final FreeDrawState state = freeDrawViewModel.getState();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(), "Can't draw here", JOptionPane.WARNING_MESSAGE);
            state.setError(null);
        }
        repaint();
    }
}
