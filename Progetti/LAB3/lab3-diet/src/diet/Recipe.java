package diet;

import java.util.LinkedList;
import java.util.List;

public class Recipe implements NutritionalElement {
	

	private String name;
	private Food food;
	private double weight = 0.0;

	private List<Ingredient> ingredients = new LinkedList<>();

	private static class Ingredient {
		final NutritionalElement ne;
		final double qty;

		public Ingredient(NutritionalElement ne, double qty){
			this.ne = ne;
			this. qty = qty;
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
		weight += quantity;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	
	@Override
	public double getCalories() {
		double result = 0.0;
		for( Ingredient i : ingredients){
			result += i.ne.getCalories() / 100 * i.qty;
		}
		return result * 100 / weight;
	}
	

	@Override
	public double getProteins() {
		double result = 0.0;
		for( Ingredient i : ingredients){
			result += i.ne.getProteins() / 100 * i.qty;
		}
		return result * 100 / weight;
	}

	@Override
	public double getCarbs() {
		double result = 0.0;
		for( Ingredient i : ingredients){
			result += i.ne.getCarbs() / 100 * i.qty;
		}
		return result * 100 / weight;
	}

	@Override
	public double getFat() {
		double result = 0.0;
		for( Ingredient i : ingredients){
			result += i.ne.getFat() / 100 * i.qty;
		}
		return result * 100 / weight;
	}

	
	@Override
	public boolean per100g() {
		return true;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(Ingredient i : ingredients){
			sb.append(i.ne.getName()).append(":").append(String.format("%03.1f", i.qty)).append("\n");
	
		}
		return sb.toString();
	}
	
}
