package database.keys;

import database.Course;
import database.Students;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = {"course", "student"})
@Data
public class CompositeKey implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "course_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "FK_COURSE_ID"),
            columnDefinition = "int unsigned")
    protected Course course;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "student_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "FK_STUDENT_ID"),
            columnDefinition = "int unsigned")
    protected Students student;

    public CompositeKey(Course course, Students student) {
        this.student = student;
        this.course = course;
    }

    @Override
    public String toString() {
        String s = "Подписка на курс " + course.getName() +
                " студент " + student.getName() +
                " стоимость " + course.getPrice() + "р.";
        return s;
    }
}
