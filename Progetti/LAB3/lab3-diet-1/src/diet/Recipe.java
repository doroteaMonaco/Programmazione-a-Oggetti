package diet;

import java.util.ArrayList;
import java.util.List;

import diet.Food.RawMaterial;


public class Recipe implements NutritionalElement {
	

	private String nameRecipe;
	private List<NutritionalElement> ingredients;
	private Food food = new Food();
	private double calories = 0.0, carbs = 0.0, fats = 0.0, proteins = 0.0, totQuantity = 0.0;

	public Recipe(String name){
		this.nameRecipe = name;
		this.ingredients = new ArrayList<>();
	}
	
	public Recipe addIngredient(String material, double quantity) {

		NutritionalElement element = this.food.getRawMaterial(material);
		this.ingredients.add(element);
		calories += (element.getCalories()*quantity)/100;
		carbs += (element.getCarbs()*quantity)/100;
		proteins += (element.getProteins()*quantity)/100;
		fats += (element.getFat()*quantity)/100;
		totQuantity += quantity;

		return this;
	}

	@Override
	public String getName() {
		return this.nameRecipe;
	}

	
	@Override
	public double getCalories() {
		return (calories*totQuantity)/100;
	}
	

	@Override
	public double getProteins() {
		return (proteins*totQuantity)/100;
	}

	@Override
	public double getCarbs() {
		return (carbs*totQuantity)/100;
	}

	@Override
	public double getFat() {
		return (fats*totQuantity)/100;
	}

	@Override
	public boolean per100g() {
		return true;
	}
	
}
