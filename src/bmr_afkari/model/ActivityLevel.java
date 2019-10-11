package bmr_afkari.model;

/**
 *
 * @author 52196
 */
public enum ActivityLevel {
    SEDENTARY("Sedentary", 1.2),
    INACTIVE("Inactive", 1.375),
    ACTIVE("Active", 1.55),
    VERY_ACTIVE("Very active", 1.725),
    EXTREMELY_ACTIVE("Extremely active", 1.9);

    private String label;
    private double factor;

    ActivityLevel(String label, double factor) {
        this.label = label;
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }

    public String toString() {
        return label;
    }
}
