public class PrototypeManager {

    private final RallyCar baseConfig;

    public PrototypeManager(RallyCar baseConfig) {
        this.baseConfig = baseConfig;
    }

    public RallyCar cloneForDriver(String driverName) {
        return new RallyCar.Builder()
            .teamName(baseConfig.getTeamName())
            .driverName(driverName)
            .engine(baseConfig.getEngine())
            .tires(baseConfig.getTires())
            .turboBoost(baseConfig.hasTurbo())
            .build();
    }

    public RallyCar getBase() {
        return baseConfig;
    }
}
