import Company.Accounting.Company;

import static Company.Accounting.Post.*;

public class Main {

    public static void main(String[] args) {

        Company company = new Company(50000, 110000, 150000);
        Company bestCompany = new Company(70000, 150000, 250000);

        company.hireAll(company.getListOfNewStaff(180), OPERATOR);
        company.hireAll(company.getListOfNewStaff(80), MANAGER);
        company.hireAll(company.getListOfNewStaff(10), TOP_MANAGER);

        company.getLowestSalaryStaff(30);
        company.getTopSalaryStaff(15);

        company.fire(0, 0, 50, "MANAGER");
        company.fire(0, 0, 50, "OPERATOR");
        company.fire(0, 0, 50, "TOP_MANAGER");

/*
Увольнение 50% сотрудников всех должностей
        company.fire(0,0, 50, "ALL");
*/
        company.getLowestSalaryStaff(30);
        company.getTopSalaryStaff(15);
    }
}
