package use_case.undo_redo;

public interface UndoRedoOutputBoundary {

    void prepareSuccessView(UndoRedoOutputData outputData);

    void prepareFailView(String message, UndoRedoOutputData outputData);
}