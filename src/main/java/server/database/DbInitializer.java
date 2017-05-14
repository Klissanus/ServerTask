package server.database;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import server.RestClient;
import server.database.lesson.Dao.LessonDao;
import server.database.lesson.Step;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klissan on 13.05.2017.
 */
public class DbInitializer {
    private static LessonDao lessonDao = DaoProvider.getLessonDao();

    public static void initDb(){
        if(!isDbEmpty()){
            return;
        }

        try {
            boolean hasNextPage = false;
            int page = 1;
            do {
                JSONObject json = RestClient.requestLessonPage(page++);
                hasNextPage = json.getJSONObject("meta").getBoolean("has_next");

                JSONArray lessons = json.getJSONArray("lessons");
                addLessonsToDb(lessons);
            }while(hasNextPage);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void addLessonsToDb(JSONArray lessons) {
        for (int i = 0; i < lessons.length(); i++) {
            JSONObject lesson = lessons.getJSONObject(i);
            int lessonId = lesson.getInt("id");

            JSONArray steps = lessons.getJSONObject(i).getJSONArray("steps");
            List<Integer> stepsId = getStepsId(steps);

            lessonDao.addLessonSteps(lessonId, stepsId);
        }
    }

    private static List<Integer> getStepsId(JSONArray steps){
        List<Integer> stepsId = new ArrayList<>();
        for(int j = 0; j < steps.length(); j++){
            stepsId.add(steps.getInt(j));
        }
        return stepsId;
    }

    private static boolean isDbEmpty(){
        @Nullable List<Step> steps = lessonDao.getSteps(1);
        return steps == null;
    }
}
