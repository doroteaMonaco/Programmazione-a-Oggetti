package restaurantChain;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InvalidNameException;

public class Chain {	


	List<Restaurant> restaurants = new ArrayList<>();
	

	public void addRestaurant(String name, int tables) throws InvalidName{
		boolean flag = false;

		for(Restaurant r: this.restaurants){
			if(!r.getName().equals(name)){
				flag = true;
				r = new Restaurant(name, tables);
				this.restaurants.add(r);
			}
		}

		if(flag == false){
			throw new InvalidName();
		}
	}
		
	public Restaurant getRestaurant(String name) throws InvalidName{

		boolean flag = false;
		Restaurant restaurant = null;

		for(Restaurant r: this.restaurants){
			if(r.getName().equals(name)){
				flag = true;
				restaurant = r;
			}
		}

		if(flag == false){
			throw new InvalidName();
		}
    	return restaurant;
	}
		
	public List<Restaurant> sortByIncome(){
		return null;
	}
		
	public List<Restaurant> sortByRefused(){
		return null;
	}
		
	public List<Restaurant> sortByUnusedTables(){
		return null;
	}
}
