
public class Measurement {

    //fields
    private double amount;
    private String unit; // e.g. "ml", "mg", "lbs", "kg"

    //Constructor
    public Measurement(double amount, String unit) {
        this.amount = amount;
        this.unit = unit;
    }

    //Getters
    public double getAmount() { return amount; }
    public String getUnit() { return unit; }

    //Other Methods
    @Override
    public String toString() {
        return amount + " " + unit;
    }
}
