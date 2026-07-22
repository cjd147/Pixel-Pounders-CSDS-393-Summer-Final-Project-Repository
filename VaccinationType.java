import java.util.List;

/**
 * VaccinationType represents an overall category of a specific vaccine. It is a grouping of the same
 * vaccination.
 */
public class VaccinationType {

    //fields

    //vaccineName
    private String vaccineName;

    //singleDose represents if it is a one time vaccine
    private boolean singleDose;

    //fullyGiven represents if the vaccine is no longer needed to be given to the animal ever.
    private boolean fullyGiven;

    /**doses represents the number of dosages given to the animal at the moment, with each dose being a
     * separate shot of the same type. This number is set to one if singleDose is true.
     */

    private int doses;

    /**totalDosesNeeded represents the number of dosages given to the animal needed to be completely vaccinated,
     * with each dose being a separate shot of the same type. This number is set to one if singleDose is
     * true.
     */

    private int totalDosesNeeded;

    //Represents the total times this specific vaccine has been given.
    private List<Vaccination> vaccinationList;

    //Constructor
    public VaccinationType(String vaccineName){
        this.vaccineName = vaccineName;

    }

    //Getters/Setters

    //vaccineName - Getter only as the name cannot be changed once created.

    public String getVaccineName() {
        return vaccineName;
    }

    //singleDose

    public boolean isSingleDose() {
        return singleDose;
    }

    public void setSingleDose(boolean singleDose) {
        this.singleDose = singleDose;
        if (singleDose){
            doses = 1;
            totalDosesNeeded = 1;
            this.setFullyGiven(true);
            this.setVaccinationList((List<Vaccination>) vaccinationList.getFirst());

        } else {
            setFullyGiven(false);
        }

    }

    //fullyGiven

    public boolean isFullyGiven() {
        return fullyGiven;
    }

    public void setFullyGiven(boolean fullyGiven) {
        this.fullyGiven = fullyGiven;
        if (fullyGiven){
            this.setDoses(this.getTotalDosesNeeded());
        }

    }


    //doses

    public int getDoses() {
        return doses;
    }

    public void setDoses(int doses) {
        this.doses = doses;
    }

    public void addDose(Vaccination v){
        this.doses = doses + 1;
        this.addVaccinationDose(v);
    }

    //totalDosesNeeded

    public int getTotalDosesNeeded() {
        return totalDosesNeeded;
    }

    public void setTotalDosesNeeded(int totalDosesNeeded) {
        this.totalDosesNeeded = totalDosesNeeded;
        if(totalDosesNeeded > 1){
            singleDose = false;
        }

    }

    //vaccinationList

    public List<Vaccination> getVaccinationList() {
        return vaccinationList;
    }

    public void setVaccinationList(List<Vaccination> vaccinationList) {
        this.vaccinationList = vaccinationList;
    }

    public void addVaccinationDose(Vaccination v){
        vaccinationList.add(v);
    }

    public void removeVaccinationDose(Vaccination v){
        vaccinationList.remove(v);
    }
    public void removeVaccinationDose(int i){
        vaccinationList.remove(i);
    }
}
