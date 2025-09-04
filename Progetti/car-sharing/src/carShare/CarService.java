package carShare;

import java.util.*;
import java.util.stream.Collectors;

public class CarService {

	public static class Parking{
		private String name;
		List<Prenotazione> autoPrenotate;

		public Parking(String name){
			this.name = name;
			this.autoPrenotate = new ArrayList<>();
		}

		public String getName(){
			return name;
		}

		public void addAuto(Prenotazione p){
			if(!this.autoPrenotate.contains(p)){
				this.autoPrenotate.add(p);
			}
		}

		public List<Prenotazione> getAutoPrenotate(){
			return autoPrenotate;
		}
	}

	Map<String, Parking> parkings = new TreeMap<>();
	
	public void addParking(String name) throws InvalidName{

		if(this.parkings.containsKey(name)){
			throw new InvalidName();
		}

		this.parkings.put(name, new Parking(name));
	}

	public static class Car{
		private Parking p;
		private String targa;
		private double tariffaMin, tariffaKM;
		private boolean avaible;

		public Car(Parking p, String targa, double tariffaMin, double tariffaKM){
			this.p = p;
			this.targa = targa;
			this.tariffaKM = tariffaKM;
			this.tariffaMin = tariffaMin;
			this.avaible = false;
		}

		public Parking getParking(){
			return p;
		}

		public String getTarga(){
			return targa;
		}

		public double getTarMIN(){
			return tariffaMin;
		}

		public double getTarKM(){
			return tariffaKM;
		}

		public void setAvaible(boolean a){
			this.avaible = a;
		}

		public boolean isAvaible(){
			return avaible;
		}
	}

	Map<String, Car> cars = new TreeMap<>();

	public void addCar(String parking, String licencePlate, double minRate, double kmRate) throws InvalidName{

		if(!this.parkings.containsKey(parking)){
			throw new InvalidName();
		}
		if(this.cars.containsKey(licencePlate)){
			throw new InvalidName();
		}

		this.cars.put(licencePlate, new Car(this.parkings.get(parking), licencePlate, minRate, kmRate));
		this.cars.get(licencePlate).setAvaible(true);
	}
		
	public SortedSet<String> getAvailableCars(String parking) throws InvalidName{
		
		SortedSet<String> avaibles = new TreeSet<>();

		for(Car c: this.cars.values()){
			if(c.getParking().equals(this.parkings.get(parking))){
				if(c.isAvaible() == true){
					avaibles.add(c.getTarga());
				}
			}
		}
		return avaibles;
	}
	
	public static class Subscriber{
		private String card;
		private List<Prenotazione> carsBooked;

		public Subscriber(String card){
			this.card = card;
			this.carsBooked = new ArrayList<>();
		}

		public String getCard(){
			return card;
		}

		public void addBooking(Prenotazione p){
			if(p.isBooked() == false){
				this.carsBooked.add(p);
				p.setBooked(true);
			}
		}

		public List<Prenotazione> getBooking(){
			return this.carsBooked;
		}

		public boolean hasBooking(){
			return this.carsBooked.stream().anyMatch(Prenotazione::isBooked);
		}
	}

	Map<String, Subscriber> subscribers = new TreeMap<>();

	public void addSubscriber(String card) throws InvalidName{
		if(this.subscribers.containsKey(card)){
			throw new InvalidName();
		}

		this.subscribers.put(card, new Subscriber(card));
	}
	
	public List<String> getSubscribers(){

		List<String> sub = new ArrayList<>();

		for(Subscriber c: this.subscribers.values()){
			sub.add(c.getCard());
		}
		return sub.stream().sorted().toList();
	}
	
	public static class Prenotazione{
		private String parkingPartenza;
		private String targa;
		private boolean booked, consegnata;

		public Prenotazione(String parkingPartenza, String targa){
			this.parkingPartenza = parkingPartenza;
			this.targa = targa;
			this.booked = false;
			this.consegnata = false;
		}

		public void setBooked(boolean b){
			this.booked = b;
		}

		public boolean isBooked(){
			return booked;
		}

		public String getParkingPartenza(){
			return parkingPartenza;
		}

		public String getTarga(){
			return targa;
		}

		public void setConsegnata(boolean c){
			this.consegnata = c;
		}

