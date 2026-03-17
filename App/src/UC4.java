import java.util.*;

// Room base class
abstract class Room {
    protected String type;
    protected double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public abstract void displayDetails();
}

// Concrete room types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 2000);
    }

    public void displayDetails() {
        System.out.println(type + " | Price: " + price);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 3500);
    }

    public void displayDetails() {
        System.out.println(type + " | Price: " + price);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 5000);
    }

    public void displayDetails() {
        System.out.println(type + " | Price: " + price);
    }
}

// Inventory (read-only usage here)
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 0); // unavailable
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Set<String> getRoomTypes() {
        return inventory.keySet();
    }
}

// Search Service
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, List<Room> rooms) {
        System.out.println("\nAvailable Rooms:");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getType());

            // Only show available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("----------------------");
            }
        }
    }
}

// Main class
public class UC4{

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App v4.0 ===");

        // Inventory (read-only)
        RoomInventory inventory = new RoomInventory();

        // Room objects (domain model)
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Search
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, rooms);
    }
}
