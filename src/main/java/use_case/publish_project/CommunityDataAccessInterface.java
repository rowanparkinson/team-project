package use_case.publish_project;

import entity.Project;

public interface CommunityDataAccessInterface {
    void addToCommunity(Project project, long id, String name, String description);
}
