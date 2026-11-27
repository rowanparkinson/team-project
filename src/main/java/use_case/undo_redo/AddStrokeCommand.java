package use_case.undo_redo;

import java.util.Objects;

import entity.Drawing;
import entity.Stroke;

public class AddStrokeCommand implements EditCommand {
    private final Drawing drawing;
    private final Stroke stroke;
    private boolean applied;

    public AddStrokeCommand(Drawing drawing, Stroke stroke) {
        this.drawing = Objects.requireNonNull(
                drawing, "Drawing cannot be null.");
        this.stroke = Objects.requireNonNull(
                stroke, "Stroke cannot be null.");

        if (drawing.getStrokes().contains(stroke)) {
            throw new IllegalArgumentException(
                    "Stroke is already in the drawing.");
        }
    }

    @Override
    public void execute() {
        if (applied) {
            throw new IllegalStateException(
                    "Command has already been executed.");
        }

        if (drawing.getStrokes().contains(stroke)) {
            throw new IllegalStateException(
                    "Stroke is already in the drawing.");
        }

        drawing.addStroke(stroke);
        applied = true;
    }

    @Override
    public void undo() {
        if (!applied) {
            throw new IllegalStateException(
                    "Command has not been executed.");
        }

        if (!drawing.removeStroke(stroke)) {
            throw new IllegalStateException(
                    "Stroke is no longer in the drawing.");
        }

        applied = false;
    }
}
