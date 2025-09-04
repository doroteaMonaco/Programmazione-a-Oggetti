package diet;


public interface NutritionalElement {
	
	String getName();
	
	
	double getCalories();
	
	
	double getProteins();
	
	
	double getCarbs();
	
	
	double getFat();

	
	boolean per100g();

	default double getCalories(double quantity){
		return getCalories() * quantity / (per100g() ? 100 : 1);
	}

	default double getProteins(double quantity){
		return getCalories() * quantity / (per100g() ? 100 : 1);
	}

	default double getFat(double quantity){
		return getCalories() * quantity / (per100g() ? 100 : 1);
	}

	default double getCarbs(double quantity){
		return getCalories() * quantity / (per100g() ? 100 : 1);
	}

}