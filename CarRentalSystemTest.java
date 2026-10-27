
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CarRentalSystemTest {

    @Test
    void rentingAvailableCarMakesItUnavailable() {
        CarRentalSystem system = new CarRentalSystem();
        Car car = new Car("C001", "Toyota", "Camry", 2000);
        Customer customer = new Customer("CUS001", "Arpitha");

        system.addCar(car);
        system.addCustomer(customer);
        system.rentCar(car, customer, 3);

        assertFalse(car.isAvailable());
    }

    @Test
    void returningRentedCar() {
        CarRentalSystem system = new CarRentalSystem();
        Car car = new Car("C001", "Toyota", "Camry", 2000);
        Customer customer = new Customer("CUS001", "Arpitha");

        system.addCar(car);
        system.rentCar(car, customer, 3);
        system.returnCar(car);

        assertTrue(car.isAvailable());
    }

    @Test
    void rentingRentedCar() {
        CarRentalSystem system = new CarRentalSystem();
        Car car = new Car("C001", "Toyota", "Camry", 2000);
        Customer customer = new Customer("CUS001", "Arpitha");

        system.rentCar(car, customer, 3);
        system.rentCar(car, customer, 2);

        assertFalse(car.isAvailable());
    }
}
