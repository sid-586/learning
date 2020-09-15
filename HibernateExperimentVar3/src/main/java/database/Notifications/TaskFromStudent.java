package database.Notifications;

import database.Students;
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
public class TaskFromStudent extends Notifications {
    @NotNull
    @JoinColumn(name = "sender", nullable = false, insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected Students studentTaskSender;
    protected int taskNumber;

    public TaskFromStudent(Teacher addressee, String text, String headline, @NotNull Students student, int taskNumber) {
        super(addressee, text, headline);
        this.taskNumber = taskNumber;
        this.studentTaskSender = student;
    }
}
