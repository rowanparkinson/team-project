package interface_adapter;

import java.awt.*;

/**
 * The state for the Canvas View Model.
 */
public class CanvasState {

    private String projectId;
    private String projectTitle;
    private String publishName;
    private String publishMessage = "";
    private int canvasWidth;
    private int canvasHeight;
    private Color canvasColour;

    public String getProjectId() {
        return projectId;
    }
    public void setProjectId(String id) {
        this.projectId = id;
    }

    public String getTitle() {return projectTitle;}
    public void setTitle(String title) {this.projectTitle = title;}

    public String getPublishName() {
        return publishName;
    }
    public void setPublishName(String name) {
        this.publishName = name;
    }

    public String getPublishMessage() {
        return publishMessage;
    }
    public void setPublishMessage(String message) {
        this.publishMessage = message;
    }

    public int getCanvasWidth() {return canvasWidth;}
    public void setCanvasWidth(int width) {this.canvasWidth = width;}

    public int getCanvasHeight() {return canvasHeight;}
    public void setCanvasHeight(int height) {this.canvasHeight = height;}

    public Color getCanvasColour() {return canvasColour;}
    public void setCanvasColour(Color colour) {this.canvasColour = colour;}
}
