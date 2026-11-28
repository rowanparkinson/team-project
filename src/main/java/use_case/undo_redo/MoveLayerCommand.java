package use_case.undo_redo;

import java.util.Objects;

import entity.Layer;

/**
 * Moves a layer and restores its previous position on undo.
 */
public class MoveLayerCommand implements EditCommand {
    private final Layer layer;
    private final double newX;
    private final double newY;

    private double oldX;
    private double oldY;
    private boolean initialized;
    private boolean applied;

    public MoveLayerCommand(Layer layer, double newX, double newY) {
        this.layer = Objects.requireNonNull(
                layer, "Layer cannot be null.");

        if (!Double.isFinite(newX) || !Double.isFinite(newY)) {
            throw new IllegalArgumentException(
                    "Coordinates must be finite.");
        }

        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void execute() {
        if (applied) {
            throw new IllegalStateException(
                    "Command has already been executed.");
        }

        if (!initialized) {
            oldX = layer.getX();
            oldY = layer.getY();
            initialized = true;
        }

        layer.setPosition(newX, newY);
        applied = true;
    }

    @Override
    public void undo() {
        if (!applied) {
            throw new IllegalStateException(
                    "Command has not been executed.");
        }

        layer.setPosition(oldX, oldY);
        applied = false;
    }
}