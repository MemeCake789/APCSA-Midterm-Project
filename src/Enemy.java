package com.apcsa_midterm;

import java.io.IOException;
import java.util.Scanner;

public class Enemy {
    private int X; // Current X coordinate of the enemy
    private int Y; // Current Y coordinate of the enemy
    private ObjectStorage enemyObject; // Object representation of enemy in game
    private Map map; // Reference to game map
    private Player player;
    private boolean canMove = false; // Flag to track if enemy can move
    private boolean isAttacking = false; // Flag to track if enemy is attacking
    private int health;
    private String name;
    private int attackDamage = 0;
    private int damage = 0;

    /**
     * Constructs a new Enemy object with the specified starting coordinates, icon, game map, player reference, health, attack damage, and name.
     *
     * @param startX The starting X coordinate of the enemy.
     * @param startY The starting Y coordinate of the enemy.
     * @param icon The icon or visual representation of the enemy.
     * @param map The game map object.
     * @param player The player object.
     * @param health The initial health of the enemy.
     * @param damage The attack damage of the enemy.
     * @param name The name of the enemy.
     */
    public Enemy(int startX, int startY, String icon, Map map, Player player, int health, int damage, String name) {
        this.map = map;
        this.player = player;
        enemyObject = new ObjectStorage(startX, startY, icon, "enemy");
        this.X = startX;
        this.Y = startY;
        this.health = health;
        this.name = name;
        this.attackDamage = damage;
        map.getObjects().add(enemyObject);
    }

