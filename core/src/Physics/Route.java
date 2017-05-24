package Physics;

import Map.MapRepresentation;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameController;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;
import org.xguzm.pathfinding.grid.finders.ThetaStarGridFinder;

import java.awt.*;
import java.util.List;

/**
 * Created by Mike on 24.05.2017.
 */
public class Route {
    Point Start;
    Point End;
    MapRepresentation map;
    List<GridCell> path;
    GridFinderOptions opt;
    ThetaStarGridFinder<GridCell> finder;
    int index = 0;

    public Route(Point start, Point end, MapRepresentation rep) {
        Start = start;
        End = end;
        map = rep;
        opt = new GridFinderOptions();
        finder = new ThetaStarGridFinder<GridCell>(GridCell.class, opt);
        FindRoute();
    }

    public GridCell GetNextCell() {
        return path.get(index);
    }

    public boolean EndMove() {
        index++;
        if (path.size() == index)
            return false;
        return true;
    }

    private void FindRoute() {

        GridCell start = map.GridCells.getCell(Start.x, Start.y), end = map.GridCells.getCell(End.x, End.y);

        //test orthogonal movement only
        opt.allowDiagonal = true;
        opt.dontCrossCorners = true;
        

        path = finder.findPath(start, end, map.GridCells);

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
    }
}
