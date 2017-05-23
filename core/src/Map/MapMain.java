package Map;


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

/**
 * Created by Mike on 18.05.2017.
 */
public class MapMain {
    NavigationGrid<GridCell> grid;
    ThetaStarGridFinder<GridCell> finder;
    GridFinderOptions opt;

    public MapMain() {
        grid = NavGraphFactory.getGridCellMap();
        opt = new GridFinderOptions();
        finder = new ThetaStarGridFinder<GridCell>(GridCell.class, opt);

    }

    public void Movement() {
        List<GridCell> path;
        GridCell start = grid.getCell(2, 0), end = grid.getCell(4, 7);

        //test orthogonal movement only
        opt.allowDiagonal = false;

        path = finder.findPath(start, end, grid);

        //TODO: smarter test...how to make sure path is smooth?
        int i = 0;
        System.out.println("  Path: no diagonal movement allowed ");
        for (GridCell cell : path) {
            System.out.println("    (" + (i++) + ") " + cell);
        }

        //test diagonal movement
        opt.allowDiagonal = true;

        path = finder.findPath(start, end, grid);
        //  assertNotNull(String.format("No path found from %s to %s for diagnoal movement", start, end), path);

        //TODO: smarter test...how to make sure path is smooth?
        i = 0;
        System.out.println("  Diagonal movement allowed ");
        for (GridCell cell : path) {
            System.out.println("    (" + (i++) + ") " + cell);
        }
    }
}
