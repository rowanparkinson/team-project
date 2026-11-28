package entity;

import java.util.UUID;

public abstract class Layer {

    private String name;
    private boolean visible;
    private int x = 0;
    private int y = 0;
    private double transparency;
    private String id;

    public Layer(String name) {
        this.name = name;
        this.visible = true;
        this.transparency = 1.0;
        this.id = UUID.randomUUID().toString();
    }

    public Layer(String name, int x, int y) {
        this.name = name;
        this.visible = true;
        this.transparency = 1.0;
        this.x = x;
        this.y = y;
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void toggleLayer() {
        this.visible = !this.visible;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getTransparency() {
        return transparency;
    }

    public void adjustTransparency(double transparency) {
        if (transparency < 0.0 || transparency > 1.0) {
            throw new IllegalArgumentException(
                    "Transparency must be between 0.0 and 1.0"
            );
        }

        this.transparency = transparency;
    }

    public String getId() {
        return id;
    }
}
