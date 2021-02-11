package utils;

public class confJndiEjbUrl {

    /**
     * JNDI Pattern
     * Syntax: ejb:${appName}/${moduleName}/${beanName}!${remoteView}
     *
     * @param appName         name of EAR deployment (or empty for single EJB/WAR deployments)
     * @param moduleName      name of EJB/WAR deployment
     * @param beanName        name of the EJB (Simple name of EJB class)
     * @param remoteInterface fully qualified remote interface class
     * @return String
     * @author Meudje Karl
     */
    public static String getEjbUrl(String appName, String moduleName, String beanName, String remoteInterface) {

        String sb = "ejb:/" +
                appName +
                "/" +
                moduleName +
                "/" +
                beanName +
                "!" +
                remoteInterface;
        return sb;
    }
}
