package JadePackage;

import Interfaces.AgentInterface;
import Physics.AgentPhysics;
import TaskPackage.Task;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameController;
import com.mygdx.game.MainAgent;
import com.mygdx.game.MainAgentStatus;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ProposeInitiator;
import jade.proto.ProposeResponder;
import jade.wrapper.AgentController;
import sun.applet.Main;

import java.awt.*;

/**
 * Created by Mike on 07.05.2017.
 */
public class RobotAgent extends Agent {
    MainAgent main;
    AID TaskTopic;
    MessageTemplate TaskTpl;


    public void setMain(MainAgent main) {
        this.main = main;
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
                        Point Transit =GameController.getInstance().map.GetMapRepresentation().GetTransitPositions().get(10);
                        main.PhysicalAgent.NewRoute(Transit);
                    }
                }
            }
        };
     //   addBehaviour(wait);
addBehaviour(WaitForSimulationEnd);
        CreateTopics();
      //.  addBehaviour(AskForTask);
    }



    Behaviour AskForTask = new OneShotBehaviour() {
        @Override
        public void action() {
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.addReceiver(GameController.getInstance().MasterRobot.getAID());
            send(msg);

            addBehaviour(WaitForTaskResponse);
        }
    };

    Behaviour WaitForTaskResponse = new CyclicBehaviour() {
        @Override
        public void action() {
            ACLMessage msg = receive(MessageTemplate.MatchOntology(GameConfig.TaskOntology));
            if( msg != null )
            {
                if( msg.getPerformative() == ACLMessage.CONFIRM) {
                    HandleReceivedTask(msg.getContent());
                    if( main.status != MainAgentStatus.Idle)
                    {
                        String domek ="";
                    }
                }
                else
                {
                    int aa = 2;
                }
            }
            else
                block();

        }
    };

    private void HandleReceivedTask(String content)
    {
        String[] parts = content.split(";");
        int id = Integer.parseInt(parts[0]);
        System.out.println("Dostałem zadanie o id:" + id);
        main.TaskID = id;
        main.status = MainAgentStatus.DoingTask;
        //main.CurrentTask = GameController.getInstance().taskControl.GetTaskById(id);
    }

    public void TaskDone(){
        addBehaviour(AskForTask);
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
            ACLMessage msg = receive(MessageTemplate.MatchOntology(GameConfig.StartStopOntology));
            if( msg != null )
            {
                if( msg.getPerformative() == ACLMessage.CANCEL)
                {
                    main.ReturnToBase = true;
                    removeBehaviour(WaitForTaskResponse);

                }
                if( msg.getPerformative() == ACLMessage.AGREE)
                {
                    main.ReturnToBase = false;
              //      main.status = MainAgentStatus.Idle;
                    addBehaviour(AskForTask);
                }
            }
        }
    };



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


    private void CreateTopics() {
        TopicManagementHelper topicHelper = null;
        try {
            topicHelper = (TopicManagementHelper) getHelper(TopicManagementHelper.SERVICE_NAME);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

         TaskTopic = topicHelper.createTopic("Task");
        try {
            topicHelper.register(TaskTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        TaskTpl = MessageTemplate.MatchTopic(TaskTopic);
    }
}
