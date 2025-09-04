package it.polito.project;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReviewServer {

	
	List<String> groups = new ArrayList<>();
	Map<String, Review> reviews = new HashMap<>();
	Map<String, Boolean> polls = new HashMap<>();

	public static class Review{

		private String id;
		private String title;
		private String topic;
		private String group;
		private List<Orario> slotOrari;
		private List<Preference> preferences;

		public Review(String id, String title, String topic, String group){
			this.id = id;
			this.title = title;
			this.topic = topic;
			this.group = group;
			this.slotOrari = new ArrayList<>();
			this.preferences = new ArrayList<>();
		}

		public String getID(){
			return id;
		}

		public String getTitle(){
			return title;
		}

		public String getTopic(){
			return topic;
		}

		public String getGroup(){
			return group;
		}

		public void setPreference(Preference p){
			if(!this.preferences.contains(p)){
				this.preferences.add(p);
			}
		}

		public List<Preference> getPreferences(){
			return this.preferences;
		}

		public void setSlot(Orario o){
			if(!this.slotOrari.contains(o)){
				this.slotOrari.add(o);
			}
		}

		public List<Orario> getSlot(){
			return this.slotOrari;
		}

	}

	public static class Orario{
		private LocalTime oraInizio, oraFine;
		private LocalDate data;

		List<Preference> preferences = new ArrayList<>();

		public Orario(LocalDate data, LocalTime oraInizio, LocalTime oraFine){
			this.data = data;
			this.oraInizio = oraInizio;
			this.oraFine = oraFine;
		}

		public LocalDate getDate(){
			return data;
		}

		public LocalTime getOraInizio(){
			return oraInizio;
		}

		public LocalTime getOraFine(){
			return oraFine;
		}

		public String toString(){
			return oraInizio + "-" + oraFine;
		}

		public void setPreference(Preference p){
			if(!this.preferences.contains(p)){
				this.preferences.add(p);
			}
		}

		public List<Preference> getPreferences(){
			return this.preferences;
		}

		public boolean supera(Orario o){
			if(!this.data.equals(o.getDate())){
				return false;
			}

			return this.oraInizio.isBefore(o.getOraFine()) && this.oraFine.isAfter(o.getOraInizio());
		}

	}

	public static class Preference{
		private String email, nome, cognome;
		private Orario o;
		private Review r;

		public Preference(String email, String nome, String cognome){
			this.email = email;
			this.nome = nome;
			this.cognome = cognome;

		}

		public String getEmail(){
			return email;
		}

		public String getNome(){
			return nome;
		}

		public String getCognome(){
			return cognome;
		}

		public String toString(){
			return o.getDate().toString() + "T" + o.getOraInizio().toString() + "-" + o.getOraFine().toString() + "=" + email;
		}

	}

	public void addGroups(String... groups) {
		for(String s: groups){
			this.groups.add(s);
		}
	}


	public Collection<String> getGroups() {
		return this.groups;
	}
	
	

	public String addReview(String title, String topic, String group) throws ReviewException {
		
		int id = this.reviews.size() + 1;
		String idS = String.valueOf(id);
		Review r = new Review(idS, title, topic, group);

		if(!this.groups.contains(group)){
			throw new ReviewException();
		}

		this.reviews.put(idS, r);

		return idS;
	}


	public Collection<String> getReviews(String group) {

		Collection<String> rev = new ArrayList<>();

		for(Review r: this.reviews.values()){
			if(r.getGroup().equals(group)){
				rev.add(r.getID());
			}
		}
		return rev;
	}

	
	public String getReviewTitle(String reviewId) {
		
		for(Review r: this.reviews.values()){
			if(r.getID().equals(reviewId)){
				return r.getTitle();
			}
		}
		return null;
	}

	
	public String getReviewTopic(String reviewId) {
		for(Review r: this.reviews.values()){
			if(r.getID().equals(reviewId)){
				return r.getTopic();
			}
		}
		return null;
	}

	// R2
		
	
	public double addOption(String reviewId, String date, String start, String end) throws ReviewException {
		
		Review r = this.reviews.get(reviewId);
		Double intervallo = 0.0;
		LocalDate data = LocalDate.parse(date);
		LocalTime inizio = LocalTime.parse(start);
		LocalTime fine = LocalTime.parse(end);
		Orario or = new Orario(data, inizio, fine);
		
		if(!this.reviews.containsKey(reviewId)){
			throw new ReviewException();
		}
		if(fine.isBefore(inizio)){
			throw new ReviewException();
		}


		for(Orario orario: this.reviews.get(reviewId).getSlot()){
			if(orario.supera(or)){
				throw new ReviewException();
			}
		}

		r.getSlot().add(or);

		Duration dur = Duration.between(inizio, fine);
		intervallo = dur.toMinutes() / 60.0;

		return intervallo;
	}

	
	public Map<String, List<String>> showSlots(String reviewId) {

		Map<String, List<String>> slot = new HashMap<>();

		Review r =this.reviews.get(reviewId);
		List<Orario> orari = r.getSlot();


		for(Orario o: orari){
			slot.put(o.getDate().toString(), new ArrayList<>());
			slot.get(o.getDate().toString()).add(o.toString());
		}

		return slot;
	}

	
	public void openPoll(String reviewId) {
		if(this.reviews.containsKey(reviewId)){
			this.polls.put(reviewId, true);
		}
	}


	
	public int selectPreference(String email, String name, String surname, String reviewId, String date, String slot) throws ReviewException {
		
		Preference p = new Preference(email, date, slot);

		if(!this.reviews.containsKey(reviewId)){
			throw new ReviewException();
		}

		Review r = this.reviews.get(reviewId);
		
		for(String s: this.polls.keySet()){
			if(s.equals(reviewId)){
				r.setPreference(p);
			}
		}


		return r.getPreferences().size();
	}

	
	public Collection<String> listPreferences(String reviewId) {

		Collection<String> pref = new ArrayList<>();
		Review r = this.reviews.get(reviewId);

		for(Preference p : r.getPreferences()){
			pref.add(p.toString());
		}

		return pref;
	}

	
	public Collection<String> closePoll(String reviewId) {

		Review r = this.reviews.get(reviewId);

		this.polls.put(reviewId, false);

		Collection<String> pref = new ArrayList<>();
		for(Preference p : r.getPreferences()){
			pref.add(p.toString());
		}

		return pref;
	}

	
	public Map<String, List<String>> reviewPreferences(String reviewId) {
		return null;
	}


	/**
	 * computes the number preferences collected for each review
	 * The result is a map that associates to each review id the relative count of preferences expressed
	 * 
	 * @return the map id : preferences -> count
	 */
	public Map<String, Integer> preferenceCount() {
		return null;
	}
}
