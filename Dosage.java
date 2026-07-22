
public class Dosage {

    //fields
    private Measurement medicationAmount; // e.g. 11 ml
    private Measurement bodyWeightBasis;  // e.g. 10 lbs

    //Constructor
    public Dosage(Measurement medicationAmount, Measurement bodyWeightBasis) {
        this.medicationAmount = medicationAmount;
        this.bodyWeightBasis = bodyWeightBasis;
    }

    // Calculates how much medication is needed for a given animal weight
    public double calculateDose(double animalWeight) {
        return (medicationAmount.getAmount() / bodyWeightBasis.getAmount()) * animalWeight;
    }

    //Getters
    public Measurement getMedicationAmount() { return medicationAmount; }
    public Measurement getBodyWeightBasis() { return bodyWeightBasis; }
}