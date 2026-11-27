package use_case.undo_redo;

import java.util.Objects;

public class UndoRedoInteractor implements UndoRedoInputBoundary {
    private final EditHistory editHistory;
    private final UndoRedoOutputBoundary presenter;

    public UndoRedoInteractor(EditHistory editHistory,
                              UndoRedoOutputBoundary presenter) {
        this.editHistory = Objects.requireNonNull(
                editHistory, "Edit history cannot be null.");
        this.presenter = Objects.requireNonNull(
                presenter, "Presenter cannot be null.");
    }

    @Override
    public void undo() {
        if (editHistory.undo()) {
            presenter.prepareSuccessView(createOutputData());
        }
        else {
            presenter.prepareFailView(
                    "There are no actions to undo.", createOutputData());
        }
    }

    @Override
    public void redo() {
        if (editHistory.redo()) {
            presenter.prepareSuccessView(createOutputData());
        }
        else {
            presenter.prepareFailView(
                    "There are no actions to redo.", createOutputData());
        }
    }

    private UndoRedoOutputData createOutputData() {
        return new UndoRedoOutputData(
                editHistory.canUndo(), editHistory.canRedo());
    }
}
