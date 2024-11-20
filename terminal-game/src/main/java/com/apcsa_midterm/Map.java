package com.apcsa_midterm;

import java.util.ArrayList;
import java.util.List;

public class Map {
    protected int width;
    protected int height;
    protected String title;
    protected List<ObjectStorage> objects;

    protected String[] inventory = {    };

    public Map(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        objects = new ArrayList<>();
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // Ignore
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public boolean isOnBorder(int x, int y) {
        return x == 0 || y == 0 || x == width - 1 || y == height - 1;
    }

    public void printBorder(int x, int y) {
        if (y == 0 && x >= 3 && x < 6 + title.length()) {
            if (x == 3 || x == 5 + title.length()) {
                System.out.print(Colors.BOLD + Colors.WHITE + "──────" + Colors.RESET);
            } else if (x == 4) {
                System.out.print(Colors.BOLD + Colors.WHITE + ":" + Colors.RESET);
            } else {
                System.out.print(Colors.BOLD + Colors.WHITE + title.charAt(x - 5) + Colors.RESET);
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
        
        System.out.print(Colors.BOLD + Colors.WHITE + borderChar + Colors.RESET);
    }

    public void draw(List<ObjectStorage> objects) {
        clearScreen();
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

    public List<ObjectStorage> getObjects() {
        return objects;
    }

    public void drawMap(String[][] map) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!map[y][x].equals(" ")) {
                    objects.add(new ObjectStorage(x, y, map[y][x] + map[y][x], "map"));
                }
            }
        }
    }
}
