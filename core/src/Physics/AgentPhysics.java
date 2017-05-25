package Physics;

import Interfaces.AgentInterface;
import Map.MapMain;
import Map.MapRepresentation;
import TaskPackage.Task;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameController;
import com.mygdx.game.MainAgent;
import org.xguzm.pathfinding.grid.GridCell;
import sun.util.resources.cldr.ebu.CurrencyNames_ebu;

import java.awt.*;

/**
 * Created by Mike on 07.05.2017.
 */
public class AgentPhysics extends PhysicsObject {
    MainAgent main;
    Vector2 DestinationPosition;
    Route CurrentRoute;
    Point End;
    MapMain mainMap;


    public boolean LestMove = false;

    public void setMain(MainAgent main) {
        this.main = main;
    }


    public void NewRoute(Point end) {
        this.End = end;
    }

    public AgentPhysics(Point position, Point velocity, int width, int height) {
        super(position, velocity, width, height);
    }


    public Point GetPosition() {
        return position;
    }

    public Vector2 GetPhysicalPosition() {
        return PhysicalPosition;
    }

    public void SetPoisition(Point vec) {
        position = vec;
        PhysicalPosition.x = position.x * 32;
        PhysicalPosition.y = position.y * 32;
        SetRectangle();
    }

    public void OnCollision() {

    }


    public void Update() {
        HandleTaskIfNotNull();
        if (CurrentRoute != null) {
            CurrentRoute.MakeMove();
        }

        //  if (LestMove)
        //  position.add(new Point(1, 1));
        // Collision check
        // Move prediction
        // Check with route
        // Actual position change
    }


    public void HandleTaskIfNotNull() {
        if (main.CurrentTask == null)
            return;
        if (mainMap == null) {
            mainMap = new MapMain();
            mainMap.CreateGrid(new TmxMapLoader().load("map.tmx"));
        }

        switch (main.CurrentTask.Status) {

            case NotBegan:

                mainMap.RefreshGrid();
                CurrentRoute = new Route(position, main.CurrentTask.GetStartPoint(), mainMap.GetMapRepresentation(), this);
                main.CurrentTask.Status = Task.TaskStage.MovingToStart;
                break;
            case MovingToStart:
                if (CurrentRoute.Status == Route.RouteStatus.DestinationAchieved) {
                    main.CurrentTask.Status = Task.TaskStage.StartPointAchieved;
                    main.CurrentTask.ResetWait();
                }
                break;
            case StartPointAchieved:
                if (main.CurrentTask.IsEndWait()) {
                    mainMap.RefreshGrid();
                    CurrentRoute = new Route(position, main.CurrentTask.GetEndPoint(), mainMap.GetMapRepresentation(), this);
                    main.CurrentTask.Status = Task.TaskStage.MovingToEnd;
                }
                break;
            case MovingToEnd:
                if (CurrentRoute.Status == Route.RouteStatus.DestinationAchieved) {
                    main.CurrentTask.Status = Task.TaskStage.TaskEnded;
                }
                break;
            case EndPointAchieved:
                break;
            case TaskEnded:
                main.CurrentTask.IsDone = true;
                GameController.getInstance().taskControl.SetAsDone(main.CurrentTask.id);
                main.JadeAgent.TaskDone();
                break;
        }
    }
}
