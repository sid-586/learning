import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Main {


    private static String preInput;
    private static String currentID;


    public static void main(String[] args) {
        TreeMap<String, Contacts> phoneBook = new TreeMap<>();
        String input = "";
        boolean isCancelled;
        boolean isFinished = false;

        while (!isFinished) {
            if (input.equals("") || input.equals("cancel") || input.equals("list")) {
                System.out.println(" Введите номер или имя контакта, LIST - для вывода полного списка абонентов, EXIT - для завершения работы программы");
                Scanner scanner1 = new Scanner(System.in);
                preInput = scanner1.nextLine();
                input = "";
            }

            isCancelled = false;
            switch (controlCommand(preInput, input)) {
                case "list":
                    printMap(phoneBook);
                    continue;
                case "phoneNumber":
                case "name":
                    searchContact(preInput, phoneBook);
                    break;
                case "add":
                    addContact(preInput, phoneBook, input);
                    break;
                case "exit":
                    if (endOfCode()) {
                        isFinished = true;
                    }
                    break;
                case "edit":
                    isCancelled = editContact(currentID, phoneBook);
                    input = "";
                    break;
                case "delete":
                    isCancelled = deleteContact(currentID, phoneBook);
                    input = "";
                    break;
                case "cancel":
                    isCancelled = true;
                    break;
            }
            if (isFinished)
                break;
            if (isCancelled == false) {
                if (searchContact(preInput, phoneBook) == true) {
                    System.out.println("Введите следующую команду -" + "\n" +
                            "ADD для добавления нового номера в контакт" + "\n" +
                            "EDIT для редактирования данных контакта" + "\n" +
                            "DELETE для удаления контакта" + "\n" +
                            "CANCEL для возврата в основное меню" + "\n" +
                            "EXIT для завершения работы программы");
                    Scanner scanner2 = new Scanner(System.in);
                    input = scanner2.nextLine();
                } else {
                    System.out.println(preInput);
                    addContact(preInput, phoneBook, input);
                }
            }
        }
    }

    private static String controlCommand(String preInput, String input) {
        String typeOfInput;
        if (!input.equals("") && !input.equals("cancel")) {
            preInput = input;
        }
        if (preInput.equalsIgnoreCase("list") ||
                preInput.equalsIgnoreCase("add") ||
                preInput.equalsIgnoreCase("exit") ||
                preInput.equalsIgnoreCase("edit") ||
                preInput.equalsIgnoreCase("delete") ||
                preInput.equalsIgnoreCase("cancel")) {
            typeOfInput = preInput;
        } else {
            if (preInput.replaceFirst("^8", "+7").replaceAll("[^+\\d]", "").matches("\\+\\d{11}")) {
                typeOfInput = "phoneNumber";
            } else {
                typeOfInput = "name";
            }
        }
        return typeOfInput;
    }

    private static void printMap(TreeMap<String, Contacts> phoneBook) {

        for (String key : phoneBook.keySet()) {
            System.out.println(phoneBook.get(key).getName());
            for (int i = 0; i < phoneBook.get(key).getPhoneNumbers().size(); i++) {
                System.out.println("\t" + phoneBook.get(key).getPhoneNumbers().get(i));
            }
        }
    }


    private static boolean searchContact(String preInput, TreeMap<String, Contacts> phoneBook) {

        for (String key : phoneBook.keySet()) {
            if (phoneBook.get(key).getName().equals(preInput)) {
                printContact(key, phoneBook);
                currentID = phoneBook.get(key).getName();
                return true;
            }
            if (key.equals(currentID)) {
                printContact(key, phoneBook);
                return true;
            }
            if (preInput.replaceFirst("^8", "+7").replaceAll("[^+\\d]", "").matches("\\+\\d{11}")) {
                preInput = preInput.replaceFirst("^8", "+7").replaceAll("[^+\\d]", "");
                for (int i = 0; i < phoneBook.get(key).getPhoneNumbers().size(); i++) {
                    for (String str : phoneBook.get(key).getPhoneNumbers()) {
                        if (str.equals(preInput)) {
                            printContact(key, phoneBook);
                            currentID = phoneBook.get(key).getName();
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    private static void addContact(String preInput, TreeMap<String, Contacts> phoneBook, String input) {
        Scanner in = new Scanner(System.in);
        String phoneNumber = "";
        String name;
        if (input.equals("")) {
            if (preInput.replaceFirst("^8", "+7").replaceAll("[^+\\d]", "").matches("\\+\\d{11}")) {
                phoneNumber = preInput;
                System.out.println("Введите данные нового контакта");
                name = in.nextLine();
                for (String str : phoneBook.keySet()) {
                    if (phoneBook.get(str).getName().equals(name)) {
                        phoneBook.get(str).getPhoneNumbers().add(phoneNumber);
                        System.out.println("Такой контакт уже существует, номер добавлен в данные этого контакта");
                        return;
                    }
                }
            } else {
                name = preInput;
                System.out.println("Введите номер нового абонента");
                phoneNumber = checkNumber(phoneNumber, phoneBook);
            }
            System.out.println(name + " " + phoneNumber);
            phoneBook.put(name, new Contacts(name, phoneNumber));
            System.out.println("Создан новый контакт");
        } else {
            name = preInput;
            System.out.println("!");
            System.out.println("Введите номер для добавления в контакт " + currentID);
            phoneNumber = checkNumber(phoneNumber, phoneBook);
            phoneBook.get(name).getPhoneNumbers().add(phoneNumber);
        }
    }

    private static void printContact(String key, TreeMap<String, Contacts> phoneBook) {
        System.out.println(phoneBook.get(key).getName());
        for (int j = 0; j < phoneBook.get(key).getPhoneNumbers().size(); j++) {
            System.out.println("\t" + phoneBook.get(key).getPhoneNumbers().get(j));
        }
    }

    private static String checkNumber(String phoneNumber, TreeMap<String, Contacts> phoneBook) {
        boolean isCheckNumber = false;
        Scanner in = new Scanner(System.in);

        while (!isCheckNumber) {
            if (phoneNumber.equals("")) {
                phoneNumber = in.nextLine().replaceFirst("^8", "+7").replaceAll("[^+\\d]", "");
            }
            isCheckNumber = true;
            if (phoneNumber.matches("\\+\\d{11}")) {
                for (String str1 : phoneBook.keySet()) {
                    for (int i = 0; i < phoneBook.get(str1).getPhoneNumbers().size(); i++) {
                        for (String str : phoneBook.get(str1).getPhoneNumbers()) {
                            if (str.equals(phoneNumber)) {
                                System.out.println("Введенный номер уже принадлежит абоненту " + phoneBook.get(str1).getName() + "\n" + "Введите верный номер");
                                phoneNumber = "";
                                isCheckNumber = false;
                            }
                        }
                    }
                }
            } else {
                System.out.println("Введите корректный номер");
                phoneNumber = "";
                isCheckNumber = false;
            }
        }
        return phoneNumber;
    }

    private static boolean editContact(String currentID, TreeMap<String, Contacts> phoneBook) {

        Pattern chkNumber = Pattern.compile("\\+\\d{11}");
        boolean isCancelled = false;

        System.out.println("Для изменения данных введите дополнительную информацию для контакта," + "\n" +
                "Для изменения номера введите номер, который нужно исправить и, через пробел, - новый номер" + "\n" +
                "Для возврата в основное меню - CANCEL");
        printContact(currentID, phoneBook);
        Scanner in = new Scanner(System.in);
        String localInput = in.nextLine();
        if (localInput.equalsIgnoreCase("cancel")) {
            isCancelled = true;
        }
        if (chkNumber.matcher(localInput.replaceFirst("^8", "+7").replaceAll("[^+\\d]", "")).find()) {

            String[] numberForCorrect = localInput.split("\\s");
            phoneBook.get(currentID).getPhoneNumbers().remove(phoneBook.get(currentID).getPhoneNumbers().indexOf(numberForCorrect[0]));
            if (chkNumber.matcher(numberForCorrect[1].replaceFirst("^8", "+7").replaceAll("[^+\\d]", "")).find()) {
                if (!searchContact(numberForCorrect[1], phoneBook)) {
                    phoneBook.get(currentID).getPhoneNumbers().add(numberForCorrect[1]);
                    printContact(currentID, phoneBook);
                } else {
                    System.out.println("Введенный номер уже есть в телефонной книге");
                }
            } else {
                System.out.println("Введенный номер некорректен");
            }
        } else {
            String oldName = phoneBook.get(currentID).getName();
            phoneBook.get(currentID).setName(oldName + " " + localInput);
            printContact(currentID, phoneBook);
        }
        return isCancelled;
    }

    private static boolean endOfCode() {
        return true;
    }

    private static boolean deleteContact(String id, TreeMap<String, Contacts> phoneBook) {

        System.out.println("Для удаления контакта введите цифру 1" + "\n" + "для удаления номера из контакта введите цифру 2" + "\n" + "для возврата в основное меню введите CANCEL");
        boolean isCancelled = false;
        Scanner in = new Scanner(System.in);
        String string = in.nextLine();
        switch (string) {
            case "1":
                phoneBook.remove(id);
                break;
            case "2":
                System.out.println("Введите порядковый номер телефонного номера в контакте, который необходимо удалить");
                printContact(id, phoneBook);
                int n = in.nextInt();
                phoneBook.get(id).getPhoneNumbers().remove(n - 1);
                printContact(id, phoneBook);
                break;
            case "CANCEL":
                isCancelled = true;
                break;
        }
        return isCancelled;
    }
}
