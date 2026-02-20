public enum TireType {
    GRAVEL("Gravel Soft", 8, 6),
    ASPHALT("Asphalt Hard", 6, 9);

    final String label;
    final int gripBonus;
    final int wearPenalty;

    TireType(String label, int gripBonus, int wearPenalty) {
        this.label = label;
        this.gripBonus = gripBonus;
        this.wearPenalty = wearPenalty;
    }
}
