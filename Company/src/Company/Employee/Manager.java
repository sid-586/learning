package Company.Employee;

import Company.Accounting.Company;
import Company.Accounting.Post;


public class Manager extends Staff implements Employee {

    int incomeFromManager;

    public Manager(Company company, Post post, String name) {
        super(company.getId(), post, name);
        this.fixSalary = company.getFixSalaryManager();
        this.incomeFromManager = (int) (Math.random() * 20000 + fixSalary * 2);
        this.position = post;
    }

    public int getIncomeFromStaff() {
        return incomeFromManager;
    }

    @Override
    public double getMonthSalary() {
        this.monthSalary = fixSalary + incomeFromManager * 0.05;
        return monthSalary;
    }
}