import java.util.HashMap;

public abstract class Client {

    protected double balanceOfAccount;
    protected String accountNumber;

    public void getBalance(){
        System.out.println("Остаток на счете № " + accountNumber + " составляет " + balanceOfAccount +  "руб.");
    }

    public abstract void putMoneyToAccount(double amount);
    public abstract void getMoneyFromAccount(double amount);
}
