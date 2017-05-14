package server;

import server.database.DbInitializer;
import server.servlets.ServletApi;

/**
 * Entry point of server
 */
public class ServerMain {
    public static void main(String[] args) {
        try {
            DbInitializer.initDb();
            ServletApi.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
