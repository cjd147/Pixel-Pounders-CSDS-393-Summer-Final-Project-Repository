import java.time.LocalDate;

/**
 * Vaccination represents a specific vaccination given at one point (a single shot). There can be multiple
 * of the same one, which is represented in VaccinationType.
 */
public class Vaccination extends MedicalData{

    //fields

    //vaccineType
    private VaccinationType vaccinationType;

    //Constructor

    public Vaccination(VaccinationType vaccinationType, LocalDate diagnoseDate){
        this.vaccinationType = vaccinationType;
        this.setDiagnoseDate(diagnoseDate);

    }

    //Getters/Setters

    //vaccinationType

    public VaccinationType getVaccinationType(){
        return vaccinationType;
    }

    public void setVaccinationType(VaccinationType vaccinationType) {
        this.vaccinationType = vaccinationType;
    }

}
