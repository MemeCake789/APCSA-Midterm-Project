package com.apcsa_midterm;

/**
 * The ObjectStorage class represents a game object with position, appearance, and item properties.
 * 
 * There are two constructors:
 * 1. A basic constructor for general game objects
 * 2. An extended constructor for inventory items with additional properties
 */


public class ObjectStorage {
    private int x;
    private int y;
    private String icon;
    private String type;
    private String itemName;
    private String itemType;
    private int itemProperty;

    /**
     * Constructs an ObjectStorage instance with the specified coordinates, icon, and type.
     * The item-related properties (name, type, and property) are initialized to null or 0.
     *
     * @param x The x-coordinate of the object.
     * @param y The y-coordinate of the object.
     * @param icon The icon representation of the object.
     * @param type The type of the object.
     */
    public ObjectStorage(int x, int y, String icon, String type) {
        this.x = x;
        this.y = y;
        this.icon = icon;
        this.type = type;

        this.itemName = null;
        this.itemType = null;
        this.itemProperty = 0;
    } // default constructor

    /**
     * Constructs an ObjectStorage instance with the specified coordinates, icon, type, and item-related properties.
     * this constructor. is specific to inventory items.
     * 
     * @param x The x-coordinate of the object.
     * @param y The y-coordinate of the object.
     * @param icon The icon representation of the object.
     * @param type The type of the object.
     * @param itemName The name of the item.
     * @param itemType The type of the item (e.g. "weapon", "armor", "potion").
     * @param itemP The property of the item.
     */
    public ObjectStorage(int x, int y, String icon, String type, 
        
        String itemName ,
        String itemType, 
        int itemP) { // extra parameters if is a item

        
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
        this.itemProperty = itemP;
    } // item constructor

    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    // Getters
    
    /**
     * Gets the x-coordinate of the object.
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the object.
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the icon representation of the object.
     * @return The icon string.
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Gets the type of the object.
     * @return The object type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the x-coordinate of the object.
     * @param x The new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the object.
     * @param y The new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the icon representation of the object.
     * @param icon The new icon string.
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Sets the type of the object.
     * @param type The new object type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the name of the item.
     * @return The item name.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets the type of the item.
     * @return The item type.
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * Gets the property value of the item.
     * @return The item property value.
     */
    public int getItemProperty() {
        return itemProperty;
    }
}