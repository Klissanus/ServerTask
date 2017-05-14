package server.database.lesson.Dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;
import server.database.helper.HibernateHelper;
import server.database.lesson.Lesson;
import server.database.lesson.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klissan on 13.05.2017.
 */
public class LessonDaoHbntImpl implements LessonDao {
    private static final Logger log = LogManager.getLogger(LessonDaoHbntImpl.class);
    private static HibernateHelper dbHelper = new HibernateHelper();


    @Override
    public void addLessonSteps(int lesson_id, List<Integer> steps_id) {
        Lesson lesson = new Lesson(lesson_id);
        List<Step> steps = new ArrayList<>();
        for (Integer step_id : steps_id){
            Step step = new Step(step_id);
            step.setLesson(lesson);
            steps.add(step);
        }
        dbHelper.doTransactional(session -> saveLessonSteps(session, lesson, steps));
    }

    private Session saveLessonSteps(Session session, Lesson lesson, List<Step> steps){
        session.save(lesson);
        for(Step step: steps){
            session.save(step);
        }
        return session;
    }

    @Override
    public @Nullable List<Step> getSteps(int lessonId) {
        log.info("Searching steps by lesson id " + lessonId);
        List response = dbHelper.selectTransactional(session ->
            session.createQuery("from Step s where s.lesson.id = :id")
                .setCacheable(true)
                .setParameter("id", lessonId).list());
        if (response == null || response.isEmpty()) return null;
        return (List<Step>) response;
    }
}
