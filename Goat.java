public class Goat extends Animal{

    //fields

    private boolean hasHorns;



    //Constructor

    //Basic Constructor with name and sex
    public Goat(String nickname, boolean isFemale){
        setNickname(nickname);
        setFemale(isFemale);
        setAlive(true);
        setBreedable(true);

    }

    //getter/setters

    //hasHorns

    public boolean getHasHorns() {
        return hasHorns;
    }

    public void setHasHorns(boolean b) {this.hasHorns = b;}
}
