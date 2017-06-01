package server.database;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import server.RestClient;
import server.database.lesson.Dao.LessonDao;
import server.database.lesson.Dao.LessonDaoHbntImpl;
import server.database.lesson.Step;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Database Initializer with data from Stepic
 */
public class DbInitializer {
    private static LessonDao lessonDao = new LessonDaoHbntImpl();

    /**
     * Initialise Database with data of lesson and steps from Stepic
     * First check is db empty if yes
     * Requesting Stepic's lessons while pages exists
     * and save lessons to db
     */
    public static void initDb() {
        //just check exist lesson with id=1 in db
        if (!isDbEmpty()) {
            return;
        }

        /** parse json with format:
         * {
         "meta": {
         "page": 1,
         "has_next": true,
         "has_previous": false
         },
         "lessons": [
         {
         "id": 1,
         "steps": [
         541,
         1053,
         92,
         4,
         443
         ],
         ...
         },
         ...
         ]
         }
         */
        try {
            boolean hasNextPage;
            int page = 1;
            do {
                JSONObject json = RestClient.requestLessonPage(page++);
                hasNextPage = json.getJSONObject("meta").getBoolean("has_next");

                JSONArray lessons = json.getJSONArray("lessons");
                addLessonsToDb(lessons);
            } while (hasNextPage);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Parse json format ;
     * "lessons": [
     * {
     * "id": 1,
     * "steps": [
     * 541,
     * 1053,
     * 92,
     * 4,
     * 443
     * ],
     * ...
     * },
     * ...
     * ]
     *
     * And add lessons to db
     *
     * @param lessons
     *     json ArrayObject contains lessons
     */
    private static void addLessonsToDb(JSONArray lessons) {
        for (int i = 0; i < lessons.length(); i++) {
            JSONObject lesson = lessons.getJSONObject(i);
            int lessonId = lesson.getInt("id");

            JSONArray steps = lessons.getJSONObject(i).getJSONArray("steps");
            List<Integer> stepsId = getStepsId(steps);
            List<Integer> textSteps = new ArrayList<>();
            //request steps
            for(Integer id : stepsId){
                try {
                    if (RestClient.isText(id)){
                        textSteps.add(id);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }

            lessonDao.addLessonSteps(lessonId, textSteps);
        }
    }

    /**
     * Parse json format :
     * "steps": [
     * 541,
     * 1053,
     * 92,
     * 4,
     * 443
     * ],
     *
     * @param steps
     *     jsonArray object contains steps of lesson
     *
     * @return List<Integer> steps of lesson
     */
    private static List<Integer> getStepsId(JSONArray steps) {
        List<Integer> stepsId = new ArrayList<>();
        for (int j = 0; j < steps.length(); j++) {
            stepsId.add(steps.getInt(j));
        }
        return stepsId;
    }

    /**
     * Just check exist lesson with id=1 in db
     *
     * @return true if lesson not exist in db else otherwise
     */
    private static boolean isDbEmpty() {
        @Nullable List<Step> steps = lessonDao.getSteps(15);
        return steps == null;
    }
}
