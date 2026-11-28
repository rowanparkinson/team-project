package interface_adapter.canvas;

import java.awt.*;
import entity.Canvas;
import entity.Layer;

public class CanvasState {
    private String projectId;
    private String projectTitle;
    private int canvasWidth;
    private int canvasHeight;
    private Canvas canvas;
    private Layer selectedLayer;
    private Color canvasColour;
    private String errorMessage;


    public String getProjectId() { return this.projectId; }
    public void setProjectId(String projectId) {this.projectId = projectId;}

    public String getTitle() {return projectTitle;}
    public void setTitle(String title) {this.projectTitle = title;}

    public int getCanvasWidth() {return canvasWidth;}
    public void setCanvasWidth(int width) {this.canvasWidth = width;}

    public int getCanvasHeight() {return canvasHeight;}
    public void setCanvasHeight(int height) {this.canvasHeight = height;}

    public Color getCanvasColour() {return canvasColour;}
    public void setCanvasColour(Color colour) {this.canvasColour = colour;}

    public String getErrorMessage() {return errorMessage;}
    public void setErrorMessage(String errorMessage) {this.errorMessage = errorMessage;}

    public Canvas getCanvas() {return canvas;}
    public void setCanvas(Canvas canvas) {this.canvas = canvas;}

    public Layer getselectedLayer() {return selectedLayer;}
}
