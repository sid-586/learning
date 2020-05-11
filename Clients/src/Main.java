import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер счета");
        String input = scanner.nextLine();
        if (input.matches("\\d{20}")) {
            runCommand(input);
        } else {
            System.out.println("Неверный формат ввода");
        }
    }

    public static void runCommand(String input) {

        if (input.charAt(4) == '0') {
            CorporateClient corporateClient = new CorporateClient(input, 0);
            System.out.println("Открыт счет юридического лица № " + input +  "руб.");
            operations(corporateClient);
        } else  if (input.charAt(4) == '3') {
            EnterpriserClient enterpriserClient = new EnterpriserClient(input, 0);
            System.out.println("Открыт счет ИП № " + input +  "руб.");
            operations(enterpriserClient);
        } else if (input.charAt(4) == '7') {
            IndividualClient individualClient = new IndividualClient(input, 0);
            System.out.println("Открыт счет физического лица № " + input +  "руб.");
            operations(individualClient);
        }else {
            System.out.println("Некорректный номер счета");
        }
    }

    public static void operations(Client client) {
        while (true) {
            System.out.println("Выберите следующее действие:" + "\n" +
                    "PUT - для внесения на счет" + "\n" +
                    "GET - для снятия со счета" + "\n" +
                    "INFO - для вывода информации о балансе" + "\n" +
                    "EXIT - для выхода");
            Scanner in1 = new Scanner(System.in);
            String command = in1.nextLine();
            if (command.equalsIgnoreCase("EXIT")) {
                return;
            }
            if (command.equalsIgnoreCase("INFO")) {
                client.getBalance();
                continue;
            }
            if (command.equalsIgnoreCase("GET")) {
                System.out.println("Введите сумму снятия денег");
                client.getMoneyFromAccount(new Scanner(System.in).nextDouble());
                client.getBalance();
            } else {
                if (command.equalsIgnoreCase("PUT")) {
                    System.out.println("Введите сумму внесения денег");
                    client.putMoneyToAccount(new Scanner(System.in).nextDouble());
                    client.getBalance();
                } else {
                    System.out.println("Неверная команда");
                }
            }
        }
    }
}


