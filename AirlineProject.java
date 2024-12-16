import java.util.Scanner;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

public class AirlineProject {

    private static class Passenger {
        String name;
        String sex;
        String age;
        String passportNumber;

        Passenger(String name, String sex, String age, String passportNumber) {
            this.name = name;
            this.sex = sex;
            this.age = age;
            this.passportNumber = passportNumber;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);

        List<Passenger> firstClass = new ArrayList<>();
        List<Passenger> economyClass = new ArrayList<>();

        while (true) {
            System.out.println("\nWelcome to X-AIRLINE home page !!");
            System.out.println("  1. Assign a seat");
            System.out.println("  2. Display options (search, seat status)");
            System.out.println("  3. Exit");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine();

            if (choice.length() != 1 || "123".indexOf(choice) == -1) {
                System.out.println("Please enter a valid input.");
                continue;
            }

            switch (choice.charAt(0)) {
                case '1':
                    assignSeat(sc, firstClass, economyClass);
                    break;
                case '2':
                    displayOptions(sc, firstClass, economyClass);
                    break;
                case '3':
                    System.out.println("\n-----THANK YOU FOR USING X-AIRLINE------\n");
                    sc.close();
                    return;
            }
        }
    }

    private static void assignSeat(Scanner sc, List<Passenger> firstClass, List<Passenger> economyClass) {
        while (true) {
            System.out.println("--------Assigning a seat-------");
            System.out.println("  1. For first class." + (firstClass.size() >= 30 ? " (full)" : ""));
            System.out.println("  2. For economy class." + (economyClass.size() >= 70 ? " (full)" : ""));
            System.out.println("  3. Return to home page.");
            System.out.print("Enter your choice: ");

            String classChoice = sc.nextLine();

            if (classChoice.length() != 1 || "123".indexOf(classChoice) == -1) {
                System.out.println("Please enter a valid input.");
                continue;
            }

            switch (classChoice.charAt(0)) {
                case '1':
                    if (firstClass.size() >= 30) {
                        System.out.print("SORRY!! The first class section is full. Is it acceptable to be placed in the economy section? 'Y' for yes, 'N' for no: ");
                        String yOrN = sc.nextLine();
                        if (yOrN.equalsIgnoreCase("Y")) {
                            classChoice = "2";
                        } else {
                            System.out.println("The next flight leaves in 3 hours!!");
                            return;
                        }
                    } else {
                        firstClass.add(collectPassengerData(sc));
                        System.out.println("SEAT ADDED SUCCESSFULLY!!");
                        System.out.println("Your seat number is " + firstClass.size());
                    }
                    break;
                case '2':
                    if (economyClass.size() >= 70) {
                        System.out.print("SORRY!! The economy class section is full. Is it acceptable to be placed in the first class section? 'Y' for yes, 'N' for no: ");
                        String yOrN = sc.nextLine();
                        if (yOrN.equalsIgnoreCase("Y")) {
                            classChoice = "1";
                        } else {
                            System.out.println("The next flight leaves in 3 hours!!");
                            return;
                        }
                    } else {
                        economyClass.add(collectPassengerData(sc));
                        System.out.println("SEAT ADDED SUCCESSFULLY!!");
                        System.out.println("Your seat number is " + (economyClass.size() + 30));
                    }
                    break;
                case '3':
                    return;
            }
        }
    }

    private static Passenger collectPassengerData(Scanner sc) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your sex: ");
        String sex = sc.nextLine();
        System.out.print("Enter your age: ");
        String age = sc.nextLine();
        System.out.print("Enter your passport number: ");
        String passportNumber = sc.nextLine();

        return new Passenger(name, sex, age, passportNumber);
    }

    private static void displayOptions(Scanner sc, List<Passenger> firstClass, List<Passenger> economyClass) {
        while (true) {
            System.out.println("\n----------Display options----------");
            System.out.println("  1. Search person (using passport number)");
            System.out.println("  2. Display seat status");
            System.out.println("  3. Return to home page");
            System.out.print("Enter your choice: ");

            String displayChoice = sc.nextLine();

            if (displayChoice.length() != 1 || "123".indexOf(displayChoice) == -1) {
                System.out.println("Please enter a valid input.");
                continue;
            }

            switch (displayChoice.charAt(0)) {
                case '1':
                    searchPassenger(sc, firstClass, economyClass);
                    break;
                case '2':
                    displaySeatStatus(firstClass, economyClass);
                    break;
                case '3':
                    return;
            }
        }
    }

    private static void searchPassenger(Scanner sc, List<Passenger> firstClass, List<Passenger> economyClass) {
        System.out.print("Enter the passport number: ");
        String searchPassportNum = sc.nextLine();

        for (int i = 0; i < firstClass.size(); i++) {
            if (firstClass.get(i).passportNumber.equals(searchPassportNum)) {
                System.out.println("==PERSON FOUND IN FIRST CLASS==");
                displayPassengerInfo(firstClass.get(i), i + 1);
                return;
            }
        }

        for (int i = 0; i < economyClass.size(); i++) {
            if (economyClass.get(i).passportNumber.equals(searchPassportNum)) {
                System.out.println("==PERSON FOUND IN ECONOMY CLASS==");
                displayPassengerInfo(economyClass.get(i), i + 31);
                return;
            }
        }

        System.out.println("No person found with this passport number.");
    }

    private static void displaySeatStatus(List<Passenger> firstClass, List<Passenger> economyClass) {
        System.out.println("\n----------FIRST CLASS STATUS (" + firstClass.size() + "/30)----------");
        for (int i = 0; i < firstClass.size(); i++) {
            displayPassengerInfo(firstClass.get(i), i + 1);
        }

        System.out.println("\n----------ECONOMY CLASS STATUS (" + economyClass.size() + "/70)----------");
        for (int i = 0; i < economyClass.size(); i++) {
            displayPassengerInfo(economyClass.get(i), i + 31);
        }
    }

    private static void displayPassengerInfo(Passenger passenger, int seatNumber) {
        System.out.printf("Seat Number: %d, Name: %s, Sex: %s, Age: %s, Passport Number: %s\n", 
                seatNumber, passenger.name, passenger.sex, passenger.age, passenger.passportNumber);
    }
}
