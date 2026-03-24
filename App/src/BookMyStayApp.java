import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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
    }

    public int getAvailableRooms(String roomType) {
        return rooms.getOrDefault(roomType, 0);
    }

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

                        System.out.println("Booking successful!");

                    } catch (InvalidBookingException e) {
                        // Graceful error handling
                        System.out.println("Booking Failed: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Unexpected error occurred.");
                    }
                    break;

                case 2:
                    inventory.displayInventory();
                    break;

                case 3:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 3);

        scanner.close();
    }
}