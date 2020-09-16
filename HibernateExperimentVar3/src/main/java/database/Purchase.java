package database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"course", "student"})
@Table(name = "PurchaseList", indexes = {@Index(name = "purchase_idx", columnList = "course_id, student_id")})
@IdClass(Purchase.PurchaseListKey.class)
public class Purchase implements Serializable {
    @Id
    protected Course course;
    @Id
    protected Students student;

    @Column(name = "course_id")
    protected Integer courseId;
    @Column(name = "student_id")
    protected Integer studentId;

    @Column(name = "course_name")
    protected String courseName;
    @Column(name = "student_name")
    protected String studentName;
    protected int price;
    @Column(name = "subscription_date")
    protected Date subscriptionDate;

    public Purchase(Course course, Students student) {

        this.price = course.price;
        this.courseName = course.name;
        this.studentName = student.name;
        this.setCourse(course);
        this.setStudent(student);
    }

    @Override
    public String toString() {
        String s = "Студент " + studentName + " приобрёл курс " + courseName + " стоимостью " + price;
        return s;
    }

    @Embeddable
    @Data
    @EqualsAndHashCode(of = {"course", "student"})
    @NoArgsConstructor
    @AllArgsConstructor
    static class PurchaseListKey implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
        @JoinColumn(name = "course_id",
                updatable = false,
                insertable = false,
                foreignKey = @ForeignKey(name = "FK_PL_C_ID"),
                columnDefinition = "int unsigned")
        protected Course course;

        @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
        @JoinColumn(name = "student_id",
                updatable = false,
                insertable = false,
                foreignKey = @ForeignKey(name = "FK_PL_S_ID"),
                columnDefinition = "int unsigned")
        protected Students student;

    }
}
