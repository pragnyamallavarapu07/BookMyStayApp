import java.util.LinkedList;
import java.util.Queue;

/**
 * Reservation class
 * Represents a guest's intent to book a room.
 *
 * @version 5.0
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

/**
 * BookingRequestQueue
 * Manages incoming booking requests using FIFO principle.
 *
 * @version 5.0
 */
class BookingRequestQueue {

    private Queue<Reservation> bookingQueue;

    public BookingRequestQueue() {
        bookingQueue = new LinkedList<>();
    }

    /**
     * Add booking request to queue
     */
    public void addRequest(Reservation reservation) {
        bookingQueue.offer(reservation);
        System.out.println("Booking request received from " + reservation.getGuestName());
    }

    /**
     * Display all queued booking requests
     */
    public void displayQueue() {

        System.out.println("\n--- Current Booking Request Queue (FIFO Order) ---");

        if (bookingQueue.isEmpty()) {
            System.out.println("No booking requests in queue.");
            return;
        }

        for (Reservation r : bookingQueue) {
            r.displayReservation();
        }
    }
}

/**
 * Application Entry Point
 * Demonstrates request intake using FIFO queue.
 *
 * @version 5.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("        Book My Stay App v5.1       ");
        System.out.println("====================================");

        // Initialize booking queue
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Simulate guest booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add requests to queue (FIFO)
        requestQueue.addRequest(r1);
        requestQueue.addRequest(r2);
        requestQueue.addRequest(r3);

        // Display queue state
        requestQueue.displayQueue();

        System.out.println("\nRequests are stored in arrival order and ready for allocation.");
        System.out.println("No inventory updates have occurred yet.");
    }
}