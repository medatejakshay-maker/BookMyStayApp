/**
 * Book My Stay - Hotel Booking Management System
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Demonstrates object-oriented modeling using:
 * - Abstract Class
 * - Inheritance
 * - Polymorphism
 * - Encapsulation
 *
 * @author Developer
 * @version 2.1
 */

/* Abstract Room Class */
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

    public void displayRoomDetails() {

        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq ft");
        System.out.println("Price     : $" + price + " per night");
    }
}

/* Single Room Implementation */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 100);
    }
}

/* Double Room Implementation */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 180);
    }
}

/* Suite Room Implementation */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 600, 350);
    }
}

/* Application Entry Point */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println(" Book My Stay - Hotel Booking App ");
        System.out.println(" Version: 2.1 ");
        System.out.println("====================================");

        /* Create Room Objects (Polymorphism) */
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        /* Static Availability Variables */
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("\n--- Room Details ---");

        System.out.println("\nSingle Room:");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailable);

        System.out.println("\nDouble Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailable);

        System.out.println("\nSuite Room:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailable);

        System.out.println("\nApplication terminated successfully.");
    }
}