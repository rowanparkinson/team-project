package use_case.undo_redo;

import entity.Drawing;
import entity.Stroke;

public class UndoRedoTest {

    public static void main(String[] args) {
        Drawing drawing = new Drawing("Test drawing");
        EditHistory history = new EditHistory();
        TestPresenter presenter = new TestPresenter();

        UndoRedoInputBoundary interactor =
                new UndoRedoInteractor(history, presenter);

        // 1. Undo on empty history should report failure.
        interactor.undo();
        check(!presenter.success, "Empty undo reports failure");
        check(!presenter.data.canUndo()
                && !presenter.data.canRedo(), "History starts empty");

        // 2. Execute a new stroke command.
        Stroke first = new Stroke("#FF0000", 2.0);
        first.addPoint(10, 20);
        first.addPoint(30, 40);
        history.execute(new AddStrokeCommand(drawing, first));

        check(drawing.getStrokes().size() == 1, "Stroke added");

        // 3. Undo through the interactor.
        interactor.undo();

        check(presenter.success, "Undo reports success");
        check(drawing.isEmpty(), "Undo removes the stroke");
        check(!presenter.data.canUndo()
                && presenter.data.canRedo(), "Undo updates history state");

        // 4. Redo through the interactor.
        interactor.redo();

        check(presenter.success, "Redo reports success");
        check(drawing.getStrokes().size() == 1
                        && drawing.getStrokes().get(0) == first,
                "Redo restores the original stroke");
        check(presenter.data.canUndo()
                && !presenter.data.canRedo(), "Redo updates history state");

        // 5. A new edit after undo must clear redo history.
        interactor.undo();

        Stroke second = new Stroke("#0000FF", 3.0);
        second.addPoint(50, 60);
        history.execute(new AddStrokeCommand(drawing, second));

        check(!history.canRedo(), "New edit clears redo history");

        interactor.redo();

        check(!presenter.success, "Unavailable redo reports failure");
        check(drawing.getStrokes().size() == 1
                        && drawing.getStrokes().get(0) == second,
                "Unavailable redo leaves the drawing unchanged");

        System.out.println("All manual checks passed.");
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError("FAILED: " + description);
        }
        System.out.println("PASS: " + description);
    }

    /**
     * Captures interactor output without opening a GUI.
     */
    private static class TestPresenter implements UndoRedoOutputBoundary {
        private boolean success;
        private UndoRedoOutputData data;

        @Override
        public void prepareSuccessView(UndoRedoOutputData outputData) {
            success = true;
            data = outputData;
        }

        @Override
        public void prepareFailView(String message,
                                    UndoRedoOutputData outputData) {
            success = false;
            data = outputData;
        }
    }
}