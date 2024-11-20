package com.apcsa_midterm;

public class ObjectStorage {
    private int x;
    private int y;
    private String icon;
    private String type;
    private String itemName;
    private String itemType;
    private int itemProperty;

    public ObjectStorage(int x, int y, String icon, String type) {
        this.x = x;
        this.y = y;
        this.icon = icon;
        this.type = type;

        this.itemName = null;
        this.itemType = null;
        this.itemProperty = 0;
    } // default constructor

    public ObjectStorage(int x, int y, String icon, String type, 
        
        String itemName ,
        String itemType, 
        int itemProperty) { // extra parameters if is a item

        
        this.x = x;
        this.y = y;
        if (itemType.equals("weapon")) {
            this.icon = Colors.BRIGHT_RED + icon + Colors.RESET;
        } else if (itemType.equals("armor")) {
            this.icon = Colors.BLUE + icon  + Colors.RESET; 
        } else if (itemType.equals("potion")) {
            this.icon = Colors.BRIGHT_GREEN + icon + Colors.RESET;
        }
        this.type = type;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemProperty = itemProperty;
    } // item constructor

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

    public String getItemName() {
        return itemName;
    }
    public String getItemType() {
        return itemType;
    }

    public int getItemProperty() {
        return itemProperty;
    }
}
