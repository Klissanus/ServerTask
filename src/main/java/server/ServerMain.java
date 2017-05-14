package server;

import server.database.DbInitializer;
import server.servlets.ServletApi;

/**
 * Created by Klissan on 10.05.2017.
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
