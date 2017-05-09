package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 09.05.2017.
 */
public class GameConfig {
    public static List<Vector2> SpawnPositions = new ArrayList<Vector2>() {{
        add(new Vector2(10, 10));
        add(new Vector2(100, 10));
    }};

}
