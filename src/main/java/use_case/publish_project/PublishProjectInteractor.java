package use_case.publish_project;

import entity.Project;

/**
 * The Publishing Project Interactor.
 */
public class PublishProjectInteractor implements PublishProjectInputBoundary {
    private final LocalDataAccessInterface localDataAccessObject;
    private final CommunityDataAccessInterface communityDataAccessObject;
    private final PublishProjectOutputBoundary publishProjectPresenter;

    public PublishProjectInteractor(LocalDataAccessInterface localDataAccessInterface,
                                    CommunityDataAccessInterface communityDataAccessInterface,
                                    PublishProjectOutputBoundary publishProjectOutputBoundary) {
        this.localDataAccessObject = localDataAccessInterface;
        this.communityDataAccessObject = communityDataAccessInterface;
        this.publishProjectPresenter = publishProjectOutputBoundary;
    }

    @Override
    public void execute(PublishProjectInputData inputData) {
        if (inputData.getName() == null || inputData.getName().isBlank()) {
            publishProjectPresenter.prepareFailView("Name cannot be empty.");
        } else if (inputData.getDescription() == null || inputData.getDescription().isBlank()) {
            publishProjectPresenter.prepareFailView("Description cannot be empty.");
        } else {
            Project project = getProject(inputData.getId());

            if (project == null) {
                publishProjectPresenter.prepareFailView("No projects has been found in saved files.");
            } else if (project.getIsPublished()) {
                publishProjectPresenter.prepareFailView("Project is already published.");
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
