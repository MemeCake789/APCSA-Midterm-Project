package com.apcsa_midterm;

import java.util.ArrayList;
import java.util.List;

public class Map extends Grid {
    private List<ObjectStorage> objects;

        public Map(int width, int height, String title) {
        super(width, height, title);
        objects = new ArrayList<>();
        
        // objects.add(new ObjectStorage(3, 1, "ッ", "player"));
        // objects.add(new ObjectStorage(10, 1, "Ø ", "enemy"));
    }


    // basic 20 x 20 map as array
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
        // for length of map
        // add each item as a objectStorage
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!map[y][x].equals(" ")) {
                    objects.add(new ObjectStorage(x, y, map[y][x] + map[y][x], "map"));
                }
            }
        }
    }


}
