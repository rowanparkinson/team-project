package data_access;

import entity.Project;
import use_case.publish_project.LocalDataAccessInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SavedProjectDataAccess implements LocalDataAccessInterface {
    @Override
    public Object findProject(long projectId) {
        Map<Long, Object> projects = readData();
        return projects.get(projectId);
    }

    private Map<Long, Object> readData() {
        Map<Long, Object> projects = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("projects.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                long id = Long.parseLong(columns[0]);
                Object project = columns[2];
                projects.put(id, project);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }
}
