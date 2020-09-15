package database.Notifications;

import database.Students;
import database.Teacher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("C")
public class CommentFromStudent extends Notifications {
    @NotNull
    @JoinColumn(name = "sender", nullable = false, insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected Students studentSender;

    public CommentFromStudent(@NotNull Students student, String headline, String text, Teacher addressee) {
        super(addressee, text, headline);
        this.studentSender = student;
    }

}
