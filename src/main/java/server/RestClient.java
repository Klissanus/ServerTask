package server;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Klissan on 10.05.2017.
 */
public class RestClient {
    private static final Logger log = LogManager.getLogger(RestClient.class);
    private static final OkHttpClient client = new OkHttpClient();
    private static final String URL_LESSON = "http://stepik.org/api/lessons/";
    private static final String URL_LESSON_PAGE = "https://stepik.org/api/lessons?page=";

    public static JSONArray requestSteps(int lessonId) throws IOException {
        String requestUrl = URL_LESSON + lessonId;
        Request request = new Request.Builder()
            .url(requestUrl)
            .get().build();

        log.info("Requesting " + requestUrl);
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Bad Request");
        }

        JSONObject json = new JSONObject(response.body().string());
        JSONArray lessons = json.getJSONArray("lessons");
        JSONArray steps = lessons.getJSONObject(0).getJSONArray("steps");
        log.info("Received steps : " + steps);
        return steps;
    }

    public static JSONObject requestLessonPage(int pageNum) throws IOException {
        String requestUrl = URL_LESSON_PAGE + pageNum;
        Request request = new Request.Builder()
            .url(requestUrl)
            .get().build();

        log.info("Requesting " + requestUrl);
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Bad Request");
        }
        return new JSONObject(response.body().string());
    }
}
