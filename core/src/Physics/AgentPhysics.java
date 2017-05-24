package Physics;

import Interfaces.AgentInterface;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameController;
import com.mygdx.game.MainAgent;
import org.xguzm.pathfinding.grid.GridCell;

import java.awt.*;

/**
 * Created by Mike on 07.05.2017.
 */
public class AgentPhysics extends PhysicsObject {
    MainAgent main;
    Vector2 DestinationPosition;
    Route CurrentRoute;


    public boolean LestMove = false;

    public void setMain(MainAgent main) {
        this.main = main;
    }


    public void NewRoute(Point End) {
        CurrentRoute = new Route(position,End, GameController.getInstance().map.GetMapRepresentation());
    }

    public AgentPhysics(Point position, Point velocity, int width, int height) {
        super(position, velocity, width, height);
    }


    public Point GetPosition() {
        return position;
    }

    public void SetPoisition(Point vec) {
        position = vec;
    }

    public void Update() {
        if( CurrentRoute != null)
        {
            GridCell cell = CurrentRoute.GetNextCell();


            position.x = cell.x;
            position.y = cell.y;
            if(!CurrentRoute.EndMove())
            {
                CurrentRoute = null;
            }
        }
      //  if (LestMove)
          //  position.add(new Point(1, 1));
        // Collision check
        // Move prediction
        // Check with route
        // Actual position change
    }
}
