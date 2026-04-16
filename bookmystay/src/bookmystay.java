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
// Thread-Safe Inventory
// ===============================
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory(int single, int doub, int suite) {
        rooms.put("Single", single);
        rooms.put("Double", doub);
        rooms.put("Suite", suite);
    }

    // synchronized method → critical section
    public synchronized boolean allocateRoom(String type) {

        int available = rooms.getOrDefault(type, 0);

        if (available > 0) {
            rooms.put(type, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + ": " + rooms.get(type));
        }
    }
}

// ===============================
// Booking Processor (Thread)
// ===============================
class BookingProcessor extends Thread {

    private Queue<Reservation> queue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {
            Reservation r;

            // synchronized queue access
            synchronized (queue) {
                if (queue.isEmpty()) {
                    break;
                }
                r = queue.poll(); // FIFO
            }

            // simulate processing delay
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}

            // critical section → allocation
            boolean success = inventory.allocateRoom(r.roomType);

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " booked " + r.roomType + " for " + r.guestName);
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " FAILED for " + r.guestName + " (" + r.roomType + ")");
            }
        }
    }
}

// ===============================
// Main Class (lowercase)
// ===============================
public class bookmystay {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        // ===== Inventory =====
        System.out.print("Enter Single rooms: ");
        int s = sc.nextInt();

        System.out.print("Enter Double rooms: ");
        int d = sc.nextInt();

        System.out.print("Enter Suite rooms: ");
        int su = sc.nextInt();

        RoomInventory inventory = new RoomInventory(s, d, su);

        // ===== Booking Queue =====
        Queue<Reservation> queue = new LinkedList<>();

        System.out.print("\nEnter number of booking requests: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= n; i++) {
            System.out.println("\nRequest " + i);

            System.out.print("Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Room Type: ");
            String type = sc.nextLine();

            queue.offer(new Reservation(name, type));
        }

        // ===== Multiple Threads =====
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);
        BookingProcessor t3 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");
        t3.setName("Thread-3");

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for completion
        t1.join();
        t2.join();
        t3.join();

        // Final inventory
        inventory.displayInventory();

        sc.close();
    }
}