		public boolean isConsegnata(){
			return consegnata;
		}
	}
	public String reserve(String card, String parking) throws InvalidName{
		
		if(!this.parkings.containsKey(parking)){
			throw new InvalidName();
		}

		if(!this.subscribers.containsKey(card)){
			throw new InvalidName();
		}

		Subscriber s = this.subscribers.get(card);

		if(s.hasBooking() == true){
			return null;
		}

		if(getAvailableCars(parking).size() == 0){
			return null;
		}

		String targa = getAvailableCars(parking).getFirst();
		Prenotazione p = new Prenotazione(parking, targa);
		if(this.cars.get(targa).isAvaible() == true){
			s.addBooking(p);
			p.setBooked(true);
			this.parkings.get(parking).addAuto(p);
		}

		return targa;
	}
	
	public String release(String card, String plate) throws InvalidName{
		String targa = null;

		if(!this.cars.containsKey(plate)){
			throw new InvalidName();
		}

		if(!this.subscribers.containsKey(card)){
			throw new InvalidName();
		}

		Subscriber s = this.subscribers.get(card);
		for(Prenotazione p: s.getBooking()){
			if(p.getTarga().equals(plate) && p.isBooked() == true){
				p.setBooked(false);
				this.cars.get(plate).setAvaible(true);
				targa = p.getTarga();
				return targa;
			}
		}
		
		return null;
	}
	
	public Set<String> getReserved(String parking) throws InvalidName{
		
		Set<String> reserved = new HashSet<>();

		for(Prenotazione p: this.parkings.get(parking).getAutoPrenotate()){
			if(p.isBooked() == true){
				reserved.add(p.getTarga());
			}
		}
		
		return reserved.stream().sorted().collect(Collectors.toSet());
	}
	
	public String useCar(String card, String plate) throws InvalidName{
		if(!this.cars.containsKey(plate)){
			throw new InvalidName();
		}

		if(!this.subscribers.containsKey(card)){
			throw new InvalidName();
		}

		String targa = null;
		for(Prenotazione p: this.subscribers.get(card).getBooking()){
			if(p.getTarga().equals(plate) && p.isBooked() == true){
				p.setConsegnata(true);
				this.cars.get(plate).setAvaible(false);
				targa = p.getTarga();
				return targa;
			}
		}
		
		return null;
	}
	
	List<String> importi = new ArrayList<>();

	public String terminate(String card, String plate, String parking, int min, int km) throws InvalidName{
		if(!this.cars.containsKey(plate)){
			throw new InvalidName();
		}

		if(!this.subscribers.containsKey(card)){
			throw new InvalidName();
		}

		Subscriber s = this.subscribers.get(card);
		double importoAddebbito = 0.0;
		StringBuffer sb = new StringBuffer();


		for(Prenotazione p : s.getBooking()){
			if(p.getTarga().equals(plate) && p.isBooked() == true){
				this.parkings.get(parking).addAuto(p);
				p.setBooked(false);
				this.cars.get(plate).setAvaible(true);
				p.setConsegnata(false);
				importoAddebbito = min * this.cars.get(plate).getTarMIN() + km * this.cars.get(plate).getTarKM();
				sb.append(card).append(":").append(plate).append(":")
				.append(importoAddebbito).append(":").append(p.getParkingPartenza())
				.append(":").append(parking);

				this.importi.add(sb.toString());
				return sb.toString();
			}
		}
		
		return null;
	}

	public List<String> charges() {
		return this.importi.stream().sorted((a,b) -> {String[] aParts = a.split(":");
		String[] bParts = b.split(":");
		double aAmount = Double.parseDouble(aParts[2]);
		double bAmount = Double.parseDouble(bParts[2]);
		return Double.compare(bAmount, aAmount);
	})
		.toList();
	}
		
	public List<String> subscriberCharges(String card) throws InvalidName{
		if(!this.subscribers.containsKey(card)){
			throw new InvalidName();
		}
		
		return this.importi.stream().filter(sub -> sub.startsWith(card + ":"))
		.toList();
	}
	
	public double averageCharge() {

		double somma = this.importi.stream().mapToDouble(imp -> {String[] parts = imp.split(":");
	    return Double.parseDouble(parts[2]);}).sum();

		return somma/this.importi.size();
	}
	
	public long departuresFrom(String parking) throws InvalidName{
		
		if(!this.parkings.containsKey(parking)){
			throw new InvalidName();
		}

		long somma = this.importi.stream().mapToLong(imp ->
		{String[] parts = imp.split(":");
	    if(parts[3].equals(parking){return Long.parseLong(parts[2])})})
		.sum();
		
		return somma;
	}
}
