package JadePackage;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 * Created by Mike on 26.04.2017.
 */
public class JadeController {
    ContainerController myContainer;
    public JadeController() {
        Runtime myRuntime = Runtime.instance();

// prepare the settings for the platform that we're going to start
        Profile myProfile = new ProfileImpl("localhost", 1099,
                Profile.PLATFORM_ID);

        myProfile.setParameter(Profile.PLATFORM_ID, "MyMainPlatform");

        // this is not working , why ? =>
        myProfile.setParameter("gui", "true");

//// .. load a container into the above variable ..
//
//        try {
//            AgentController rma = myContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
//            rma.start();
//        } catch(StaleProxyException e) {
//            e.printStackTrace();
//        }
// create the main container
        myContainer = myRuntime.createMainContainer(myProfile);

    }
}
