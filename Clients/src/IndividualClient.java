public class IndividualClient extends Client {

    public IndividualClient(String accountNumber, double balanceOfAccount) {
        this.balanceOfAccount = balanceOfAccount;
        this.accountNumber = accountNumber;
    }

    @Override
    public void putMoneyToAccount(double amount) {
        balanceOfAccount += amount;
    }

    @Override
    public void getMoneyFromAccount(double amount) {
        balanceOfAccount -= amount;
    }
}
