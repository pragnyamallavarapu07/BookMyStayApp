import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Room class representing common room properties.
 * @version 4.0
 */
abstract class Room {

    private String roomType;
    private int beds;
    private int size;
    private double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq.ft");
        System.out.println("Price     : $" + price);
    }
}

/**
 * Concrete room types
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 200, 100.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 350, 180.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 500, 350.0);
    }
}

/**
 * Centralized Inventory class (read-only access for search).
 * @version 4.0
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 0); // Example unavailable room
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

/**
 * Search Service responsible for read-only room search.
 * @version 4.0
 */
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("\n--- Available Rooms ---");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            if (available > 0) {

                room.displayDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println("------------------------------");
            }
        }
    }
}

/**
 * Application Entry Point
 * Demonstrates safe read-only search.
 * @version 4.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("        Book My Stay App v4.1       ");
        System.out.println("====================================");

        // Initialize room domain objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService();

        // Perform search (read-only)
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("\nSearch completed. Inventory state unchanged.");
    }
}