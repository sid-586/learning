package Company.Accounting;

import Company.Employee.*;
import Util.Names;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Company {


    private int fixSalaryOperator;
    private int fixSalaryManager;
    private int fixSalaryTopManager;
    private int id;
    private List<Staff> listOfStaff;
    private int numberOfFiredTotal;

    public Company(int fixSalaryOperator, int fixSalaryManager, int fixSalaryTopManager) {
        this.fixSalaryOperator = fixSalaryOperator;
        this.fixSalaryManager = fixSalaryManager;
        this.fixSalaryTopManager = fixSalaryTopManager;
        // Имитация табельного номера сотрудника в штатной ведомости компании
        this.id = 1;
                /* Штатная ведомость компании (табельный номер+должность+имя)
                 Присвоение имени реализовано генератором строк из 8 случайных символов в классе Names.
                 В принципе, для целей задания можно было бы обойтись без этого, решил оставить как имитацию реального ввода ФИО при приеме на работу
                */
        this.listOfStaff = new ArrayList<Staff>();

    }


    public void hire(Post post) {
        switch (post) {
            case MANAGER:
                listOfStaff.add(new Manager(this, post.MANAGER, Names.getName()));
                changeSalaryOfTopManagers();
                break;
            case OPERATOR:
                listOfStaff.add(new Operator(this, post.OPERATOR, Names.getName()));
                break;
            case TOP_MANAGER:
                listOfStaff.add(new TopManager(this, post.TOP_MANAGER, Names.getName()));
                break;
        }
        id++;
    }

    // Прием группы сотрудников проще реализовать циклом приема одного сотрудника, но в задании сказано - принять список(

    // Выделение должностей принимаемого списка
    public void hireAll(List<Staff> listOfNewStaff, Post post) {
        int index = listOfStaff.size();
        listOfStaff.addAll(listOfNewStaff);
        switch (post) {
            case MANAGER:
                for (int i = 1; i <= listOfNewStaff.size(); i++) {
                    String newStaffName = listOfStaff.get(index).getName();
                    listOfStaff.set(index, new Manager(this, post.MANAGER, newStaffName));
                    id++;
                    index++;
                    changeSalaryOfTopManagers();
                }
                break;
            case OPERATOR:
                for (int i = 1; i <= listOfNewStaff.size(); i++) {
                    String newStaffName = listOfStaff.get(index).getName();
                    listOfStaff.set(index, new Operator(this, post.OPERATOR, newStaffName));
                    id++;
                    index++;
                }
                break;
            case TOP_MANAGER:
                for (int i = 1; i <= listOfNewStaff.size(); i++) {
                    String newStaffName = listOfStaff.get(index).getName();
                    listOfStaff.set(index, new TopManager(this, post.TOP_MANAGER, newStaffName));
                    id++;
                    index++;
                }
                break;
        }
    }


    // Определение типа увольнения: одного сотруника по его табельному номеру или группы (в чел. или %)
    public void fire(int id, int numberOfFired, int partOfStaffsToFire, String nameOfPosition) {
        if (id != 0) {
            for (int i = 0; i < listOfStaff.size(); i++) {
                if (listOfStaff.get(i).getId() == id) {
                    this.listOfStaff.remove(i);
                    numberOfFiredTotal++;
                    return;
                }
            }
        }
        if (numberOfFired != 0) {
            fireByPosts(numberOfFired, 0, nameOfPosition);
            return;
        }
        if (partOfStaffsToFire != 0) {
            fireByPosts(0, partOfStaffsToFire, nameOfPosition);
            return;
        }
    }


    // Распределение увольняемых по должностям

    private void fireByPosts(int numberOfFired, int partOfStaffsToFire, String nameOfPosition) {

        Post post;
        switch (nameOfPosition) {
            case "MANAGER":
                post = Post.MANAGER;
                fireOperation(numberOfFired, partOfStaffsToFire, post);
                changeSalaryOfTopManagers();
                break;
            case "OPERATOR":
                post = Post.OPERATOR;
                fireOperation(numberOfFired, partOfStaffsToFire, post);
                break;
            case "TOP_MANAGER":
                post = Post.TOP_MANAGER;
                fireOperation(numberOfFired, partOfStaffsToFire, post);
                break;
            case "ALL":
                numberOfFired = numberOfFired / 3;
                partOfStaffsToFire = partOfStaffsToFire / 3;
                post = Post.TOP_MANAGER;
                fireOperation(numberOfFired, partOfStaffsToFire, post);
                post = Post.MANAGER;
                fireOperation(numberOfFired, partOfStaffsToFire, post);
                post = Post.OPERATOR;
                fireOperation(numberOfFired, partOfStaffsToFire, post);
                break;
        }
    }

    // удаление уволенных из штатной ведомости

    private void fireOperation(int numberOfFired, double partOfStaffsToFire, Post post) {
        if (numberOfFired == 0) {
            int countOfStaffs = 0;
            for (Staff employee : listOfStaff) {
                if (employee.getPosition().equals(post)) {
                    countOfStaffs++;
                }
            }
            numberOfFired = (int) (countOfStaffs * (partOfStaffsToFire / 100));
        }
        int i = 1;
        for (int j = listOfStaff.size() - 1; j >= 0; j--) {
            if (listOfStaff.get(j).getPosition().equals(post) && i <= numberOfFired) {
                this.listOfStaff.remove(j);
                i++;
            }
        }
        numberOfFiredTotal += numberOfFired;
    }

    public int getTotalIncom() {
        int incomeFromManager = 0;
        for (int i = id - numberOfFiredTotal - 2; i >= 0; i--) {
            if (listOfStaff.get(i).getPosition().equals(Post.MANAGER)) {
                incomeFromManager += listOfStaff.get(i).getIncomeFromStaff();
            }
        }
        return incomeFromManager;
    }

    private void changeSalaryOfTopManagers() {
        for (int i = id - numberOfFiredTotal - 2; i >= 0; i--) {
            if (listOfStaff.get(i).getPosition().equals(Post.TOP_MANAGER)) {
                id = listOfStaff.get(i).getId();
                String savedName = listOfStaff.get(i).getName();
                listOfStaff.set(i, new TopManager(this, Post.TOP_MANAGER, savedName));
            }
        }
    }


    public List<Staff> getLowestSalaryStaff(int count) {
        Comparator comparator = new SalaryComparator();
        Collections.sort(listOfStaff, comparator);
        List<Staff> topSalaryStaff = new ArrayList<>();
        System.out.println("Список " + count + " зарплат по возрастанию" + "\n");
        for (int i = 0; i < count && i < listOfStaff.size(); i++) {
            topSalaryStaff.add(listOfStaff.get(i));
            System.out.println((i + 1) + ". " + topSalaryStaff.get(i).getMonthSalary());
        }
        return topSalaryStaff;
    }

    public List<Staff> getTopSalaryStaff(int count) {
        Collections.reverse(listOfStaff);
        List<Staff> topSalaryStaff = new ArrayList<>();
        System.out.println("Список " + count + " зарплат по убыванию" + "\n");
        for (int i = 0; i < count && i < listOfStaff.size(); i++) {
            topSalaryStaff.add(listOfStaff.get(i));
            System.out.println((i + 1) + ". " + topSalaryStaff.get(i).getMonthSalary());
        }
        return topSalaryStaff;
    }

    public void printList() {
        for (Staff staff : listOfStaff) {
            System.out.println(staff.getId() + " " + staff.getPosition() + " " + staff.getName() + " " + staff.getMonthSalary());
        }
    }


    // получение списка кандидатов на прием на работу до присвоения табельного номера и должности
    public List<Staff> getListOfNewStaff(int countOfHired) {
        List<Staff> listOfNewStaff = new ArrayList<Staff>();
        for (int i = 0; i < countOfHired; i++) {
            listOfNewStaff.add(new Candidate(0, null, Names.getName()));
        }
        return listOfNewStaff;
    }


    public int getFixSalaryOperator() {
        return fixSalaryOperator;
    }

    public int getFixSalaryManager() {
        return fixSalaryManager;
    }

    public int getFixSalaryTopManager() {
        return fixSalaryTopManager;
    }

    public int getId() {
        return id;
    }
}

