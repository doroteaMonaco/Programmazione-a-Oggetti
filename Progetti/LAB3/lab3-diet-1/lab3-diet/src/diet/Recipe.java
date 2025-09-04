package diet;

import java.util.LinkedList;
import java.util.List;

public class Recipe implements NutritionalElement {

	private String name; 
	private Food food;
	private double weigth = 0.0;

	private List<Ingredient> ingredients = new LinkedList<>();
	
	private static class Ingredient{
		NutritionalElement ne;
		double quantity;

		public Ingredient(NutritionalElement ne, double quantity){
			this.ne = ne;
			this.quantity = quantity;
		}
	}

	public Recipe(String name, Food food){
		this.name = name;
		this.food = food;
	}

	public Recipe addIngredient(String material, double quantity) {
		NutritionalElement raw = food.getRawMaterial(material);
		Ingredient ing = new Ingredient(raw, quantity);
		ingredients.add(ing);
		weigth += quantity;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	
	@Override
	public double getCalories() {
		double result = 0.0;
		for(Ingredient i : ingredients){
			result += i.ne.getCalories() / 100 * i.quantity;
		}
		return result;
	}
	

	@Override
	public double getProteins() {
		double result = 0.0;
		for(Ingredient i : ingredients){
			result += i.ne.getProteins() / 100 * i.quantity;
		}
		return result;
	}

	@Override
	public double getCarbs() {
		double result = 0.0;
		for(Ingredient i : ingredients){
			result += i.ne.getCarbs() / 100 * i.quantity;
		}
		return result;
	}

	@Override
	public double getFat() {
		double result = 0.0;
		for(Ingredient i : ingredients){
			result += i.ne.getFat() / 100 * i.quantity;
		}
		return result;
	}

	@Override
	public boolean per100g() {
		return true;
	}
	
}
