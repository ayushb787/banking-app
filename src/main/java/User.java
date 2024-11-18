import java.util.ArrayList;

public class User {
    public static ArrayList<User> users = new ArrayList<>();
    public String username;
    public String name;
    public int age;
    public String password;
    public String email;
    public String accountNumber;
    public AccountDetails accountDetails;
    public ArrayList<Transaction> transactions;


    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName(){
        return name;
    }
}
