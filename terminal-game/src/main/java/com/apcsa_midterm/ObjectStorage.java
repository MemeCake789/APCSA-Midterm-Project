package com.apcsa_midterm;

public class ObjectStorage {
    private int x;
    private int y;
    private String icon;
    private String type;

    public ObjectStorage(int x, int y, String icon, String type) {
        this.x = x;
        this.y = y;
        this.icon = icon;
        this.type = type;
    }

    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getIcon() {
        return icon;
    }

    public String getType() {
        return type;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setType(String type) {
        this.type = type;
    }
}
