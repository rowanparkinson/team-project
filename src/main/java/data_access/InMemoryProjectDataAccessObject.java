package data_access;

import entity.Project;
import use_case.free_draw.FreeDrawDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory implementation of the DAO for storing projects.
 */
public class InMemoryProjectDataAccessObject implements FreeDrawDataAccessInterface {

    private final Map<String, Project> projects = new HashMap<>();

    private Project currentProject;

    /**
     * Sets the project that is currently open.
     * @param project the project to open
     */
    public void setCurrentProject(Project project) {
        this.currentProject = project;
        if (project != null) {
            projects.put(project.getId(), project);
        }
    }

    @Override
    public Project getCurrentProject() {
        return currentProject;
    }

    @Override
    public void save(Project project) {
        projects.put(project.getId(), project);
    }
}
