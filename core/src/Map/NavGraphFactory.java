package Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.sun.org.apache.bcel.internal.generic.RET;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;

public class NavGraphFactory {


    private static // 0 means closed, 1 means open, 2 is marker for start, 3 is marker for goal
            int[][] navCells = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},    // 8
            {0, 0, 0, 0, 3, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 1, 1, 0, 0},   // 4
            {0, 0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 2, 1, 1, 0, 0, 0, 0, 0}    //0
            //0				 5			 9
    };

    public static MapRepresentation CreateRepMap(TiledMap map) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        int width = layer.getWidth();
        int height = layer.getHeight();
        MapRepresentation RetValue = new MapRepresentation(width, height);
        GridCell[][] cells = new GridCell[width][height];

        CreateForLayer(RetValue, "Base", map, width, height);
        CreateForLayer(RetValue, "Rack", map, width, height);
        CreateForLayer(RetValue, "CarryLoadPoint", map, width, height);
        CreateForLayer(RetValue, "SpawnPoint", map, width, height);

        CreateGridCellsMap(RetValue,map,width,height);

        String domek = "";

        return RetValue;
    }

    private static void CreateForLayer(MapRepresentation Rep, String name, TiledMap map, int width, int height) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(name);

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    int id = cell.getTile().getId();
                    Rep.IntCells[x][y] = id;
                }
            }
    }


    public static void CreateGridCellsMap(MapRepresentation rep, TiledMap map, int width, int height) {
        GridCell[][] cells = new GridCell[width][height];

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                GridCell GridCell = new GridCell(x, y, GetIsWalkable(rep.IntCells[x][y]));
                cells[x][y] = GridCell;
            }

        for( int i =0; i < 30; i++)
            for( int j = 0; j < 30; j++)
            {
                if(cells[i][j].isWalkable() )
                {
                    int a = 2;
                }
            }

        rep.GridCells = new NavigationGrid<GridCell>(cells, false);
    }

    private static boolean GetIsWalkable(int id) {
        if (id == MapMain.Rack)
            return false;
        return true;
    }


    public static NavigationGrid<GridCell> getGridCellMap() {
        GridCell[][] cells = new GridCell[navCells[0].length][navCells.length];


        for (int y = navCells.length - 1; y >= 0; y--)
            for (int x = 0; x < navCells[0].length; x++) {
                int invY = navCells.length - 1 - y;
                GridCell cell = new GridCell(x, invY, navCells[y][x] > 0);
                cells[x][invY] = cell;
            }

        return new NavigationGrid<GridCell>(cells, false);

    }

    public static NavigationGrid<GridCell> getAutoAssignedGridCellMap() {
        GridCell[][] cells = new GridCell[navCells[0].length][navCells.length];


        for (int y = navCells.length - 1; y >= 0; y--)
            for (int x = 0; x < navCells[0].length; x++) {
                int invY = navCells.length - 1 - y;
                GridCell cell = new GridCell(navCells[y][x] > 0);
                cells[x][invY] = cell;
            }

        return new NavigationGrid<GridCell>(cells, true);

    }
}