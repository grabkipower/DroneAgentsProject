package JadePackage;

import Interfaces.AgentInterface;
import Physics.AgentPhysics;
import com.badlogic.gdx.math.Vector2;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.proto.ProposeInitiator;
import jade.proto.ProposeResponder;
import jade.wrapper.AgentController;

/**
 * Created by Mike on 07.05.2017.
 */
public class RobotAgent extends Agent {
    public AgentPhysics PhysicalAgent;


    public void SetPhysics(AgentPhysics agentPhysics) {
        PhysicalAgent = agentPhysics;
    }


    Behaviour wait;
    protected  void setup()
    {
        wait = new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if( msg != null)
                {
                    if( msg.getPerformative() == ACLMessage.INFORM) {
                        System.out.println("Dostalem rozkaz");
                        PhysicalAgent.LestMove = true;
                    }
                }
            }
        };
        addBehaviour(wait);
    }






    Behaviour ReceiveMessages = new CyclicBehaviour() {
        @Override
        public void action() {
            // Topic for start / edn
            // New tasks
            // Collision Info
            // Receive start message and abandon behaviour
        }
    };

    Behaviour WaitForSimulationEnd = new CyclicBehaviour() {
        @Override
        public void action() {
            ACLMessage acl = receive();
        }
    };

    public Vector2 GetAgentPosition()
    {
        return PhysicalAgent.GetPosition();
    }

    Behaviour WaitForTasks = new CyclicBehaviour() {
        @Override
        public void action() {
            // Wait for new tasks
        }
    };

    Behaviour WorkOnTask = new CyclicBehaviour() {
        @Override
        public void action() {
            // Make your way to complete the task
        }
    };

    Behaviour ListenForCollisionInfo = new CyclicBehaviour() {
        @Override
        public void action() {

        }
    };

    Behaviour InformAboutCollision = new OneShotBehaviour() {
        @Override
        public void action() {

        }
    };

    private void ReceiveTask()
    {}

    private void ConfirmReceivedTask()
    {}

    private void ConfirmTaskCompletition()
    {}

    private void CheckBatteryLifetime()
    {
    }
}
