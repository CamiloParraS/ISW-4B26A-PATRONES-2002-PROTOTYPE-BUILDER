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
                case "4" -> runStage();
                case "5" -> {
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
                [1] Build a New Team           (Builder)
                [2] Clone Setup for New Driver (Prototype, Add a driver for a Team)
                [3] View Garage / Roster
                [4] Run Stage                  Run a stage with a car from the garage
                [5] Exit
            """
        );
        System.out.print(" Choice: ");
    }

    // === Builder Pattern ======================
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

    // == GARAGE ======================

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

    // === the protoype pattern ======================

    private static void prototype() {
        List<RallyCar> garage = RallyCar.getGarage();
        RallyCar base;

        if (garage.isEmpty()) {
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
            System.out.println("  Select a base car to clone:\n");
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
            String input = sc.nextLine().trim();

            if (input.equals("0")) {
                base = new RallyCar.Builder()
                    .teamName("M-Sport Ford")
                    .driverName("PROTOTYPE BASE")
                    .engine(EngineType.RALLY)
                    .tires(TireType.GRAVEL)
                    .turboBoost(true)
                    .build();
            } else {
                int idx = parseIndex(input, garage.size());
                base = garage.get(idx);
            }
        }

        System.out.println("\n  Base car:");
        System.out.println(base);

        PrototypeManager manager = new PrototypeManager(base);

        System.out.print("\n  New driver name: ");
        String driverName = sc.nextLine().trim();

        RallyCar cloned = manager.cloneForDriver(driverName);
        RallyCar.addToGarage(cloned);

        System.out.println("\n  === CAR CLONED SUCCESSFULLY ===");
        System.out.println(cloned);
        System.out.println();
        pause();
    }

    // === THE STAGE (YESSSSSSSSSSSSSSSSSSSSSs)

    private static void runStage() {
        List<RallyCar> garage = RallyCar.getGarage();
        if (garage.isEmpty()) {
            System.out.println(
                "\n  No cars in garage yet. Build or clone a car first.\n"
            );
            pause();
            return;
        }

        System.out.println("\n  Select a car to run the stage:\n");
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
        RallyCar car = garage.get(idx);

        StageRunner.StageResult result = StageRunner.run(car, "Monte Carlo");

        System.out.println("\n  === STAGE RESULT ===");
        System.out.println(result);
        pause();
    }

    // === EXTRA ===

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
            [1] Spec Homologation 1.2T  (minimum legal spec, very reliable, low power)
            [2] Stock 1.6T              (safe, low power)
            [3] Performance 2.0T        (balanced)
            [4] Full Rally 2.0T         (max power, higher risk)
            [5] Hybrid 2.0T+E           (highest power, moderate risk)"""
        );
        System.out.print("  Choice: ");
        return switch (sc.nextLine().trim()) {
            case "1" -> EngineType.SPEC;
            case "3" -> EngineType.PERFORMANCE;
            case "4" -> EngineType.RALLY;
            case "5" -> EngineType.HYBRID;
            default -> EngineType.STOCK;
        };
    }

    private static TireType pickTires() {
        System.out.println(
            """
            Select tires:
             [1] Snow / Ice      (very low grip, high wear — winter stages)
             [2] Asphalt Hard    (low grip, durable)
             [3] Intermediate    (balanced across all surfaces)
             [4] Gravel Soft     (good grip, moderate wear)
             [5] Tarmac Soft     (maximum grip, very high wear)"""
        );
        System.out.print("  Choice: ");
        return switch (sc.nextLine().trim()) {
            case "1" -> TireType.SNOW;
            case "2" -> TireType.ASPHALT;
            case "3" -> TireType.INTER;
            case "5" -> TireType.TARMAC;
            default -> TireType.GRAVEL;
        };
    }

    private static boolean pickTurbo() {
        System.out.print("  Enable Turbo Boost? [y/n]: ");
        return sc.nextLine().trim().equalsIgnoreCase("y");
    }
}
