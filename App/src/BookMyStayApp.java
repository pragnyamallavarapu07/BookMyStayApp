import java.util.*;

/**
 * Reservation class
 * Represents a booking request from a guest.
 * @version 6.0
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * RoomInventory
 * Manages available room counts.
 * @version 6.0
 */
class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        int count = inventory.get(roomType);
        inventory.put(roomType, count - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

/**
 * BookingRequestQueue
 * Stores booking requests in FIFO order.
 * @version 6.0
 */
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
        System.out.println("Booking request received from " + r.getGuestName());
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

/**
 * BookingService
 * Processes booking requests and allocates rooms.
 * @version 6.0
 */
class BookingService {

    private RoomInventory inventory;

    // Track allocated rooms per type
    private HashMap<String, Set<String>> allocatedRooms = new HashMap<>();

    // Track all room IDs to prevent duplicates
    private Set<String> usedRoomIds = new HashSet<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Generate unique room ID
     */
    private String generateRoomId(String roomType) {

        String prefix = roomType.replace(" ", "").substring(0, 3).toUpperCase();
        String roomId;

        do {
            roomId = prefix + "-" + (100 + new Random().nextInt(900));
        } while (usedRoomIds.contains(roomId));

        usedRoomIds.add(roomId);
        return roomId;
    }

    /**
     * Process booking request
     */
    public void processReservation(Reservation r) {

        String roomType = r.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("Reservation FAILED for " + r.getGuestName() +
                    " (No available " + roomType + ")");
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRooms.putIfAbsent(roomType, new HashSet<>());
        allocatedRooms.get(roomType).add(roomId);

        inventory.decrementRoom(roomType);

        System.out.println("Reservation CONFIRMED");
        System.out.println("Guest: " + r.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Assigned Room ID: " + roomId);
        System.out.println("--------------------------------");
    }

    public void displayAllocations() {

        System.out.println("\nAllocated Rooms:");

        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " -> " + allocatedRooms.get(type));
        }
    }
}

/**
 * Application Entry
 * Demonstrates room allocation system.
 * @version 6.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("        Book My Stay App v6.1       ");
        System.out.println("====================================");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService bookingService = new BookingService(inventory);

        // Simulated booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Double Room"));

        System.out.println("\nProcessing Reservations...\n");

        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            bookingService.processReservation(r);
        }

        bookingService.displayAllocations();
        inventory.displayInventory();
    }
}