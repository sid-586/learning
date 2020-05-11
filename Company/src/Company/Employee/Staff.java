package Company.Employee;

import Company.Accounting.Post;

public abstract class Staff implements Employee {

    protected double monthSalary;
    protected Post position;
    protected int id, fixSalary;
    protected String name;

    protected Staff(int id, Post position, String name) {
        this.id = id;
        this.position = position;
        this.name = name;
    }

    public Post getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public double getMonthSalary() {
        return 0;
    }

    @Override
    public int getIncomeFromStaff() {
        return 0;
    }
}
