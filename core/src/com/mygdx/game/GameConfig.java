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
        add(new Point(200,10));
    }};
    public static int MapWidth = 100;
    public static int MapHeight = 100;

}
