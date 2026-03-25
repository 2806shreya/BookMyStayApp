import java.util.*;

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

    public int getBookingId() {
        return bookingId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return "BookingID: " + bookingId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Nights: " + nights;
    }
}

// Booking History class (stores confirmed bookings)
class BookingHistory {
    private List<Reservation> historyList;

    public BookingHistory() {
        historyList = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        historyList.add(reservation);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(historyList); // return copy (safe)
    }
}

// Reporting Service class
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\n--- Booking History ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummaryReport(List<Reservation> reservations) {
        System.out.println("\n--- Summary Report ---");

        int totalBookings = reservations.size();
        int totalNights = 0;

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : reservations) {
            totalNights += r.getNights();

            roomTypeCount.put(
                    r.getRoomType(),
                    roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Nights Booked: " + totalNights);

        System.out.println("\nRoom Type Distribution:");
        for (String type : roomTypeCount.keySet()) {
            System.out.println(type + " -> " + roomTypeCount.get(type));
        }
    }
}

// Main class
public class UC8 {

    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        Reservation r1 = new Reservation(101, "Shreya", "Deluxe", 3);
        Reservation r2 = new Reservation(102, "Aman", "Standard", 2);
        Reservation r3 = new Reservation(103, "Riya", "Suite", 5);

        // Add to booking history
        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);

        // Admin retrieves booking history
        List<Reservation> storedReservations = bookingHistory.getAllReservations();

        // Display bookings
        reportService.displayAllBookings(storedReservations);

        // Generate report
        reportService.generateSummaryReport(storedReservations);
    }
}