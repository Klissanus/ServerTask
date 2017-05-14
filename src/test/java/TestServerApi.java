import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Simple tests for server api
 */
public class TestServerApi {

    private static final Logger log = LogManager.getLogger(TestServerApi.class);
    private static final OkHttpClient client = new OkHttpClient();

    private static final String HOST = "http://localhost";
    private static final int PORT = 8080;

    private static String requestUrl = HOST + ":" + PORT + "/data?lesson=";
    private static String requestUrlRedir = HOST + ":" + PORT + "/data/redirect?lesson=";

    private static final int SLEEP_TIME = 7000;



    @Test
    public void testNonExistLesson() throws InterruptedException {
        new ServerThread();
        Thread.sleep(SLEEP_TIME);

        int lessonId = 20;
        try {
            Response response = request(requestUrl + lessonId);
            Response responseRedir = request(requestUrlRedir + lessonId);

            String message = response.message();
            String messageRedir = responseRedir.message();
            log.info(message);
            log.info(messageRedir);
            Assert.assertEquals(message, messageRedir);

        } catch (IOException e) {
            log.error(e.toString());
        }
    }


    @Test
    public void testExistLesson() throws InterruptedException {
        new ServerThread();
        Thread.sleep(SLEEP_TIME);

        int lessonId = 10;
        try {
            Response response = request(requestUrl + lessonId);
            Response responseRedir = request(requestUrlRedir + lessonId);

            String body = response.body().string();
            String bodyRedir = responseRedir.body().string();
            log.info(body);
            log.info(bodyRedir);
            Assert.assertEquals(body, bodyRedir);

        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    private Response request(String url) throws IOException {
        Request request = new Request.Builder()
            .url(url).get().build();
        log.info("Requesting " + url);
        return client.newCall(request).execute();
    }


}


