public enum EngineType {
    SPEC("Spec Homologation 1.2T", -5, 2),
    STOCK("Stock 1.6T", 0, 5),
    PERFORMANCE("Perf 2.0T", 10, 12),
    RALLY("Full Rally 2.0T", 20, 8),
    HYBRID("Hybrid 2.0T+E", 25, 11);

    final String label;
    final int powerBonus;
    final int reliabilityRisk;

    EngineType(String label, int powerBonus, int reliabilityRisk) {
        this.label = label;
        this.powerBonus = powerBonus;
        this.reliabilityRisk = reliabilityRisk;
    }
}
