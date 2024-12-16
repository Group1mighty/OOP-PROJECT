import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Manager manager = new Manager("Alice", "alice@airlines.com");
        Customer customer = new Customer("Bob", "bob@domain.com");

        while (true) {
            System.out.println("\n==== Airline Management System ====");
            System.out.println("1. Login as Manager");
            System.out.println("2. Login as Customer");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    managerMenu(manager, scanner);
                    break;
                case 2:
                    customerMenu(customer, scanner);
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Manager Menu
    private static void managerMenu(Manager manager, Scanner scanner) {
        while (true) {
            System.out.println("\n==== Manager Menu ====");
            System.out.println("1. Add a Flight");
            System.out.println("2. Update a Flight");
            System.out.println("3. Cancel a Flight");
            System.out.println("4. List All Flights");
            System.out.println("5. View Notifications");
            System.out.println("6. Clear Notifications");
            System.out.println("7. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Flight ID: ");
                        String flightId = scanner.nextLine();
                        System.out.print("Enter Origin: ");
                        String origin = scanner.nextLine();
                        System.out.print("Enter Destination: ");
                        String destination = scanner.nextLine();
                        System.out.print("Enter Departure Time: ");
                        String departureTime = scanner.nextLine();
                        System.out.print("Enter Arrival Time: ");
                        String arrivalTime = scanner.nextLine();
                        System.out.print("Enter Total Seats: ");
                        int totalSeats = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        Flight newFlight = new Flight(flightId, origin, destination, departureTime, arrivalTime, totalSeats);
                        manager.manageFlight(newFlight, "add");
                        break;
                    case 2:
                        System.out.print("Enter Flight ID to Update: ");
                        String updateId = scanner.nextLine();
                        System.out.print("Enter New Origin: ");
                        String newOrigin = scanner.nextLine();
                        System.out.print("Enter New Destination: ");
                        String newDestination = scanner.nextLine();
                        System.out.print("Enter New Departure Time: ");
                        String newDepartureTime = scanner.nextLine();
                        System.out.print("Enter New Arrival Time: ");
                        String newArrivalTime = scanner.nextLine();
                        System.out.print("Enter New Total Seats: ");
                        int newTotalSeats = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        Flight updatedFlight = new Flight(updateId, newOrigin, newDestination, newDepartureTime, newArrivalTime, newTotalSeats);
                        manager.manageFlight(updatedFlight, "update");
                        break;
                    case 3:
                        System.out.print("Enter Flight ID to Cancel: ");
                        String cancelId = scanner.nextLine();
                        manager.manageFlight(new Flight(cancelId, "", "", "", "", 0), "cancel");
                        break;
                    case 4:
                        FlightSchedule.listFlights();
                        break;
                    case 5:
                        manager.viewNotifications();
                        break;
                    case 6:
                        manager.clearNotifications();
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Customer Menu
    private static void customerMenu(Customer customer, Scanner scanner) {
        while (true) {
            System.out.println("\n==== Customer Menu ====");
            System.out.println("1. Book a Flight");
            System.out.println("2. View Booking History");
            System.out.println("3. View Notifications");
            System.out.println("4. Clear Notifications");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        FlightSchedule.listFlights();
                        System.out.print("Enter Flight ID to Book: ");
                        String flightId = scanner.nextLine();
                        Flight flightToBook = findFlightById(flightId);
                        if (flightToBook != null) {
                            customer.bookFlight(flightToBook);
                        } else {
                            System.out.println("Flight not found.");
                        }
                        break;
                    case 2:
                        customer.viewNotifications(); // Booking history could be shown as notifications.
                        break;
                    case 3:
                        customer.viewNotifications();
                        break;
                    case 4:
                        customer.clearNotifications();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Utility method to find a flight by its ID
    private static Flight findFlightById(String flightId) {
        for (Flight flight : FlightSchedule.getFlights()) {
            if (flight.getFlightId().equalsIgnoreCase(flightId)) {
                return flight;
            }
        }
        return null;
    }
}

