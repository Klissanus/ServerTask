package server.database.lesson;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jetbrains.annotations.NotNull;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by Klissan on 13.05.2017.
 */

@Entity
@Table(name = "lessons")
public class Lesson implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lesson")
    private Set<Step> steps;


    //Constructors
    public Lesson() {
    }

    public Lesson(int id) {
        this.id = id;
    }


    //Getters and setters
    public int getId() {
        return id;
    }
    void setId(int id){this.id = id;}

    public Set<Step> getSteps() {
        return steps;
    }
    void setSteps(Set<Step> steps) { this.steps = steps;}


    @Override
    public String toString() {
        return "Lesson { id : " + this.id + " steps : " + this.steps + "}";
    }
}
