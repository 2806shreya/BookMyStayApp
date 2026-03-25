import java.util.*;

// Custom Exception
class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private int bookingId;
    private String guestName;
    private String roomType;
    private boolean isCancelled;

    public Reservation(int bookingId, String guestName, String roomType) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.isCancelled = false;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        isCancelled = true;
    }

    @Override
    public String toString() {
        return "BookingID: " + bookingId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Status: " + (isCancelled ? "Cancelled" : "Confirmed");
    }
}

// Inventory class
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Standard", 2);
        rooms.put("Deluxe", 2);
        rooms.put("Suite", 1);
    }

    public void bookRoom(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public void releaseRoom(String type) {
        rooms.put(type, rooms.get(type) + 1);
    }

    public void displayInventory() {
        System.out.println("\nRoom Availability:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " -> " + rooms.get(type));
        }
    }
}

// Booking History
class BookingHistory {
    private Map<Integer, Reservation> bookings = new HashMap<>();

    public void addReservation(Reservation r) {
        bookings.put(r.getBookingId(), r);
    }

    public Reservation getReservation(int id) {
        return bookings.get(id);
    }

    public void displayAll() {
        System.out.println("\nBooking History:");
        for (Reservation r : bookings.values()) {
            System.out.println(r);
        }
    }
}

// Cancellation Service
class CancellationService {
    private RoomInventory inventory;
    private BookingHistory history;
    private Stack<Integer> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancelBooking(int bookingId) throws CancellationException {

        Reservation r = history.getReservation(bookingId);

        // Validation
        if (r == null) {
            throw new CancellationException("Booking does not exist.");
        }

        if (r.isCancelled()) {
            throw new CancellationException("Booking already cancelled.");
        }

        // Rollback tracking (LIFO)
        rollbackStack.push(bookingId);

        // Restore inventory
        inventory.releaseRoom(r.getRoomType());

        // Update booking status
        r.cancel();

        System.out.println("\nCancellation Successful for Booking ID: " + bookingId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recent Cancellations): " + rollbackStack);
    }
}

// Main class
public class UC10 {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancelService = new CancellationService(inventory, history);

        // Simulate bookings
        Reservation r1 = new Reservation(101, "Shreya", "Deluxe");
        Reservation r2 = new Reservation(102, "Aman", "Standard");

        inventory.bookRoom("Deluxe");
        inventory.bookRoom("Standard");

        history.addReservation(r1);
        history.addReservation(r2);

        inventory.displayInventory();
        history.displayAll();

        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\nEnter Booking ID to Cancel:");
            int id = sc.nextInt();

            cancelService.cancelBooking(id);

        } catch (CancellationException e) {
            System.out.println("\nCancellation Failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nUnexpected Error: " + e.getMessage());
        }

        // Final state
        inventory.displayInventory();
        history.displayAll();
        cancelService.showRollbackStack();
    }
}