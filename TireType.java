public enum TireType {
    SNOW("Snow / Ice", 3, 10),
    ASPHALT("Asphalt Hard", 6, 9),
    INTER("Intermediate", 7, 7),
    GRAVEL("Gravel Soft", 8, 6),
    TARMAC("Tarmac Soft", 10, 12);

    final String label;
    final int gripBonus;
    final int wearPenalty;

    TireType(String label, int gripBonus, int wearPenalty) {
        this.label = label;
        this.gripBonus = gripBonus;
        this.wearPenalty = wearPenalty;
    }
}
