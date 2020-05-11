package Company.Employee;

import Company.Accounting.Post;


public class Candidate extends Staff {
    public Candidate(int id, Post position, String name) {
        super(id, position, name);
        this.id = 0;
        this.position = null;
    }
}
