import java.util.List;

public class Farm {

    //fields

    //farmName
    private String farmName;

    //farmAddress
    private String farmAddress;

    //Herd List
    private List<Herd> farmHerds;

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

}
