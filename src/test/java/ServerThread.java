import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ServerMain;

/**
 * Server for tests
 */
class ServerThread implements Runnable{
    private static final Logger log = LogManager.getLogger(ServerThread.class);
    private Thread thread;

    ServerThread(){
        thread = new Thread(this, "Server");
        thread.start();
        log.info("Server has started in new thread");
    }

    @Override
    public void run() {
        ServerMain.main(new String[1]);
    }
}
