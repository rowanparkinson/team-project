package interface_adapter.undo_redo;

import java.util.Objects;

import use_case.undo_redo.UndoRedoInputBoundary;

public class UndoRedoController {
    private final UndoRedoInputBoundary interactor;

    public UndoRedoController(UndoRedoInputBoundary interactor) {
        this.interactor = Objects.requireNonNull(
                interactor, "Interactor cannot be null.");
    }

    public void undo() {
        interactor.undo();
    }

    public void redo() {
        interactor.redo();
    }
}
