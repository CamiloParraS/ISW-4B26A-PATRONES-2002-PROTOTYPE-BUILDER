import java.util.ArrayList;
import java.util.List;

public class RallyCar implements Cloneable {

    private static final List<RallyCar> garage = new ArrayList<>();

    public static void addToGarage(RallyCar car) {
        garage.add(car);
    }

    public static List<RallyCar> getGarage() {
        return garage;
    }

    private String teamName;
    private String driverName;
    private EngineType engine;
    private TireType tires;
    private boolean turboBoost;

    private RallyCar() {}

    public String getTeamName()  { return teamName;   }
    public String getDriverName(){ return driverName; }
    public EngineType getEngine(){ return engine;     }
    public TireType getTires()   { return tires;      }
    public boolean hasTurbo()    { return turboBoost; }

    public void setDriverName(String name) {
        this.driverName = name;
    }

    @Override
    public RallyCar clone() {
        try {
            return (RallyCar) super.clone(); 
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone failed", e);
        }
    }

    @Override
    public String toString() {
        return String.format(
            "  Team   : %s%n  Driver : %s%n  Engine : %s%n  Tires  : %s%n  Turbo  : %s",
            teamName, driverName, engine.label, tires.label, turboBoost ? "YES" : "NO"
        );
    }

    public static class Builder {

        private final RallyCar car = new RallyCar();

        public Builder teamName(String name)  { car.teamName   = name; return this; }
        public Builder driverName(String name){ car.driverName = name; return this; }
        public Builder engine(EngineType e)   { car.engine     = e;    return this; }
        public Builder tires(TireType t)      { car.tires      = t;    return this; }
        public Builder turboBoost(boolean tb) { car.turboBoost = tb;   return this; }

        public RallyCar build() {
            if (car.teamName   == null) car.teamName   = "Unknown Team";
            if (car.driverName == null) car.driverName = "Unknown Driver";
            if (car.engine     == null) car.engine     = EngineType.STOCK;
            if (car.tires      == null) car.tires      = TireType.GRAVEL;
            return car;
        }
    }
}