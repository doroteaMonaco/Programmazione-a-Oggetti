package delivery;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.text.html.HTMLDocument.Iterator;


public class Delivery {
	// R1


	public class Ristorante{
		private String nome;
		private String categoria;
		private List<Piatto> piatti;
		private List<Ordine> ordini;
		private List<Valutazione> valutazioni;


		public Ristorante(String nome, String categoria){
			this.nome = nome;
			this.categoria = categoria;
			this.piatti = new ArrayList<>();
			this.ordini = new ArrayList<>();
			this.valutazioni = new ArrayList<>();
		}

		public String getNome(){
			return nome;
		}

		public String getCategoria(){
			return categoria;
		}

		public void addPiatto(Piatto p){
			if(!this.piatti.contains(p)){
				this.piatti.add(p);
			}
		}

		public List<Piatto> getPiatti(){
			return this.piatti;
		}

		public void addOrdine(Ordine o){
			this.ordini.add(o);
		}

		public List<Ordine> getOrdini(){
			return this.ordini;
		}

		public void addValutazione(Valutazione v){
			this.valutazioni.add(v);
		}

		public List<Valutazione> getValutazioni(){
			return this.valutazioni;
		}

		public double getValutazioneMedia(){

			int somma = 0;

			for(Valutazione v: this.valutazioni){
				somma += v.getValutazione();
			}
			return somma/this.valutazioni.size();
		}
	}

	List<String> categorie = new ArrayList<>();
	Map<String, Ristorante> ristoranti = new TreeMap<>();

	public void addCategory (String category) throws DeliveryException {

		for(String c: this.categorie){
			if(c.equals(category)){
				throw new DeliveryException();
			}
		}

		this.categorie.add(category);
	}

	public List<String> getCategories(){
		return this.categorie;
	}
	
	
	public void addRestaurant (String name, String category) throws DeliveryException {
		if(this.ristoranti.containsKey(name)){
			throw new DeliveryException();
		}

		if(category == null){
			throw new DeliveryException();
		}

		if(!this.categorie.contains(category)){
			throw new DeliveryException();
		}

		this.ristoranti.put(name, new Ristorante(name, category));
		
	}
	
	
	public List<String> getRestaurantsForCategory(String category) {

		List<String> tmp = new ArrayList<>();
		
		if(!this.categorie.contains(category)){
			return null;
		}

		for(Ristorante r: this.ristoranti.values()){
			if(r.getCategoria().equals(category)){
				tmp.add(r.getNome());
			}
		}

        return tmp.stream().sorted().toList();
	}
	
	// R2
	
	public class Piatto{
		private String nome;
		private float prezzo;

		public Piatto(String nome, float prezzo){
			this.nome = nome;
			this.prezzo = prezzo;
		}

		public String getNome(){
			return nome;
		}

		public float getPrezzo(){
			return prezzo;
		}
	}
	
	public void addDish(String name, String restaurantName, float price) throws DeliveryException {
	
		Ristorante r = this.ristoranti.get(restaurantName);
		for(Piatto p: r.getPiatti()){
			if(p.getNome().equals(name)){
				throw new DeliveryException();
			}
		}

		r.addPiatto(new Piatto(name, price));
	}
	
	
	public Map<String,List<String>> getDishesByPrice(float minPrice, float maxPrice) {

		Map<String,List<String>> tmp = new TreeMap<>();

		for(Ristorante r: this.ristoranti.values()){
			for(Piatto p: r.getPiatti()){
				if(p.getPrezzo() >= minPrice && p.getPrezzo() <= maxPrice){
					tmp.put(r.getNome(), new ArrayList<>());
					tmp.get(r.getNome()).add(p.getNome());
				}
			}
		}
        return tmp;
	}
	
	
	public List<String> getDishesForRestaurant(String restaurantName) {
        
		List<String> tmp = new ArrayList<>();

		Ristorante r = this.ristoranti.get(restaurantName);
		for(Piatto p: r.getPiatti()){
			tmp.add(p.getNome());
		}
		return tmp.stream().sorted().toList();
	}
	
	
	public List<String> getDishesByCategory(String category) {

		List<String> tmp = new ArrayList<>();

		for(Ristorante r: this.ristoranti.values()){
			if(r.getCategoria().equals(category)){
				for(Piatto p: r.getPiatti()){
					tmp.add(p.getNome());
				}
			}
		}
        return tmp;
	}
	
	//R3
	
	public class Ordine{

