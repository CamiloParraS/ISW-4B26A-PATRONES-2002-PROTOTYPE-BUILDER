import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        printBanner();

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> builder();
                case "2" -> protoype();
                case "3" -> {
                    System.out.println("\n  Goodbye! \n");
                    running = false;
                }
                default -> System.out.println("  Invalid option, try again.\n");
            }
        }
        sc.close();
    }

    private static void printBanner() {
        System.out.println("\nWRC\n");
    }

    private static void printMainMenu() {
        System.out.println(
            """
                         MAIN MENU
                         [1] Builder   – build a custom car
                         [2] Prototype – clone a team base car
                         [3] Exit
            """
        );
        System.out.print(" Choice: ");
    }

    // ── Option 1 : BUILDER ────────────────────────────────────

    private static void builder() {
        System.out.println("\n  ── BUILDER PATTERN ──\n");

        System.out.print("  Team name   : ");
        String team = sc.nextLine().trim();

        System.out.print("  Driver name : ");
        String driver = sc.nextLine().trim();

        EngineType engine = pickEngine();
        TireType tires = pickTires();
        boolean turbo = pickTurbo();

        // Build step-by-step using the Builder
        RallyCar car = new RallyCar.Builder()
            .teamName(team)
            .driverName(driver)
            .engine(engine)
            .tires(tires)
            .turboBoost(turbo)
            .build();

        System.out.println("\n  ── CAR BUILT SUCCESSFULLY ──");
        System.out.println(car);
        System.out.println();
    }

    private static void protoype() {
        System.out.println(
            "A fixed M-Sport Ford base car is created once Two driver-specific clones are produced from it without changing the original"
        );

        RallyCar teamBase = new RallyCar.Builder()
            .teamName("M-Sport Ford")
            .driverName("PROTOTYPE BASE")
            .engine(EngineType.RALLY)
            .tires(TireType.GRAVEL)
            .turboBoost(true)
            .build();

        System.out.println("  Base prototype:");
        System.out.println(teamBase);

        PrototypeManager manager = new PrototypeManager(teamBase);

        System.out.print("\n  Driver A name: ");
        String driverA = sc.nextLine().trim();
        RallyCar carA = manager.cloneForDriver(driverA);

        System.out.print("  Driver B name: ");
        String driverB = sc.nextLine().trim();
        RallyCar carB = manager.cloneForDriver(driverB);

        System.out.println("\n  ── CLONED CAR – " + driverA + " ──");
        System.out.println(carA);

        System.out.println("\n  ── CLONED CAR – " + driverB + " ──");
        System.out.println(carB);

        System.out.println("\n  ── ORIGINAL PROTOTYPE (unchanged) ──");
        System.out.println(teamBase);
        System.out.println();
    }

    private static EngineType pickEngine() {
        System.out.println(
            """
            Select engine:
             [1] Stock 1.6T       (safe, low power)
             [2] Performance 2.0T (balanced)
             [3] Full Rally 2.0T  (max power, higher risk)"""
        );
        System.out.print("  Choice: ");
        return switch (sc.nextLine().trim()) {
            case "2" -> EngineType.PERFORMANCE;
            case "3" -> EngineType.RALLY;
            default -> EngineType.STOCK;
        };
    }

    private static TireType pickTires() {
        System.out.println(
            """
            Select tires:
             [1] Gravel Soft  (more grip, higher wear)
             [2] Asphalt Hard (less grip, more durable)"""
        );
        System.out.print("  Choice: ");
        return sc.nextLine().trim().equals("2")
            ? TireType.ASPHALT
            : TireType.GRAVEL;
    }

    private static boolean pickTurbo() {
        System.out.print("  Enable Turbo Boost? [y/n]: ");
        return sc.nextLine().trim().equalsIgnoreCase("y");
    }
}
