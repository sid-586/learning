package database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@BatchSize(size = 100)
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String name;
    protected int age;
    @Column(name = "registration_date")
    protected Date registrationDate;

    @OneToMany(mappedBy = "id.student")
    protected Set<Subscriptions> subscriptionsToCourses = new HashSet<>();

    @OneToMany(mappedBy = "key.student", fetch = FetchType.EAGER)
    protected Set<LinkedPurchaseList> linkedPurchaseListHashSetForStudents = new HashSet<>();

    @Override
    public String toString() {
        String s = "Студент " + name;
        return s;
    }
}
