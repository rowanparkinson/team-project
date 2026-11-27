package use_case.undo_redo;

/**
 * An editing action that can be executed and undone.
 */
public interface EditCommand {
    void execute();
    void undo();
}
