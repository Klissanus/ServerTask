package server;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import server.exceptions.RequestStepicException;

import java.io.IOException;

/**
 * Server client that connect to Stepic and received data
 */
public class RestClient {
    private static final Logger log = LogManager.getLogger(RestClient.class);
    private static final OkHttpClient client = new OkHttpClient();
    /** url to get lesson steps by its id */
    private static final String URL_LESSON = "http://stepik.org/api/lessons/";
    /** url to get lessons on page */
    private static final String URL_LESSON_PAGE = "https://stepik.org/api/lessons?page=";

    /**
     * Request Stepic for get lesson info by given lessonId
     *
     * @param lessonId
     *     id of stepic lesson
     *
     * @return JSONArray which contains steps of lesson with givaen Id
     *
     * @throws IOException
     *     if response execute with error
     * @throws RequestStepicException
     *     if Stepic return error
     *     or internet connection lost
     */
    public static JSONArray requestSteps(int lessonId) throws IOException {
        String requestUrl = URL_LESSON + lessonId;
        Request request = new Request.Builder()
            .url(requestUrl)
            .get().build();

        log.info("Requesting " + requestUrl);
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.info("Unsuccessful request");
            throw new RequestStepicException(
                "Unsuccessful request to url" + requestUrl);
        }

        JSONObject json = new JSONObject(response.body().string());
        JSONArray lessons = json.getJSONArray("lessons");
        JSONArray steps = lessons.getJSONObject(0).getJSONArray("steps");
        log.info("Received steps : " + steps);
        return steps;
    }

    /**
     * Request Stepic for get lessons in page
     *
     * @param pageNum
     *     number of page with lessons
     *
     * @return received page in json
     *
     * @throws IOException
     *     if response execute with error
     * @throws RequestStepicException
     *     if Stepic return error
     *     or internet connection lost
     */
    public static JSONObject requestLessonPage(int pageNum) throws IOException {
        String requestUrl = URL_LESSON_PAGE + pageNum;
        Request request = new Request.Builder()
            .url(requestUrl)
            .get().build();

        log.info("Requesting " + requestUrl);
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.info("Unsuccessful request");
            throw new RequestStepicException(
                "Unsuccessful request to url" + requestUrl);
        }
        log.info("Received page in json " + pageNum);
        String json = response.body().string();
        response.body().close();
        return new JSONObject(json);
    }

    public static boolean isText(int stepId) throws IOException {
        String URL_STEP_ID = "http://stepik.org/api/steps/";
        String requestUrl = URL_LESSON_PAGE + stepId;
        Request request = new Request.Builder()
            .url(requestUrl)
            .get().build();

        log.info("Requesting " + requestUrl);
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.info("Unsuccessful request");
            throw new RequestStepicException(
                "Unsuccessful request to url" + requestUrl);
        }
        log.info("Received step in json " + stepId);

        JSONObject json = new JSONObject(response.body().string());
        JSONObject block = json.getJSONArray("steps")
            .getJSONObject(0).getJSONObject("block");
        String name = block.getString("name");

        response.body().close();
        return "text".equals(name);
    }
}
