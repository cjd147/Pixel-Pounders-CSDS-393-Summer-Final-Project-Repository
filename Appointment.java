
//Appointment Class represents veterinary appointments

import java.time.LocalDateTime;
import java.util.List;

public class Appointment{

    //fields

    //Veterinarian
    private String veterinarian;

    //Veterinary Practice
    private String veterinaryPractice;

    //Date and Time
    private LocalDateTime appointmentDate;

    //Notes
    private List<Note> notes;

    //Constructor

    public Appointment(String veterinaryPractice, LocalDateTime appointmentDate){
        this.veterinaryPractice = veterinaryPractice;
        this.appointmentDate = appointmentDate;

    }

    //Getter/Setters

    //veterinarian

    public String getVeterinarian() {
        return veterinarian;
    }

    public void setVeterinarian(String veterinarian) {
        this.veterinarian = veterinarian;
    }

    //veterinaryPractice

    public String getVeterinaryPractice() {
        return veterinaryPractice;
    }

    public void setVeterinaryPractice(String veterinaryPractice) {
        this.veterinaryPractice = veterinaryPractice;
    }

    //appointmentDate

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    //notes

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void addNote(Note note){
        notes.add(note);
    }

    //Other Methods


}
