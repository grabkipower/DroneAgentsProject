package JadePackage;

import TaskPackage.Order;
import TaskPackage.Task;
import com.badlogic.gdx.Game;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameController;
import com.mygdx.game.MainAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mike on 07.05.2017.
 */
public class Master extends Agent {
    Behaviour Start;
    AID TaskTopic;
    MessageTemplate TaskTpl;
    int index = 0;
    GameController cont = GameController.getInstance();

    protected void setup() {
        Start = new OneShotBehaviour() {
            @Override
            public void action() {
                try {
                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CreateTopics();
                cont = GameController.getInstance();
                addBehaviour(StartEndChecker);
//                addBehaviour(GiveOutTasks);
            }
        };
        addBehaviour(Start);

    }

    Behaviour StartEndChecker = new CyclicBehaviour() {
        @Override
        public void action() {

            if( cont.StartTheGame && !cont.GameInProgress )
            {
                addBehaviour(StartSimulation);
                cont.GameInProgress = true;
            }

            if( cont.StopTheGame && cont.GameInProgress)
            {
                addBehaviour(StopSimulation);
            }

        }
    };
    Behaviour StartSimulation = new OneShotBehaviour() {
        @Override
        public void action() {
            addBehaviour(GiveOutTasks);
            addBehaviour(SendStartMessage);
        }
    };
    Behaviour StopSimulation = new OneShotBehaviour() {
        @Override
        public void action() {
            removeBehaviour(GiveOutTasks);
            addBehaviour(SendReturnMessage);
        }
    };

    Behaviour SendStartMessage = new OneShotBehaviour() {
        @Override
        public void action() {
            ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
            msg.setOntology(GameConfig.StartStopOntology);
            msg.setContent("Start!");
            AddAllReceiversAllAgents(msg);
            send(msg);
        }
    };

    Behaviour SendReturnMessage = new OneShotBehaviour() {
        @Override
        public void action() {
            ACLMessage msg = new ACLMessage(ACLMessage.CANCEL);
            msg.setOntology(GameConfig.StartStopOntology);
            msg.setContent("Return!");
            AddAllReceiversAllAgents(msg);
            send(msg);
        }
    };

    Behaviour HandleOrders = new CyclicBehaviour() {
        @Override
        public void action() {
//            if (GameController.getInstance().UndoneTasks.size() != 0) {
//                // Broadcast that i have new task
//                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
//                AddAllReceiversAllAgents(msg);
//                msg.setLanguage("English");
//                msg.setOntology("New-Task");
//                msg.setContent("Available");
//                send(msg);
//
//                // add behaviour giving out tasks
//            }

        }
    };
    Behaviour GiveOutTasks = new CyclicBehaviour() {
        @Override
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                ACLMessage reply = msg.createReply();
                reply.addReceiver(msg.getSender());
                reply.setOntology(GameConfig.TaskOntology);
                if (msg.getPerformative() == ACLMessage.REQUEST) {
                    // Jezeli agent poprosil o zadanie to mu odpowiedz
                    int TaskId = GameController.getInstance().taskControl.GetUnassignedTaskId();
                    if (TaskId != -1) {
                        reply.setPerformative(ACLMessage.CONFIRM);
                        long time = System.currentTimeMillis();
                        String content = Integer.toString(TaskId) + ";" + Long.toString(time);
                        reply.setContent((content));
                        index++;
                        System.out.println("Master: wysyłam zlecenie do zadania o numerze: " + content);

                    } else {
                        // Sorry, nie ma wolnych tasków, spróbuj później
                        reply.setPerformative(ACLMessage.DISCONFIRM);
                    }
                }

                send(reply);
            } else {
                block();
            }


        }
    };


    private void AddAllReceiversAllAgents(ACLMessage msg) {
        for (MainAgent agent : GameController.getInstance().Agents) {
            msg.addReceiver(agent.JadeAgent.getAID());
        }
        return;
    }

    private void CheckForUncompletedTasks() {

    }

    private void InformAboutNewTasks() {

    }

    private void SendTaskToAgent() {

    }

    private void ConfirmCompletitionOfATask() {

    }

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
