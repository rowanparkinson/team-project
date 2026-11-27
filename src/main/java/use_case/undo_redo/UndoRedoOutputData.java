package use_case.undo_redo;

public class UndoRedoOutputData {
    private final boolean canUndo;
    private final boolean canRedo;

    public UndoRedoOutputData(boolean canUndo, boolean canRedo) {
        this.canUndo = canUndo;
        this.canRedo = canRedo;
    }

    public boolean canUndo() {
        return canUndo;
    }

    public boolean canRedo() {
        return canRedo;
    }
}