package server.servlets;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Created by Klissan on 10.05.2017.
 */
public class ServletApi {


    public static void start() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
            ServletContainer.class, "/*");

        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
            "jersey.config.server.provider.packages",
            "server.servlets"
        );

    /*jerseyServlet.setInitParameter(
        "com.sun.jersey.spi.container.ContainerRequestFilters",
        AuthenticationFilter.class.getCanonicalName()
    );*/

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    private ServletApi() {
    }

}