		private String nomePersona;
		private Map<String, Integer> piattiOrdinati;
		private int tempoConsegna;
		private int distanza;
		private int numOrdine;
		private boolean isAssegnato;

		public Ordine(int numOrdine, String nomePersona, int tempoConsegna, int distanza, Map<String, Integer> piattiOrdinati){
			this.numOrdine = numOrdine;
			this.nomePersona = nomePersona;
			this.tempoConsegna = tempoConsegna;
			this.distanza = distanza;
			this.piattiOrdinati = new TreeMap<>(piattiOrdinati);
			this.isAssegnato = false;
		}

		public int getNumeroOrdine(){
			return numOrdine;
		}

		public String getNomePersona(){
			return nomePersona;
		}

		public int getTempoConsegna(){
			return tempoConsegna;
		}

		public int getDistanza(){
			return distanza;
		}

		public void addPiattoOrdinato(String piatto, int quantità){
			if(!this.piattiOrdinati.containsKey(piatto)){
				this.piattiOrdinati.put(piatto, quantità);
			}
		}

		public Map<String, Integer> getPiattiOrdinati(){
			return this.piattiOrdinati;
		}

		public void setAssegnato(boolean a){
			this.isAssegnato = a;
		}

		public boolean isAssegnato(){
			return isAssegnato;
		}

	}

	List<Ordine> ordiniAssgnati = new ArrayList<>();
	int numOrdine = 1;
	public int addOrder(String dishNames[], int quantities[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance) {

		int i = 0;
		Map<String, Integer> tmp = new TreeMap<>();
		Ristorante r = this.ristoranti.get(restaurantName);

		for(i = 0; i < dishNames.length; i++){
			tmp.put(dishNames[i], quantities[i]);
		}

		Ordine o = new Ordine(numOrdine, customerName, deliveryTime, deliveryDistance, tmp);
		r.addOrdine(o);
		this.ordiniAssgnati.add(o);

		
		return numOrdine++;
	}
	
	
	public List<Integer> scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders) {
        List<Integer> tmp = new ArrayList<>();
		int count = 0;

		for(Ordine o: this.ordiniAssgnati){
			if(o.isAssegnato() == false && o.getTempoConsegna() == deliveryTime && o.getDistanza() <= maxDistance){
				tmp.add(o.getNumeroOrdine());
                o.setAssegnato(true);
                count++;
                if (count == maxOrders) {
                    break;
				}
			}
		}
		return tmp;
	}
	

	public int getPendingOrders() {

		int countPending = 0;

		for(Ristorante r: this.ristoranti.values()){
			for(Ordine o: r.getOrdini()){
				if(o.isAssegnato() == false){
					countPending++;
				}
			}
		}
        return countPending;
	}
	
	// R4

	public class Valutazione{
		private int valutazione;
		private String nomeR;

		public Valutazione(int valutazione, String nomeR){
			this.nomeR = nomeR;
			this.valutazione = valutazione;
		}

		public String getNomeR(){
			return nomeR;
		}

		public int getValutazione(){
			return valutazione;
		}

	}
	
	public void setRatingForRestaurant(String restaurantName, int rating) {
		Ristorante r = this.ristoranti.get(restaurantName);
		if(r != null){
			r.addValutazione(new Valutazione(rating, restaurantName));
		}
	}
	
	
	public List<String> restaurantsAverageRating() {
		List<Ristorante> tmp = new ArrayList<>();
		List<String> tmpS = new ArrayList<>();

		for(Ristorante r: this.ristoranti.values()){
			tmp.add(r);
		}

		tmpS = tmp.stream().sorted(Comparator.comparingDouble(Ristorante::getValutazioneMedia).reversed())
		.map(Ristorante::getNome).toList();
        return tmpS;
	}
	
	//R5
	
	public Map<String,Long> ordersPerCategory() {
		Map<String,Long> tmp = new TreeMap<>();

		long somma = 0;

		for(String c : this.categorie){
			for(Ristorante r: this.ristoranti.values()){
				if(r.getCategoria().equals(c)){
					somma += r.getOrdini().size();
				}
			}
			tmp.put(c, somma);
		}
        return tmp;
	}
	

	public String bestRestaurant() {

		double max = 0;
		String nome = null;

		for(Ristorante r: this.ristoranti.values()){
			if(r.getValutazioneMedia() >= max){
				max = r.getValutazioneMedia();
				nome = r.getNome();
			}
		}
        return nome;
	}
}
