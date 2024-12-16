import java.util.List;
import java.util.ArrayList;

// Abstract class for common attributes and behavior (Abstraction)
abstract class User {
    private String name;
    private String email;
    private List<Notification> notifications = new ArrayList<>();

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Common methods for notifications
    public void receiveNotification(String message) {
        Notification notification = new Notification(message);
        notifications.add(notification);
    }

    public void viewNotifications() {
        if (notifications.isEmpty()) {
            System.out.println("No notifications.");
        } else {
            System.out.println("Notifications:");
            for (Notification notification : notifications) {
                System.out.println("- " + notification);
            }
        }
    }

    public void clearNotifications() {
        notifications.clear();
        System.out.println("All notifications cleared.");
    }

    // Abstract method for roles
    public abstract void displayRole();

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setEmail(String email) {
        this.email = email;
    }
}

// Manager class extending User (Inheritance)
class Manager extends User {
    public Manager(String name, String email) {
        super(name, email);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Manager");
    }

    // Manager-specific method for managing flight schedules
    public void manageFlight(Flight flight, String action) throws Exception {
        switch (action.toLowerCase()) {
            case "add":
                FlightSchedule.addFlight(flight);
                break;
            case "update":
                FlightSchedule.updateFlight(flight);
                break;
            case "cancel":
                FlightSchedule.cancelFlight(flight.getFlightId());
                break;
            default:
                throw new Exception("Invalid action. Use 'add', 'update', or 'cancel'.");
        }
    }

    // Generate report
    public void generateReport() {
        List<Flight> flights = FlightSchedule.getFlights();
        if (flights.isEmpty()) {
            System.out.println("No flights available for reporting.");
            return;
        }

        System.out.println("Flight Reports:");
        for (Flight flight : flights) {
            System.out.println("Flight ID: " + flight.getFlightId());
            System.out.println("Origin: " + flight.getOrigin());
            System.out.println("Destination: " + flight.getDestination());
            System.out.println("Departure: " + flight.getDepartureTime());
            System.out.println("Arrival: " + flight.getArrivalTime());
            System.out.println("Available Seats: " + flight.getAvailableSeats());
            System.out.println("Booked Seats: " + (flight.getTotalSeats() - flight.getAvailableSeats()));
            System.out.println("----------------------------");
        }
    }

    public void checkLowSeatAvailability(Flight flight) {
        if (flight.getAvailableSeats() < 10) {
            receiveNotification(
                    "Low seats alert: Flight " + flight.getFlightId() + " has less than 10 seats available.");
        }
    }

    public void sendDailySummary() {
        List<Flight> flights = FlightSchedule.getFlights();
        if (flights.isEmpty()) {
            receiveNotification("Daily Summary: No flights were booked today.");
        } else {
            StringBuilder summary = new StringBuilder("Daily Summary:\n");
            for (Flight flight : flights) {
                summary.append("Flight ID: ").append(flight.getFlightId())
                        .append(", Booked Seats: ")
                        .append(flight.getTotalSeats() - flight.getAvailableSeats())
                        .append("\n");
            }
            receiveNotification(summary.toString());
        }
    }

}

// Encapsulated Flight class
class Flight {
    private String flightId;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int availableSeats;
    private int totalSeats; // New field

    // Constructor
    public Flight(String flightId, String origin, String destination, String departureTime, String arrivalTime,
            int totalSeats) {
        if (totalSeats < 0) {
            throw new IllegalArgumentException("Total seats cannot be negative.");
        }
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = totalSeats; // Initially, all seats are available
        this.totalSeats = totalSeats;
    }

    // Getters and setters
    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        if (totalSeats < 0) {
            throw new IllegalArgumentException("Total seats cannot be negative.");
        }
        this.totalSeats = totalSeats;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        if (availableSeats < 0) {
            throw new IllegalArgumentException("Available seats cannot be negative.");
        }
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return "Flight ID: " + flightId + ", Origin: " + origin + ", Destination: " + destination +
                ", Departure: " + departureTime + ", Arrival: " + arrivalTime + ", Seats: " + availableSeats;
    }
}

// FlightSchedule class to manage a list of flights
class FlightSchedule {
    private static List<Flight> flights = new ArrayList<>();

    // Add a flight
    public static void addFlight(Flight flight) {
        flights.add(flight);
        System.out.println("Flight added successfully: " + flight);
    }

