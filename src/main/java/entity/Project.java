package entity;

import java.util.List;
import java.util.UUID;

/**
 * An entity representing a project. Projects have a id, name, published status, holds canvas and palette.
 */
public class Project {
    private final String id; // unique id
    private String name;
    private boolean isPublished;
    private Canvas canvas;
    private ColourPalette palette;

    /**
     * Creates a new project with the given name.
     * @param name the project name
     */
    public Project(String name) {
        this.id = UUID.randomUUID().toString();
        canvas = new Canvas();
        isPublished = false;
        palette = new ColourPalette();

        if (name.isEmpty()) {
            this.name = "My Project";
        }
        else {
            this.name = name;
        }
    }

    public Project(String name, String id) {
        this.id = id;
        canvas = new Canvas();
        isPublished = false;
        palette = new ColourPalette();

        if (name.isEmpty()) {
            this.name = "My Project";
        }
        else {
            this.name = name;
        }
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Project name cannot be empty.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean getIsPublished() {
        return isPublished;
    }

    public void updateIsPublished() {
        isPublished = !isPublished;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public ColourPalette getPalette() {
        return palette;
    }

    public void setPalette(List<String> newColours) {
        palette.setColours(newColours);
    }

    public boolean isEmpty() {
        return canvas.isEmpty();
    }
}
