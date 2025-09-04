package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import diet.Order.OrderStatus;


public class Restaurant {
	
	private String name;
	private Time open;
	private Time close;
	private List<WorkingHours> workingHours = new ArrayList<>();
	private Map<String, Menu> menus = new TreeMap<>();
	private List<Order> orders = new ArrayList<>();

	public Restaurant(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	
	public void setHours(String ... hm) {
		for (int i = 0; i < hm.length / 2; i++){
			workingHours.add(hm[2 * i], hm[2* i + 1]);
		}
	}

	public Time checkTime(Time t){
		Collections.sort(workingHours);
		for(WorkingHours w : workingHours){
			if(!w.includes(t)){
				return t;
			}
		}
		for(WorkingHours w : workingHours){
			if(w.getOpen().compareTo(t) > 0){
				return w.getOpen();
			}
		}
		return workingHours.get(0).getOpen();
	}

	
	public boolean isOpenAt(String time){
		
		for(WorkingHours wh : workingHours){
			if(wh.equals(time)){
				return true;
			}
		}
		
		return false;
	}

	
	public void addMenu(Menu menu) {
		menus.put(name, menu);
	}


	public Menu getMenu(String name) {
		return menus.get(name);
	}


	public String ordersWithStatus(OrderStatus status) {
		StringBuffer b = new StringBuffer();
		orders.sort(Comparator.comparing((Order o)->o.getName()).thenComparing(Order::getFirstName).thenComparing(Order::deliveryTime));
		for(Order o : orders){
			if(o.getStatus() == status){
				b.append(o);
			}
		}
		
		return b.toString();
	}

	public void addOrder(Order o){
		orders.add(o);
	}
}
