package interface_adapter.undo_redo;

import java.util.Objects;

import use_case.undo_redo.UndoRedoOutputBoundary;
import use_case.undo_redo.UndoRedoOutputData;

public class UndoRedoPresenter implements UndoRedoOutputBoundary {
    private final UndoRedoViewModel viewModel;

    public UndoRedoPresenter(UndoRedoViewModel viewModel) {
        this.viewModel = Objects.requireNonNull(
                viewModel, "View model cannot be null.");
    }

    @Override
    public void prepareSuccessView(UndoRedoOutputData outputData) {
        viewModel.update(
                outputData.canUndo(),
                outputData.canRedo(),
                "");
    }

    @Override
    public void prepareFailView(String message,
                                UndoRedoOutputData outputData) {
        viewModel.update(
                outputData.canUndo(),
                outputData.canRedo(),
                message);
    }
}
