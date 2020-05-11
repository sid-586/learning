public class CorporateClient extends Client {

    public CorporateClient(String accountNumber, double balanceOfAccount) {
        this.balanceOfAccount = balanceOfAccount;
        this.accountNumber = accountNumber;
    }


    @Override
    public void putMoneyToAccount(double amount) {
        balanceOfAccount += amount;
    }



    @Override
    public void getMoneyFromAccount(double amount) {
        balanceOfAccount = balanceOfAccount - 1.01 * amount;
    }
}
