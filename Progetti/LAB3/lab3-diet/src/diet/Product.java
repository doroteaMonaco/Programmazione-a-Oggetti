package diet;

public class Product extends  Element {

    public Product(String name, double calories, double carbs, double proteins, double fat){
        super(name, calories, proteins, carbs, fat);
    }
    
    @Override
    public boolean per100g(){
        return false;
    
}
