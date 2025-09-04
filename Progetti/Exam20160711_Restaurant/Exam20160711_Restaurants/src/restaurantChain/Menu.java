package restaurantChain;

public class Menu {
    String name;
    Double prezzo;

    public Menu(String name, Double prezzo){
        this.name = name;
        this.prezzo = prezzo;
    }

    public String getName(){
        return name;
    }

    public Double getPrezzo(){
        return prezzo;
    }    
}
