
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    @Test
    void newCarIsAvailable() {
        Car car = new Car("C001", "Toyota", "Camry", 2000);

        assertTrue(car.isAvailable());
    }

    @Test
    void rentingCar() {
        Car car = new Car("C001", "Toyota", "Camry", 2000);

        car.rent();

        assertFalse(car.isAvailable());
    }

    @Test
    void returningCar() {
        Car car = new Car("C001", "Toyota", "Camry", 2000);

        car.rent();
        car.returnCar();

        assertTrue(car.isAvailable());
    }

    @Test
    void calculatePrice() {
        Car car = new Car("C001", "Toyota", "Camry", 2000);

        assertEquals(6000.0, car.calculatePrice(3), 0.001);
    }
}
