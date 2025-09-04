package diet;

public class RawMaterial extends Element {

    public RawMaterial(String name, double calories, double carbs, double proteins, double fat){
        super(name, calories, proteins, carbs, fat);
    }
    
    @Override
    public boolean per100g(){
        return true;
    }
    
}
