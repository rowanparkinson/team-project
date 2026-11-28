package use_case.undo_redo;

import java.util.ArrayDeque;
import java.util.Deque;

public class EditHistory {
    private final Deque<EditCommand> undoStack = new ArrayDeque<>();
    private final Deque<EditCommand> redoStack = new ArrayDeque<>();

    /**
     * Executes a new command and records it after success.
     */
    public void execute(EditCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null.");
        }

        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public boolean undo() {
        if (!canUndo()) {
            return false;
        }

        EditCommand command = undoStack.peek();
        command.undo();
        undoStack.pop();
        redoStack.push(command);
        return true;
    }

    public boolean redo() {
        if (!canRedo()) {
            return false;
        }

        EditCommand command = redoStack.peek();
        command.execute();
        redoStack.pop();
        undoStack.push(command);
        return true;
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    /**
     * Clears history without changing the project.
     */
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}