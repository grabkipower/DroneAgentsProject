package JadePackage;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Created by Mike on 07.05.2017.
 */
public class Master extends Agent {
    Behaviour Start;

    protected void setup() {
        Start = new OneShotBehaviour() {
            @Override
            public void action() {
                try {
                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("Agent_1", AID.ISLOCALNAME));
                msg.setLanguage("English");
                msg.setOntology("Weather-Forecast-Ontology");
                msg.setContent("Today it’s raining");
                System.out.println("siemanko");
                send(msg);


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
            // Check the order queue for new tasks
            // Create task queue
            // Inform agents about new tasks
        }
    };

    private void CheckForUncompletedTasks() {

    }

    private void InformAboutNewTasks() {

    }

    private void SendTaskToAgent() {

    }

    private void ConfirmCompletitionOfATask() {

    }
}