    // Update a flight
    public static void updateFlight(Flight updatedFlight) throws Exception {
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightId().equals(updatedFlight.getFlightId())) {
                flights.set(i, updatedFlight);
                System.out.println("Flight updated successfully: " + updatedFlight);
                return;
            }
        }
        throw new Exception("Flight not found.");
    }

    // Cancel a flight
    public static void cancelFlight(String flightId) throws Exception {
        for (Flight flight : flights) {
            if (flight.getFlightId().equals(flightId)) {
                flights.remove(flight);
                System.out.println("Flight cancelled successfully: " + flight);
                return;
            }
        }
        throw new Exception("Flight not found.");
    }

    // List all flights
    public static void listFlights() {
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
        } else {
            System.out.println("Available Flights:");
            for (Flight flight : flights) {
                System.out.println(flight);
            }
        }
    }

    // Search for flights based on criteria
    public static List<Flight> searchFlights(String origin, String destination) {
        List<Flight> matchingFlights = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getOrigin().equalsIgnoreCase(origin) && flight.getDestination().equalsIgnoreCase(destination)) {
                matchingFlights.add(flight);
            }
        }
        return matchingFlights;
    }

    public static List<Flight> getFlights() {
        return flights;
    }
}

// Main class to demonstrate functionality
public class AirplaneManagementSystem {
    public static void main(String[] args) {
        try {
            // Create a manager
            Manager manager = new Manager("Alice", "alice@airlines.com");

            // Create some flights
            Flight flight1 = new Flight("F101", "New York", "Los Angeles", "10:00 AM", "1:00 PM", 150);
            Flight flight2 = new Flight("F102", "Chicago", "Miami", "2:00 PM", "5:00 PM", 200);

            // Add flights
            manager.manageFlight(flight1, "add");
            manager.manageFlight(flight2, "add");

            // List flights
            FlightSchedule.listFlights();

            // Update a flight
            Flight updatedFlight = new Flight("F101", "New York", "San Francisco", "10:00 AM", "2:00 PM", 140);
            manager.manageFlight(updatedFlight, "update");

            // List flights again
            FlightSchedule.listFlights();

            // Cancel a flight
            manager.manageFlight(flight2, "cancel");

            // List flights again
            FlightSchedule.listFlights();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

// Customer class extending User (Inheritance)
class Customer extends User {
    private List<Flight> bookings = new ArrayList<>(); // Encapsulation

    public Customer(String name, String email) {
        super(name, email);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Customer");
    }

    @Override
    public void receiveNotification(String message) {
        super.receiveNotification("Customer Alert: " + message);
    }

    // Search for flights (already implemented)
    public void searchFlights(String origin, String destination) {
        List<Flight> results = FlightSchedule.searchFlights(origin, destination);
        if (results.isEmpty()) {
            System.out.println("No flights found for the given criteria.");
        } else {
            System.out.println("Matching Flights:");
            for (Flight flight : results) {
                System.out.println(flight);
            }
        }
    }

    // New: Book a flight
    public void bookFlight(String flightId) throws Exception {
        Flight selectedFlight = null;

        // Find the flight by ID in the flight schedule
        for (Flight flight : FlightSchedule.getFlights()) {
            if (flight.getFlightId().equalsIgnoreCase(flightId)) {
                selectedFlight = flight;
                break;
            }
        }

        if (selectedFlight == null) {
            throw new Exception("Flight with ID " + flightId + " not found.");
        }

        // Check seat availability
        if (selectedFlight.getAvailableSeats() > 0) {
            selectedFlight.setAvailableSeats(selectedFlight.getAvailableSeats() - 1);
            bookings.add(selectedFlight); // Add the flight to customer's bookings
            receiveNotification("Booking confirmed for flight " + selectedFlight.getFlightId() + " from "
                    + selectedFlight.getOrigin() + " to " + selectedFlight.getDestination());
        } else {
            throw new Exception("No seats available for flight ID: " + flightId);
        }
    }

    // Cancel a booking
    public void cancelBooking(String flightId) throws Exception {
        Flight bookingToCancel = null;

        // Search for the booking in the customer's list
        for (Flight booking : bookings) {
            if (booking.getFlightId().equalsIgnoreCase(flightId)) {
                bookingToCancel = booking;
                break;
            }
        }

        if (bookingToCancel == null) {
            throw new Exception("No booking found with flight ID: " + flightId);
        }

        // Increment the available seats for the flight
        bookingToCancel.setAvailableSeats(bookingToCancel.getAvailableSeats() + 1);

        // Remove the flight from the customer's bookings
        bookings.remove(bookingToCancel);

        System.out.println("Booking canceled successfully for flight: " + bookingToCancel);
    }

    // View customer's bookings
    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            System.out.println("Your Bookings:");
            for (Flight booking : bookings) {
                System.out.println(booking);
            }
        }
    }

    public void sendFlightReminder() {
        for (Flight flight : bookings) {
            receiveNotification("Reminder: Upcoming flight " + flight.getFlightId() + " from " + flight.getOrigin()
                    + " departs at " + flight.getDepartureTime());
        }
    }
}

class Notification {
    private String message;

    public Notification(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
