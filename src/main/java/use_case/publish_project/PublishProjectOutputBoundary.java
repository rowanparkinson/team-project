package use_case.publish_project;

public interface PublishProjectOutputBoundary {
    public void prepareSuccessView(PublishProjectOutputData outputData);

    public void prepareFailView(String error);
}
