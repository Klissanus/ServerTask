package server.database.lesson;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * Lessons described by its id and steps
 */
@Entity
@Table(name = "lessons")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Lesson implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lesson")
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
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

    void setId(int id) {
        this.id = id;
    }

    public Set<Step> getSteps() {
        return steps;
    }

    void setSteps(Set<Step> steps) {
        this.steps = steps;
    }


    @Override
    public String toString() {
        return "Lesson { id : " + this.id + " steps : " + this.steps + "}";
    }
}
