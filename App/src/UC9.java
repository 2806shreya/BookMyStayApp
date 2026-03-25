import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private int bookingId;
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(int bookingId, String guestName, String roomType, int nights) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    @Override
    public String toString() {
        return "BookingID: " + bookingId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Nights: " + nights;
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

    public boolean isValidRoomType(String type) {
        return rooms.containsKey(type);
    }

    public int getAvailableRooms(String type) {
        return rooms.getOrDefault(type, 0);
    }

    public void bookRoom(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Availability:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " -> " + rooms.get(type));
        }
    }
}

// Validator class
class InvalidBookingValidator {

    public static void validate(String guestName, String roomType, int nights, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (nights <= 0) {
            throw new InvalidBookingException("Number of nights must be greater than 0.");
        }

        if (inventory.getAvailableRooms(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for selected type.");
        }
    }
}

// Booking service
class BookingService {
    private RoomInventory inventory;
    private int bookingCounter = 100;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public Reservation createBooking(String guestName, String roomType, int nights)
            throws InvalidBookingException {

        // Validation (Fail-Fast)
        InvalidBookingValidator.validate(guestName, roomType, nights, inventory);

        // Update inventory only after validation
        inventory.bookRoom(roomType);

        return new Reservation(++bookingCounter, guestName, roomType, nights);
    }
}

// Main class
public class UC9 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        inventory.displayInventory();

        try {
            System.out.println("\nEnter Guest Name:");
            String name = sc.nextLine();

            System.out.println("Enter Room Type (Standard/Deluxe/Suite):");
            String roomType = sc.nextLine();

            System.out.println("Enter Number of Nights:");
            int nights = sc.nextInt();

            // Attempt booking
            Reservation reservation = bookingService.createBooking(name, roomType, nights);

            System.out.println("\nBooking Successful!");
            System.out.println(reservation);

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("\nBooking Failed: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("\nUnexpected Error: " + e.getMessage());
        }

        // System continues safely
        inventory.displayInventory();
    }
}
