package diet;

public class Element implements NutritionalElement {

    private String name;
    private Double proteins, carbs, fat, calories;

    public Element(String name, Double calories, Double proteins, Double carbs, Double fat){
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fat = fat;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public double getProteins(){
        return proteins;
    }

    @Override
    public double getCalories(){
        return calories;
    }

    @Override
    public double getFat(){
        return fat;
    }

    @Override
    public double getCarbs(){
        return carbs;
    }

    @Override
    public boolean per100g(){
        return false;
    }
    
}
