package database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"course", "teacher"})
@IdClass(TeacherToCoursePool.CompositeTeacherKey.class)
public class TeacherToCoursePool implements Serializable {

    @Id
    protected Course course;
    @Id
    protected Teacher teacher;

    public TeacherToCoursePool(Course course, Teacher teacher) {
        this.setCourse(course);
        this.setTeacher(teacher);

        course.getTeachersSet().add(this);
        teacher.getCoursesSet().add(this);
    }

    @Override
    public String toString() {
        String s = "Teacher " + teacher.getName() + " has course " + course.getName();
        return s;
    }


    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(of = {"course", "teacher"})
    static class CompositeTeacherKey implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
        @JoinColumn(name = "course_id",
                insertable = false,
                updatable = false,
                foreignKey = @ForeignKey(name = "FK_T2C_ID"),
                columnDefinition = "int unsigned")
        protected Course course;

        @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
        @JoinColumn(name = "teacher_id",
                insertable = false,
                updatable = false,
                foreignKey = @ForeignKey(name = "FK_C2T_ID"),
                columnDefinition = "int unsigned")
        protected Teacher teacher;
    }
}