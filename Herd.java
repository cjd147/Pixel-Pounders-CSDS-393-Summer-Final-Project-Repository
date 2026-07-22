import java.util.ArrayList;
import java.util.List;

public class Herd<T extends Animal> {

    //fields
    private final Class<T> animalType;
    private String herdName;
    private List<T> herdMembers;

    //Constructor
    public Herd(String herdName, Class<T> animalType) {
        this.herdName = herdName;
        this.animalType = animalType;
        this.herdMembers = new ArrayList<>();
    }

    //getters/setters

    //herdMembers
    public void add(T animal) {
        herdMembers.add(animal);
    }

    public List<T> getMembers() {
        return herdMembers;
    }

    //herdName

    public String getHerdName() {
        return herdName;
    }

    public void setHerdName(String herdName) {
        this.herdName = herdName;
    }

    //animalType - No setter because animal type cannot be changed once the herd is created
    public Class<T> getAnimalType() {
        return animalType;
    }

}
