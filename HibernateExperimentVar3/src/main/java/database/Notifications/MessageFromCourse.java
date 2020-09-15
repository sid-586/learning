package database.Notifications;

import database.Course;
import database.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor

public class MessageFromCourse extends Notifications {
    @NotNull
    @JoinColumn(name = "sender", nullable = false, insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected Course courseSender;

    public MessageFromCourse(Teacher addressee, String text, String headline, @NotNull Course course) {
        super(addressee, text, headline);
        this.courseSender = course;
    }
}
