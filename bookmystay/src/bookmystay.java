import java.util.*;

// ===============================
// Reservation Class
// ===============================
class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;
    boolean isActive;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isActive = true;
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

    public void increaseRoom(String type) {
        rooms.put(type, rooms.get(type) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + ": " + rooms.get(type));
        }
    }
}

// ===============================
// Cancellation Service
// ===============================
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String id, Map<String, Reservation> bookings, RoomInventory inventory) {

        if (!bookings.containsKey(id)) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        Reservation r = bookings.get(id);

        if (!r.isActive) {
            System.out.println("Cancellation Failed: Already cancelled.");
            return;
        }

        // Push room ID to stack (LIFO)
        rollbackStack.push(r.roomId);

        // Restore inventory
        inventory.increaseRoom(r.roomType);

        // Mark as cancelled
        r.isActive = false;

        System.out.println("Booking Cancelled for " + r.guestName +
                " | Released Room ID: " + r.roomId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recently Released Rooms): " + rollbackStack);
    }
}

// ===============================
// Main Class (lowercase)
// ===============================
public class bookmystay {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Sample confirmed bookings
        Map<String, Reservation> bookings = new HashMap<>();

        bookings.put("R1", new Reservation("R1", "Akshay", "Single", "S1"));
        bookings.put("R2", new Reservation("R2", "Rahul", "Double", "D1"));
        bookings.put("R3", new Reservation("R3", "Priya", "Suite", "SU1"));

        // Inventory
        RoomInventory inventory = new RoomInventory(1, 1, 1);

        CancellationService service = new CancellationService();

        System.out.print("Enter Reservation ID to cancel: ");
        String id = sc.nextLine();

        service.cancelBooking(id, bookings, inventory);

        // Show updated inventory
        inventory.displayInventory();

        // Show rollback stack
        service.showRollbackStack();

        sc.close();
    }
}