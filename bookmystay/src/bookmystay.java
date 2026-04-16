import java.util.*;

// ===============================
// Reservation Class
// ===============================
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

// ===============================
// Booking Queue
// ===============================
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Enqueue
    public void addRequest(Reservation r) {
        queue.offer(r);
        System.out.println("Booking request added for " + r.getGuestName());
    }

    // Display FIFO order
    public void showRequests() {
        if (queue.isEmpty()) {
            System.out.println("No booking requests.");
            return;
        }

        System.out.println("\nBooking Requests (FIFO Order):");
        for (Reservation r : queue) {
            r.display();
        }
    }
}

// ===============================
// Main Class (lowercase as requested)
// ===============================
public class bookmystay {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingRequestQueue queue = new BookingRequestQueue();

        System.out.print("Enter number of booking requests: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter details for request " + i);

            System.out.print("Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Room Type (Single/Double/Suite): ");
            String type = sc.nextLine();

            Reservation r = new Reservation(name, type);
            queue.addRequest(r);
        }

        queue.showRequests();

        sc.close();
    }
}