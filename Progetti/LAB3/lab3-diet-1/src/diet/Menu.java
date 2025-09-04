package diet;

import java.util.ArrayList;
import java.util.List;

public class Menu implements NutritionalElement {

	private String name;
	private Food food = new Food();
	private List<NutritionalElement> rec;
	private List<NutritionalElement> prod;
	private double calories = 0.0, carbs = 0.0, fats = 0.0, proteins = 0.0;

	public Menu(String name){
		this.name = name;
		this.rec = new ArrayList<>();
		this.prod = new ArrayList<>();
	}

    public Menu addRecipe(String recipe, double quantity) {

		NutritionalElement element = this.food.getRecipe(recipe);
		this.rec.add(element);
		calories += (element.getCalories()*quantity);
		carbs += (element.getCarbs()*quantity);
		proteins += (element.getProteins()*quantity);
		fats += (element.getFat()*quantity);


		return this;
	}

	
    public Menu addProduct(String product) {

		NutritionalElement element = this.food.getProduct(product);
		this.prod.add(element);
		calories += element.getCalories();
		carbs += element.getCarbs();
		proteins += element.getProteins();
		fats += element.getFat();

		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		return calories;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		return proteins;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		return carbs;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		return fats;
	}

	
	@Override
	public boolean per100g() {
		return false;
	}
}