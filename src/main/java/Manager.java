import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.confJndiEjbUrl;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.List;

import static utils.ContextUtils.createEjbProxy;
import static utils.ContextUtils.createRemoteEjbContext;

public class Manager {


    public static void main(String[] args) {

        Logger log = LogManager.getLogger("Manager");

        log.info("Initialisation Remote Connexion");
// Connection to Wildfly Server instance
        String host = "127.0.0.1";
        String port = "8080"; // Wildfly HTTP port

        Context remotingContext;
        try {
            remotingContext = createRemoteEjbContext(host, port);
        } catch (NamingException e) {
            log.error("Error setting up remoting context");
            return;
        }

        final String appName = "ejb-remote";
        final String moduleName = "";
        final String beanName = "PersonServiceImpl";
        final String remoteInterface = "PersonInterface";

        String jndiEjbUrl = confJndiEjbUrl.getEjbUrl(appName, moduleName, beanName, remoteInterface);

        PersonInterface personService = null;
        try {


            personService = createEjbProxy(remotingContext, jndiEjbUrl, PersonInterface.class);
            log.info(" Connexion to Remote is Done !");

            personService.addName("Judin");
            personService.addName("Meddi");
            personService.addName("Jeyapriya");
            personService.addName("Ibrahim");

        } catch (NamingException e) {
            log.error("Error resolving bean");
        } catch (ClassCastException e) {
            log.error("Resolved EJB is of wrong type");
        } catch (Exception ex) {
            log.error("Je bug ici () {}", ex);
        }


        List<String> personnes = personService.getAll();

// personnes.forEach((personne) -> System.out.println(personne));

        /* personnes.forEach((personne) -> {
                   System.out.println(personne);
    });  */
        personnes.forEach(System.out::println);


        log.info("Client B");
    }

}
