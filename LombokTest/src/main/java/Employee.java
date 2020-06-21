import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@Data
@AllArgsConstructor
public class Employee
{
    private String name;
    private Integer salary;
    private Date workStart;

}
