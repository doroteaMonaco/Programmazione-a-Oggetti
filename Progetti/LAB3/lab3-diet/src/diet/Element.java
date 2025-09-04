package diet;

public abstract class Element implements NutritionalElement {

    private String name;
    private double calories;
    private double carbs;
    private double fat;
    private double proteins;

    public Element(String name, double calories, double proteins, double carbs, double fat){
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.proteins = proteins;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public double getCalories(){
        return calories;
    }

    @Override
    public double getCarbs(){
        return carbs;
    }

    @Override
    public double getProteins(){
        return proteins;
    }

    @Override
    public double getFat(){
        return fat;
    }
    
}
