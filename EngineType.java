public enum EngineType {
    STOCK("Stock 1.6T", 0, 5),
    PERFORMANCE("Perf 2.0T", 10, 12),
    RALLY("Full Rally 2.0T", 20, 8);

    final String label;
    final int powerBonus;
    final int reliabilityRisk;

    EngineType(String label, int powerBonus, int reliabilityRisk) {
        this.label = label;
        this.powerBonus = powerBonus;
        this.reliabilityRisk = reliabilityRisk;
    }
}
