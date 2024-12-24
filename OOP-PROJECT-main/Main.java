import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Manager manager = new Manager("Alice", "alice@airlines.com");
        Customer customer = new Customer("Bob", "bob@domain.com");

        // Load flights from file at startup
        List<Flight> flights = FileManager.loadFlightsFromFile();
        FlightSchedule.setFlights(flights);

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
                    // Save flights to file before exiting
                    FileManager.saveFlightsToFile(FlightSchedule.getFlights(),"flights.txt");
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
            System.out.println("7. Save Flights to File");
            System.out.println("8. Load Flights from File");
            System.out.println("9. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Flight ID: ");
                        String flightId = scanner.nextLine();

                        System.out.print("Enter Origin Airport Code: ");
                        String originCode = scanner.nextLine();
                        System.out.print("Enter Origin Airport Name: ");
                        String originName = scanner.nextLine();
                        System.out.print("Enter Origin Location: ");
                        String originLocation = scanner.nextLine();
                        Airport origin = new Airport(originCode, originName, originLocation);

                        System.out.print("Enter Destination Airport Code: ");
                        String destinationCode = scanner.nextLine();
                        System.out.print("Enter Destination Airport Name: ");
                        String destinationName = scanner.nextLine();
                        System.out.print("Enter Destination Location: ");
                        String destinationLocation = scanner.nextLine();
                        Airport destination = new Airport(destinationCode, destinationName, destinationLocation);

                        System.out.print("Enter Departure Time: ");
                        String departureTime = scanner.nextLine();
                        System.out.print("Enter Arrival Time: ");
                        String arrivalTime = scanner.nextLine();

                        System.out.print("Enter Airplane ID: ");
                        String airplaneId = scanner.nextLine();
                        System.out.print("Enter Airplane Model: ");
                        String airplaneModel = scanner.nextLine();
                        System.out.print("Enter Airplane Capacity: ");
                        int capacity = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        Airplane airplane = new Airplane(airplaneId, airplaneModel, capacity);

                        Flight newFlight = new Flight(flightId, origin, destination, departureTime, arrivalTime, airplane);
                        manager.manageFlight(newFlight, "add");
                        break;
                    case 2:
                        System.out.print("Enter Flight ID to Update: ");
                        String updateId = scanner.nextLine();

                        System.out.print("Enter New Origin Airport Code: ");
                        String newOriginCode = scanner.nextLine();
                        System.out.print("Enter New Origin Airport Name: ");
                        String newOriginName = scanner.nextLine();
                        System.out.print("Enter New Origin Location: ");
                        String newOriginLocation = scanner.nextLine();
                        Airport newOrigin = new Airport(newOriginCode, newOriginName, newOriginLocation);

                        System.out.print("Enter New Destination Airport Code: ");
                        String newDestinationCode = scanner.nextLine();
                        System.out.print("Enter New Destination Airport Name: ");
                        String newDestinationName = scanner.nextLine();
                        System.out.print("Enter New Destination Location: ");
                        String newDestinationLocation = scanner.nextLine();
                        Airport newDestination = new Airport(newDestinationCode, newDestinationName, newDestinationLocation);

                        System.out.print("Enter New Departure Time: ");
                        String newDepartureTime = scanner.nextLine();
                        System.out.print("Enter New Arrival Time: ");
                        String newArrivalTime = scanner.nextLine();

                        System.out.print("Enter New Airplane ID: ");
                        String newAirplaneId = scanner.nextLine();
                        System.out.print("Enter New Airplane Model: ");
                        String newAirplaneModel = scanner.nextLine();
                        System.out.print("Enter New Airplane Capacity: ");
                        int newCapacity = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        Airplane newAirplane = new Airplane(newAirplaneId, newAirplaneModel, newCapacity);

                        Flight updatedFlight = new Flight(updateId, newOrigin, newDestination, newDepartureTime, newArrivalTime, newAirplane);
                        manager.manageFlight(updatedFlight, "update");
                        break;
                    case 3:
                        System.out.print("Enter Flight ID to Cancel: ");
                        String cancelId = scanner.nextLine();
                        manager.manageFlight(new Flight(cancelId, null, null, null, null, null), "cancel");
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
                    FileManager.saveFlightsToFile(FlightSchedule.getFlights(), "flights.txt");
                        break;
                    case 8:
                        List<Flight> loadedFlights = FileManager.loadFlightsFromFile();
                        FlightSchedule.setFlights(loadedFlights);
                        System.out.println("Flights loaded from file.");
                        break;
                    case 9:
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
                            customer.bookFlight(flightId);
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