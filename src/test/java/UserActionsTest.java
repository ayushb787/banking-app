import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserActionsTest {

    private UserActions userActions;

    @BeforeEach
    void setUp() {
        userActions = new UserActions();
        User.users.clear();
    }

    @Test
    void testCreateUser_Success() {
        User user = userActions.createUser(
                "Ayush Bhandari", "ayush.bhandari@gmail.com", "password123", "ayush123", 1000.0, "Savings");

        assertNotNull(user);
        assertEquals("Ayush Bhandari", user.name);
        assertEquals("ayush123", user.username);
        assertEquals(1000.0, user.accountDetails.availableBalance);
        assertEquals(1, User.users.size());
    }

    @Test
    void testCreateUser_InvalidEmail() {
        User user = userActions.createUser(
                "Ayush Bhandari", "invalidemail", "password123", "ayush123", 1000.0, "Savings");

        assertNull(user);
        assertEquals(0, User.users.size());
    }

    @Test
    void testCreateUser_DuplicateUsername() {
        userActions.createUser(
                "Ayush Bhandari", "ayush.bhandari@gmail.com", "password123", "ayush123", 1000.0, "Savings");

        User duplicateUser = userActions.createUser(
                "Ayush Bhandari", "ayush.duplicate@gmail.com", "password123", "ayush123", 1000.0, "Savings");

        assertNull(duplicateUser);
        assertEquals(1, User.users.size());
    }

    @Test
    void testLoginUser_Success() {
        userActions.createUser(
                "Ayush Bhandari", "ayush.bhandari@gmail.com", "password123", "ayush123", 1000.0, "Savings");

        User loggedInUser = userActions.loginUser("ayush123", "password123");

        assertNotNull(loggedInUser);
        assertEquals("ayush123", loggedInUser.username);
    }

    @Test
    void testLoginUser_InvalidCredentials() {
        userActions.createUser(
                "Ayush Bhandari", "ayush.bhandari@gmail.com", "password123", "ayush123", 1000.0, "Savings");

        User loggedInUser = userActions.loginUser("ayush123", "wrongpassword");

        assertNull(loggedInUser);
    }

    @Test
    void testDepositAmount() {
        User user = userActions.createUser(
                "Ayush Bhandari", "ayush.bhandari@gmail.com", "password123", "ayush123", 1000.0, "Savings");

        userActions.depositAmount(user, 500.0);

        assertEquals(1500.0, user.accountDetails.availableBalance);
        assertEquals(2, user.transactions.size());
    }

    @Test
    void testWithdrawAmount_Success() {
        User user = userActions.createUser(
                "Ayush Bhandari", "ayush.bhandari@gmail.com", "password123", "ayush123", 1000.0, "Savings");

        userActions.withdrawAmount(user, 500.0);

        assertEquals(500.0, user.accountDetails.availableBalance);
        assertEquals(2, user.transactions.size());
    }

    @Test
    void testWithdrawAmount_InsufficientBalance() {
        User user = userActions.createUser(
                "Ayush Bhandari", "ayush.bhandari@gmail.com", "password123", "ayush123", 1000.0, "Savings");

        userActions.withdrawAmount(user, 1500.0);

        assertEquals(1000.0, user.accountDetails.availableBalance);
        assertEquals(1, user.transactions.size());
    }

    @Test
    void testGenerateStatement() {
        User user = userActions.createUser(
                "Ayush Bhandari", "ayush.bhandari@gmail.com", "password123", "ayush123", 1000.0, "Savings");

        userActions.depositAmount(user, 500.0);
        userActions.withdrawAmount(user, 300.0);

        // Capture the transactions
        List<Transaction> transactions = user.transactions;

        assertEquals(3, transactions.size());
        assertEquals("Deposit", transactions.get(0).transactionType);
        assertEquals("Deposit", transactions.get(1).transactionType);
        assertEquals("Withdraw", transactions.get(2).transactionType);
    }

    @Test
    void testAddMonthlyInterest_Success() {
        User user = userActions.createUser(
                "Ayush Bhandari", "ayush.bhandari@gmail.com", "password123", "ayush123", 1000.0, "Savings");

        userActions.addMonthlyInterest(user);

        assertTrue(user.accountDetails.availableBalance > 1000.0);
        assertEquals(2, user.transactions.size());
        assertEquals("Interest Deposit", user.transactions.get(1).transactionType);
    }

    @Test
    void testAddMonthlyInterest_CheckingAccount() {
        User user = userActions.createUser(
                "Ayush Bhandari", "ayush.bhandari@gmail.com", "password123", "ayush123", 1000.0, "Checking");

        userActions.addMonthlyInterest(user);

        assertEquals(1000.0, user.accountDetails.availableBalance);
        assertEquals(1, user.transactions.size()); // No interest transaction added
    }
}
