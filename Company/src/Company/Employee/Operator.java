package Company.Employee;

import Company.Accounting.Company;
import Company.Accounting.Post;


public class Operator extends Staff implements Employee {

    public Operator(Company company, Post post, String name) {
        super(company.getId(), post, name);
        this.fixSalary = company.getFixSalaryOperator() + (int) (Math.random() * company.getFixSalaryOperator() / 3);  // имитация персональных надбавок для наглядности работы программы
        this.position = post;
    }

    @Override
    public double getMonthSalary() {
        this.monthSalary = fixSalary;
        return monthSalary;
    }

    @Override
    public int getIncomeFromStaff() {
        return 0;
    }
}
