package com.apcsa_midterm;

import java.util.Scanner;

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
    private boolean isAttacking = false; // Flag to track if enemy is attacking
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
     * Checks if the enemy is currently colliding with the player.
     * @return true if the enemy's coordinates match the player's coordinates, false otherwise.
     */
    public boolean isCollidingWithPlayer() {
        return (this.X == player.getPlayerObject().getX() && 
                this.Y == player.getPlayerObject().getY());
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
        if (!canMove) {
            map.setActionMessage("You hear movement in the distance.");

            
        }
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

    if (isCollidingWithPlayer()) {
        attack();
        
    }
}
public static void wait(int ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        // Ignore
    }
}

    
    /**
     * Gets the enemy's object representation
     * @return ObjectStorage instance representing the enemy
     */
    public ObjectStorage getEnemyObject() {
        return enemyObject;
    }

    public void attack() {
        map.setActionMessage("You and the monster cross paths and engage in battle!");
        map.setActionOptions(4, "  [ A ] Attack");
        map.setActionOptions(5, "  [ I ] Inspect");
        map.setActionOptions(6, "  [ B ] Inventory");
        map.setActionOptions(7, "");
        player.getInput = false;
        isAttacking = true;
        
        // String[] attackScreen = ;
     // show attack screen

        map.setScreenType("attack");
        map.setScreenText(new String[]{
            " ┌─:battle───────────────────────────┐",
            " │░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░ │",
            " │ ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░│",
            " │  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  │",
            " │░  ░  ░  ░  ░           ░  ░  ░  ░ │",
            " │ ░  ░  ░  ░   ▓█▓▓██░    ░  ░  ░  ░│",
            " │  ░  ░  ░   ███░ ░ ▓██▓█  ░  ░  ░  │",
            " │░  ░  ░   █▓▓▓░ ░  ░   ░█     ░  ░ │",
            " │ ░  ░  ░   ██ ░\\__░  __/░█▓    ░  ░│",
            " │  ░  ░  ░   █░ <X> ░ <X> ░█░ ░  ░  │",
            " │░  ░  ░  ░  ▓█ ░ ┌──┐ ░ ░█▓   ░  ░ │",
            " │ ░  ░  ░     █░ ░│    ░▓█   ░  ░  ░│",
            " ├───────────░█▓███└─  ███───────────┤",
            " │          ░█▓▓        ░██          │",
            " │         ▓█▓           ░▓█░        │",
            " │       █▓░               ▓█▓░      │",
            " │                                   │",
            " │                                   │",
            " │                                   │",
            " └───────────────────────────────────┘" 
        });
    }

    private boolean[] attackPositions = new boolean[10];
    private boolean[] cursorPositions = new boolean[10];

    private void setCursorPosition(int position) {
        for (int i = 0; i < cursorPositions.length; i++) {
            cursorPositions[i] = false;
        }
         cursorPositions[position] = true;
    }

    private String getCursorPosition(int position, int side) {
        if (cursorPositions[position]) {
            if (side == 1) {
                return Colors.BLUE + ">" + Colors.RESET;   
            } else if (side == 2) {
                return Colors.BLUE + "<" + Colors.RESET;
            }
        } else {
            return " ";
        }
        return " "; // Default return statement
    }
    
    private void generateAttackPosition() {
        // for loop to update all attack positions to false
        for (int i = 0; i < attackPositions.length; i++) {
            attackPositions[i] = false;
        }
        // generate random number between 0 and 9  
        int random = (int) (Math.random() * 10);
        // set attack position to true
        attackPositions[random] = true;
    }
    private String getAttackPosition(int position) {
        if (attackPositions[position]) {
            return Colors.BG_RED + " " + Colors.RESET;
        } else if (position > 0 && attackPositions[position - 1]) {
            return Colors.BG_YELLOW + " " + Colors.RESET;
        } else if (position < attackPositions.length - 1 && attackPositions[position + 1]) {
            return Colors.BG_YELLOW + " " + Colors.RESET;
        } else {
            return Colors.DIM + "░" + Colors.RESET;
        }
    }
    
    
    // create scanner to read input from user
    private Scanner in = new Scanner(System.in);
    
    public void handleAttack(char input) {

        if (isAttacking) {

            if (Character.toLowerCase(input) == 'a') {
                for (int i = 0; i < 3; i++) {
                    generateAttackPosition();
                    for (int j = 0; j < cursorPositions.length; j++) {
                        wait(100);
                        setCursorPosition(j);
                        map.draw(null);
                        map.setScreenText(new String[]{
                            " ┌─:battle───────────────────────────┐",
                            " │░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░ │",
                            " │ ░  ░  ░  ░  ░  ░  ░  ░  ░     ░  ░│",
                            " │  ░  ░  ░  ░  ░  ░  ░  ░   DMG  ░  │",
                            " │░  ░  ░        ░  ░  ░  ░  ┌─┐   ░ │",
                            " │ ░  ░   ▓█▓▓██     ░  ░  ░"+getCursorPosition(0, 1)+"│"+getAttackPosition(0)+ "│"+getCursorPosition(0, 2)+"░  ░│",
                            " │  ░   ███░ ░ ▓██▓█  ░  ░  "+getCursorPosition(1, 1)+"│"+getAttackPosition(1)+ "│"+getCursorPosition(1, 2)+" ░  │",
                            " │░   █▓▓▓░ ░  ░   ░█  ░  ░ "+getCursorPosition(2, 1)+"│"+getAttackPosition(2)+ "│"+getCursorPosition(2, 2)+"  ░ │",
                            " │ ░   ██ ░\\__░  __/░█▓ ░   "+getCursorPosition(3, 1)+"│"+getAttackPosition(3)+ "│"+getCursorPosition(3, 2)+"░  ░│",
                            " │  ░   █░ <X> ░ <X> ░█  ░  "+getCursorPosition(4, 1)+"│"+getAttackPosition(4)+ "│"+getCursorPosition(4, 2)+" ░  │",
                            " │░  ░  ▓█ ░ ┌──┐ ░ ░█▓   ░ "+getCursorPosition(5, 1)+"│"+getAttackPosition(5)+ "│"+getCursorPosition(5, 2)+"  ░ │",
                            " │ ░     █░ ░│    ░▓█   ░  ░"+getCursorPosition(6, 1)+"│"+getAttackPosition(6)+ "│"+getCursorPosition(6, 2)+"░  ░│",
                            " ├─────░█▓███└─  ███────────"+getCursorPosition(7, 1)+"│"+getAttackPosition(7)+ "│"+getCursorPosition(7, 2)+"────┤",
                            " │    ░█▓▓        ░██       "+getCursorPosition(8, 1)+"│"+getAttackPosition(8)+ "│"+getCursorPosition(8, 2)+"    │",
                            " │   ▓█▓           ░▓█░     "+getCursorPosition(9, 1)+"│"+getAttackPosition(9)+ "│"+getCursorPosition(9, 2)+"    │",
                            " │ █▓░               ▓█▓░    └─┘     │",
                            " │                                   │",
                            " │                                   │",
                            " │                                   │",
                            " └───────────────────────────────────┘" 
                                            });
    

                    }
                    map.setActionMessage("You ready your weapon.");
                    map.setScreenText(new String[]{
                        " ┌─:battle───────────────────────────┐",
                        " │░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░ │",
                        " │ ░  ░  ░  ░  ░  ░  ░  ░  ░     ░  ░│",
                        " │  ░  ░  ░  ░  ░  ░  ░  ░   DMG  ░  │",
                        " │░  ░  ░        ░  ░  ░  ░  ┌─┐   ░ │",
                        " │ ░  ░   ▓█▓▓██     ░  ░  ░"+getCursorPosition(0, 1)+"│"+getAttackPosition(0)+ "│"+getCursorPosition(0, 2)+"░  ░│",
                        " │  ░   ███░ ░ ▓██▓█  ░  ░  "+getCursorPosition(1, 1)+"│"+getAttackPosition(1)+ "│"+getCursorPosition(1, 2)+" ░  │",
                        " │░   █▓▓▓░ ░  ░   ░█  ░  ░ "+getCursorPosition(2, 1)+"│"+getAttackPosition(2)+ "│"+getCursorPosition(2, 2)+"  ░ │",
                        " │ ░   ██ ░\\__░  __/░█▓ ░   "+getCursorPosition(3, 1)+"│"+getAttackPosition(3)+ "│"+getCursorPosition(3, 2)+"░  ░│",
                        " │  ░   █░ <X> ░ <X> ░█  ░  "+getCursorPosition(4, 1)+"│"+getAttackPosition(4)+ "│"+getCursorPosition(4, 2)+" ░  │",
                        " │░  ░  ▓█ ░ ┌──┐ ░ ░█▓   ░ "+getCursorPosition(5, 1)+"│"+getAttackPosition(5)+ "│"+getCursorPosition(5, 2)+"  ░ │",
                        " │ ░     █░ ░│    ░▓█   ░  ░"+getCursorPosition(6, 1)+"│"+getAttackPosition(6)+ "│"+getCursorPosition(6, 2)+"░  ░│",
                        " ├─────░█▓███└─  ███────────"+getCursorPosition(7, 1)+"│"+getAttackPosition(7)+ "│"+getCursorPosition(7, 2)+"────┤",
                        " │    ░█▓▓        ░██       "+getCursorPosition(8, 1)+"│"+getAttackPosition(8)+ "│"+getCursorPosition(8, 2)+"    │",
                        " │   ▓█▓           ░▓█░     "+getCursorPosition(9, 1)+"│"+getAttackPosition(9)+ "│"+getCursorPosition(9, 2)+"    │",
                        " │ █▓░               ▓█▓░    └─┘     │",
                        " │                                   │",
                        " │                                   │",
                        " │                                   │",
                        " └───────────────────────────────────┘" 
                                        });
                                                            map.draw(null);
                }
            } else if (Character.toLowerCase(input) == 'i') {
                map.setActionMessage(".");
            } else if (Character.toLowerCase(input) == 'b') {
                map.setActionMessage("You open your bag.");
            }
        }
       
    }
}
