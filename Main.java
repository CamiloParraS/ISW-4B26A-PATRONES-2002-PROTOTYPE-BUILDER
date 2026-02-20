import java.util.List;
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
                case "2" -> prototype();
                case "3" -> viewGarage();
                case "4" -> {
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
                [1] Builder   - build a custom car
                [2] Prototype - clone an existing car
                [3] Garage    - view all created cars
                [4] Exit
            """
        );
        System.out.print(" Choice: ");
    }

    // Builder Pattern
    private static void builder() {
        System.out.print("  Team name   : ");
        String team = sc.nextLine().trim();

        System.out.print("  Driver name : ");
        String driver = sc.nextLine().trim();

        EngineType engine = pickEngine();
        TireType tires = pickTires();
        boolean turbo = pickTurbo();

        RallyCar car = new RallyCar.Builder()
            .teamName(team)
            .driverName(driver)
            .engine(engine)
            .tires(tires)
            .turboBoost(turbo)
            .build();

        // Save to garage, later used for the prototype
        RallyCar.addToGarage(car);

        System.out.println("\n  === CAR BUILT SUCCESSFULLY ===");
        System.out.println(car);
        System.out.println();
        pause();
    }

    private static void viewGarage() {
        System.out.println("\n === GARAGE === \n");
        List<RallyCar> garage = RallyCar.getGarage();
        if (garage.isEmpty()) {
            System.out.println("   No cars built yet.");
            return;
        }
        for (int i = 0; i < garage.size(); i++) {
            String header = String.format("   === Car #%d ===", i + 1);
            String footer = "   " + "=".repeat(header.trim().length());

            System.out.println(header);
            System.out.println(garage.get(i));
            System.out.println(footer);
        }
        pause();
    }

    // the protoype pattern

    private static void prototype() {
        List<RallyCar> garage = RallyCar.getGarage();

        RallyCar base;

        if (garage.isEmpty()) {
            // No cars built yet
            System.out.println(
                "  No cars in garage yet. Using default M-Sport Ford base.\n"
            );
            base = new RallyCar.Builder()
                .teamName("M-Sport Ford")
                .driverName("PROTOTYPE BASE")
                .engine(EngineType.RALLY)
                .tires(TireType.GRAVEL)
                .turboBoost(true)
                .build();
        } else {
            System.out.println("  Select a base car from the garage:\n");
            System.out.println(
                "  [0] Default - M-Sport Ford / Full Rally 2.0T / Gravel / Turbo"
            );
            for (int i = 0; i < garage.size(); i++) {
                System.out.printf(
                    "  [%d] %s / %s%n",
                    i + 1,
                    garage.get(i).getTeamName(),
                    garage.get(i).getDriverName()
                );
            }
            System.out.print("\n  Choice: ");
            int idx = parseIndex(sc.nextLine().trim(), garage.size());
            base = garage.get(idx);
        }

        System.out.println("\n  Base car being cloned:");
        System.out.println(base);

        PrototypeManager manager = new PrototypeManager(base);

        System.out.print("\n  Driver A name: ");
        String driverA = sc.nextLine().trim();
        RallyCar carA = manager.cloneForDriver(driverA);

        System.out.print("  Driver B name: ");
        String driverB = sc.nextLine().trim();
        RallyCar carB = manager.cloneForDriver(driverB);

        System.out.println("\n  === CLONED CAR - " + driverA + " ===");
        System.out.println(carA);

        System.out.println("\n  === CLONED CAR - " + driverB + " ===");
        System.out.println(carB);

        System.out.println(base);
        System.out.println();
        pause();
    }

    private static void pause() {
        System.out.print("\n  Press ENTER to return to the menu...");
        sc.nextLine();
    }

    private static int parseIndex(String input, int max) {
        try {
            int i = Integer.parseInt(input) - 1;
            if (i >= 0 && i < max) return i;
        } catch (NumberFormatException ignored) {}
        System.out.println("  Invalid choice, defaulting to first car.");
        return 0;
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
