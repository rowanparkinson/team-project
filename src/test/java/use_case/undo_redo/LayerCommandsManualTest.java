package use_case.undo_redo;

import entity.Canvas;
import entity.Layer;

/**
 * Manual checks for reversible layer operations.
 */
public class LayerCommandsManualTest {

    public static void main(String[] args) {
        Canvas canvas = new Canvas();
        EditHistory history = new EditHistory();

        Layer a = new Layer("A") { };
        Layer b = new Layer("B") { };
        Layer c = new Layer("C") { };

        canvas.addLayer(a);
        canvas.addLayer(b);

        // Create, undo, and redo.
        history.execute(new CreateLayerCommand(canvas, c));
        check(canvas.getLayers().size() == 3, "Create adds layer");

        history.undo();
        check(!canvas.getLayers().contains(c), "Undo removes new layer");

        history.redo();
        check(canvas.getLayers().get(2) == c, "Redo restores new layer");

        // Delete and restore the original order.
        history.execute(new DeleteLayerCommand(canvas, b));
        check(!canvas.getLayers().contains(b), "Delete removes layer");

        history.undo();
        check(canvas.getLayers().get(1) == b,
                "Undo delete restores original index");

        history.redo();
        check(!canvas.getLayers().contains(b), "Redo deletes again");

        history.undo();

        // Reorder, undo, and redo.
        history.execute(new ReorderLayerCommand(canvas, b, 2));
        check(canvas.getLayers().get(2) == b, "Reorder moves layer");

        history.undo();
        check(canvas.getLayers().get(1) == b, "Undo restores order");

        history.redo();
        check(canvas.getLayers().get(2) == b, "Redo restores new order");

        // Move, undo, and redo.
        a.setPosition(10, 20);
        history.execute(new MoveLayerCommand(a, 100, 200));
        check(a.getX() == 100 && a.getY() == 200, "Move updates position");

        history.undo();
        check(a.getX() == 10 && a.getY() == 20, "Undo restores position");

        history.redo();
        check(a.getX() == 100 && a.getY() == 200, "Redo restores move");

        // A new edit clears redo history.
        history.undo();
        history.execute(new MoveLayerCommand(a, 50, 60));
        check(!history.canRedo(), "New edit clears redo history");

        // Undo across two different command types.
        history.undo();
        history.undo();

        check(a.getX() == 10 && a.getY() == 20,
                "Mixed undo restores coordinates");
        check(canvas.getLayers().get(1) == b,
                "Mixed undo restores layer order");

        System.out.println("All layer command checks passed.");
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError("FAILED: " + description);
        }
        System.out.println("PASS: " + description);
    }
}