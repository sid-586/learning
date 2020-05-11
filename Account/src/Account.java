import java.util.Calendar;
import java.util.Date;

public class Account {

    protected String accountNumber;
    protected double balanceOfAccount;


    public Account(String accountNumber) {
            this.accountNumber = accountNumber;
    }

    public void putMoneyToAccount(double amount) {
        balanceOfAccount += amount;
    }

    public void getMoneyFromAccount(double amount) {
        balanceOfAccount -= amount;
    }

    public void getBalanceOfAccount(String accountNumber) {
            System.out.println("Остаток на счете № " + accountNumber + " - " + balanceOfAccount);
    }
}
