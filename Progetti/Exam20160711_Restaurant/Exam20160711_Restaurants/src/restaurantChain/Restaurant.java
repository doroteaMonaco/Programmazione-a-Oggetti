package restaurantChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Restaurant {
	
	String name;
	Integer numTables;
	List<Menu> menus = new ArrayList<>();
	Map<Restaurant, Gruppo> gruops = new TreeMap<>();
	List<Restaurant> restaurantsAggiornata = new ArrayList<>();

	public Restaurant(String name, int numTables){
		this.name = name;
		this.numTables = numTables;
	}

	public String getName(){
		return name;
	}

	public Integer getTables(){
		return numTables;
	}
	
	public void addMenu(String name, double price) throws InvalidName{

		boolean flag = false;

		for(Menu m: this.menus){
			if(!m.getName().equals(name)){
				flag = true;
				m = new Menu(name, price);
				this.menus.add(m);
			}
		}

		if(flag == false){
			throw new InvalidName();
		}

	}
	
	public int reserve(String name, int persons) throws InvalidName{

		boolean flag = false;
		int numTavoli = 0;

		for(Map.Entry<Restaurant, Gruppo> entry: gruops.entrySet()){
			if(!entry.getValue().equals(name)){
				flag = true;
				Gruppo g = new Gruppo(name, numTables);
				gruops.put(entry.getKey(), g);
			}
		}

		if(flag == false){
			throw new InvalidName();
		}

		for(Map.Entry<Restaurant, Gruppo> entry: gruops.entrySet()){
			if(entry.getValue().equals(name)){
				if(entry.getValue().getNumPersone() > 4){
					numTavoli = (entry.getValue().getNumPersone() % 4);
				}
				else{
					numTavoli = 1;
				}
			}
			aggiornaTavoli(entry.getKey(), numTavoli);
		}

		return numTavoli;
	}


	private void aggiornaTavoli(Restaurant rist, int numTavoli) {
		int num = 0;
		for(Restaurant r: restaurantsAggiornata){
			if(r.getName().equals(rist.getName())){
				num = rist.getTables() - numTavoli;
				Restaurant re = new Restaurant(rist.getName(), num);
				restaurantsAggiornata.add(re);
			}
		}
	}

	public int getRefused(){

		int contRefesud = 0;

		for(Restaurant r: restaurantsAggiornata){
			if(r.getTables() == 0){
				contRefesud++;
			}
		}
		return contRefesud;
	}
	
	public int getUnusedTables(){

		int contUnused = 0;

		for(Restaurant r: restaurantsAggiornata){
			if(r.getTables() == 0){
				contUnused++;
			}
		}
		return contUnused;
	}
	
	public boolean order(String name, String... menu) throws InvalidName{
		return false;
	}
	
	public List<String> getUnordered(){
		return null;
	}
	
	public double pay(String name) throws InvalidName{
		return -1.0;
	}
	
	public List<String> getUnpaid(){
		return null;
	}
	
	public double getIncome(){
		return -1.0;
	}

}
