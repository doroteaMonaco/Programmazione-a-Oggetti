package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Facade class for the diet management.
 * It allows defining and retrieving raw materials and products.
 *
 */
public class Food {

	public static class RawMaterial implements NutritionalElement{
		String name;
		Double calories, proteins, carbs, fats;

		public RawMaterial(String name, double calories, double proteins, double fats, double carbs){
			this.name = name;
			this.calories = calories;
			this.proteins = proteins;
			this.carbs = carbs;
			this.fats = fats;
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
			return fats;
		}

		@Override
		public boolean per100g(){
			return true;
		}

	}

	List<NutritionalElement> rawMaterials = new ArrayList<>();
	
	public void defineRawMaterial(String name, double calories, double proteins, double carbs, double fat) {
		this.rawMaterials.add(new RawMaterial(name, calories, proteins, fat, carbs));
	}

	
	public Collection<NutritionalElement> rawMaterials() {

		Collection<NutritionalElement> ne = new ArrayList<>();

		for(NutritionalElement n : this.rawMaterials){
			ne.add(n);
		}
		return ne.stream().sorted(Comparator.comparing(NutritionalElement::getName)).toList();
	}


	public NutritionalElement getRawMaterial(String name) {

		for(NutritionalElement n: this.rawMaterials){
			if(n.getName().equals(name)){
				return n;
			}
		}
		return null;
	}

	public static class Product implements NutritionalElement{
		String name;
		Double calories, proteins, carbs, fats;

		public Product(String name, double calories, double proteins, double fats, double carbs){
			this.name = name;
			this.calories = calories;
			this.proteins = proteins;
			this.carbs = carbs;
			this.fats = fats;
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
			return fats;
		}

		@Override
		public boolean per100g(){
			return false;
		}

	}

	List<NutritionalElement> products = new ArrayList<>();

	public void defineProduct(String name, double calories, double proteins, double carbs, double fat) {
		Product p = new Product(name, calories, proteins, fat, carbs);
		this.products.add(p);

	}

	
	public Collection<NutritionalElement> products() {

		Collection<NutritionalElement> prodotti = new ArrayList<>();

		for(NutritionalElement ne: this.products){
			prodotti.add(ne);
		}

		return prodotti.stream().sorted(Comparator.comparing(NutritionalElement::getName)).toList();
	}

	
	public NutritionalElement getProduct(String name) {

		for(NutritionalElement ne: this.products){
			if(ne.getName().equals(name)){
				return ne;
			}
		}
		return null;
	}

	List<NutritionalElement> recipes = new ArrayList<>();

	public Recipe createRecipe(String name) {

		Recipe r = new Recipe(name);
		this.recipes.add(r);
		return r;
	}
	

	public Collection<NutritionalElement> recipes() {
		Collection<NutritionalElement> recipe = new ArrayList<>();

		for(NutritionalElement n: this.recipes){
			recipe.add(n);
		}
		return recipe.stream().sorted(Comparator.comparing(NutritionalElement::getName)).toList();
	}

	
	public NutritionalElement getRecipe(String name) {

		
		for(NutritionalElement n: this.recipes){
			if(n.getName().equals(name)){
				return n;
			}
		}
		return null;
	}

	List<NutritionalElement> menus = new ArrayList<>();

	public Menu createMenu(String name) {

		Menu m = new Menu(name);
		this.menus.add(m);
		return m;
	}
}