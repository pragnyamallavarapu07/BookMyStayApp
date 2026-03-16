import java.util.HashMap;
import java.util.Map;

/**
 * RoomInventory class
 * Responsible for centralized room availability management.
 *
 * Uses HashMap to store room type -> available count.
 *
 * @version 3.0
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    /**
     * Constructor initializes room availability.
     */
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types and their availability
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 2);
    }

    /**
     * Get availability for a specific room type
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Update availability after booking/cancellation
     */
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        }
    }

    /**
     * Display entire inventory
     */
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}

/**
 * Application Entry for Use Case 3
 * Demonstrates centralized inventory management.
 *
 * @version 3.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("       Book My Stay App v3.1        ");
        System.out.println("====================================");

        // Initialize inventory system
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        // Example: Check availability
        System.out.println("\nChecking availability for Single Room...");
        int singleAvailable = inventory.getAvailability("Single Room");
        System.out.println("Single Room Available: " + singleAvailable);

        // Example: Update availability after booking
        System.out.println("\nBooking 1 Single Room...");
        inventory.updateAvailability("Single Room", singleAvailable - 1);

        // Display updated inventory
        inventory.displayInventory();
    }
}