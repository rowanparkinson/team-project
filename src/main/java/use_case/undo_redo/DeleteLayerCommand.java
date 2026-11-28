package use_case.undo_redo;

import java.util.Objects;

import entity.Canvas;
import entity.Layer;

/**
 * Deletes a layer and restores its original index on undo.
 */
public class DeleteLayerCommand implements EditCommand {
    private final Canvas canvas;
    private final Layer layer;

    private int originalIndex;
    private boolean applied;

    public DeleteLayerCommand(Canvas canvas, Layer layer) {
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

        int index = canvas.getLayers().indexOf(layer);

        if (index < 0) {
            throw new IllegalStateException(
                    "Layer is not on the canvas.");
        }

        originalIndex = index;
        canvas.removeLayer(layer);
        applied = true;
    }

    @Override
    public void undo() {
        if (!applied) {
            throw new IllegalStateException(
                    "Command has not been executed.");
        }

        if (canvas.getLayers().contains(layer)) {
            throw new IllegalStateException(
                    "Layer is already on the canvas.");
        }

        int currentSize = canvas.getLayers().size();

        if (originalIndex > currentSize) {
            throw new IllegalStateException(
                    "Cannot restore the original layer index.");
        }

        canvas.addLayer(layer);

        if (originalIndex != currentSize) {
            canvas.moveLayer(currentSize, originalIndex);
        }

        applied = false;
    }
}