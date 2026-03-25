import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Inventory (CRITICAL RESOURCE)
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Standard", 1);
        rooms.put("Deluxe", 1);
    }

    // SYNCHRONIZED → critical section
    public synchronized boolean allocateRoom(String type, String guest) {
        int available = rooms.getOrDefault(type, 0);

        if (available > 0) {
            System.out.println(Thread.currentThread().getName() +
                    " allocated " + type + " to " + guest);

            rooms.put(type, available - 1);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " FAILED for " + guest + " (No " + type + " rooms left)");
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " -> " + rooms.get(type));
        }
    }
}

// Shared Booking Queue
class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

// Worker Thread
class BookingProcessor extends Thread {
    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(String name, BookingQueue queue, RoomInventory inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            // synchronized access to queue
            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) break;

            // Critical section (inventory)
            inventory.allocateRoom(request.roomType, request.guestName);

            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Main class
public class UC11 {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Simulate multiple booking requests
        queue.addRequest(new BookingRequest("Shreya", "Deluxe"));
        queue.addRequest(new BookingRequest("Aman", "Deluxe"));
        queue.addRequest(new BookingRequest("Riya", "Standard"));
        queue.addRequest(new BookingRequest("Karan", "Standard"));

        // Multiple threads (guests)
        BookingProcessor t1 = new BookingProcessor("Thread-1", queue, inventory);
        BookingProcessor t2 = new BookingProcessor("Thread-2", queue, inventory);

        // Start threads
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final state
        inventory.displayInventory();
    }
}