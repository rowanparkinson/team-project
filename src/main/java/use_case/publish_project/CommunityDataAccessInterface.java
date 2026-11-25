package use_case.publish_project;

import entity.Project;

public interface CommunityDataAccessInterface {
    void addToCommunity(Project project, String id, String name, String description);
}
