import java.time.LocalDate;

public class Sheep extends Animal{

    //fields

    private String woolType;
    private boolean hasHorns;



    //Constructor

    //Basic Constructor with name and sex
    public Sheep(String nickname, boolean isFemale){
        setNickname(nickname);
        setFemale(isFemale);
        setAlive(true);
        setBreedable(true);

    }

    //getter/setters

    //woolType

    public String getWoolType(){return woolType;}

    public void setWoolType(String woolType) {
        this.woolType = woolType;
    }

    //hasHorns

    public boolean getHasHorns() {
        return hasHorns;
    }

    public void setHasHorns(boolean b) {this.hasHorns = b;}


}
