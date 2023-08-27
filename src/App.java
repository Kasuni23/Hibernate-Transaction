import org.hibernate.Session;

import Util.sessionFactoryConfiguration;

public class App {
    public static void main(String[] args) throws Exception {
        Session session = sessionFactoryConfiguration.getInstance().getSession();
    }
}
