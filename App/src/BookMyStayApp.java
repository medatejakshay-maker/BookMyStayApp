import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay - Hotel Booking Management System
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates how HashMap can be used to maintain a
 * centralized inventory system for room availability.
 *
 * @author Developer
 * @version 3.1
 */


/* =========================
   Abstract Room Class
   ========================= */
abstract class Room {

    protected String roomType;
    protected int beds;
    protected double size;
    protected double price;

    public Room(String roomType, int beds, double size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq ft");
        System.out.println("Price     : $" + price + " per night");
    }
}


/* =========================
   Single Room
   ========================= */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 100);
    }
}


/* =========================
   Double Room
   ========================= */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 180);
    }
}


/* =========================
   Suite Room
   ========================= */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 600, 350);
    }
}


/* =========================
   Room Inventory Class
   ========================= */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    /**
     * Constructor initializes room availability
     */
    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    /**
     * Get availability of a room type
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Update availability
     */
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /**
     * Display full inventory
     */
    public void displayInventory() {

        System.out.println("\n--- Current Room Inventory ---");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(
                    entry.getKey() + " : " + entry.getValue() + " available"
            );
        }
    }
}


/* =========================
   Application Entry Point
   ========================= */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println(" Book My Stay - Hotel Booking App ");
        System.out.println(" Version: 3.1 ");
        System.out.println("====================================");


        /* Create room objects */
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        /* Display room details */
        System.out.println("\n--- Room Details ---");

        single.displayRoomDetails();
        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println();

        suite.displayRoomDetails();


        /* Initialize inventory */
        RoomInventory inventory = new RoomInventory();

        /* Display inventory */
        inventory.displayInventory();


        /* Example update */
        System.out.println("\nUpdating inventory (Single Room booked)...");

        int current = inventory.getAvailability("Single Room");
        inventory.updateAvailability("Single Room", current - 1);

        inventory.displayInventory();

        System.out.println("\nApplication terminated successfully.");
    }
}