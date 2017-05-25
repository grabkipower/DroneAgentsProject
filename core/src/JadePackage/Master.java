package JadePackage;

import TaskPackage.Order;
import TaskPackage.Task;
import com.badlogic.gdx.Game;
import com.mygdx.game.GameController;
import com.mygdx.game.MainAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
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

    protected void setup() {
        Start = new OneShotBehaviour() {
            @Override
            public void action() {
                try {
                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GameController.getInstance().AddOrder(false);
                CreateTopics();
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("Agent_1", AID.ISLOCALNAME));
                msg.setLanguage("English");
                msg.setOntology("Weather-Forecast-Ontology");
                msg.setContent("Today it’s raining");
                System.out.println("siemanko");
            //    send(msg);
             //   addBehaviour(HandleOrders);
                addBehaviour(GiveOutTasks);

            }
        };
        addBehaviour(Start);

    }

    Behaviour StartSimulation = new OneShotBehaviour() {
        @Override
        public void action() {
            // Send message to all robots, to start the simulation
            // Add all other cyclic behaviours


        }
    };


    Behaviour HandleOrders = new CyclicBehaviour() {
        @Override
        public void action() {
            if (GameController.getInstance().UndoneTasks.size() != 0) {
                // Broadcast that i have new task
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                AddAllReceiversAllAgents(msg);
                msg.setLanguage("English");
                msg.setOntology("New-Task");
                msg.setContent("Available");
                send(msg);

                // add behaviour giving out tasks
            }

        }
    };
    Behaviour GiveOutTasks = new CyclicBehaviour() {
        @Override
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                ACLMessage reply = msg.createReply();
                reply.addReceiver(msg.getSender());
                if (msg.getPerformative() == ACLMessage.REQUEST)
                {
                    // Jezeli agent poprosil o zadanie to mu odpowiedz
                    if (GameController.getInstance().UndoneTasks.size() != 0)
                    {

                        reply.setPerformative(ACLMessage.CONFIRM);
                        reply.setContent((Integer.toString( GameController.getInstance().UndoneTasks.get(index).id)));
                        index++;
                        System.out.println("Master: wysyłam zlecenie do zadania o numerze: "+ Integer.toString(index));

                    }
                    else{
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
