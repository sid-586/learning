import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("Введите 20-ти значный номер счета");
        String accountNumber = in.nextLine();
        if (accountNumber.matches("\\d{20}")) {
            Account account1 = new Account(accountNumber);
            account1.getBalanceOfAccount(accountNumber);
        }

        DepositeAccount depoAccount1 = new DepositeAccount("22222222222222222222");
        CreditAccount creditAccount = new CreditAccount("33333333333333333333");

        depoAccount1.putMoneyToAccount(500.0);
        depoAccount1.getMoneyFromAccount(200.0);
        creditAccount.putMoneyToAccount(1000);
        creditAccount.getMoneyFromAccount(500);

        depoAccount1.getBalanceOfAccount("22222222222222222222");
        creditAccount.getBalanceOfAccount("33333333333333333333");

    }
}
