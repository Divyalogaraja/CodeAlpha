package task3;
import java.io.*;
import java.util.*;

public class Task3 {
    static Scanner scanner = new Scanner(System.in);

    static class Room implements Serializable {
        String category;
        int roomNumber;
        boolean isBooked;

        Room(String category, int roomNumber) {
            this.category = category;
            this.roomNumber = roomNumber;
            this.isBooked = false;
        }
    }

    static class Booking implements Serializable {
        String guestName;
        String category;
        int roomNumber;
        String paymentStatus;

        Booking(String guestName, String category, int roomNumber, String paymentStatus) {
            this.guestName = guestName;
            this.category = category;
            this.roomNumber = roomNumber;
            this.paymentStatus = paymentStatus;
        }

        public String toString() {
            return "Guest: " + guestName +
                   "\nRoom No: " + roomNumber +
                   "\nCategory: " + category +
                   "\nPayment: " + paymentStatus + "\n";
        }
    }

    static List<Room> rooms = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static final String FILE = "bookings.dat";

    // Initialize hotel rooms
    static {
        for (int i = 1; i <= 3; i++) rooms.add(new Room("Standard", i));
        for (int i = 4; i <= 6; i++) rooms.add(new Room("Deluxe", i));
        for (int i = 7; i <= 9; i++) rooms.add(new Room("Suite", i));
        loadBookingsFromFile();
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View All Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt(); scanner.nextLine();
            switch (choice) {
                case 1 -> showAvailableRooms();
                case 2 -> bookRoom();
                case 3 -> cancelBooking();
                case 4 -> viewBookings();
                case 5 -> {
                    saveBookingsToFile();
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void showAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            if (!r.isBooked) {
                System.out.println("Room " + r.roomNumber + " - " + r.category);
            }
        }
    }

    static void bookRoom() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Choose room category (Standard/Deluxe/Suite): ");
        String category = scanner.nextLine();

        for (Room r : rooms) {
            if (!r.isBooked && r.category.equalsIgnoreCase(category)) {
                r.isBooked = true;
                String paymentStatus = simulatePayment();
                Booking booking = new Booking(name, r.category, r.roomNumber, paymentStatus);
                bookings.add(booking);
                System.out.println("Room booked successfully!\n" + booking);
                return;
            }
        }
        System.out.println("Sorry, no available rooms in that category.");
    }

    static String simulatePayment() {
        System.out.print("Enter payment amount to confirm booking: ₹");
        scanner.nextLine(); // simulate input
        return "Paid";
    }

    static void cancelBooking() {
        System.out.print("Enter your name to cancel booking: ");
        String name = scanner.nextLine();
        Iterator<Booking> iterator = bookings.iterator();
        boolean found = false;

        while (iterator.hasNext()) {
            Booking b = iterator.next();
            if (b.guestName.equalsIgnoreCase(name)) {
                iterator.remove();
                for (Room r : rooms) {
                    if (r.roomNumber == b.roomNumber) {
                        r.isBooked = false;
                        break;
                    }
                }
                System.out.println("Booking cancelled for " + name);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No booking found under that name.");
        }
    }

    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }
        System.out.println("\nAll Bookings:");
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }

    static void saveBookingsToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE))) {
            out.writeObject(bookings);
            out.writeObject(rooms);
            System.out.println("Bookings saved.");
        } catch (IOException e) {
            System.out.println("Error saving bookings.");
        }
    }

    @SuppressWarnings("unchecked")
    static void loadBookingsFromFile() {
        File file = new File(FILE);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE))) {
            bookings = (List<Booking>) in.readObject();
            rooms = (List<Room>) in.readObject();
        } catch (Exception e) {
            System.out.println("Error loading bookings.");
        }
    }
}


