package bmr_afkari;

/**
 *
 * @author Younes
 */
public enum ActivityLevel {
    SEDENTARY("Sedentary"),
    INACTIVE("Inactive"),
    ACTIVE("Active"),
    VERY_ACTIVE("Very active"),
    EXTREMELY_ACTIVE("Extremely active");

    private String label;

    ActivityLevel(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
