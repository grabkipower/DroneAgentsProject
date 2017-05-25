package TaskPackage;

import java.awt.*;

/**
 * Created by Mike on 25.05.2017.
 */
public class Task {
    Carry carry;
    public Point Transit;
    boolean IsDone = false;
    public int id;
    public int OrderId;
    int AgentIndex = -1;
    boolean TransitToShelf;

    public Task(Carry carry, Point transit, int id, int orderId,boolean transitToSHelf) {
        this.carry = carry;
        Transit = transit;
        this.id = id;
        OrderId = orderId;
        TransitToShelf = transitToSHelf;
    }

    public Point GetStartPoint(){
    if(TransitToShelf)
        return Transit;
    return carry.RackPos;
    }
    public Point GetEndPoint(){
    if(TransitToShelf)
        return carry.RackPos;
    return Transit;
    }
}
