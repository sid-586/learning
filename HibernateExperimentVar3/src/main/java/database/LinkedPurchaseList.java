package database;

import database.keys.CompositeKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@EqualsAndHashCode(of = {"key"})
@NoArgsConstructor
@Data
@BatchSize(size = 100)
public class LinkedPurchaseList {

    @EmbeddedId
    protected CompositeKey key = new CompositeKey();

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public LinkedPurchaseList(Course course, Students student) {

        this.key.setCourse(course);
        this.key.setStudent(student);

        course.getLinkedPurchaseListCollection().add(this);
        student.getLinkedPurchaseListHashSetForStudents().add(this);
    }

    @Override
    public String toString() {
        return key.toString() + " " + subscriptionDate;
    }
}
