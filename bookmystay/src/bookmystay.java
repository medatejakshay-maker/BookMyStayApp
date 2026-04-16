import java.util.*;

// ===============================
// Add-On Service Class
// ===============================
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// ===============================
// Add-On Service Manager
// ===============================
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<Service>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Display services + calculate total cost
    public void showServices(String reservationId) {

        if (!serviceMap.containsKey(reservationId)) {
            System.out.println("No services selected for this reservation.");
            return;
        }

        List<Service> services = serviceMap.get(reservationId);

        double totalCost = 0;

        System.out.println("\nServices for Reservation ID: " + reservationId);

        for (Service s : services) {
            System.out.println("- " + s.getServiceName() + " : ₹" + s.getCost());
            totalCost += s.getCost();
        }

        System.out.println("Total Add-On Cost: ₹" + totalCost);
    }
}

// ===============================
// Main Class
// ===============================
public class bookmystay {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        System.out.print("Enter number of services: ");
        int n = sc.nextInt();
        sc.nextLine(); // clear buffer

        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter service " + i + " details");

            System.out.print("Service Name: ");
            String name = sc.nextLine();

            System.out.print("Cost: ");
            double cost = sc.nextDouble();
            sc.nextLine(); // clear buffer

            Service service = new Service(name, cost);
            manager.addService(reservationId, service);
        }

        // Display services + cost
        manager.showServices(reservationId);

        sc.close();
    }
}