import java.util.*;

// Reservation class (represents booking request)
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

    public void display() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}

// Booking Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // Display all requests (without removing)
    public void displayQueue() {
        System.out.println("\nCurrent Booking Requests (FIFO Order):");
        for (Reservation r : queue) {
            r.display();
        }
    }
}

// Main class
public class UC5 {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App v5.0 ===");

        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Simulating booking requests
        requestQueue.addRequest(new Reservation("Shreya", "Single Room"));
        requestQueue.addRequest(new Reservation("Aman", "Double Room"));
        requestQueue.addRequest(new Reservation("Riya", "Suite Room"));

        // Display queue
        requestQueue.displayQueue();

        System.out.println("\nRequests are waiting for allocation...");
    }
}
