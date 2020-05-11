package Company.Accounting;

import Company.Employee.Staff;
import java.util.Comparator;

public class SalaryComparator implements Comparator<Staff> {
    @Override
    public int compare(Staff o1, Staff o2) {
        return new Double(o1.getMonthSalary()).compareTo(o2.getMonthSalary());
    }
}
