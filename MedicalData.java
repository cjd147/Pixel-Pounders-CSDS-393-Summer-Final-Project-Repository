import java.time.LocalDate;

public abstract class MedicalData {

    //fields

    //name
    private String name;

    //diagnoseDate represents either the date diagnosed or the date administered depending on the type of data.
    private LocalDate diagnoseDate;

    //Getter/Setters

    //name

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //diagnoseDate

    public LocalDate getDiagnoseDate() {
        return diagnoseDate;
    }

    public void setDiagnoseDate(LocalDate diagnoseDate) {
        this.diagnoseDate = diagnoseDate;
    }

}


