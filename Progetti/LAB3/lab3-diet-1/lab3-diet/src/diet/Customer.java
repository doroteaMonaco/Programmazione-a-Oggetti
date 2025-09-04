package diet;

import java.util.ArrayList;
import java.util.List;

public class Customer implements Comparable<Customer> {
	
	private String lastName, firstName, email, phone;
	private List<Order> orders = new ArrayList<>();


	public Customer(String firstName, String lastName, String email, String phone){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void SetEmail(String email) {
		this.email = email;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString(){
		return firstName + " " + lastName;
	}

	@Override
	public int compareTo(Customer o){
		int last = this.lastName.compareTo(o.getLastName());
		if(last == 0){
			return this.firstName.compareTo(o.getFirstName());
		}
		return last;
	}

	public void addOrder(Order o){
		orders.add(o);
	}
	
	
}
