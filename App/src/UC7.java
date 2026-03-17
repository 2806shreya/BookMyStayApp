import java.util.*;

// Add-On Service class
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getServiceName() {
        return serviceName;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private HashMap<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added " + service.getServiceName()
                + " to Reservation " + reservationId);
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        System.out.println("\nServices for Reservation " + reservationId + ":");

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            System.out.println(s.getServiceName() + " - ₹" + s.getPrice());
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

// Main class
public class UC7 {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App v7.0 ===");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Example reservation IDs (from UC6)
        String res1 = "SI1";
        String res2 = "SU1";

        // Add services
        manager.addService(res1, new AddOnService("Breakfast", 500));
        manager.addService(res1, new AddOnService("WiFi", 200));

        manager.addService(res2, new AddOnService("Airport Pickup", 1000));

        // Display services
        manager.displayServices(res1);
        manager.displayServices(res2);
    }
}