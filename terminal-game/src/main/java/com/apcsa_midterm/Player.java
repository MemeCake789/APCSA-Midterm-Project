package com.apcsa_midterm;
public class Player {
    
    private int X;
    private int Y;
    private ObjectStorage playerObject;
    private Map map;
    
    public Player(int startX, int startY, String icon, Map map){
        this.map = map;
        playerObject = new ObjectStorage(startX, startY, icon, "player");
        this.X = startX;
        this.Y = startY;
        map.getObjects().add(playerObject);
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
        int newX = X;
        int newY = Y;
        
        switch(Character.toLowerCase(input)) {
            case 'w':
                newY--;
                break;
            case 's':
                newY++;
                break;
            case 'a':
                newX--;
                break;
            case 'd':
                newX++;
                break;
        }
        
        // Using Grid's isOnBorder method to check boundaries
        if (!isWallAt(newX, newY)) {
            X = newX;
            Y = newY;
            playerObject.move(newX, newY);
        }
    }
    
    public ObjectStorage getPlayerObject() {
        return playerObject;
    }
}
