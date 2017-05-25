package Map;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameController;
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

    public Point FindNearbyNotBlocked(Point point) throws Exception {
        if (CheckIsBlocked(point)) {
            int i = point.x - 1;
            int j = point.y - 1;
            for (; i <= point.x + 1; i++)
                for (; j <= point.y + 1; j++) {
                    if (!CheckIsBlocked(i, j))
                        return new Point(i, j);
                }
        }
        throw new Exception("Not blocked point not found!");
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

    public List<Point> GetRackPositions() {
        List<Point> ret = new ArrayList<Point>();
        for (int i = 0; i < Width; i++)
            for (int j = 0; j < Height; j++) {
                if (IntCells[i][j] == MapMain.Rack) {
                    Point p = new Point(i, j);
                    ret.add(p);
                }
            }
        return ret;
    }

    public List<Point> GetTransitPositions() {
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

    private boolean CheckIsBlocked(Point p) {
        return CheckIsBlocked(p.x, p.y);
    }

    private boolean CheckIsBlocked(int x, int y) {
        if (x < 0 || x > GameConfig.MapWidth)
            return true;
        if (y < 0 || y > GameConfig.MapHeight)
            return true;
        if (IntCells[x][y] == MapMain.Rack)
            return true;
        return false;
    }

}
