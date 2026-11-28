package use_case.publish_project;

import entity.Project;

/**
 * The Publishing Project Interactor.
 */
public class PublishProjectInteractor implements PublishProjectInputBoundary {
    private final LocalDataAccessInterface localDataAccessObject;
    private final CommunityDataAccessInterface communityDataAccessObject;
    private final LoadedProjectDataAccessInterface loadedProjectDataAccessObject;
    private final PublishProjectOutputBoundary publishProjectPresenter;

    public PublishProjectInteractor(LocalDataAccessInterface localDataAccessInterface,
                                    CommunityDataAccessInterface communityDataAccessInterface,
                                    LoadedProjectDataAccessInterface loadedProjectDataAccessInterface,
                                    PublishProjectOutputBoundary publishProjectOutputBoundary) {
        this.localDataAccessObject = localDataAccessInterface;
        this.communityDataAccessObject = communityDataAccessInterface;
        this.loadedProjectDataAccessObject = loadedProjectDataAccessInterface;
        this.publishProjectPresenter = publishProjectOutputBoundary;
    }

    @Override
    public void execute(PublishProjectInputData inputData) {
        if (inputData.getName() == null || inputData.getName().isBlank()) {
            publishProjectPresenter.prepareFailView("Name cannot be empty.");
        } else if (inputData.getDescription() == null || inputData.getDescription().isBlank()) {
            publishProjectPresenter.prepareFailView("Description cannot be empty.");
        } else {
            Project project = null;
            if (loadedProjectDataAccessObject.containsProject(inputData.getId())) {
                project = loadedProjectDataAccessObject.getProject(inputData.getId());
            }
            else
            {
                project = getProject(inputData.getId());
            }

            if (project == null) {
                publishProjectPresenter.prepareFailView("No projects has been found.");
            } else {
                if (project.getIsPublished()) {
                    republishProject(project, inputData.getId(), inputData.getName(), inputData.getDescription());
                }
                else {
                    publishProject(project, inputData.getId(), inputData.getName(), inputData.getDescription());
                }

                final PublishProjectOutputData outputData = new PublishProjectOutputData(project.getName());
                publishProjectPresenter.prepareSuccessView(outputData);
            }
        }
    }

    private Project getProject(String projectId) {
        return localDataAccessObject.findProject(projectId);
    }

    private void publishProject(Project project, String id, String name, String description) {
        project.updateIsPublished();
        communityDataAccessObject.addToCommunity(project, id, name, description);
    }

    private void republishProject(Project project, String id, String name, String description) {
        communityDataAccessObject.modifyInCommunity(project, id, name, description);
    }
}
