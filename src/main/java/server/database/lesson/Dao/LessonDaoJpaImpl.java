package server.database.lesson.Dao;

import org.jetbrains.annotations.Nullable;
import server.database.lesson.Step;

import java.util.List;

/**
 * Created by Klissan on 14.05.2017.
 */
public class LessonDaoJpaImpl implements LessonDao {
    @Override
    public void addLessonSteps(int lesson_id, List<Integer> steps_id) {

    }

    @Override
    public @Nullable List<Step> getSteps(int id) {
        return null;
    }
}
