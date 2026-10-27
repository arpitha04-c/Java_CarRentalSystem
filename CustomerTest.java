import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    void createsCustomerId() {
        Customer customer = new Customer("CUS001", "Arpitha");

        assertEquals("CUS001", customer.getCustomerId());
    }

    @Test
    void returnCustomerName() {
        Customer customer = new Customer("CUS001", "Arpitha");

        assertEquals("Arpitha", customer.getName());
    }
}