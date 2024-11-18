import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilityFunctionsTest {

    @Test
    public void testGenerateAccountNumber() {
        String accountNumber = UtilityFunctions.generateAccountNumber();
        Assertions.assertEquals(12, accountNumber.length(), "Account number should be 12 digits long");
    }

    @Test
    public void testIsValidEmail_Valid() {
        boolean isValid = UtilityFunctions.isValidEmail("ayush@gmail.com");
        Assertions.assertTrue(isValid, "Email validation should pass for a valid email");
    }

    @Test
    public void testIsValidEmail_Invalid() {
        boolean isValid = UtilityFunctions.isValidEmail("invalid-email");
        Assertions.assertFalse(isValid, "Email validation should fail for an invalid email");
    }

    @Test
    public void testGenerateTransactionId() {
        String transactionId = UtilityFunctions.generateTransactionId();
        Assertions.assertTrue(transactionId.startsWith("TXN-"), "Transaction ID should start with 'TXN-'");
    }
}
