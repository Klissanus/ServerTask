package server.database.lesson.Dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import server.database.helper.JpaHelper;
import server.database.lesson.Lesson;
import server.database.lesson.Step;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klissan on 14.05.2017.
 */
public class LessonDaoJpaImpl implements LessonDao {
    private static final Logger log = LogManager.getLogger(LessonDaoJpaImpl.class);
    private static JpaHelper dbHelper = new JpaHelper();

    @Override
    public void addLessonSteps(int lesson_id, List<Integer> steps_id) {
        Lesson lesson = new Lesson(lesson_id);
        List<Step> steps = new ArrayList<>();
        for (Integer step_id : steps_id){
            Step step = new Step(step_id);
            step.setLesson(lesson);
            steps.add(step);
        }
        dbHelper.doTransactional(manager -> saveLessonSteps(manager, lesson, steps));
    }

    @Override
    public @Nullable List<Step> getSteps(int lessonId) {
        log.info("Searching steps by lesson id " + lessonId);
        List response = dbHelper.selectTransactional(manager ->
            manager.createQuery("from Step s where s.lesson.id = :id")
                .setParameter("id", lessonId).getResultList());
        if (response == null || response.isEmpty()) return null;
        return (List<Step>) response;
    }

    private EntityManager saveLessonSteps(EntityManager manager, Lesson lesson, List<Step> steps){
        manager.persist(lesson);
        for(Step step: steps){
            manager.persist(step);
        }
        return manager;
    }
}
