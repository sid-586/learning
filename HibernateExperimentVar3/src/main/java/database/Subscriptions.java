package database;

import database.keys.CompositeKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class Subscriptions {
    @EmbeddedId
    protected CompositeKey id;

    @Column(name = "subscription_date")
    protected Date subscriptionDate;

    public Subscriptions(Course course, Students student, Date subscriptionDate) {

        this.subscriptionDate = subscriptionDate;
        this.id.setStudent(student);
        this.id.setCourse(course);

        course.getSubscriptionsSet().add(this);
        student.getSubscriptionsToCourses().add(this);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
