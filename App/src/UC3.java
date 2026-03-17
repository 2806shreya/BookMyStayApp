import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 3: Centralized Room Inventory Management
 * Version: 3.0
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room types with availability
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, count);
        } else {
            System.out.println("Room type not found!");
        }
    }

    // Display all rooms
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class UC3{

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App v3.0 ===");

        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Check availability
        System.out.println("\nAvailable Single Rooms: "
                + inventory.getAvailability("Single Room"));

        // Update inventory
        inventory.updateAvailability("Single Room", 8);

        // Display updated inventory
        inventory.displayInventory();
    }
}