package Map;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;
import org.xguzm.pathfinding.grid.finders.ThetaStarGridFinder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Mike on 18.05.2017.
 */
public class MapMain {
    public static int Rack = 10;
    public static int Spawn = 34;
    public static int Transit = 13624;
    public static int Floor = 9051;

    public static int TileWidthNumber = 30;
    public static int TileHeightNumber = 30;

    TiledMap tiledMap;

    MapRepresentation MapRep;
    public MapMain() {

    }



    public void CreateMapRep(MapRepresentation map)
    {
        MapRep = new MapRepresentation(map.Width,map.Height);
        MapRep.GridCells = map.GridCells;
        MapRep.IntCells = map.IntCells;
    }
    public void RefreshGrid()
    {
        MapRep = NavGraphFactory.CreateRepMap(tiledMap);
        TileWidthNumber = MapRep.Width;
        TileHeightNumber = MapRep.Height;
    }

    public void CreateGrid(TiledMap map) {
        tiledMap = map;
        MapRep = NavGraphFactory.CreateRepMap(map);
        TileWidthNumber = MapRep.Width;
        TileHeightNumber = MapRep.Height;
    }

    public List<Point> GetSpawnPositions(){
        return MapRep.GetSpawnPositions();
    }

    public MapRepresentation GetMapRepresentation()
    {
        return MapRep;
    }

//    public void Movement() {
//        List<GridCell> path;
//        GridCell start = grid.getCell(2, 0), end = grid.getCell(4, 7);
//
//        //test orthogonal movement only
//        opt.allowDiagonal = false;
//
//        path = finder.findPath(start, end, grid);
//
//        //TODO: smarter test...how to make sure path is smooth?
//        int i = 0;
//        System.out.println("  Path: no diagonal movement allowed ");
//        for (GridCell cell : path) {
//            System.out.println("    (" + (i++) + ") " + cell);
//        }
//
//        //test diagonal movement
//        opt.allowDiagonal = true;
//
//        path = finder.findPath(start, end, grid);
//        //  assertNotNull(String.format("No path found from %s to %s for diagnoal movement", start, end), path);
//
//        //TODO: smarter test...how to make sure path is smooth?
//        i = 0;
//        System.out.println("  Diagonal movement allowed ");
//        for (GridCell cell : path) {
//            System.out.println("    (" + (i++) + ") " + cell);
//        }
//    }
}
