package diet;

import java.util.*;

import javax.swing.plaf.metal.MetalBorders.OptionDialogBorder;



public class Takeaway {

	private Food food;
	private int h, m;
	private Map<String, Restaurant> restaurants = new TreeMap<>();
	private Collection<Customer> customers = new ArrayList<>();

	public Takeaway(Food food){
		this.food = food;
	}

	public Restaurant addRestaurant(String restaurantName) {
		Restaurant r = new Restaurant(restaurantName);
		restaurants.put(restaurantName, r);
		return r;
	}

	
	public Collection<String> restaurants() {
		return restaurants.keySet() ;
	}

	
	public Customer registerCustomer(String firstName, String lastName, String email, String phoneNumber) {
		Customer c = new Customer(firstName, lastName, email, phoneNumber);
		customers.add(c);
		return c;
	}


	public Collection<Customer> customers(){
		List<Customer> c = new ArrayList<>();
		Collections.sort(c);
		return c;
	}

	public Order createOrder(Customer customer, String restaurantName, String time) {
		Restaurant restaurant = new Restaurant(restaurantName);
		Order order = new Order(customer, restaurant, h, m);
		restaurant.addOrder(order);
		customer.addOrder(order);
		return order;
	}

	
	public Collection<Restaurant> openRestaurants(String time){
		List<Restaurant> opRestaurant = new ArrayList<>();
		Time t = new Time(time);
		for(Restaurant r : restaurants.values()){
			if(r.checkTime(t) == t){
				opRestaurant.add(r);
			}
		}
		Collections.sort(opRestaurant);
		return opRestaurant;
	}
		
}
