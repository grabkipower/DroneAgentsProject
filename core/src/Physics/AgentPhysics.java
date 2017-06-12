package Physics;

import Interfaces.AgentInterface;
import Map.MapMain;
import Map.MapRepresentation;
import TaskPackage.Task;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameController;
import com.mygdx.game.MainAgent;
import com.mygdx.game.MainAgentStatus;
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
        HandleBaseReturn();

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

    private void HandleBaseReturn() {
        if (main.ReturnToBase == true && main.status != MainAgentStatus.SimulationEnded) {
            main.status = MainAgentStatus.SimulationEnded;
            mainMap.RefreshGrid();
            CurrentRoute = new Route(position, main.Conf.SpawnPosition, mainMap.GetMapRepresentation(), this);
            RemoveTask();
        }
    }

    private void RemoveTask()
    {
        //todo repair this:
        main.CurrentTask = null;
    }

    public void HandleTaskIfNotNull() {
        if (main.IsDoingTask == false && main.CurrentTask == null && main.TaskID != -1) {
            main.CurrentTask = GameController.getInstance().taskControl.GetTaskById(main.TaskID);
            System.out.println("Przypisuje sobie zadanie o id " + main.TaskID);
            main.IsDoingTask = true;
            main.status = MainAgentStatus.DoingTask;
        }
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
                main.CurrentTask = null;
                main.JadeAgent.TaskDone();
                main.IsDoingTask = false;
                System.out.println("Wykonalem zadanie o id " + main.TaskID);
                main.TaskID = -1;
                main.status = MainAgentStatus.Idle;
                break;
        }
    }
}
