public class PrototypeManager {

    private final RallyCar baseConfig;

    public PrototypeManager(RallyCar baseConfig) {
        this.baseConfig = baseConfig;
    }

    public RallyCar cloneForDriver(String driverName) {
        RallyCar cloned = baseConfig.clone(); // true prototype clone
        cloned.setDriverName(driverName);
        return cloned;
    }

    public RallyCar getBase() {
        return baseConfig;
    }
}
