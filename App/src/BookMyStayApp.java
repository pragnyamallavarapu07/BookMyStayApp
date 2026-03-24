import java.util.*;

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

    public static void main(String[] args) {

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
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);

        // Display selected services
        System.out.println("\nSelected Services:");
        List<AddOnService> services = manager.getServices(reservationId);

        for (AddOnService service : services) {
            System.out.println("- " + service);
        }

        // Display total cost
        double totalCost = manager.calculateTotalCost(reservationId);
        System.out.println("Total Add-On Cost: ₹" + totalCost);

        scanner.close();
    }
}