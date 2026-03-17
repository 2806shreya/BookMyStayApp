import java.util.*;

// Reservation (same as UC5)
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

// Booking Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // removes from queue
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Inventory Service
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decreaseAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// Booking Service (MAIN LOGIC)
class BookingService {

    // Map: Room Type → Set of allocated Room IDs
    private HashMap<String, Set<String>> allocatedRooms = new HashMap<>();

    public BookingService() {
        allocatedRooms.put("Single Room", new HashSet<>());
        allocatedRooms.put("Double Room", new HashSet<>());
        allocatedRooms.put("Suite Room", new HashSet<>());
    }

    // Generate unique Room ID
    private String generateRoomId(String type, int count) {
        return type.substring(0, 2).toUpperCase() + count;
    }

    public void processBookings(BookingRequestQueue queue, RoomInventory inventory) {

        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();

            String type = r.getRoomType();

            if (inventory.getAvailability(type) > 0) {

                Set<String> roomSet = allocatedRooms.get(type);

                String roomId = generateRoomId(type, roomSet.size() + 1);

                // Ensure uniqueness
                while (roomSet.contains(roomId)) {
                    roomId = generateRoomId(type, roomSet.size() + 1);
                }

                // Allocate
                roomSet.add(roomId);

                // Update inventory
                inventory.decreaseAvailability(type);

                System.out.println("Booking Confirmed!");
                System.out.println("Guest: " + r.getGuestName());
                System.out.println("Room Type: " + type);
                System.out.println("Room ID: " + roomId);
                System.out.println("----------------------");

            } else {
                System.out.println("Booking Failed for " + r.getGuestName()
                        + " (No rooms available for " + type + ")");
            }
        }
    }
}

// Main Class
public class UC6{

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App v6.0 ===");

        // Queue
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Shreya", "Single Room"));
        queue.addRequest(new Reservation("Aman", "Single Room"));
        queue.addRequest(new Reservation("Riya", "Single Room")); // will fail

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Booking Service
        BookingService service = new BookingService();

        // Process bookings
        service.processBookings(queue, inventory);
    }
}