package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 09.05.2017.
 */
public class GameConfig {
    public static List<Point> SpawnPositions = new ArrayList<Point>() {{
        add(new Point(10, 10));
        add(new Point(100, 10));
        add(new Point(200, 10));
        add(new Point(300, 20));
        add(new Point(200, 10));
        add(new Point(300, 20));
        add(new Point(200, 10));
        add(new Point(300, 20));
    }};
    public static List<String> CarryNames = new ArrayList<String>() {{
        add("Fish");
        add("Mechanical Parts");
        add("Toys");
        add("Phones");
        add("PC Parts");
        add("Trousers");
        add("Tissues");
        add("Chips");
        add("Drones");
        add("Javelin Rockets");
    }};

    public static int MapWidth = 100;
    public static int MapHeight = 100;
    public static double Velocity = 3.5f;
    public static float TileSize = 32;
    public static float Threshold = 0.00001f;
    public static int AgentLoadTime = 500000;
    public static long AgentsLoadTimeFloat = 5000;

}
