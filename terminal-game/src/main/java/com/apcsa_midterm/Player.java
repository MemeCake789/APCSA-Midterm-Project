package com.apcsa_midterm;


public class Player {
    private int X;
    private int Y;
    private ObjectStorage playerObject;
    private Map map;
    private item[] inventory = new item[5];

    public boolean getInput = true;

    public Player(int startX, int startY, String icon, Map map) {
        this.map = map;
        playerObject = new ObjectStorage(startX, startY, icon, "player");
        this.X = startX;
        this.Y = startY;
        map.getObjects().add(playerObject);
        updateInventoryDisplay();
    }

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

    private boolean isWallAt(int x, int y) {
        for (ObjectStorage object : map.getObjects()) {
            if (object.getX() == x && object.getY() == y && object.getType().equals("map")) {
                return true;
            }
        }
        return map.isOnBorder(x, y);
    }

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
                        // I had to move the object out of bounds because idk
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
            return Colors.RED + "! :" + property + Colors.RESET;
        } else if (type.equals("armor")) {
            return Colors.BLUE + "0 : " + property + Colors.RESET; 
        } else if (type.equals("potion")) {
            return Colors.BRIGHT_GREEN + "+ : "  + property + Colors.RESET;
        }
        return "";
    }
}
