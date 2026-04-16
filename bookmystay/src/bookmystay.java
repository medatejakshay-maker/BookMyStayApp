import java.util.*;

// ===============================
// Custom Exception
// ===============================
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// ===============================
// Reservation Class
// ===============================
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// ===============================
// Inventory Class
// ===============================
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory(int single, int doub, int suite) {
        rooms.put("Single", single);
        rooms.put("Double", doub);
        rooms.put("Suite", suite);
    }

    public int getAvailable(String type) {
        return rooms.getOrDefault(type, 0);
    }

    public void reduceRoom(String type) throws InvalidBookingException {
        int count = rooms.get(type);

        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for " + type);
        }

        rooms.put(type, count - 1);
    }

    public boolean isValidRoomType(String type) {
        return rooms.containsKey(type);
    }
}

// ===============================
// Booking Service with Validation
// ===============================
class BookingService {

    public void processBooking(Reservation r, RoomInventory inventory) {

        try {
            // ===== Validation =====
            if (r.guestName == null || r.guestName.trim().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty");
            }

            if (!inventory.isValidRoomType(r.roomType)) {
                throw new InvalidBookingException("Invalid room type: " + r.roomType);
            }

            // ===== Allocation =====
            inventory.reduceRoom(r.roomType);

            // ===== Success =====
            System.out.println("Booking Confirmed for " + r.guestName +
                    " (" + r.roomType + " Room)");

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// ===============================
// Main Class (lowercase)
// ===============================
public class bookmystay {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Inventory setup
        RoomInventory inventory = new RoomInventory(2, 1, 1);

        BookingService service = new BookingService();

        System.out.print("Enter number of booking attempts: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= n; i++) {

            System.out.println("\nEnter details for booking " + i);

            System.out.print("Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Room Type (Single/Double/Suite): ");
            String type = sc.nextLine();

            Reservation r = new Reservation(name, type);

            // Process with validation
            service.processBooking(r, inventory);
        }

        sc.close();
    }
}