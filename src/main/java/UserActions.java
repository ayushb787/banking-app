import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class UserActions {

    public User createUser(String name, String email, String password, String username, double initialDeposit, String accountType) {
        User user = new User();


        for(User u: User.users){
            if(Objects.equals(u.username, username)){
                System.out.println("Account with same username already exists");
                return null;
            }
        }

        if(!UtilityFunctions.isValidEmail(email)){
            System.out.println("Invalid email address");
            return null;
        }

        user.username = username;
        user.name = name;
        user.age = 23;
        user.email = email;
        user.password = password;
        user.accountNumber = UtilityFunctions.generateAccountNumber();
        AccountDetails accountDetails = new AccountDetails();
        user.accountDetails = accountDetails;
        accountDetails.availableBalance = initialDeposit;
        accountDetails.accountHoldersAddress = "";
        accountDetails.accountNumber = user.accountNumber;
        accountDetails.accountOpeningDate = LocalDate.now();
        accountDetails.accountHoldersName = user.name;
        accountDetails.accountType = accountType;
        addTransaction(user, "Deposit", initialDeposit);

        User.users.add(user); //from User class we have accessed global users arraylist
        return user;
    }

    public User loginUser(String username, String password) {
        for (User user : User.users) {
            if (username.equals(user.username) && password.equals(user.password)) {
                return user;
            }
        }
        return null;
    }

    public void addTransaction(User user, String transactionType, double initialDeposit){
        Transaction transaction = new Transaction();
        transaction.transactionId = UtilityFunctions.generateTransactionId();
        transaction.transactionDate = LocalDate.now();
        transaction.transactionType = transactionType;
        transaction.amount = initialDeposit;
        transaction.accountNumber = user.accountNumber;
        if(user.transactions == null){
            user.transactions = new ArrayList<>();
        }
        user.transactions.add(transaction);
    }

    public void generateStatement(User user){
        System.out.printf("%-25s %-25s %-25s %-25s %-22s%n",
                "Transaction Id", "Transaction Date", "Transaction Type", "Amount", "Account Number");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Transaction t : user.transactions) {
            System.out.printf("%-25s %-25s %-25s %-25.2f %-22s%n",
                    t.transactionId,
                    t.transactionDate,
                    t.transactionType,
                    t.amount,
                    t.accountNumber);
        }
    }

    public void depositAmount(User user, double amount){
        user.accountDetails.availableBalance += amount;
        addTransaction(user, "Deposit", amount);
        System.out.println("Amount deposited");
    }

    public void withdrawAmount(User user, double amount){
        if(user.accountDetails.availableBalance < amount){
            System.out.println("Insufficient balance");
            return;
        }
        user.accountDetails.availableBalance -= amount;
        addTransaction(user, "Withdraw", amount);
        System.out.println("Withdraw successful");
    }

    public void addMonthlyInterest(User user){
        double interestRate = 0.05;
        if(Objects.equals(user.accountDetails.accountType, "Savings")){
            user.accountDetails.availableBalance += interestRate*user.accountDetails.availableBalance;
            addTransaction(user, "Interest Deposit", interestRate*user.accountDetails.availableBalance);
            System.out.println("Successfully added monthly Interest rate");
        }else {
            System.out.println("Interest not applicable for Checking account.");
        }

    }

}
