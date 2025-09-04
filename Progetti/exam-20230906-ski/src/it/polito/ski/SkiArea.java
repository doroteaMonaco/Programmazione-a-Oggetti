package it.polito.ski;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SkiArea {

	
	String name;
	Map<String, Impianto> impianti = new TreeMap<>();
	Map<String, Impianto> impiantiRisalita = new TreeMap<>();
	Map<String, Slope> piste = new TreeMap<>();
	Map<String, Parking> parcheggi = new TreeMap<>();
	Map<String, Set<String>> parcheggiImpianti = new TreeMap<>();

	public static class Impianto{
		String code, category;
		int capacity;
		Map<String, Impianto> impRisal;
		

		public Impianto(String code, String categoty, int capacity){
			this.code = code;
			this.category = categoty;
			this.capacity = capacity;

		}

		public String getCode(){
			return code;
		}

		public String getCategory(){
			return category;
		}

		public int getCapacity(){
			return capacity;
		}

	}

	public static class Slope{

		String name;
		String difficulty;
		Impianto i;

		public Slope(String name, String difficulty, Impianto i){
			this.name = name;
			this.difficulty = difficulty;
			this.i = i;
		}

		public String getName(){
			return name;
		}

		public String getDifficulty(){
			return difficulty;
		}

		public Impianto getImpianto(){
			return i;
		}
	}

	public static class Parking{
		
		String name;
		int capacity;

		public Parking(String name, int capacity){
			this.name = name;
			this.capacity = capacity;
		}

		public String getName(){
			return name;
		}

		public int getCapacity(){
			return capacity;
		}
	}

	public SkiArea(String name){
		this.name = name;
	}

	public String getName() {
		return name; 
	}


    public void liftType(String code, String category, int capacity) throws InvalidLiftException {

		InvalidLiftException ile = new InvalidLiftException();

		if(this.impianti.containsKey(code)){
			throw ile;
		}

		this.impianti.put(code, new Impianto(code, category, capacity));
    }
    
    public String getCategory(String typeCode) throws InvalidLiftException {

		String category = null;
		
		if(!this.impianti.containsKey(typeCode)){
			throw new InvalidLiftException();
		}

		Impianto i = this.impianti.get(typeCode);
		category = i.getCategory();

		return category;
    }

   
    public int getCapacity(String typeCode) throws InvalidLiftException {

		int capacity = 0;

		if(!this.impianti.containsKey(typeCode)){
			throw new InvalidLiftException();
		}

		Impianto i = this.impianti.get(typeCode);
		capacity = i.getCapacity();

        return capacity;
    }


	public Collection<String> types() {

		Collection<String> tipi = new ArrayList<>();

		for(Map.Entry<String, Impianto> entry: this.impianti.entrySet()){
			tipi.add(entry.getValue().getCategory());
		}

		return tipi;
	}
	
    public void createLift(String name, String typeCode) throws InvalidLiftException{

		if(!this.impianti.containsKey(typeCode)){
			throw new InvalidLiftException();
		}

		Impianto i = new Impianto(typeCode, this.impianti.get(typeCode).getCategory(), this.impianti.get(typeCode).getCapacity());
		this.impiantiRisalita.put(name, i);
    }
    
	public String getType(String lift) {

		String tipo = null;

		Impianto i = this.impiantiRisalita.get(lift);
		tipo = i.getCode();
		
		return tipo;
	}

	
	public List<String> getLifts(){

		List<String> nomi = new ArrayList<>();

		for(Map.Entry<String, Impianto> entry: this.impiantiRisalita.entrySet()){
			nomi.add(entry.getKey());
		}

		Comparator<String> cmp = Comparator.comparing(s -> s.toString());

		return nomi.stream().sorted(cmp).toList();
    }


    public void createSlope(String name, String difficulty, String lift) throws InvalidLiftException {

		Impianto i = this.impiantiRisalita.get(lift);

		if(i == null){
			throw new InvalidLiftException();
		}

		this.piste.put(lift, new Slope(name, difficulty, i));

    }
    
    
	public String getDifficulty(String slopeName) {

		String difficulty = null;

		Slope s = this.piste.get(slopeName);
		difficulty = s.getDifficulty();
		
		return difficulty;
	}

	
	public String getStartLift(String slopeName) {

		String nomeImpianto = null;

		Slope s = this.piste.get(slopeName);
		nomeImpianto = s.getImpianto().getCode();

		return nomeImpianto;
	}

	
    public Collection<String> getSlopes(){

		Collection<String> slopes = new ArrayList<>();

		for(Map.Entry<String, Slope> p: this.piste.entrySet()){
			slopes.add(p.getValue().toString());
		}

		return slopes;
    }

    
    public Collection<String> getSlopesFrom(String lift){

		Collection<String> slopes = new ArrayList<>();

		for(Slope slope : this.piste.values()){
			if(slope.getImpianto().getCode().equals(lift)){
				slopes.add(slope.getName());
			}
		}

		return slopes;
    }

 
    public void createParking(String name, int slots){

		for(Map.Entry<String, Parking> p: this.parcheggi.entrySet()){
			if(!p.getKey().equals(name)){
				this.parcheggi.put(name, new Parking(name, slots));
			}
		}
    }


	public int getParkingSlots(String parking) {

		Parking p = this.parcheggi.get(parking);
		int slots = p.getCapacity();
		return slots;
	}

	
	public void liftServedByParking(String lift, String parking) {
		
		Parking p = this.parcheggi.get(parking);
		Impianto i = this.impiantiRisalita.get(lift);

		for(Map.Entry<String, Set<String>> parkPlant : this.parcheggiImpianti.entrySet()){
			if(parkPlant.getKey().equals(p.getName())){
				parkPlant.getValue().add(i.getCode());
			}
		}

	}

	
	public Collection<String> servedLifts(String parking) {

		Collection<String> impiantiPark = new ArrayList<>();

		Parking p = this.parcheggi.get(parking);
		for(Map.Entry<String, Set<String>> parkPlant : this.parcheggiImpianti.entrySet()){
			if(parkPlant.getKey().equals(p.getName())){
				impiantiPark.add(parkPlant.getValue().toString());
			}
		}
		return impiantiPark;
	}

	
	public boolean isParkingProportionate(String parkingName) {

		boolean proportionate = false;
		int count = 0;

		Parking p = this.parcheggi.get(parkingName);

		for(Map.Entry<String, Set<String>> parkPlant : this.parcheggiImpianti.entrySet()){
			if(parkPlant.getKey().equals(parkingName)){
				for(String s: parkPlant.getValue()){
					Impianto i = this.impiantiRisalita.get(s);
					count += i.getCapacity();
				}
			}
		}

		if(count/p.getCapacity() < 30){
			proportionate = true;
		}

		return proportionate;
	}

	
    public void readLifts(Reader path) throws IOException, InvalidLiftException {

		BufferedReader r = new BufferedReader(path);
		
		String line;
		while((line = r.readLine()) != null){
			String[] parts = line.trim().split(";");
			if(parts[0].equals("T")){
				if(line.length() != 4){
					throw new IOException();
				}
				String codice = parts[1];
				String categoria = parts[2];
				int numeroPosti = Integer.parseInt(parts[3]);
				Impianto impiantoRisal = new Impianto(codice, categoria, numeroPosti);
				this.impiantiRisalita.put(codice, impiantoRisal);
			}
			int numeroPosti = 0;
			if(parts[0].equals("L")){
				if(line.length() != 3){
					throw new IOException();
				}
				String nome = parts[1];
				String codice = parts[2];
				if(this.impianti.containsKey(nome)){
					numeroPosti = this.impianti.get(nome).getCapacity();
				}
				this.impianti.put(codice, new Impianto(codice, codice, numeroPosti));
			}
		}
	}
}
