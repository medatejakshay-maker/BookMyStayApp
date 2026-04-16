import java.io.*;
import java.util.*;

// ===============================
// Reservation Class (Serializable)
// ===============================
class Reservation implements Serializable {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String id, String name, String type) {
        this.reservationId = id;
        this.guestName = name;
        this.roomType = type;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// ===============================
// Inventory Class (Serializable)
// ===============================
class RoomInventory implements Serializable {
    Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory(int s, int d, int su) {
        rooms.put("Single", s);
        rooms.put("Double", d);
        rooms.put("Suite", su);
    }

    public void display() {
        System.out.println("\nInventory:");
        for (String key : rooms.keySet()) {
            System.out.println(key + ": " + rooms.get(key));
        }
    }
}

// ===============================
// System State Wrapper
// ===============================
class SystemState implements Serializable {
    RoomInventory inventory;
    List<Reservation> history;

    public SystemState(RoomInventory inv, List<Reservation> hist) {
        this.inventory = inv;
        this.history = hist;
    }
}

// ===============================
// Persistence Service
// ===============================
class PersistenceService {

    private static final String FILE_NAME = "hotel_data.ser";

    // SAVE
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("\nData saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // LOAD
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("Data loaded successfully!");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh...");
        } catch (Exception e) {
            System.out.println("Error loading data. Starting safe state...");
        }

        return null;
    }
}

// ===============================
// Main Class (lowercase)
// ===============================
public class bookmystay {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // ===== Load Previous State =====
        SystemState state = PersistenceService.load();

        RoomInventory inventory;
        List<Reservation> history;

        if (state != null) {
            inventory = state.inventory;
            history = state.history;
        } else {
            // Fresh start
            inventory = new RoomInventory(2, 2, 1);
            history = new ArrayList<>();
        }

        // ===== Add New Booking =====
        System.out.print("\nEnter new booking? (yes/no): ");
        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("yes")) {

            System.out.print("Reservation ID: ");
            String id = sc.nextLine();

            System.out.print("Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Room Type: ");
            String type = sc.nextLine();

            Reservation r = new Reservation(id, name, type);
            history.add(r);

            System.out.println("Booking added!");
        }

        // ===== Display Current State =====
        System.out.println("\n===== Current Booking History =====");
        for (Reservation r : history) {
            r.display();
        }

        inventory.display();

        // ===== Save State Before Exit =====
        PersistenceService.save(new SystemState(inventory, history));

        sc.close();
    }
}