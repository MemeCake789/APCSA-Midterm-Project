package com.apcsa_midterm;

import java.util.ArrayList;
import java.util.List;

/**
 * The Map class represents a game map with a grid-based layout.
 * It manages the game state including objects, player position, and display screens.
 * 
 * This class handles:
 * - Map dimensions (width x height)
 * - Level information 
 * - Game objects 
 * - Screen types (map view, attack view)
 * - Player inventory
 * - Game status and action menus *
 */


public class Map {
    protected int width;                       // Width of grid
    protected int height;                      // Height of grid
    protected String title;                    // Name of grid
    public int levelNumber;                    // Current level
    protected List<ObjectStorage> objects;     // All items in the level
    protected String screen;                   // Screen type (Map, Attack)
    public String statusText = "Loading...";   // Status bar, shows level info: rooom, health, x, y
    public String type = "map";                
    private String[] attackScreen = {  

     };
    protected String[] inventory = {    };
    private String[] actions = {
        "", 
        "  You enter the first room. There is something in the distance.", 
        "",
        "  Actions:",
        "  [ W ] Move Up", 
        "  [ S ] Move Down",
        "  [ A ] Move Left",
        "  [ D ] Move Right",
        ""
       };
    private String[] actionMenu = {
            "┌─:action───────────────────────────────────────────────────────────────┐",
            "│                                                                       │",
            "├───────────────────────────────────────────────────────────────────────┘"
    };
    public Map(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.screen = "map";
        this.levelNumber = 1;
        objects = new ArrayList<>();
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
    /**
     * Sets the screen type for the Map object.
     *
     * @param screenType the new screen type to set
     */
    public void setScreenType(String screenType) {
        this.screen = screenType;
    }

    /**
     * Sets the attack screen text for the Map object.
     *
     * @param screen the new attack screen text to set
     */
    public void setScreenText(String[] screen) {
        this.attackScreen = screen;
    }
    /**
     * Clears the console screen by printing the ANSI escape sequence to move the cursor to the top-left corner and clear the entire screen.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    /**
     * Checks if current draw position is on the edge
     * 
     * @param x x-coordinate of the draw position
     * @param y y-coordinate of the draw position
     * 
     * @return true if the draw position is on the edge, false otherwise
     */
    public boolean isOnBorder(int x, int y) {
        return x == 0 || y == 0 || x == width - 1 || y == height - 1;
    }


    /**
     * Draws the status information for the game on the console.
     */
    public void drawStatus(){
        
        System.out.println("┌─:status───────────────────────────────────────────────────────────────┐");
        System.out.println("│" +" "+ statusText + " ".repeat(70 - statusText.length()) + "│");
        System.out.println("└───────────────────────────────────────────────────────────────────────┘");
    }
    /**
     * Prints the border characters for the game map.
     * The border is drawn using Unicode box-drawing characters, with the title text centered at the top.
     * The method handles the different cases for the corners, edges, and center of the border.
     *
     * @param x the x-coordinate of the current draw position
     * @param y the y-coordinate of the current draw position
     */
    public void printBorder(int x, int y) {
        if (y == 0 && x >= 2 && x < 6 + title.length()) {
            if (x == 2) {
                System.out.print( Colors.WHITE + ":" + Colors.RESET);
            } else if (x >= 5 && x < 5 + title.length()) {  // Fixed condition
                System.out.print( Colors.WHITE + title.charAt(x - 5) + Colors.RESET);
            } else if (x == 5 + title.length()) {
                System.out.print( Colors.WHITE + "──────────" + Colors.RESET);
            }
            return;
        }
    
    

        String borderChar;
        if (x == 0 && y == 0) borderChar = "┌";
        else if (x == 0 && y == height - 1) borderChar = "└";
        else if (x == width - 1 && y == 0) borderChar = "┐";
        else if (x == width - 1 && y == height - 1) borderChar = "┘";
        else if (x == 0 || x == width - 1) borderChar = "│";
        else if (y == 0 || y == height - 1) borderChar = "──";
        else borderChar = " ";
        
        System.out.print(Colors.WHITE + borderChar + Colors.RESET);
    }

/**
 * Draws the action menu on the console, displaying the action options and the current action message.
 */
public void drawActionMenu() {
    System.out.println(Colors.WHITE + actionMenu[0] + Colors.RESET);
    for (int i = 0; i < actions.length; i++) {
        if (i == 1) {
            System.out.println(Colors.ITALIC + "│ " + actions[i] + " ".repeat(70 - actions[i].length()) + "│" + Colors.RESET);
        }else{
                    System.out.println(Colors.WHITE + "│ " + actions[i] + " ".repeat(70 - actions[i].length()) + "│" + Colors.RESET);
        }
    }
    System.out.println(Colors.WHITE + actionMenu[actionMenu.length - 1] + Colors.RESET);
}

/**
 * Sets the action message at index 1 in the actions array.
 *
 * @param message the new action message to set
 */
void setActionMessage(String message) {
    this.actions[1] = message;
    drawActionMenu();
    draw(objects);
}

/**
 * Sets the action options for the specified index in the actions array.
 *
 * @param num      the index of the action option to set
 * @param options  the new action options to set
 */
void setActionOptions(int num, String options) {
    this.actions[num] = options;
}
    public void draw(List<ObjectStorage> objects) {
        clearScreen();
        if (screen.equals("map")) {

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (isOnBorder(x, y)) {
                        printBorder(x, y);
                    } else if (getCell(x, y, objects) != null) {
                        System.out.print(Colors.BRIGHT_WHITE + Colors.BOLD + getCell(x, y, objects) + Colors.RESET);
                    } else {
                        System.out.print(Colors.DIM + "· " + Colors.RESET);
                    }
                }
                if(y < inventory.length && inventory[y] != null){
                    System.out.println(inventory[y]);
                } else{
                    System.out.println();
                }
            }
            drawStatus();
            drawActionMenu();
            
        } else if (screen.equals("attack")) {
            for (int i = 0; i < attackScreen.length; i++) {
                String line = attackScreen[i];
                for (int j = 0; j < line.length(); j++) {
                    char c = line.charAt(j);
                    if (c == '░') {
                        System.out.print(Colors.DIM + c + Colors.RESET);
                    } else if (c == '█') {
                        System.out.print(Colors.BRIGHT_GREEN + c + Colors.RESET);     
                    }else if (c == '▓') {
                        System.out.print(Colors.BRIGHT_GREEN+ c + Colors.RESET);
                    }else if (c == '▒'){
                        System.out.print(Colors.BRIGHT_RED+ c + Colors.RESET);
                    }
                    else if (c == 'X') {
                        System.out.print(Colors.RED+ c + Colors.RESET);

                    } else{
                        System.out.print(c);
                    }
                }
                if(i < inventory.length && inventory[i] != null){
                    System.out.println(inventory[i]);
                } else{
                    System.out.println();
                }

                
            }
                        drawStatus();
            drawActionMenu();

        }

        
            
        
        
    }
