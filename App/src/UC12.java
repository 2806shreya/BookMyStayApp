import java.io.*;
import java.util.*;

// Booking class
class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    int bookingId;
    String customerName;
    String roomType;

    public Booking(int bookingId, String customerName, String roomType) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String toString() {
        return bookingId + " | " + customerName + " | " + roomType;
    }
}

// Main System Class
public class UseCase12DataPersistenceRecovery {

    static Map<String, Integer> inventory = new HashMap<>();
    static List<Booking> bookings = new ArrayList<>();

    static final String FILE_NAME = "hotel_data.ser";

    // Save data (Serialization)
    public static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(bookings);
            System.out.println("✅ Data saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    // Load data (Deserialization)
    @SuppressWarnings("unchecked")
    public static void loadData() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("⚠ No previous data found. Starting fresh.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            inventory = (Map<String, Integer>) ois.readObject();
            bookings = (List<Booking>) ois.readObject();
            System.out.println("✅ Data loaded successfully.");
        } catch (Exception e) {
            System.out.println("⚠ Corrupted data. Starting with empty system.");
            inventory = new HashMap<>();
            bookings = new ArrayList<>();
        }
    }

    // Initialize default inventory
    public static void initializeInventory() {
        if (inventory.isEmpty()) {
            inventory.put("Single", 5);
            inventory.put("Double", 3);
            inventory.put("Suite", 2);
        }
    }

    // Add booking
    public static void addBooking(Scanner sc) {
        System.out.print("Enter Booking ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Room Type (Single/Double/Suite): ");
        String room = sc.nextLine();

        if (inventory.containsKey(room) && inventory.get(room) > 0) {
            bookings.add(new Booking(id, name, room));
            inventory.put(room, inventory.get(room) - 1);
            System.out.println("✅ Booking successful!");
        } else {
            System.out.println("❌ Room not available.");
        }
    }

    // View data
    public static void viewData() {
        System.out.println("\n📦 Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " Rooms: " + inventory.get(key));
        }

        System.out.println("\n📋 Bookings:");
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Load previous data
        loadData();

        // Initialize if empty
        initializeInventory();

        int choice;

        do {
            System.out.println("\n===== Hotel Booking System =====");
            System.out.println("1. Add Booking");
            System.out.println("2. View Data");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addBooking(sc);
                    break;
                case 2:
                    viewData();
                    break;
                case 3:
                    saveData(); // Save before exit
                    System.out.println("👋 Exiting system...");
                    break;
                default:
                    System.out.println("❌ Invalid choice");
            }

        } while (choice != 3);

        sc.close();
    }
}