package TaskPackage;

import com.mygdx.game.GameConfig;
import com.mygdx.game.GameController;

import java.awt.*;
import java.util.Random;

/**
 * Created by Mike on 25.05.2017.
 */
public class Task {
    Carry carry;
    public Point Transit;
    public Point RackNearbyPosition;
    public boolean IsDone = false;
    public boolean IsAssigned = false;
    public int id;
    public int OrderId;
    int AgentIndex = -1;
    boolean TransitToShelf;
    public TaskStage Status;
    int wait = 0;
    long StartWait;
    private long CurrentWaitTime = 0;


    public Task(Carry carry, Point transit, int id, int orderId, boolean transitToSHelf) {
        this.carry = carry;
        Transit = transit;
        this.id = id;
        OrderId = orderId;
        TransitToShelf = transitToSHelf;
        Status = TaskStage.NotBegan;


        try {
            RackNearbyPosition = GameController.getInstance().map.GetMapRepresentation().FindNearbyNotBlocked(carry.RackPos);
        } catch (Exception e) {
            RackNearbyPosition = null;
            System.out.println("TransitPositionSet!");
        }
    }

    public void SetRackNearbyPosition() {
        try {
            RackNearbyPosition = GameController.getInstance().map.GetMapRepresentation().FindNearbyNotBlocked(carry.RackPos);
        } catch (Exception e) {
            RackNearbyPosition = Transit;
            System.out.println("TransitPositionSet!");
        }
    }

    public void ResetWait() {
        StartWait = System.currentTimeMillis();
    }

    public boolean IsEndWait() {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis - StartWait >= GameConfig.AgentsLoadTimeFloat;
    }


    public Point GetStartPoint() {
        if (TransitToShelf)
            return Transit;
        return GetRackPosition();
    }

    public Point GetEndPoint() {
        if (TransitToShelf) {
            return GetRackPosition();
        }
        return Transit;
    }

    private Point GetRackPosition() {
        if (RackNearbyPosition == null)
            SetRackNearbyPosition();
        return RackNearbyPosition;
    }

    public enum TaskStage {
        NotBegan, MovingToStart, StartPointAchieved, MovingToEnd, EndPointAchieved, TaskEnded
    }
}