/**
 * Gets the cell texture at the specified coordinates in the map.
 * @param x The x-coordinate of the cell.
 * @param y The y-coordinate of the cell.
 * @param objects The list of objects in the map.
 * @return The texture of the cell, or null if the cell is empty.
 */
    public String getCell(int x, int y, List<ObjectStorage> objects) {
        for (ObjectStorage object : objects) {
            if (object.getX() == x && object.getY() == y) {
                return object.getIcon();
            }
        }
        return null;
    }

    
    // Map specific code
    public String[][][] level = {
        {
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}

        },

        {
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", "█", "█", "█", "▄", "▄", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "▀", "▀", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", "█", " ", "█", "░", "░", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "▄", "▄", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", " ", " ", "█", " ", "█", "▀", "▀", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "░", "░", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", "█", " ", "█", "▄", "▄", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "▀", "▀", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "█", "█", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", "█", " ", " ", " ", " ", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "█", "▄", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", " ", " ", "█", " ", " ", " ", " ", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "█", "█", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", "█", " ", " ", " ", " ", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "█", "▀", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", "█", " ", " ", " ", " ", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", "█", "█", "█", "█", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", " ", " ", " ", " ", " ", " ", " ", " "}
        },

        {
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", "█", "█", "█", "█", "█", "█", " ", "▓", " ", "█", " ", "█", "▓", "█", "▓", "█", "█", "█", " "},
            {" ", "█", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", " ", " ", " ", " ", "█", "▓", " "},
            {" ", "▓", " ", " ", " ", " ", "█", " ", "▓", " ", "█", " ", "█", " ", " ", " ", " ", "█", "█", " "},
            {" ", "▓", " ", " ", " ", " ", "█", " ", "▓", " ", "█", " ", " ", " ", " ", " ", " ", "▓", "▓", " "},
            {" ", "█", " ", " ", " ", " ", "█", " ", "█", " ", "█", " ", "█", " ", " ", " ", " ", "▓", "█", " "},
            {" ", "█", "█", "█", "▓", "█", "█", " ", "█", " ", "█", " ", "█", "█", "▓", "█", "▓", "█", "▓", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {"█", "█", "█", " ", "█", "▓", "█", "█", "▓", " ", "█", "█", "▓", "█", " ", "█", "▓", "█", "█", "▓"},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {"▓", "▓", "█", " ", "█", "█", "▓", "█", "▓", " ", "█", "▓", "█", "▓", " ", "█", "▓", "█", "▓", "█"},
            {" ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", "█", "█", "█", " ", "▓", "▓", " ", "▓", " ", "▓", " ", "▓", "█", "▓", "▓", "█", "█", "█", " "},
            {" ", "▓", " ", " ", " ", " ", "▓", " ", "▓", " ", "▓", " ", "▓", " ", " ", " ", " ", "█", "█", " "},
            {" ", "█", " ", " ", " ", " ", "▓", " ", "█", " ", "▓", " ", "▓", " ", " ", " ", " ", "█", "▓", " "},
            {" ", "▓", " ", " ", " ", " ", "█", " ", "▓", " ", "█", " ", "█", " ", " ", " ", " ", "▓", "▓", " "},
            {" ", "█", "█", "█", "▓", "▓", "▓", " ", "▓", " ", "▓", " ", "█", "█", "█", " ", "█", "█", "▓", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}
        }
        ,

        {
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", "█", "█", "█", "▄", "▄", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "▀", "▀", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", "█", " ", "█", "░", "░", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "▄", "▄", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", " ", " ", "█", " ", "█", "▀", "▀", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "░", "░", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", "█", " ", "█", "▄", "▄", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "▀", "▀", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "█", "█", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", "█", " ", " ", " ", " ", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "█", "▄", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", " ", " ", "█", " ", " ", " ", " ", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "█", "█", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", "█", " ", " ", " ", " ", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", " ", "█", "█", "▀", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", "█", " ", " ", " ", " ", "█", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "█", " ", "█", "█", "█", "█", "█", "█", " ", " "},
            {" ", "▓", "▓", " ", "▓", "▓", " ", "▓", "▓", " ", "█", " ", " ", " ", " ", " ", " ", " ", " ", " "}
        },
        

    };

    public String[][] map = {
    };

    /**
     * Clears the map by setting all elements to a space character.
     */
    public void clearMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = " ";
            }
        }
    }

    /**
     * Iterates to the next map array
     */
    public void nextLevel() {
        getObjects().clear(); 
        clearMap();


        levelNumber++;

    }

    

    public ObjectStorage[][] mapData = {     

    };


    public List<ObjectStorage> getObjects() {
        return objects;
    }



    /**
     * Draws the map on the screen by adding walls and other objects to the game world.
     *
     * @param map    the 2D array representing the map
     * @param mapNum the index of the current map in the level array
     */
    public void drawMap(String[][] map, int mapNum) {
        // add walls to map
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!level[mapNum][y][x].equals(" ")) {
                    objects.add(new ObjectStorage(x, y, level[mapNum][y][x] + level[mapNum][y][x], "map"));
                    for (ObjectStorage[] objectArray : mapData) {
                        for (ObjectStorage object : objectArray) {
                            objects.add(object);
                        }
                    }
                }
            }
        }
    }

}
