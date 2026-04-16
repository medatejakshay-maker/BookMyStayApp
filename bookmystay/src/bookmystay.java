import java.util.Scanner;

// ===============================
// Room Class (Domain Model)
// ===============================
class Room {
    private int beds;
    private int size;
    private double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public int getBeds() {
        return beds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }
}

// ===============================
// RoomInventory (State Holder)
// ===============================
class RoomInventory {
    private int singleRooms;
    private int doubleRooms;
    private int suiteRooms;

    public RoomInventory(int single, int doub, int suite) {
        this.singleRooms = single;
        this.doubleRooms = doub;
        this.suiteRooms = suite;
    }

    public int getAvailableRooms(String type) {
        if (type.equalsIgnoreCase("Single")) {
            return singleRooms;
        } else if (type.equalsIgnoreCase("Double")) {
            return doubleRooms;
        } else if (type.equalsIgnoreCase("Suite")) {
            return suiteRooms;
        }
        return 0;
    }
}

// ===============================
// RoomSearchService
// ===============================
class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        System.out.println("\n===== Room Search Result =====\n");

        int singleAvailable = inventory.getAvailableRooms("Single");
        if (singleAvailable > 0) {
            System.out.println("Single Room:");
            System.out.println("Beds: " + singleRoom.getBeds());
            System.out.println("Size: " + singleRoom.getSize() + " sqft");
            System.out.println("Price per night: " + singleRoom.getPrice());
            System.out.println("Available: " + singleAvailable);
            System.out.println();
        }

        int doubleAvailable = inventory.getAvailableRooms("Double");
        if (doubleAvailable > 0) {
            System.out.println("Double Room:");
            System.out.println("Beds: " + doubleRoom.getBeds());
            System.out.println("Size: " + doubleRoom.getSize() + " sqft");
            System.out.println("Price per night: " + doubleRoom.getPrice());
            System.out.println("Available: " + doubleAvailable);
            System.out.println();
        }

        int suiteAvailable = inventory.getAvailableRooms("Suite");
        if (suiteAvailable > 0) {
            System.out.println("Suite Room:");
            System.out.println("Beds: " + suiteRoom.getBeds());
            System.out.println("Size: " + suiteRoom.getSize() + " sqft");
            System.out.println("Price per night: " + suiteRoom.getPrice());
            System.out.println("Available: " + suiteAvailable);
            System.out.println();
        }
    }
}

// ===============================
// Main Class
// ===============================
public class bookmystay {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // ===== INPUT: Inventory =====
        System.out.println("Enter available Single Rooms:");
        int singleCount = sc.nextInt();

        System.out.println("Enter available Double Rooms:");
        int doubleCount = sc.nextInt();

        System.out.println("Enter available Suite Rooms:");
        int suiteCount = sc.nextInt();

        RoomInventory inventory = new RoomInventory(singleCount, doubleCount, suiteCount);

        // ===== INPUT: Room Details =====
        System.out.println("\nEnter Single Room details (beds, size, price):");
        Room singleRoom = new Room(sc.nextInt(), sc.nextInt(), sc.nextDouble());

        System.out.println("\nEnter Double Room details (beds, size, price):");
        Room doubleRoom = new Room(sc.nextInt(), sc.nextInt(), sc.nextDouble());

        System.out.println("\nEnter Suite Room details (beds, size, price):");
        Room suiteRoom = new Room(sc.nextInt(), sc.nextInt(), sc.nextDouble());

        // ===== Search =====
        RoomSearchService service = new RoomSearchService();
        service.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);

        sc.close();
    }
}