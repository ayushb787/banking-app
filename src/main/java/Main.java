import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static UserActions ua = new UserActions();

    public User login() {
        System.out.println("Enter Username");
        String username = scanner.nextLine();
        System.out.println("Enter Password");
        String password = scanner.nextLine();
        User usr = ua.loginUser(username, password);

        if (usr != null) {
            System.out.println("Login Successful");
        } else {
            System.out.println("Login Failed");
        }
        return usr;
    }

    public void register() {
        System.out.println("Enter Username");
        String username = scanner.nextLine();
        System.out.println("Enter Name");
        String name = scanner.nextLine();
        System.out.println("Enter Email");
        String email = scanner.nextLine();
        System.out.println("Enter Password");
        String password = scanner.nextLine();

        System.out.println("Initial Deposit Amount");
        double depositAmount = scanner.nextDouble();
        scanner.nextLine(); // Clear newline after nextDouble()

        System.out.println("Account Type - (choose one of the below)");
        System.out.println("1. Savings");
        System.out.println("2. Checkings");
        int accountType = scanner.nextInt();
        scanner.nextLine(); // Clear newline after nextInt()

        if (accountType != 1 && accountType != 2) {
            System.out.println("Invalid Account Type");
            return;
        }

        User us = ua.createUser(name, email, password, username, depositAmount, accountType == 1 ? "Savings" : "Checkings");

        if (us != null) {
            System.out.println("SignUp Successful");
        } else {
            System.out.println("SignUp Failed - Duplicate Username or Email");
        }
    }

    public void fetchBankBalance(User user) {
        double accntBalance = user.accountDetails.availableBalance;
        System.out.println("==================================");
        System.out.println("Available Balance = " + accntBalance);
        System.out.println("==================================");
    }

    public void depositAmount(User user) {
        System.out.println("Enter Deposit Amount");
        double depositAmount = scanner.nextDouble();
        scanner.nextLine(); // Clear newline
        ua.depositAmount(user, depositAmount);
        System.out.println("Deposit Successful! New Balance = " + user.accountDetails.availableBalance);
    }

    public void withdrawAmount(User user) {
        System.out.println("Enter Withdraw Amount");
        double withdrawAmount = scanner.nextDouble();
        scanner.nextLine(); // Clear newline
        ua.withdrawAmount(user, withdrawAmount);
        System.out.println("Withdrawal Successful! New Balance = " + user.accountDetails.availableBalance);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Banking Application");
        Main m = new Main();
        User loggedUser = null;

        while (loggedUser == null) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            String code = scanner.nextLine();

            switch (code) {
                case "1":
                    loggedUser = m.login();
                    break;
                case "2":
                    m.register();
                    break;
                default:
                    System.out.println("Invalid Option. Please try again.");
                    break;
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("\n");
            System.out.println("Hello " + loggedUser.name + ",");
            System.out.println("    1. Check Bank Balance");
            System.out.println("    2. Fetch Account Statement");
            System.out.println("    3. Deposit Amount");
            System.out.println("    4. Withdraw Amount");
            System.out.println("    5. Add Monthly Interest to Account (@0.05% for savings accounts)");
            System.out.println("    6. Exit");
            String code = scanner.nextLine();

            switch (code) {
                case "1":
                    System.out.print("\n");
                    m.fetchBankBalance(loggedUser);
                    break;
                case "2":
                    System.out.print("\n");
                    ua.generateStatement(loggedUser);
                    break;
                case "3":
                    System.out.print("\n");
                    m.depositAmount(loggedUser);
                    break;
                case "4":
                    System.out.print("\n");
                    m.withdrawAmount(loggedUser);
                    break;
                case "5":
                    System.out.print("\n");
                    ua.addMonthlyInterest(loggedUser);
                    System.out.println("Interest Added! New Balance = " + loggedUser.accountDetails.availableBalance);
                    break;
                case "6":
                    System.out.print("\n");
                    System.out.println("Thank you for using the Banking Application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid Option. Please try again.");
                    break;
            }
        }
    }
}
