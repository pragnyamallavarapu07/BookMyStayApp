 uc10
import java.util.*;

// Custom Exception
class CancellationException extends Exception {
    public CancellationException(String message) {

import java.util.* uc9
// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
 main
        super(message);
    }
}

 uc10
// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean isCancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    public String getReservationId() {
        return reservationId;

// Class representing Room Inventory
class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return rooms.containsKey(roomType);
main
    }

    public int getAvailableRooms(String roomType) {
        return rooms.getOrDefault(roomType, 0);
    }
uc10

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        isCancelled = true;
    }

    @Override
    public String toString() {
        return "ID: " + reservationId +
                ", Guest: " + guestName +
                ", RoomType: " + roomType +
                ", RoomID: " + roomId +
                ", Status: " + (isCancelled ? "Cancelled" : "Active");
    }
}

// Inventory Class
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void display() {
        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// Cancellation Service
class CancellationService {

    private Map<String, Reservation> reservations;
    private Stack<String> rollbackStack; // stores released room IDs

    public CancellationService(Map<String, Reservation> reservations) {
        this.reservations = reservations;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId, RoomInventory inventory)
            throws CancellationException {

        if (!reservations.containsKey(reservationId)) {
            throw new CancellationException("Reservation does not exist.");
        }

        Reservation res = reservations.get(reservationId);

        if (res.isCancelled()) {
            throw new CancellationException("Booking already cancelled.");
        }

        // Step 1: Push roomId to rollback stack
        rollbackStack.push(res.getRoomId());

        // Step 2: Restore inventory
        inventory.increment(res.getRoomType());

        // Step 3: Mark reservation as cancelled
        res.cancel();

        System.out.println("Cancellation successful. Room " + res.getRoomId() + " released.");
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recent Releases): " + rollbackStack);
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();

        // Simulated booking storage
        Map<String, Reservation> reservations = new HashMap<>();

        // Pre-added bookings (for demo)
        reservations.put("R1", new Reservation("R1", "Alice", "Single", "S101"));
        reservations.put("R2", new Reservation("R2", "Bob", "Double", "D201"));

        // Assume inventory already reduced for these bookings
        inventory.decrement("Single");
        inventory.decrement("Double");

        CancellationService service = new CancellationService(reservations);

        System.out.println("=== Book My Stay App - Cancellation & Rollback ===");

        int choice;

        do {
            System.out.println("\n1. Cancel Booking");
            System.out.println("2. View Reservations");
            System.out.println("3. View Inventory");
            System.out.println("4. View Rollback Stack");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    try {
                        System.out.print("Enter Reservation ID to cancel: ");
                        String id = scanner.nextLine();

                        service.cancelBooking(id, inventory);

                    } catch (CancellationException e) {
                        System.out.println("Cancellation Failed: " + e.getMessage());


    public void bookRoom(String roomType) throws InvalidBookingException {
        if (!isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        int available = getAvailableRooms(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for selected type.");
        }

        // Prevent negative inventory
        rooms.put(roomType, available - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Availability:");
        for (String type : rooms.keySet()) {
            System.out.println(type + ": " + rooms.get(type));
        }
    }
}

// Validator Class
class InvalidBookingValidator {

    public static void validate(String guestName, String roomType, int nights, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Room type does not exist.");
        }

        if (nights <= 0) {
            throw new InvalidBookingException("Number of nights must be greater than zero.");
        }

        if (inventory.getAvailableRooms(roomType) <= 0) {
            throw new InvalidBookingException("Selected room type is fully booked.");
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();

        System.out.println("=== Book My Stay App - Error Handling & Validation ===");

        int choice;

        do {
            System.out.println("\n1. Book Room");
            System.out.println("2. View Inventory");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    try {
                        System.out.print("Enter Guest Name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter Room Type (Single/Double/Suite): ");
                        String roomType = scanner.nextLine();

                        System.out.print("Enter Number of Nights: ");
                        int nights = scanner.nextInt();

                        // Validation (Fail-Fast)
                        InvalidBookingValidator.validate(name, roomType, nights, inventory);

                        // Booking
                        inventory.bookRoom(roomType);

// Class representing an Add-On Service
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Manager class to handle mapping between reservation and services
class AddOnServiceManager {

    // Map: Reservation ID -> List of Add-On Services
    private Map<String, List<AddOnService>> reservationServicesMap;

    public AddOnServiceManager() {
        reservationServicesMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {
        reservationServicesMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    // Get services for a reservation
    public List<AddOnService> getServices(String reservationId) {
        return reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = getServices(reservationId);
        double total = 0.0;

        for (AddOnService service : services) {
            total += service.getCost();
        }

        return total;
    }
}

// Main class
public class BookMyStayApp {
main

                        System.out.println("Booking successful!");
uc9
                    } catch (InvalidBookingException e) {
                        // Graceful error handling
                        System.out.println("Booking Failed: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Unexpected error occurred.");
 main
                    }
                    break;

                case 2:
 uc10
                    System.out.println("\nReservations:");
                    for (Reservation r : reservations.values()) {
                        System.out.println(r);
                    }
                    break;

                case 3:
                    inventory.display();
                    break;

                case 4:
                    service.showRollbackStack();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;


                    inventory.displayInventory();
                    break;

                case 3:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 3);


        Scanner scanner = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.println("=== Book My Stay App - Add-On Service Selection ===");

        // Input reservation ID
        System.out.print("Enter Reservation ID: ");
        String reservationId = scanner.nextLine();

        // Sample services
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 800);

        int choice;

        do {
            System.out.println("\nSelect Add-On Services:");
            System.out.println("1. WiFi (₹200)");
            System.out.println("2. Breakfast (₹500)");
            System.out.println("3. Airport Pickup (₹800)");
            System.out.println("4. Done");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manager.addService(reservationId, wifi);
                    System.out.println("WiFi added.");
                    break;
                case 2:
                    manager.addService(reservationId, breakfast);
                    System.out.println("Breakfast added.");
                    break;
                case 3:
                    manager.addService(reservationId, airportPickup);
                    System.out.println("Airport Pickup added.");
                    break;
                case 4:
                    System.out.println("Service selection completed.");
                    break;
main
                default:
                    System.out.println("Invalid choice!");
            }

 uc10
        } while (choice != 5);


        } while (choice != 4);

        // Display selected services
        System.out.println("\nSelected Services:");
        List<AddOnService> services = manager.getServices(reservationId);

        for (AddOnService service : services) {
            System.out.println("- " + service);
        }

        // Display total cost
        double totalCost = manager.calculateTotalCost(reservationId);
        System.out.println("Total Add-On Cost: ₹" + totalCost main
 main
        scanner.close();
    }
}