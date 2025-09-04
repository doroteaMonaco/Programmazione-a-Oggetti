package diet;


public class Menu implements NutritionalElement {


	private String name;
	private Food food;
	private double fat = 0.0, calories = 0.0, proteins = 0.0, carbs = 0.0;

	public Menu(String name, Food food){
		this.name = name;
		this.food = food;
	}
	
    public Menu addRecipe(String recipe, double quantity) {

		NutritionalElement n = food.getRecipe(recipe);
		calories += n.getCalories() / 100 * quantity;
		proteins += n.getProteins() / 100 * quantity;
		carbs += n.getCarbs() / 100 * quantity;
		fat += n.getFat() / 100 * quantity;

		return this;
	}

	
    public Menu addProduct(String product) {
		NutritionalElement n = food.getProduct(product);
		calories += n.getCalories();
		proteins += n.getProteins();
		carbs += n.getCarbs();
		fat += n.getFat();

		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	
	@Override
	public double getCalories() {
		return calories;
	}

	
	@Override
	public double getProteins() {
		return proteins;
	}

	@Override
	public double getCarbs() {
		return carbs;
	}

	
	@Override
	public double getFat() {
		return fat;
	}

	@Override
	public boolean per100g() {
		return false;
	}
}