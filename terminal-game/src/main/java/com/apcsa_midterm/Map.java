package com.apcsa_midterm;

import java.util.ArrayList;
import java.util.List;

public class Map {
    protected int width;
    protected int height;
    protected String title;
    protected List<ObjectStorage> objects;
    protected String screen;
    public String statusText = "Loading...";
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
        objects = new ArrayList<>();
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
    public void setScreenType(String screenType) {
        this.screen = screenType;
    }

    public void setScreenText(String[] screen) {
        this.attackScreen = screen;
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public boolean isOnBorder(int x, int y) {
        return x == 0 || y == 0 || x == width - 1 || y == height - 1;
    }


    public void drawStatus(){
        
        System.out.println("┌─:status───────────────────────────────────────────────────────────────┐");
        System.out.println("│" +" "+ statusText + " ".repeat(70 - statusText.length()) + "│");
        System.out.println("└───────────────────────────────────────────────────────────────────────┘");
    }
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
                    }else if (c == 'X') {
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

    public String getCell(int x, int y, List<ObjectStorage> objects) {
        for (ObjectStorage object : objects) {
            if (object.getX() == x && object.getY() == y) {
                return object.getIcon();
            }
        }
        return null;
    }

    // Map specific code
    public String[][] map = {
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
    };

    public ObjectStorage[] mapItems = {
        new ObjectStorage(8, 2, "♥ ", "item", "Small Potion", "potion", 10),
        new ObjectStorage(3, 15, "/ ", "item", "Copper Sword", "weapon", 5),

    };


    public List<ObjectStorage> getObjects() {
        return objects;
    }



    public void drawMap(String[][] map) {
        // add walls to map
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!map[y][x].equals(" ")) {
                    objects.add(new ObjectStorage(x, y, map[y][x] + map[y][x], "map"));
                }
            }
        }

        // add mapItems to map
        for (ObjectStorage object : mapItems) {
            objects.add(object);
        }
    }
}
