public class EnterpriserClient extends Client {

    public EnterpriserClient(String accountNumber, double balanceOfAccount) {
        this.balanceOfAccount = balanceOfAccount;
        this.accountNumber = accountNumber;
    }

    @Override
    public void putMoneyToAccount(double amount) {
        if (amount < 1000) {
            balanceOfAccount = balanceOfAccount + 0.99 * amount;
        } else {
            balanceOfAccount = balanceOfAccount + 0.995 * amount;
        }
    }

    @Override
    public void getMoneyFromAccount(double amount) {
        balanceOfAccount -= amount;
    }
}
