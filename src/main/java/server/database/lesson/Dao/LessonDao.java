package server.database.lesson.Dao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.database.lesson.Lesson;
import server.database.lesson.Step;

import java.util.List;

/**
 * Created by Klissan on 13.05.2017.
 * Provides an abstraction layer for databases of {@link Lesson}
 */
public interface LessonDao {

    /**
     * @param lesson
     */
    void addLessonSteps(int lesson_id, List<Integer> steps_id);


    /**
     * Finds lesson by own id
     *
     * @param  id lesson id to find
     * @return Lesson object if found null otherwise
     */
    @Nullable
    List<Step> getSteps(int id);
}