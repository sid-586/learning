package database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@Table(name = "Teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int salary;
    private int age;

    @OneToMany(mappedBy = "teacher")
    private Set<TeacherToCoursePool> coursesSet = new HashSet<>();

    @Override
    public String toString() {
        String s = "Teacher " + name;
        return s;
    }

}
