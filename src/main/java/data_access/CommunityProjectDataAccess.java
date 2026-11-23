package data_access;

import entity.Project;
import use_case.publish_project.CommunityDataAccessInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CommunityProjectDataAccess implements CommunityDataAccessInterface {
    @Override
    public void addToCommunity(Project project, long id, String name, String description) {
        // TODO: Change how to save in Project in csv file using the implementation of Use Case 10
        writeData(project, id, name, description);
    }

    private void writeData(Project project, long id, String name, String description) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("communitydata.csv", true))) {
            // TODO: Change how to save in Project in csv file using the implementation of Use Case 10
            writer.write(project.toString() + "," + Long.toString(id) + "," + name + "," + description);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
