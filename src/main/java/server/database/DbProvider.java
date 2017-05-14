package server.database;

import server.database.helper.DbHelper;
import server.database.helper.HibernateHelper;
import server.database.lesson.Dao.LessonDao;
import server.database.lesson.Dao.LessonDaoHbntImpl;

/**
 * Created by Klissan on 14.05.2017.
 */
public class DbProvider {
    private static DbHelper helper = new HibernateHelper();
    private static LessonDao lessonDao = new LessonDaoHbntImpl();

    public static DbHelper getHelper(){
        return helper;
    }

    public static LessonDao getLessonDao(){
        return lessonDao;
    }
}
