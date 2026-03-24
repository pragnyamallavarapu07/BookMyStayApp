import java.util.*;

// Custom Exception
class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

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
    }

    public String getRoomType() {
        return roomType;
    }

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
                    }
                    break;

                case 2:
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

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        scanner.close();
    }
}