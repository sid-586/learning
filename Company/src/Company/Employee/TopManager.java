package Company.Employee;

import Company.Accounting.Company;
import Company.Accounting.Post;


public class TopManager extends Staff implements Employee {

    int totalIncom;

    public TopManager(Company company, Post post, String name) {
        super(company.getId(), post, name);
        this.fixSalary = company.getFixSalaryTopManager();
        this.totalIncom = company.getTotalIncom();
        this.monthSalary = getMonthSalary();
    }

    @Override
    public double getMonthSalary() {
        if (totalIncom > 10000000) {
            monthSalary = fixSalary + fixSalary * 1.5;
        } else {
            this.monthSalary = fixSalary;
        }
        return monthSalary;
    }

    @Override
    public int getIncomeFromStaff() {
        return 0;
    }

}
