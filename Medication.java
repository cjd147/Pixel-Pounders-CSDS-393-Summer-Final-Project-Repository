import java.util.HashMap;
import java.util.Map;

public class Medication extends MedicalData{

    //fields
    private Map<Class<? extends Animal>, Dosage> dosagesByAnimalType = new HashMap<>();


    //Getter/Setter
    public void setDosage(Class<? extends Animal> animalType, Dosage dosage) {
        dosagesByAnimalType.put(animalType, dosage);
    }

    public Dosage getDosage(Class<? extends Animal> animalType) {
        return dosagesByAnimalType.get(animalType);
    }


}
