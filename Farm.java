import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

public class Farm {

    //fields

    //farmName
    private String farmName;

    //farmAddress
    private String farmAddress;

    //Herd List
    private List<Herd> farmHerds;

    //Appointment List
    private List<Appointment> vetAppointments;

    //Constructor
    public Farm(String s){
        this.farmName = s;
    }

    //Getter/Setters

    //farmName
    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    //farmAddress
    public String getFarmAddress() {
        return farmAddress;
    }

    public void setFarmAddress(String farmAddress) {
        this.farmAddress = farmAddress;
    }

    //Herd List
    public List<Herd> getFarmHerds() {
        return farmHerds;
    }

    public void setFarmHerds(List<Herd> farmHerds) {
        this.farmHerds = farmHerds;
    }

    public void addFarmHerd(Herd herd){
        farmHerds.add(herd);

    }

    public void deleteFarmHerd(Herd herd) {
        farmHerds.remove(herd);
    }

    //Veterinary Appointments

    public List<Appointment> getVetAppointments() {
        return vetAppointments;
    }

    //time based getter

    public List<Appointment> getAppointmentsBefore(LocalDate localDate){
        List<Appointment> before = List.of();
        for(int i = 0; i <= vetAppointments.size(); i++){
            if(localDate.isBefore(ChronoLocalDate.from(before.get(i).getAppointmentDate()))){
                before.addLast(vetAppointments.get(i));
            }
        }
        if(before.isEmpty()){
            return null;
        }
        return before;
    }

    public List<Appointment> getAppointmentsAfter(LocalDate localDate){
        List<Appointment> after = List.of();
        for(int i = 0; i <= vetAppointments.size(); i++){
            if(localDate.isAfter(ChronoLocalDate.from(after.get(i).getAppointmentDate()))){
                after.addLast(vetAppointments.get(i));
            }
        }
        if(after.isEmpty()){
            return null;
        }
        return after;
    }

    public void setVetAppointments(List<Appointment> vetAppointments) {
        this.vetAppointments = vetAppointments;
    }

    public void addVetAppointment(Appointment appointment){
        vetAppointments.add(appointment);
    }

    public void removeVetAppointment(Appointment appointment){
        vetAppointments.remove(appointment);
    }


}
