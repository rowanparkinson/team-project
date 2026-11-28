package data_access;

import entity.Project;
import use_case.LoadedProjectDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class LoadedProjectDataAccess implements LoadedProjectDataAccessInterface {
    private final Map<String, Project> loadedProjects = new HashMap<>();


    @Override
    public Project getProject(String projectId) {
        return loadedProjects.get(projectId);
    }

    @Override
    public boolean containsProject(String projectId) {
        return loadedProjects.containsKey(projectId);
    }

    @Override
    public void saveProject(Project project) {
        loadedProjects.put(project.getId(), project);
    }

    @Override
    public void unloadProject(String projectId) {
        loadedProjects.remove(projectId);
    }
}
