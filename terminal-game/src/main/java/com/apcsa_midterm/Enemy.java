package com.apcsa_midterm;

/**
 * Represents an enemy entity in the game that follows the player
 * Will move towards the player and chase it if it spots it ( If player is in same row or column )
 */
public class Enemy {
    private int X;                      // Current X coordinate of the enemy
    private int Y;                      // Current Y coordinate of the enemy
    private ObjectStorage enemyObject;  // Object representation of enemy in game
    private Map map;                    // Reference to game map
    private Player player;     
    private boolean canMove = false;     // Flag to track if enemy can move
    
    /**
     * Creates a new enemy at specified coordinates
     * @param startX Initial X coordinate
     * @param startY Initial Y coordinate
     * @param icon Visual representation of enemy
     * @param map Game map reference
     * @param player Player reference for tracking
     */
    public Enemy(int startX, int startY, String icon, Map map, Player player) {
        this.map = map;
        this.player = player;
        enemyObject = new ObjectStorage(startX, startY, icon, "enemy");
        this.X = startX;
        this.Y = startY;
        map.getObjects().add(enemyObject);
    }
    
    /**
     * Checks if there is a wall at specified coordinates
     * @param x X coordinate to check
     * @param y Y coordinate to check
     * @return true if wall exists at coordinates, false otherwise
     */
    private boolean isWallAt(int x, int y) {
        for (ObjectStorage object : map.getObjects()) {
            if (object.getX() == x && object.getY() == y && object.getType().equals("map")) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Updates enemy position to move towards the player
     * Implements simple chase AI that moves in both X and Y directions
     * Will not move through walls
     */
public void moveTowardsPlayer() {
    int playerX = player.getPlayerObject().getX();
    int playerY = player.getPlayerObject().getY();
    
    int newX = X;
    int newY = Y;

    
    if (playerX == X || playerY == Y) {
        canMove = true;
    }

    if (canMove){
        if (playerX < X && !isWallAt(X - 1, Y)) {
            newX = X - 1;
        } else if (playerX > X && !isWallAt(X + 1, Y)) {
            newX = X + 1;
        } else if (playerY < Y && !isWallAt(X, Y - 1)) {
            newY = Y - 1;
        } else if (playerY > Y && !isWallAt(X, Y + 1)) {
            newY = Y + 1;
        }
    }



    // Update position if a valid move was found
    if (newX != X || newY != Y) {
        X = newX;
        Y = newY;
        enemyObject.move(newX, newY);
    }
}

    
    /**
     * Gets the enemy's object representation
     * @return ObjectStorage instance representing the enemy
     */
    public ObjectStorage getEnemyObject() {
        return enemyObject;
    }
}
