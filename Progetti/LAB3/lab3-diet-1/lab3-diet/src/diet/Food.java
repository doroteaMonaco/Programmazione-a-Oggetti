package diet;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

public class Food {

	private String name;
	private Double calories;
	private Double fat, proteins, carbs;

	private SortedMap<String, NutritionalElement> rawMaterials = new TreeMap<>();
	private SortedMap<String, NutritionalElement> products = new TreeMap<>();
	private SortedMap<String, NutritionalElement> recipes = new TreeMap<>();
	private SortedMap<String, NutritionalElement> menus = new TreeMap<>();


	public void defineRawMaterial(String name, double calories, double proteins, double carbs, double fat) {
		RawMaterial raw = new RawMaterial(name, calories, proteins,carbs, fat);
		rawMaterials.put(name, raw);
	}

	public Collection<NutritionalElement> rawMaterials() {
		return rawMaterials.values();
	}

	public NutritionalElement getRawMaterial(String name) {
		return rawMaterials.get(name);
	}

	public void defineProduct(String name, double calories, double proteins, double carbs, double fat) {
		Product prod = new Product(name, calories, proteins, carbs, fat);
		products.put(name, prod);
	}

	public Collection<NutritionalElement> products() {
		return products.values();
	}

	public NutritionalElement getProduct(String name) {
		return products.get(name);
	}

	public Recipe createRecipe(String name) {
		Recipe recipe = new Recipe(name, this);
		recipes.put(name, recipe);
		return recipe;
	}
	
	public Collection<NutritionalElement> recipes() {
		return recipes.values();
	}

	public NutritionalElement getRecipe(String name) {
		return recipes.get(name);
	}

	public Menu createMenu(String name, Food food) {
		Menu menu = new Menu(name, this);
		menus.put(name, menu);
		return menu;
	}
}