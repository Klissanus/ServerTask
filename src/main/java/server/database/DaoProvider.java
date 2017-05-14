package server.database;

import server.database.lesson.Dao.LessonDao;
import server.database.lesson.Dao.LessonDaoJpaImpl;

/**
 * Created by Klissan on 14.05.2017.
 */
public class DaoProvider {
    private static LessonDao lessonDao = new LessonDaoJpaImpl();

    public static LessonDao getLessonDao(){
        return lessonDao;
    }
}
