package use_case.undo_redo;

import java.util.Objects;

import entity.Canvas;
import entity.Layer;

/**
 * Changes a layer's stacking order and supports undo.
 */
public class ReorderLayerCommand implements EditCommand {
    private final Canvas canvas;
    private final Layer layer;
    private final int newIndex;

    private int oldIndex;
    private boolean applied;

    public ReorderLayerCommand(Canvas canvas, Layer layer, int newIndex) {
        this.canvas = Objects.requireNonNull(
                canvas, "Canvas cannot be null.");
        this.layer = Objects.requireNonNull(
                layer, "Layer cannot be null.");
        this.newIndex = newIndex;
    }

    @Override
    public void execute() {
        if (applied) {
            throw new IllegalStateException(
                    "Command has already been executed.");
        }

        int currentIndex = canvas.getLayers().indexOf(layer);
        int size = canvas.getLayers().size();

        if (currentIndex < 0) {
            throw new IllegalStateException(
                    "Layer is not on the canvas.");
        }

        if (newIndex < 0 || newIndex >= size) {
            throw new IndexOutOfBoundsException(
                    "New layer index is out of range.");
        }

        oldIndex = currentIndex;
        canvas.moveLayer(currentIndex, newIndex);
        applied = true;
    }

    @Override
    public void undo() {
        if (!applied) {
            throw new IllegalStateException(
                    "Command has not been executed.");
        }

        int currentIndex = canvas.getLayers().indexOf(layer);

        if (currentIndex < 0) {
            throw new IllegalStateException(
                    "Layer is not on the canvas.");
        }

        if (oldIndex < 0 || oldIndex >= canvas.getLayers().size()) {
            throw new IllegalStateException(
                    "Cannot restore the original layer index.");
        }

        canvas.moveLayer(currentIndex, oldIndex);
        applied = false;
    }
}