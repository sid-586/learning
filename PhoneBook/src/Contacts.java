import java.util.ArrayList;


public class Contacts {

    public String name;
    public String phoneNumber;
    private ArrayList<String> phoneNumbers;

    public Contacts(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumbers = new ArrayList<>();
        phoneNumbers.add(phoneNumber);

    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
