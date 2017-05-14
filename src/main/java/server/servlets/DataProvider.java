package server.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import server.RestClient;
import server.database.DbProvider;
import server.database.lesson.Dao.LessonDao;
import server.database.lesson.Dao.LessonDaoHbntImpl;
import server.database.lesson.Step;

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
 * Created by Klissan on 10.05.2017.
 */
@Path("/data")
public class DataProvider {
    private static final Logger log = LogManager.getLogger(DataProvider.class);
    private static LessonDao lessonDao = DbProvider.getLessonDao();

    @GET
    @Produces("application/json")
    public Response getSteps(@QueryParam("lesson") int lessonId) {
        log.info("Request for lesson with id = {}.", lessonId);

        if(lessonId < 0){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Step> steps = lessonDao.getSteps(lessonId);
        if(steps != null) {
            int[] steps_id = new int[steps.size()];
            for(int i = 0; i < steps_id.length; i++){
                steps_id[i] = steps.get(i).getId();
            }

            JSONObject json = new JSONObject();
            json.put("steps", steps_id);

            CacheControl cc = new CacheControl();
            cc.setMaxAge(1000);
            return Response.ok(json.toString()).cacheControl(cc).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }


    @GET
    @Path("redirect")
    @Produces("application/json")
    public Response getStepsFromStepic(@QueryParam("lesson") int lessonId) {
        log.info("Request for lesson with id = {}.", lessonId);

        if(lessonId < 0){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            JSONArray steps = RestClient.requestSteps(lessonId);
            List<Integer> steps_id = new ArrayList<>();
            for (int i=0;i< steps.length(); i++){
                steps_id.add(steps.getInt(i));
            }

            JSONObject mainObj = new JSONObject();
            mainObj.put("steps", steps_id);
            return Response.ok(mainObj.toString()).build();

        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
