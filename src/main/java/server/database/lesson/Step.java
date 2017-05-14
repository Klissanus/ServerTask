package server.database.lesson;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Steps of lesson
 */
@Entity
@Table(name = "steps")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Step implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Lesson lesson;


    //Constructors
    public Step() {
    }

    public Step(int id) {
        this.id = id;
    }


    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