    /**
     * Checks if there is a wall object at the specified coordinates .
     *
     * @param x The X coordinate to check.
     * @param y The Y coordinate to check.
     * @return `true` if there is a wall object at the specified coordinates, `false` otherwise.
     */
    private boolean isWallAt(int x, int y) {
        for (ObjectStorage object: map.getObjects()) {
            if (object.getX() == x && object.getY() == y && object.getType().equals("map")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the enemy has the same postion as the player
     * @return true if the enemy is colliding with the player, false otherwise
     */
    public boolean isCollidingWithPlayer() {
        return (this.X == player.getPlayerObject().getX() &&
            this.Y == player.getPlayerObject().getY());
    }

    /**
     * Moves the enemy towards the player.
    */
    public void moveTowardsPlayer() {
        int playerX = player.getPlayerObject().getX();
        int playerY = player.getPlayerObject().getY();

        int newX = X;
        int newY = Y;


        if (playerX == X || playerY == Y) {
            if (!canMove) {
                map.setActionMessage("You hear movement in the distance."); // lets player know enemy is moving
            }
            canMove = true;
        }

        if (canMove) {
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
            X = 1000;
            Y = 1000;

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
        map.setScreenText(new String[] {
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

    /**
     * Sets the cursor position for the enemy's attack screen.
     * @param position the position to set the cursor to (0-9)
     */
    private void setCursorPosition(int position) {
        for (int i = 0; i < cursorPositions.length; i++) {
            cursorPositions[i] = false;
        }
        cursorPositions[position] = true;
    }

    /**
     * Retrieves the cursor position for the enemy's attack screen.
     *
     * @param position the position of the cursor (0-9)
     * @param side the side of the cursor (1 for left, 2 for right)
     * @return the cursor position as a string, with the appropriate color formatting
     */
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

    /**
     * Generates a random attack position on the ui for the enemy.
     */
    private void generateAttackPosition() {
        // for loop to update all attack positions to false
        for (int i = 0; i < attackPositions.length; i++) {
            attackPositions[i] = false;
        }
        // generate random number between 2 and 7
        int random = (int) (Math.random() * (7 - 2 + 1) + 2);
        // set attack position to true
        attackPositions[random] = true;
    }

    /**
     * Used for drawing the attack screen ui.
     * 
     * @param position the position to check (0-9)
     * @return the attack position as a string, with the appropriate color formatting
     */
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


private void setDamage(int damage) {
    this.damage = damage;
}

public int getDamage() {
    return damage;
}



/**
 * Handles the player's attack input during an enemy encounter.
 * This method generates random attack positions, checks for successful hits,
 * updates the enemy's health, and handles the enemy's counterattack.
 *
 * @param input the character input from the player ('a' to attack, 'i' to inspect, 'b' to use items)
 */
public void handleAttack(char input) {
    if (isAttacking) {
        if (Character.toLowerCase(input) == 'a') {
            for (int i = 0; i < 3; i++) {
                generateAttackPosition();
                for (int j = 0; j < cursorPositions.length; j++) {
                    wait(100);
                    setCursorPosition(j);
                    map.draw(null);
                    
                    map.setScreenText(new String[] {
" ┌─:battle───────────────────────────┐",
" │░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░ │",
" │ ░  ░  ░  ░  ░  ░  ░  ░  ░     ░  ░│",
" │  ░  ░  ░  ░  ░  ░  ░  ░   "+getDamage() +" !"+"  ░  │",
" │░  ░  ░        ░  ░  ░  ░  ┌─┐   ░ │",
" │ ░  ░   ▓█▓▓██     ░  ░  ░" + getCursorPosition(0, 1) + "│" + getAttackPosition(0) + "│" + getCursorPosition(0, 2) + "░  ░│",
" │  ░   ███░ ░ ▓██▓█  ░  ░  " + getCursorPosition(1, 1) + "│" + getAttackPosition(1) + "│" + getCursorPosition(1, 2) + " ░  │",
" │░   █▓▓▓░ ░  ░   ░█  ░  ░ " + getCursorPosition(2, 1) + "│" + getAttackPosition(2) + "│" + getCursorPosition(2, 2) + "  ░ │",
" │ ░   ██ ░\\__░  __/░█▓ ░   " + getCursorPosition(3, 1) + "│" + getAttackPosition(3) + "│" + getCursorPosition(3, 2) + "░  ░│",
" │  ░   █░ <X> ░ <X> ░█  ░  " + getCursorPosition(4, 1) + "│" + getAttackPosition(4) + "│" + getCursorPosition(4, 2) + " ░  │",
" │░  ░  ▓█ ░ ┌──┐ ░ ░█▓   ░ " + getCursorPosition(5, 1) + "│" + getAttackPosition(5) + "│" + getCursorPosition(5, 2) + "  ░ │",
" │ ░     █░ ░│    ░▓█   ░  ░" + getCursorPosition(6, 1) + "│" + getAttackPosition(6) + "│" + getCursorPosition(6, 2) + "░  ░│",
" ├─────░█▓███└─  ███────────" + getCursorPosition(7, 1) + "│" + getAttackPosition(7) + "│" + getCursorPosition(7, 2) + "────┤",
" │    ░█▓▓        ░██       " + getCursorPosition(8, 1) + "│" + getAttackPosition(8) + "│" + getCursorPosition(8, 2) + "    │",
" │   ▓█▓           ░▓█░     " + getCursorPosition(9, 1) + "│" + getAttackPosition(9) + "│" + getCursorPosition(9, 2) + "    │",
" │ █▓░               ▓█▓░    └─┘     │",
" │                                   │",
" │                                   │",
" │                                   │",
" └───────────────────────────────────┘"
                        });
                    try {
                        if (System.in.available() > 0) {
                            int keyCode = System.in.read();
                            if (keyCode == 10) { // 10 is the ASCII code for ENTER/newline
            
                                if(attackPositions[j]){
                                    setDamage(getDamage() + player.damageMultiplier);
                                    map.setActionMessage("You hit the enemy!");
                                } else if (j > 0 && attackPositions[j-1]){
                                    setDamage(getDamage() + player.damageMultiplier/2);
                                    map.setActionMessage("You cut the enemy!");
                                } else if (j < attackPositions.length-1 && attackPositions[j+1]){
                                    setDamage(getDamage() + player.damageMultiplier/2);
                                    map.setActionMessage("You cut the enemy!");
                                } else{
                                    map.setActionMessage("You missed!");
                                }
                            }
                        }
                    } catch (IOException e) {
                        // Continue if there's an error
                    }

                    map.draw(null);
                }
            }
            health = health - getDamage();
            map.setActionMessage("You attacked the enemy for " + getDamage() + " damage!");
            for (int i = 0; i < 5; i++) {
                 map.setScreenText(new String[]{
" ┌─:battle───────────────────────────┐",
" │░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░ │",
" │ ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░│",
" │  ░  ░  ░  ░  ░/ ░  ░  ░  ░  ░  ░  │",
" │░  ░  ░  ░  ░      - \\  ░  ░  ░  ░ │",
" │ ░  ░  ░  ░ / ▓█▓▓██░    \\  ░  ░  ░│",
" │  ░  ░  ░/- ███░ ░ ▓██▓█- ░  ░  ░  │",
" │░  ░  ░   █▓▓▓░─── ░ ───█   \\ ░  ░ │",
" │ ░  ░  ░\\  ██ ░/▄\\░  /▄\\░█▓    ░  ░│",
" │  ░  ░  ░-  █░ \\▀/ ░ \\▀/ ░█░ ░  ░  │",
" │░  ░  ░  ░\\ ▓█ ░ ┌──┐ ░ ░█▓   ░  ░ │",
" │ ░  ░  ░     █░ ░│    ░▓█   ░  ░  ░│",
" ├───────────░█▓███│   ███──/────────┤",
" │      /   ░█▓▓   └─   ░██  -       │",
" │         ▓█▓    -    \\ ░▓█░ /      │",
" │    \\- █▓░    /          ▓█▓░      │",
" │           /      -  \\       /     │",
" │      -                 -          │",
" │                                   │",
" └───────────────────────────────────┘" 
            });
            map.draw(null);
            wait(100);
            map.setScreenText(new String[]{
" ┌─:battle───────────────────────────┐",
" │░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░ │",
" │ ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░│",
" │  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  │",
" │░  ░  ░  ░  ░           ░  ░  ░  ░ │",
" │ ░  ░  ░  ░   ▓█▓▓██░    ░  ░  ░  ░│",
" │  ░  ░  ░   ███░ ░ ▓██▓█  ░  ░  ░  │",
" │░  ░  ░   █▓▓▓░─── ░ ───█  ░  ░  ░ │",
" │ ░  ░  ░   ██ ░/▄\\░  /▄\\░█▓    ░  ░│",
" │  ░  ░  ░   █░ \\▀/ ░ \\▀/ ░█░ ░  ░  │",
" │░  ░  ░  ░  ▓█ ░ ┌──┐ ░ ░█▓   ░  ░ │",
" │ ░  ░  ░     █░ ░│    ░▓█   ░  ░  ░│",
" ├───────────░█▓███│   ███───────────┤",
" │          ░█▓▓   └─   ░██          │",
" │         ▓█▓           ░▓█░        │",
" │       █▓░               ▓█▓░      │",
" │                                   │",
" │                                   │",
" │                                   │",
" └───────────────────────────────────┘" 
            });
            map.draw(null);
                        wait(100);
                        

            }
           
            setDamage(0);


            map.setScreenText(new String[] {
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
    
            map.draw(null);


            if(health <= 0){

                map.setActionMessage("You defeated the " + name + "! (ENDING IN 5 SECONDS)");
                map.setScreenText(new String[]{
" ┌─:battle───────────────────────────┐",
" │░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░ │",
" │ ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░│",
" │  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  │",
" │░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░ │",
" │ ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░  ░│",
" │  ░  ░                 ░  ░  ░  ░  │",
" │░  ░  ░ ░   █▒ ▒▓▓▒█░  ▒   ░  ░  ░ │",
" │ ░  ░     ▒▓▒▓▓░ ░ ▓█▒▓█    ░  ░  ░│",
" │  ░  ░ ░   █▒ █ ░  ░   ░▒    ░  ░  │",
" │░  ░  ░ ░   █░░ \\/░  \\/ ░█▓   ░  ░ │",
" │ ░  ░    ░  ▒█░ /\\ ░ /\\  ░█░   ░  ░│",
" ├─────────────█ ░ ┌──┐ ░ ░▒▓────────┤",
" │         ░█  ▓  ░└─   ░▓█   ░      │",
" │      ▓▒░▒▓▒ ▓░█▒    ▒█▒           │",
" │    ▒▓░  ▓    ▒       ░█▒ ░▓█░     │",
" │                            ▓█▓    │",
" │                            ░      │",
" │                                   │",
" └───────────────────────────────────┘" 
                    
                });

                map.nextLevel();
                
                // Draw new map
                map.drawMap(map.map, map.levelNumber);
                player.getPlayerObject().move(player.levelPositions[map.levelNumber][0], player.levelPositions[map.levelNumber][1]);
                map.draw(null);

                
                wait(5000);

                
                map.setActionMessage("You move to the next level.");
                map.setScreenType("map");
            
                player.getInput = true;
                isAttacking = false;



                
                
            }
            if (isAttacking) {
                            // have a 50% chance to attack the player
                if (Math.random() < 0.5) {
                    map.setActionMessage("The "+ name +" lunges at you..."+" and attacks you for " + attackDamage + " damage!");
                    player.health -= attackDamage;

                    if (player.health <= 0) {
                        map.setActionMessage("You died! Game over.");
                        // stop program
                        System.exit(0);
                        
                    }
                } else {
                    map.setActionMessage("The "+ name +" lunges at you..." + " and misses!");
                }



            }


            
            
        }



        if (Character.toLowerCase(input) == 'i') {
            map.setActionMessage("You inspect the " + name + ": Health: " + health + " Attack: " + attackDamage);
        }

        if (Character.toLowerCase(input) == 'b') {


            wait(200);
            // create scanner
            Scanner scanner = new Scanner(System.in);
            map.setActionMessage("Select an item keycode to use ( EX ):");
            System.out.print("└─►  Input: " );
            String inventoryInput = scanner.nextLine().trim().toUpperCase();
            
            for (item inventoryItem : player.inventory) {
                if (inventoryItem != null) {
                    String itemName = inventoryItem.getName();
                    String keycode = itemName.substring(0, 2).toUpperCase();
                    if (inventoryInput.equals(keycode)) {
                        String itemType = inventoryItem.getType();
                        if (itemType.equals("weapon")) {
                            map.setActionMessage("You equip the " + inventoryItem.getName() + ".");
                            player.damageMultiplier += inventoryItem.getProperty();
                        } else if (itemType.equals("potion")) {
                            map.setActionMessage("You use the " + inventoryItem.getName() + ".");

                            player.health += inventoryItem.getProperty();

                            // remove item from inventory
                            player.removeItem(inventoryItem.getName());
                        } else if (itemType.equals("armor")) {
                            // TODO: implement armor
                        }
                        break; // exit loop once item is found and used
                    }
                }
            }
            
        }

    }
}

    

}