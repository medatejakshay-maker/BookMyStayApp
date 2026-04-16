import java.util.*;

// ===============================
// Reservation Class
// ===============================
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// ===============================
// Booking History (List Storage)
// ===============================
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Get all reservations
    public List<Reservation> getHistory() {
        return history;
    }
}

// ===============================
// Reporting Service
// ===============================
class BookingReportService {

    public void showAllBookings(List<Reservation> history) {

        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\n===== Booking History =====");
        for (Reservation r : history) {
            r.display();
        }
    }

    // Summary report
    public void generateSummary(List<Reservation> history) {

        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : history) {
            String type = r.getRoomType();
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }

        System.out.println("\n===== Booking Summary =====");
        for (String type : countMap.keySet()) {
            System.out.println(type + " Rooms Booked: " + countMap.get(type));
        }
    }
}

// ===============================
// Main Class (lowercase)
// ===============================
public class bookmystay {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        System.out.print("Enter number of confirmed bookings: ");
        int n = sc.nextInt();
        sc.nextLine(); // clear buffer

        for (int i = 1; i <= n; i++) {

            System.out.println("\nEnter details for booking " + i);

            System.out.print("Reservation ID: ");
            String id = sc.nextLine();

            System.out.print("Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Room Type (Single/Double/Suite): ");
            String type = sc.nextLine();

            Reservation r = new Reservation(id, name, type);
            history.addReservation(r);
        }

        // Display full history
        reportService.showAllBookings(history.getHistory());

        // Generate summary report
        reportService.generateSummary(history.getHistory());

        sc.close();
    }
}