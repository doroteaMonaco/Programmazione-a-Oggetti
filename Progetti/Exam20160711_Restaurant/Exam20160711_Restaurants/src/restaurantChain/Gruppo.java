package restaurantChain;

public class Gruppo {
    
    Restaurant r;
    String nameRef;
    Integer numPersone;

    public Gruppo(String name, Integer numPersone){
        this.nameRef = name;
        this.numPersone = numPersone;
    }

    public String getName(){
        return nameRef;
    }

    public Integer getNumPersone(){
        return numPersone;
    }
}
