/*
 * AP Computer Science Midterm Project: Terminal Crawler
 * By: Nicholas Mathew
 * .
 * Description: A game where the player must fight enemies 
 * and collect items to survive.
 * 
 * How to play: Use the movement keys (W,A,S,D) and then
 * press enter to move around the map.
 * 
 * - You are the "X"
 * - Enemies are the "@"
 * - Walk over items to collect them
 * 
 * Attacking:
 * 
 * When you and a enemy cross paths, a battle begins.
 * you will have the option to:
 * 
 * [ A ] : Attack
 *    -  Starts attacking the enemy, a bar will show up with a 
 *       red and yellow zone, and " >[]< " as a cursor.
 * 
 *    -  The cursor will begin moving down, you have to hit 
 *       ENTER whenever the cursor lands on yellow to deal 
 *       damage and red to deal extra damage.
 * 
 *    -  You will get three attempts to deal damage, then all 
 *       total damage will be delt to the enemy
 *    
 *    -  Then if the enemy is still alive, it will have ao 50%
 *       chance to deal damage
 * 
 *    -  If the enemy dies, you win and move to the next level
 * 
 *    -  If you die, the game ends.
 * 
 * [ I ] : Inspect
 *    -  Inspects the enemy and gives its health and attack damage
 * 
 * [ B ] : Inventory
 *    -  Alows you to equip and use items from your inventory
 * 
 * Currently, only the first level is fully built, but you can still 
 * walk around in the second
 * 
 */

 package com.apcsa_midterm;

 import java.io.IOException;
 import java.util.Scanner;

public class Main {
    private static final int MIN_WIDTH = 75;
    private static final int MIN_HEIGHT = 24;


    /**
     * Gets the current size of the terminal window.
     * 
     * This method uses the `stty size` command to retrieve the current height and width
     * of the terminal window. If the command fails for any reason, it returns a default
     * size of 24x80.
     * 
     * @return an integer array containing the height and width of the terminal window
     */
    public static int[] getTerminalSize() {
        try {
            Process process = new ProcessBuilder("stty", "size")
                .redirectInput(ProcessBuilder.Redirect.INHERIT)
                .start();
            String output = new String(process.getInputStream().readAllBytes()).trim();
            String[] parts = output.split(" ");
            return new int[] {
                Integer.parseInt(parts[0]), Integer.parseInt(parts[1])
            };
        } catch (Exception e) {
            return new int[] {
                24,
                80
            };
        }
    }

    /**
     * Pauses the current thread for the specified number of milliseconds.
     *
     * This method uses the `Thread.sleep()` method to pause the current thread for the
     * specified number of milliseconds. If the thread is interrupted during the sleep,
     * the method will ignore the `InterruptedException` and continue.
     *
     * @param ms the number of milliseconds to pause the current thread
     */
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // Ignore
        }
    }

    /**
     * Waits for the terminal window to be at least the minimum required size before starting the game.
     * 
     */
    public static void waitForValidWindowSize() {
        while (true) {
            int[] size = getTerminalSize();
            int height = size[0];
            int width = size[1];

            if (width >= MIN_WIDTH && height >= MIN_HEIGHT) {
                clearScreen();
                System.out.println(Colors.GREEN + Colors.DIM + "Terminal size is good! Starting game..." + Colors.RESET);
                break;
            }

            clearScreen();
            System.out.println(Colors.RED + "Terminal window too small!" + Colors.RESET);
            System.out.println("Current size: " + width + "x" + height);
            System.out.println("Required size: " + MIN_WIDTH + "x" + MIN_HEIGHT);
            System.out.println("\nPlease resize your terminal window and press ENTER to continue...");

            try {
                System.in.read();
            } catch (IOException e) {
                // Continue checking
            }
        }
    }



    /**
     * Clears the terminal screen : https://www.javatpoint.com/how-to-clear-screen-in-java
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        waitForValidWindowSize();

        // Color test display
        for (String i: Colors.FOREGROUNDS) {
            for (String j: Colors.STYLES) {
                for (String k: Colors.BACKGROUNDS) {
                    System.out.print(i + j + k + " [X] " + Colors.RESET);
                }
                System.out.println();
                wait(5);
            }
        }

        wait(500);

        // Initialize game components
        Map map = new Map(20, 20, "map");
        map.map = new String[][]{
         
        };
        
        Player player = new Player(3, 1, Colors.BRIGHT_GREEN + "X " + Colors.RESET, map, 1);
        Enemy enemy = new Enemy(6, 10, Colors.BG_RED + Colors.BLACK + "@" + Colors.RESET + " ", map, player, 5, 7, "Crawler");
        
        ObjectStorage[][] mapData = {
           { player.getPlayerObject(), 
            enemy.getEnemyObject(), 
            new ObjectStorage(8, 2, "♥ ", "item", "SP | Small Potion", "potion", 12), 
            new ObjectStorage(3, 15, "/ ", "item", "CS | Copper Sword", "weapon", 4)
        }
        };
        map.mapData = mapData;
        map.drawMap(map.level[map.levelNumber], map.levelNumber);
        map.draw(map.getObjects());
        // Game loop
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("└─►  Input: ");
            char input = scanner.next().charAt(0);


                // First handle attack if we're in attack mode
                if (!player.getInput) {
                    enemy.handleAttack(input);
                    map.statusText = " Health: " + player.health;
                } else {
                    // Otherwise handle normal movement
                    player.handleMovement(input);
                    enemy.moveTowardsPlayer();
                    map.statusText = "Room: Room1" + " | Health: " + player.health + " | X: " + player.X + " | Y: " + player.Y;
                    map.drawMap(map.level[map.levelNumber], map.levelNumber);
                }

                map.draw(map.getObjects());

            
        }
    }}