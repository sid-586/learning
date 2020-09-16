package database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import utils.CourseType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@Table(name = "Courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected String name;
    protected Integer duration;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    protected CourseType type;
    protected String description;
    @Column(name = "students_count")
    protected Integer studentsCount;
    protected int price;
    @Column(name = "price_per_hour")
    protected float pricePerHour;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    protected Teacher teacher;

    @OneToMany(mappedBy = "course")
    protected Set<TeacherToCoursePool> teachersSet = new HashSet<>();

    @OneToMany(mappedBy = "id.course")
    protected Set<Subscriptions> subscriptionsSet = new HashSet<>();

    @OneToMany(mappedBy = "key.course", fetch = FetchType.EAGER)
    protected Set<LinkedPurchaseList> linkedPurchaseListCollection = new HashSet<>();

    @Override
    public String toString() {
        String s = "Курс " + name + " " + description + " " + price + "р.";
        return s;
    }
}
