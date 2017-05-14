package server.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import server.RestClient;
import server.database.lesson.Dao.LessonDao;
import server.database.lesson.Dao.LessonDaoHbntImpl;
import server.database.lesson.Step;
import server.exceptions.RequestStepicException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provide REST API
 */
@Path("/data")
public class DataProvider {
    private static final Logger log = LogManager.getLogger(DataProvider.class);
    private static LessonDao lessonDao = new LessonDaoHbntImpl();


    /**
     * Response to client with lesson's steps from database
     *
     * @param lessonId
     *     id of stepic lesson
     *
     * @return Response with json steps array or error
     */
    @GET
    @Produces("application/json")
    public Response getSteps(@QueryParam("lesson") int lessonId) {
        log.info("Request for lesson with id = {}.", lessonId);

        if (lessonId < 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Step> steps = lessonDao.getSteps(lessonId);
        if (steps != null) {
            int[] steps_id = new int[steps.size()];
            for (int i = 0; i < steps_id.length; i++) {
                steps_id[i] = steps.get(i).getId();
            }

            JSONObject json = new JSONObject();
            json.put("steps", steps_id);

            log.info("Response with " + json);
            CacheControl cc = new CacheControl();
            cc.setMaxAge(1000);
            return Response.ok(json.toString()).cacheControl(cc).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    /**
     * Response to client with lesson's steps which requested from Stepic
     *
     * @param lessonId
     *     id of stepic lesson
     *
     * @return Response with json steps array or error
     */
    @GET
    @Path("redirect")
    @Produces("application/json")
    public Response getStepsFromStepic(@QueryParam("lesson") int lessonId) {
        log.info("Request for lesson with id = {}.", lessonId);

        if (lessonId < 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            JSONArray steps = RestClient.requestSteps(lessonId);
            List<Integer> steps_id = new ArrayList<>();
            for (int i = 0; i < steps.length(); i++) {
                steps_id.add(steps.getInt(i));
            }

            JSONObject json = new JSONObject();
            json.put("steps", steps_id);
            log.info("Response with " + json);
            return Response.ok(json.toString()).build();

        } catch (IOException | RequestStepicException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
