package com.apcsa_midterm;
public class Player {
    
    private int X;
    private int Y;
    private ObjectStorage playerObject;
    private Map map;

    //TODO: stop player from moving through walls

    public Player(int startX, int startY, String icon, Map map){
        this.map = map;
        playerObject = new ObjectStorage(startX, startY, icon, "player");
        this.X = startX;
        this.Y = startY;
        map.getObjects().add(playerObject);
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
        
        X = newX;
        Y = newY;
        playerObject.move(newX, newY);
    }
    
    public ObjectStorage getPlayerObject() {
        return playerObject;
    }
}
