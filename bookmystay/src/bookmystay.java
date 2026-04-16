import java.util.*;

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
// Room Inventory
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

    public void reduceRoom(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }
}

// ===============================
// Booking Service (Core Logic)
// ===============================
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomAllocationMap = new HashMap<>();

    public void processBookings(Queue<Reservation> queue, RoomInventory inventory) {

        int roomCounter = 1;

        while (!queue.isEmpty()) {

            Reservation r = queue.poll(); // FIFO
            String type = r.roomType;

            System.out.println("\nProcessing request for: " + r.guestName);

            if (inventory.getAvailable(type) > 0) {

                // Generate unique room ID
                String roomId = type.substring(0, 1).toUpperCase() + roomCounter++;

                // Ensure uniqueness using Set
                while (allocatedRoomIds.contains(roomId)) {
                    roomId = type.substring(0, 1).toUpperCase() + roomCounter++;
                }

                allocatedRoomIds.add(roomId);

                // Store in HashMap (type → set of IDs)
                roomAllocationMap.putIfAbsent(type, new HashSet<>());
                roomAllocationMap.get(type).add(roomId);

                // Update inventory immediately
                inventory.reduceRoom(type);

                // Confirm booking
                System.out.println("Booking Confirmed!");
                System.out.println("Guest: " + r.guestName);
                System.out.println("Room Type: " + type);
                System.out.println("Allocated Room ID: " + roomId);

            } else {
                System.out.println("Booking Failed! No rooms available for " + type);
            }
        }

        // Final Allocation Summary
        System.out.println("\n===== Allocation Summary =====");
        for (String type : roomAllocationMap.keySet()) {
            System.out.println(type + " Rooms Allocated: " + roomAllocationMap.get(type));
        }
    }
}

// ===============================
// Main Class (lowercase)
// ===============================
public class bookmystay {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // ===== Inventory Input =====
        System.out.print("Enter Single rooms: ");
        int single = sc.nextInt();

        System.out.print("Enter Double rooms: ");
        int doub = sc.nextInt();

        System.out.print("Enter Suite rooms: ");
        int suite = sc.nextInt();

        RoomInventory inventory = new RoomInventory(single, doub, suite);

        // ===== Queue Input =====
        Queue<Reservation> queue = new LinkedList<>();

        System.out.print("\nEnter number of booking requests: ");
        int n = sc.nextInt();
        sc.nextLine(); // clear buffer

        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter details for request " + i);

            System.out.print("Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Room Type (Single/Double/Suite): ");
            String type = sc.nextLine();

            queue.offer(new Reservation(name, type));
        }

        // ===== Process Bookings =====
        BookingService service = new BookingService();
        service.processBookings(queue, inventory);

        sc.close();
    }
}