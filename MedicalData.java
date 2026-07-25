import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class MedicalData {

    //fields

    //name
    private String name;

    //diagnoseDate represents either the date diagnosed or the date administered depending on the type of data.
    private LocalDate diagnoseDate;

    //Notes
    private final List<Note> notes = new ArrayList<>();


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

    //Notes
    public void addNote(String author, String text) {
        notes.add(new Note(author, text));
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes); // return a copy so callers can't mutate internals
    }

    public void printNotes() {
        notes.forEach(System.out::println);
    }

}


