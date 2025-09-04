package diet;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Order {

	
	private Customer customer;
	private Restaurant restaurant;
	private Time deliveryTime;
	private PaymentMethod paymentMethod;
	private OrderStatus orderStatus;
	private SortedMap<Menu, Integer> menuOrder;


	public enum OrderStatus {
		ORDERED, READY, DELIVERED
	}

	
	public enum PaymentMethod {
		PAID, CASH, CARD
	}

	public Order(Customer customer, Restaurant restaurant, int h, int m){
		this.customer = customer;
		this.restaurant = restaurant;
		this.paymentMethod = PaymentMethod.CARD;
		this.orderStatus = OrderStatus.ORDERED;
		this.deliveryTime = restaurant.checkTime(new Time(h, m));
		this.menuOrder = new TreeMap<Menu, Integer>(Comparator.comparing(Menu::getName));
	}


	public void setPaymentMethod(PaymentMethod pm) {
		this.paymentMethod = pm;
	}

	
	public PaymentMethod getPaymentMethod() {
		return this.paymentMethod;
	}

	
	public void setStatus(OrderStatus os) {
		this.orderStatus = os;
	}

	
	public OrderStatus getStatus() {
		return this.orderStatus;
	}

	
	public Order addMenus(String menu, int quantity) {
		Menu m = restaurant.getMenu(menu);
		menuOrder.put(m, quantity);
		return this;
	}

	@Override
	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append(restaurant.getName()).append(",").append(customer.getFirstName()).append(customer.getLastName()).append(": ()").append(deliveryTime).append("):");
		for(Map.Entry<Menu, Integer> m : menuOrder.entrySet()){
			b.append("\t").append(m.getKey().getName()).append("->").append(m.getValue().toString()).append("\n");
		}
		return b.toString();
	}
	
}
