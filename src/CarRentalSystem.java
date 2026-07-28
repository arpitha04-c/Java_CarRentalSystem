import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarRentalSystem {

    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {

        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }

        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Car was not rented.");
        }
    }

    // View Available Cars
    public void viewAvailableCars() {
        System.out.println("\n========== AVAILABLE CARS ==========");
        boolean found = false;
        
        for (Car car : cars) {
            if (car.isAvailable()) {
                System.out.println("--------------------------------");
                System.out.println("Car ID : " + car.getCarId());
                System.out.println("Brand  : " + car.getBrand());
                System.out.println("Model  : " + car.getModel());
                System.out.println("Price  : ₹" + car.getBasePricePerDay() + "/day");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No cars are currently available.");
        }

        System.out.println("--------------------------------");
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n========== CAR RENTAL SYSTEM ==========");
            System.out.println("1. View Available Cars");
            System.out.println("2. Rent a Car");
            System.out.println("3. Return a Car");
            System.out.println("4. Exit");
            System.out.print("Enter your choice : ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input! Enter numbers only.");
                scanner.nextLine();
                continue;
            }

            // OPTION 1
            if (choice == 1) {
                viewAvailableCars();
            }

            // OPTION 2
            else if (choice == 2) {
                System.out.println("\n========== RENT A CAR ==========");
                String customerName;

                while (true) {
                    System.out.print("Enter Customer Name : ");
                    customerName = scanner.nextLine().trim();

                    if (!customerName.isEmpty() &&
                            customerName.matches("[a-zA-Z ]+")) {
                        break;
                    }

                    System.out.println("Invalid name! Alphabets only.");
                }

                viewAvailableCars();
                String carId;

                while (true) {

                    System.out.print("\nEnter Car ID (Example: C001): ");
                    carId = scanner.nextLine().trim().toUpperCase();

                    if (carId.matches("C\\d{3}")) {
                        break;
                    }
                    System.out.println("Invalid Car ID format.");
                }

                int rentalDays = 0;

                while (true) {
                    try {
                        System.out.print("Enter Rental Days : ");
                        rentalDays = scanner.nextInt();
                        scanner.nextLine();
                        if (rentalDays > 0)
                            break;
                        System.out.println("Rental days must be greater than 0.");
                    } catch (Exception e) {
                        System.out.println("Enter numbers only.");
                        scanner.nextLine();
                    }
                }

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);

                addCustomer(newCustomer);

                Car selectedCar = null;

                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar == null) {
                    System.out.println("Car not found or unavailable.");
                    continue;
                }
                double totalPrice = selectedCar.calculatePrice(rentalDays);
                System.out.println("\n========== RENTAL INFORMATION ==========");
                System.out.println("Customer ID : " + newCustomer.getCustomerId());
                System.out.println("Customer    : " + newCustomer.getName());
                System.out.println("Car         : " + selectedCar.getBrand()
                        + " " + selectedCar.getModel());
                System.out.println("Days        : " + rentalDays);
                System.out.printf("Total Cost  : ₹%.2f%n", totalPrice);

                System.out.print("\nConfirm Rental (Y/N): ");
                String confirm = scanner.nextLine();

                if (confirm.equalsIgnoreCase("Y")) {

                    rentCar(selectedCar, newCustomer, rentalDays);

                    System.out.println("\n====================================");
                    System.out.println("         RENTAL RECEIPT");
                    System.out.println("====================================");
                    System.out.println("Customer ID   : " + newCustomer.getCustomerId());
                    System.out.println("Customer Name : " + newCustomer.getName());
                    System.out.println("Car           : " + selectedCar.getBrand()
                            + " " + selectedCar.getModel());
                    System.out.println("Rental Days   : " + rentalDays);
                    System.out.printf("Total Amount  : ₹%.2f%n", totalPrice);
                    System.out.println("====================================");
                    System.out.println("Booking Confirmed!");
                    System.out.println("Thank You for choosing our service.");
                    System.out.println("====================================");

                    System.out.println("\nThank You for using Car Rental System!");
                    
                } else {
                    System.out.println("Rental Cancelled.");
                }
            }

            // OPTION 3
            else if (choice == 3) {
                System.out.println("\n========== RETURN CAR ==========");
                String carId;

                while (true) {
                    System.out.print("Enter Car ID (Example: C001): ");
                    carId = scanner.nextLine().trim().toUpperCase();
                    if (carId.matches("C\\d{3}"))
                       break;
                    System.out.println("Invalid Car ID format.");
                }

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) &&
                            !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn == null) {
                    System.out.println("Invalid Car ID or Car is not rented.");
                    continue;
                }

                Customer customer = null;
                for (Rental rental : rentals) {
                    if (rental.getCar() == carToReturn) {
                        customer = rental.getCustomer();
                        break;
                    }
                }

                if (customer != null) {
                    returnCar(carToReturn);
                    System.out.println("Car returned successfully by "
                            + customer.getName());
                } else {

                    System.out.println("Rental information missing.");
                }
            }

            // OPTION 4
            else if (choice == 4) {
                break;
            }
            else {
                System.out.println("Invalid Choice.");
            }
        }

        scanner.close();

        System.out.println("\nThank You for using Car Rental System!");
    }
}