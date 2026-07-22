public class Horse extends Animal{

    //fields

    private String mane;
    private String tail;
    private boolean horseShoes;

    //Constructor

    //Basic Constructor with name and sex
    public Horse(String nickname, boolean isFemale){
        setNickname(nickname);
        setFemale(isFemale);
        setAlive(true);
        setBreedable(true);

    }

    //getter/setters

    //mane
    public String getMane() {return mane;}

    public void setMane(String s) {this.mane = s;}

    //tail

    public String getTail() {return tail;}

    public void setTail(String tail) {
        this.tail = tail;
    }

    //hasHorseShoes

    public boolean hasHorseShoes() {
        return horseShoes;
    }

    public void setHorseShoes(boolean horseShoes) {
        this.horseShoes = horseShoes;
    }

}
