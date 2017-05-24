package Map;

import com.badlogic.gdx.math.Vector2;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 24.05.2017.
 */
public class MapRepresentation {
    public int[][] IntCells;
    public NavigationGrid<GridCell> GridCells;
    public int Width;
    public int Height;

    public MapRepresentation(int width, int height) {
        IntCells = new int[width][height];
        Width = width;
        Height = height;
    }

    public List<Point> GetSpawnPositions() {
        List<Point> ret = new ArrayList<Point>();
        for (int i = 0; i < Width; i++)
            for (int j = 0; j < Height; j++) {
                if (IntCells[i][j] == MapMain.Spawn) {
                    Point p = new Point(i, j);
                    ret.add(p);
                }
            }
        return ret;
    }

    public List<Point> GetTransitPositions(){
        List<Point> ret = new ArrayList<Point>();
        for (int i = 0; i < Width; i++)
            for (int j = 0; j < Height; j++) {
                if (IntCells[i][j] == MapMain.Transit) {
                    Point p = new Point(i, j);
                    ret.add(p);
                }
            }
        return ret;
    }

}
