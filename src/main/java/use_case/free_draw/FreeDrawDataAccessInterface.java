package use_case.free_draw;

import entity.Project;

/**
 * DAO interface for the Free Draw Use Case.
 */
public interface FreeDrawDataAccessInterface {

    /**
     * Returns the project that is currently open.
     * @return the current project
     */
    Project getCurrentProject();

    /**
     * Saves the project.
     * @param project the project to save
     */
    void save(Project project);
}
