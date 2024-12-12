package com.apcsa_midterm;



/**
 * Represents a player in the terminal game.
 * The Player class manages the player's position, inventory, health, and movement within the game map.
 */
public class Player {
    public int X;
    public int Y;
    private ObjectStorage playerObject;
    private Map map;
    public item[] inventory = new item[5];
    public int health;
    public int damageMultiplier;

    public int[][] levelPositions = {
        { 100, 100 },
        { 3, 1 },
        { 9, 2 },

    };

    public boolean getInput = true;

    /**
     * Constructs a new Player object with the given starting position, icon, map, and health.
     *
     * @param startX the starting x-coordinate of the player
     * @param startY the starting y-coordinate of the player
     * @param icon the icon to represent the player
     * @param map the game map the player is placed on
     * @param health the initial health of the player
     */
    public Player(int startX, int startY, String icon, Map map, int health) {
        this.map = map;
        playerObject = new ObjectStorage(startX, startY, icon, "player");
        this.X = startX;
        this.Y = startY;
        this.health = health;
        this.damageMultiplier = 5;
        map.getObjects().add(playerObject);
        updateInventoryDisplay();

    }
    /**
     * Updates the inventory display by displaying the player's inventory items.
    */
    private void updateInventoryDisplay() {
        String[] inventoryDisplay = {
            " ┌─:inventory─────────────────────┐",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " │                                │",
            " └────────────────────────────────┘"
        };

        
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                inventoryDisplay[i + 1] = " │ " + inventory[i].getIcon() + " " + inventory[i].getName() + "\t"+ inventory[i].displayProperty()+"\t│";
            }
        }
        map.inventory = inventoryDisplay;
    }

    /**
     * Checks if wall is at the specified coordinates.
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @return true if there is a wall at the specified coordinates, false otherwise
     */
    private boolean isWallAt(int x, int y) {
        for (ObjectStorage object : map.getObjects()) {
            if (object.getX() == x && object.getY() == y && object.getType().equals("map")) {
                return true;
            }
        }
        return map.isOnBorder(x, y);
    }

    /**
     * Handles the player's movement based on the input character.
     * Checks for walls, updates the player's position, and handles item pickup.
     * @param input the character representing the player's movement direction (w, s, a, d)
     */
    public void handleMovement(char input) {
        if (getInput) {
            int newX = X;
            int newY = Y;
        
   
                if (Character.toLowerCase(input) == 'w') {
                    map.setActionMessage("You walked up.");
                    newY--;
                } else if (Character.toLowerCase(input) == 's') {
                    map.setActionMessage("You walked down.");
                    newY++;
                } else if (Character.toLowerCase(input) == 'a') {
                    map.setActionMessage("You walked left.");
                    newX--;
                } else if (Character.toLowerCase(input) == 'd') {
                    map.setActionMessage("You walked right.");
                    newX++;
                }
            
            if (!isWallAt(newX, newY)) {
                for (ObjectStorage object : map.getObjects()) {
                    if (object.getX() == newX && object.getY() == newY && object.getType().equals("item")) {
                        // add item to inventory
                        addItem(object.getItemName(), object.getIcon(), object.getItemType(), object.getItemProperty());
                        // remove iem from map 
                        // I had to move the object out of bounds because idk how to delete it
                        object.move(-1000, -1000);
                    }
                }
                X = newX;
                Y = newY;
                playerObject.move(newX, newY);
            } else{
                map.setActionMessage("You tried to move, but bumped into a wall.");
            }
        }
       
    }
    public ObjectStorage getPlayerObject() {
        return playerObject;
    }

    /**
     * Adds an item to the player's inventory.
     * 
     * @param name The name of the item to add.
     * @param icon The icon representing the item.
     * @param type The type of the item (e.g. "weapon", "armor", "potion").
     * @param property The property value of the item.
     */
    public void addItem(String name, String icon, String type, int property) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = new item(name, icon, type, property);
                updateInventoryDisplay();
                map.setActionMessage("You picked up " + name + " and threw it into your bag.");
                break;

            }
        }
    }

    /**
     * Removes an item from the player's inventory.
     * 
     * @param name The name of the item to remove.
     */
    public void removeItem(String name) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getName().equals(name)) {
                inventory[i] = null;
                updateInventoryDisplay();
                break;
            }
        }
    }
}

/**
 * Represents an equipable item in the game.
 */
class item {
    private String name;
    private String icon;
    private String type;
    private int property;

    public item(String name, String icon, String type, int property) {
        this.name = name;
        this.icon = icon;
        this.type = type;
        this.property = property;

        if (type.equals("weapon")) {
            this.icon = Colors.BRIGHT_RED + this.icon + Colors.RESET;
        } else if (type.equals("armor")) {
            this.icon = Colors.BRIGHT_BLUE + this.icon + Colors.RESET;
        } else if (type.equals("potion")) {
            this.icon = Colors.BRIGHT_GREEN + this.icon + Colors.RESET;
        }
    }

    public String getName() { return name; }
    public String getIcon() { return icon; }
    public String getType() { return type; }
    public int getProperty() { return property; }
    public String displayProperty() { 
        if (type.equals("weapon")) {
            return Colors.RED + "! : " + property + Colors.RESET;
        } else if (type.equals("armor")) {
            return Colors.BLUE + "0 : " + property + Colors.RESET; 
        } else if (type.equals("potion")) {
            return Colors.BRIGHT_GREEN + "+ : "  + property + Colors.RESET;
        }
        return "";
    }
}
