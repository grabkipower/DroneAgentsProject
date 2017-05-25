package Physics;

import Interfaces.AgentInterface;
import Map.MapMain;
import com.badlogic.gdx.math.Vector2;
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
    }

    public void OnCollision() {

    }

    public void OnDestinationAchieved() {
        CurrentRoute = null;
    }

    public void Update() {
        if( End != null)
        {
            MapMain newMap = new MapMain();
            newMap.CreateMapRep(GameController.getInstance().map.GetMapRepresentation());
            GameController.getInstance().map.RefreshGrid();
            CurrentRoute = new Route(position, End, GameController.getInstance().map.GetMapRepresentation(), this);
            End = null;
        }
        if (CurrentRoute != null && CurrentRoute.KeepMoving) {
            CurrentRoute.MakeMove();
        }
        if(CurrentRoute != null && CurrentRoute.KeepMoving == false)
        {
            OnDestinationAchieved();
        }
        //  if (LestMove)
        //  position.add(new Point(1, 1));
        // Collision check
        // Move prediction
        // Check with route
        // Actual position change
    }
}
