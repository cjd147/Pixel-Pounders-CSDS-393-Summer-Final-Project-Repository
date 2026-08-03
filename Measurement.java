import java.util.List;

public class Measurement {

    //fields
    private double amount;
    private String unit; // e.g. "ml", "mg", "lbs", "kg"
    private List<UnitEquivalent> equivalents;

    //Constructor
    public Measurement(double amount, String unit) {
        this.amount = amount;
        this.unit = unit;
    }

    //Getters
    public double getAmount() { return amount; }
    public String getUnit() { return unit; }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    //equivalents methods

    public List<UnitEquivalent> getEquivalents() {
        return equivalents;
    }

    public void setEquivalents(List<UnitEquivalent> equivalents) {
        this.equivalents = equivalents;
    }

    public void addEquivalent(UnitEquivalent unitEquivalent){
        equivalents.add(unitEquivalent);

    }

    public void removeEquivalent(UnitEquivalent unitEquivalent){
        equivalents.remove(unitEquivalent);
    }



    //Other Methods

    //Helper Methods

    //containsEquivalentUnit is a Helper Methods to search the equivalents list for the given unit
    //and returns true if it is.
    private boolean containsEquivalentUnit(String unit){
        for(int i = 0; i <= equivalents.size(); i++){
            if(unit.equals(equivalents.get(i).getUnitName())){
                return true;
            }
        }
        return false;
    }

    //searchEquivalentUnitIndex is a Helper Method to search for the given unit and returns the index of
    //the given unit if it is there
    private int searchEquivalentsUnitIndex(String unit){
        if(containsEquivalentUnit(unit)){
            for(int i = 0; i <= equivalents.size(); i++){
                if(unit.equals(equivalents.get(i).getUnitName())){
                    return i;
                }
            }

        }
        return -1;
    }

    //Overwritten Methods

    @Override
    public String toString() {
        return amount + " " + unit;
    }


    public boolean equals(Measurement measurement) {
        if(this.amount == measurement.getAmount() && this.unit.equals(measurement.getUnit())){
            return true;
        } else if (containsEquivalentUnit(measurement.getUnit())){
            if ((measurement.getAmount() * equivalents.get(searchEquivalentsUnitIndex(measurement.getUnit())).getConversionRate()) == amount){
                return true;
            }
        }
            return false;
    }

    //Nested Class UnitEquivalent represents equivalent values for commonly used dosages.
    public static class UnitEquivalent{

        //fields
        private String unitName;
        private double conversionRate;

        //Constructor
        public UnitEquivalent(String unitName, double conversionRate){
            this.unitName = unitName;
            this.conversionRate = conversionRate;

        }

        //Getter/Setters

        //UnitName
        public String getUnitName(){
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        //conversionRate
        public double getConversionRate() {
            return conversionRate;
        }

        public void setConversionRate(double conversionRate) {
            this.conversionRate = conversionRate;
        }


    }
}
