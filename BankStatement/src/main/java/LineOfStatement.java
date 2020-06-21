import lombok.Getter;

public class LineOfStatement {
@Getter
    private double income, expenditure;
@Getter
    private String vendor;

    public LineOfStatement(String income, String expenditure, String vendor) {
        this.income = Double.parseDouble(income);
        this.expenditure = Double.parseDouble(expenditure);
        this.vendor = vendor;
    }
}
