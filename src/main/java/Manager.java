import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.confJndiEjbUrl;

import javax.naming.Context;
import javax.naming.NamingException;

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

            personService.sayHello();


            personService.addName("Papa Moussa");
            personService.addName("Richard");
            personService.addName("...");

        } catch (NamingException e) {
            log.error("Error resolving bean");
        } catch (ClassCastException e) {
            log.error("Resolved EJB is of wrong type");
        } catch (Exception ex) {
            log.error("Je bug ici () {}", ex);
        }


        String name = personService.getName(0);

        personService.removeNameFromIndex(0);

        try {
            personService.getName(0);
        } catch (Exception ex) {
            log.error("Papa moussa n'existe plus !");
        }

        String nameTwo = personService.getName(1);
        personService.removeAll();
        String say = personService.sayHello();

        System.out.println("voici le nom d'indice 0: \t " + name);
        System.out.println("voici le nom d'indice 1: \t " + nameTwo);
        System.out.println(say);

        log.info("Youpiiii !!");
    }

}
