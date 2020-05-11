import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DepositeAccount extends Account {


    public DepositeAccount(String accountNumber) {
        super(accountNumber);
    }
    Calendar operationData = new GregorianCalendar();

    public void putMoneyToAccount(double amount) {
        this.balanceOfAccount += amount;
        this.operationData = Calendar.getInstance();
    }

    public void getMoneyFromAccount(double amount) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.MONTH, -1);
        if (operationData.compareTo(currentDate) < 0) {
            balanceOfAccount -= amount;
        } else {
            System.out.println("Дата операции не соответствует условиям");
        }
    }
}