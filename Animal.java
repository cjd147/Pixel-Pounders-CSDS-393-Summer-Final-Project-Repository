import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Animal {

    //General Fields

    //ID
    private String nickname;
    private String registeredName;
    private boolean sameName;
    private String idTag;
    private boolean registered;
    private int registeredID;

    //Sex
    private boolean isFemale;
    private boolean isBreedable;

    //Location
    private boolean isCurrentlyHere;
    private boolean alive;

    //Genealogy
    private Animal mother;
    private Animal father;
    private boolean isTwin;
    private Animal twin;
    private int yearBorn;
    private LocalDate birthdate;
    private List<Animal> children;

    private List<MedicalCondition> medicalConditionsList;
    private List<MedicalProcedure> medicalProceduresList;
    private List<Vaccination> vaccineRecord;

    private List<Medication> medicationList;
    private List<Dewormer> dewormerHistory;


    private String color;
    private String pattern;


    //Notes
    private final List<Note> notes = new ArrayList<>();

    //Getter/Setters

    //nickname Getter/Setter

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    //registeredName Getter/Setter

    public String getRegisteredName() {
        return registeredName;
    }

    public void setRegisteredName(String registeredName) {
        this.registeredName = registeredName;
    }

    //same name

    public boolean isSameName() {
        return sameName;
    }

    public void setSameNameVariable(boolean sameName) {
        this.sameName = sameName;
    }

    public void setSameName(boolean sameName) {
        this.sameName = sameName;
        if(sameName){
            registeredName = nickname;
        }
    }

    //idTag Getter/Setter

    public String getIdTag() {
        return idTag;
    }

    public void setIdTag(String idTag) {
        this.idTag = idTag;
    }

    //ifRegistered

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    //registeredID

    public int getRegisteredID() {
        return registeredID;
    }

    public void setRegisteredID(int registeredID) {
        this.registeredID = registeredID;
    }


    //isFemale Getter/Setter

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
    }

    //isBreedable Getter/Setter

    public boolean isBreedable() {
        return isBreedable;
    }

    public void setBreedable(boolean breedable) {
        isBreedable = breedable;
    }

    //isCurrentlyHere

    public boolean isCurrentlyHere() {
        return isCurrentlyHere;
    }

    public void setCurrentlyHere(boolean currentlyHere) {
        isCurrentlyHere = currentlyHere;
    }

    //alive

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
        if(!alive){
            setCurrentlyHere(false);
        }
    }

    //mother

    public Animal getMother() {
        return mother;
    }

    public void setMother(Animal mother) {
        this.mother = mother;
    }

    //father

    public Animal getFather() {
        return father;
    }

    public void setFather(Animal father) {
        this.father = father;
    }


    //isTwin

    public boolean isTwin() {
        return isTwin;
    }

    public void setTwin(boolean twin) {
        isTwin = twin;
    }

    //Twin

    public Animal getTwin() {
        if(isTwin) {
            return twin;
        }
        return null;
    }

    public void setTwin(Animal twin) {
        this.twin = twin;
        if(twin != null){
            setTwin(true);
            twin.setParents(this.getMother(), this.getFather());
        }
    }

    //yearBorn

    public int getYearBorn() {
        return yearBorn;
    }

    public void setYearBorn(int yearBorn) {
        this.yearBorn = yearBorn;
    }

    //Birthdate

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    //color

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //pattern

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    //Children list methods

    public List<Animal> getChildren() {
        return children;
    }

    public void setChildren(List<Animal> children) {
        this.children = children;
    }

    public void addChild(Animal child){
        children.add(child);
    }

    public int numChildren(){
        return children.size();
    }

    //MedicalData Methods

    //medicalConditionsList

    public List<MedicalCondition> getMedicalConditionsList() {
        return medicalConditionsList;
    }

    public void setMedicalConditionsList(List<MedicalCondition> medicalConditionsList) {
        this.medicalConditionsList = medicalConditionsList;
    }

    public void addMedicalCondition(MedicalCondition e) {medicalConditionsList.add(e);}

    public void removeMedicalCondition(MedicalCondition e) {medicalConditionsList.remove(e);}
    public void removeMedicalCondition(int i) {medicalConditionsList.remove(i);}

    //medicalProcedureList

    public List<MedicalProcedure> getMedicalProceduresList() {
        return medicalProceduresList;
    }

    public void setMedicalProceduresList(List<MedicalProcedure> medicalProceduresList) {
        this.medicalProceduresList = medicalProceduresList;
    }

    public void addMedicalProcedure(MedicalProcedure e) {medicalProceduresList.add(e);}

    public void removeMedicalProcedure(MedicalProcedure e) {medicalProceduresList.remove(e);}
    public void removeMedicalProcedure(int i) {medicalProceduresList.remove(i);}

    //vaccineRecord

    public List<Vaccination> getVaccineRecord() {return vaccineRecord;}

    public void setVaccineRecord(List<Vaccination> vaccineRecord) {
        this.vaccineRecord = vaccineRecord;
    }

    public void addVaccine(Vaccination e) {vaccineRecord.add(e);}

    public void removeVaccine(Vaccination e){vaccineRecord.remove(e);}
    public void removeVaccine(int i) {vaccineRecord.remove(i);}

    //medicationList

    public List<Medication> getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(List<Medication> medicationList) {
        this.medicationList = medicationList;
    }

    public void addMedication(Medication e) {medicationList.add(e);}

    public void removeMedication(Medication e) {medicationList.remove(e);}
    public void removeMedication(int i) {medicationList.remove(i);}

    //dewormerHistory

    public List<Dewormer> getDewormerHistory() {
        return dewormerHistory;
    }

    public void setDewormerHistory(List<Dewormer> dewormerHistory) {
        this.dewormerHistory = dewormerHistory;
    }

    public void addDewormer(Dewormer e) {dewormerHistory.add(e);}

    public void removeDewormer(Dewormer e) {dewormerHistory.remove(e);}
    public void removeDewormer(int i) {dewormerHistory.remove(i);}

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



    //Other Methods

    //Relationship methods

    //setParents sets both parents at the same time
    public void setParents(Animal mother, Animal father){
        this.setMother(mother);
        this.setFather(father);

        if (mother != null) mother.children.add(this);
        if (father != null) father.children.add(this);
    }

    //isSibling method checks to see if two Animals are siblings through either their just mother, father or both.
    //It does NOT differentiate between half and full siblings.
    public static boolean isSibling(Animal one, Animal two){
        if(one.getMother() == two.getMother()){
            return true;
        }
        return one.getFather() == two.getFather();
    }

    //isFullSibling method checks to see if the two given animals are full siblings
    public static boolean isFullSibling(Animal one, Animal two){
        return one.getMother() == two.getMother() && one.getFather() == two.getFather();
    }




}
