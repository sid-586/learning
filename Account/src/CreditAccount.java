public class CreditAccount extends Account {
    public CreditAccount(String accountNumber) {
        super(accountNumber);
    }

    public void getMoneyFromAccount(double amount) {
        balanceOfAccount -= (amount * 1.01);
    }
}


