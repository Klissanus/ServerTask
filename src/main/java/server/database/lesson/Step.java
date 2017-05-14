package server.database.lesson;


import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Klissan on 13.05.2017.
 */
@Entity
@Table(name = "steps")
public class Step implements Serializable{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;


    public Step(){}

    public Step(int id){
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
