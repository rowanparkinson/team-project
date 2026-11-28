package use_case.undo_redo;

import java.util.Objects;

import entity.Canvas;
import entity.Layer;

/**
 * Adds a layer to the canvas and supports undo.
 */
public class CreateLayerCommand implements EditCommand {
    private final Canvas canvas;
    private final Layer layer;
    private boolean applied;

    public CreateLayerCommand(Canvas canvas, Layer layer) {
        this.canvas = Objects.requireNonNull(
                canvas, "Canvas cannot be null.");
        this.layer = Objects.requireNonNull(
                layer, "Layer cannot be null.");
    }

    @Override
    public void execute() {
        if (applied) {
            throw new IllegalStateException(
                    "Command has already been executed.");
        }

        if (canvas.getLayers().contains(layer)) {
            throw new IllegalStateException(
                    "Layer is already on the canvas.");
        }

        canvas.addLayer(layer);
        applied = true;
    }

    @Override
    public void undo() {
        if (!applied) {
            throw new IllegalStateException(
                    "Command has not been executed.");
        }

        if (!canvas.removeLayer(layer)) {
            throw new IllegalStateException(
                    "Layer is no longer on the canvas.");
        }

        applied = false;
    }
}