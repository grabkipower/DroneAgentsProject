package Physics;

import Map.MapRepresentation;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameController;
import com.mygdx.game.MainAgent;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;
import org.xguzm.pathfinding.grid.finders.ThetaStarGridFinder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.StrictMath.atan2;
import static java.lang.StrictMath.sin;

public class Route {
    boolean KeepMoving = true;
    AgentPhysics agent;
    Point Start;
    Point End;
    MapRepresentation map;
    List<GridCell> path;
    List<Point> MyPath;
    GridFinderOptions opt;
    AStarGridFinder finder;
    Point CurrentDestination;
    int index = 0;
    double vel = GameConfig.Velocity;
    boolean ismove;
    Vector2 Direction;
    RouteStatus Status;
    int pathsize = 0;


    public enum RouteStatus {
        Moving, CollisionDetected, DestinationAchieved
    }

    public Route(Point start, Point end, MapRepresentation rep, AgentPhysics _agent) {
        Start = start;
        End = end;
        map = rep;
        agent = _agent;
        opt = new GridFinderOptions();
        //test orthogonal movement only
        opt.allowDiagonal = true;
        opt.dontCrossCorners = true;
        finder = new AStarGridFinder(GridCell.class);
        FindRoute();
        Status = RouteStatus.Moving;
    }

    public void GetNextCell() {
        if (index == MyPath.size()) {
            Status = RouteStatus.DestinationAchieved;
            return;
        }
        if (Status == RouteStatus.DestinationAchieved)
            return;
        CurrentDestination = MyPath.get(index);
        index++;
        Point Delta = new Point(CurrentDestination.x - agent.position.x, CurrentDestination.y - agent.position.y);
        double InclAngle = atan2(Delta.y, Delta.x);
        Direction = new Vector2((float) cos(InclAngle), (float) sin(InclAngle));
    }


    public void MakeMove() {
        if (CurrentDestination == null) {
            GetNextCell();
        }
        if (Status == RouteStatus.DestinationAchieved)
            return;

        Vector2 CalculatedPosition = new Vector2(agent.PhysicalPosition.x, agent.PhysicalPosition.y);

        CalculatedPosition.x += vel * Direction.x;
        CalculatedPosition.y += vel * Direction.y;
        Rectangle rect = new Rectangle(CalculatedPosition.x, CalculatedPosition.y, 32.0f, 32.0f);


//
//       if( CheckForCollision(agent.GetRectangle()))
//       {
//           System.out.println(agent.main.Conf.Index + " Wykrylem kolicje ");
//           return;
//       }

        agent.PhysicalPosition.x = CalculatedPosition.x;
        agent.PhysicalPosition.y = CalculatedPosition.y;
        agent.SetRectangle();

        agent.SetRectangle();

        if (!CheckForBoundary()) {
            agent.position = new Point(CurrentDestination.x, CurrentDestination.y);
            agent.SetPhysicalFromPointPosition();
            GetNextCell();
        }

    }

    private boolean CheckForCollision(Rectangle currentRect) {
        for (MainAgent AgentO : GameController.getInstance().Agents) {
            if (AgentO.Conf.Index != agent.main.Conf.Index) {
                if (Intersector.overlaps(AgentO.PhysicalAgent.GetRectangle(), currentRect))
                    return true;
            }
        }

        return false;
    }

    private boolean CheckForBoundary() {
        if (Direction.x >= 0.00001) {
            if (agent.PhysicalPosition.x >= CurrentDestination.x * GameConfig.TileSize)
                return false;
        } else if (Direction.x <= -0.00001) {
            if (agent.PhysicalPosition.x <= CurrentDestination.x * GameConfig.TileSize)
                return false;
        }

        if (Direction.y >= 0.00001) {
            if (agent.PhysicalPosition.y >= CurrentDestination.y * GameConfig.TileSize)
                return false;
        } else if (Direction.y <= -0.00001) {
            if (agent.PhysicalPosition.y <= CurrentDestination.y * GameConfig.TileSize)
                return false;
        }
        return true;
    }

    public boolean EndMove() {
        index++;
        if (path.size() == index)
            return false;
        return true;
    }

    private void FindRoute() {

        GridCell start = map.GridCells.getCell(Start.x, Start.y), end = map.GridCells.getCell(End.x, End.y);


        try {
            path = finder.findPath(start, end, map.GridCells);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (path == null) {
            int a = 2;
        }
        pathsize = path.size();
        MyPath = new ArrayList<Point>();
        for (GridCell x : path) {
            MyPath.add(new Point(x.x, x.y));
        }


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
