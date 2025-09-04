package it.polito.med;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MedManager {

	List<String> specialitiesDoctor = new ArrayList<>();
	Map<String, Doctor> doctors = new HashMap<>();
	List<Slot> slots = new ArrayList<>();
	Map<String, Appuntamento> appuntamenti = new HashMap<>();
	List<Patient> pazientiAccettati = new ArrayList<>();

	public static class Doctor{
		private String id;
		private String name, surname;
		private String speciality;
		private List<Slot> slotsforDoctor;
		private List<Patient> patients;
		boolean prenotato = false;

		public Doctor(String id, String name, String surname, String speciality){
			this.id = id;
			this.name = name;
			this.surname = surname;
			this.speciality = speciality;
			this.slotsforDoctor = new ArrayList<>();
			this.patients = new ArrayList<>();
			this.prenotato = false;
		}

		public String getID(){
			return id;
		}

		public String getName(){
			return name;
		}

		public String getSurname(){
			return surname;
		}

		public String getSpeciality(){
			return speciality;
		}

		public void addSlot(Slot s){
			this.slotsforDoctor.add(s);
		}

		public List<Slot> getSlots(){
			return this.slotsforDoctor;
		}

		public void addPatient(Patient p){
			if(!this.patients.contains(p)){
				this.patients.add(p);
			}
		}

		public List<Patient> getPatients(){
			return this.patients;
		}

		public boolean isPrenotato(){
			return prenotato;
		}

		public void setPrenotato(Boolean p){
			this.prenotato = p;
		}
	}

	public static class Slot{
		private LocalDate date;
		private LocalTime start, end;
		private int durata;

		public Slot(LocalDate date, LocalTime start, LocalTime end, int durata){
			this.date = date;
			this.start = start;
			this.end = end;
			this.durata = durata;
		}

		public LocalDate getDate(){
			return date;
		}

		public LocalTime getStart(){
			return start;
		}

		public LocalTime getEnd(){
			return end;
		}

		public int getDurata(){
			return durata;
		}

		public String toString(){
			DateTimeFormatter format = DateTimeFormatter.ofPattern("HH::mm");
			return start.format(format) + "-" + end.format(format);
		}

	}

	public static class Patient{
		private String ssn;
		private String name, surname;

		public Patient(String ssn, String name, String surname){
			this.ssn = ssn;
			this.name = name;
			this.surname = surname;
		}

		public String getSSN(){
			return ssn;
		}

		public String getName(){
			return name;
		}

		public String getSurname(){
			return surname;
		}
	}

	public static class Appuntamento{
		private String ssn;
		private String docID;
		private LocalDate date;
		private LocalTime slot;
		boolean completed;

		public Appuntamento(String ssn, String docID, LocalDate date, LocalTime slot){
			this.ssn = ssn;
			this.docID = docID;
			this.date = date;
			this.slot = slot;
			this.completed = false;
		}

		public String getSSN(){
			return ssn;
		}

		public String getDocID(){
			return docID;
		}

		public LocalDate getDate(){
			return date;
		}

		public LocalTime getSlot(){
			return slot;
		}

		public boolean isCompleted(){
			return completed;
		}

		public void setCompleted(Boolean flag){
			this.completed = flag;
		}
	}

	public void addSpecialities(String... specialities) {
		
		for(String s: specialities){
			if(!this.specialitiesDoctor.contains(s)){
				this.specialitiesDoctor.add(s);
			}
		}
	}

	
	public Collection<String> getSpecialities() {
		return specialitiesDoctor;
	}
	
	

	public void addDoctor(String id, String name, String surname, String speciality) throws MedException {

		Doctor d = new Doctor(id, name, surname, speciality);

		if(this.doctors.containsKey(id)){
			throw new MedException();
		}
		if(!this.specialitiesDoctor.contains(speciality)){
			throw new MedException();
		}

		this.doctors.put(id, d);
	}


	public Collection<String> getSpecialists(String speciality) {

		Collection<String> doctorsForSpeciality = new ArrayList<>();

		for(Doctor d: this.doctors.values()){
			if(d.getSpeciality().equals(speciality)){
				doctorsForSpeciality.add(d.getID());
			}
		}

		return doctorsForSpeciality;
	}


	public String getDocName(String code) {

		Doctor d = this.doctors.get(code);
		return d.getName();
	}

	public String getDocSurname(String code) {
		Doctor d = this.doctors.get(code);
		return d.getSurname();
	}

	
	public int addDailySchedule(String code, String date, String start, String end, int duration) {

		LocalDate data = LocalDate.parse(date);
		LocalTime inizio = LocalTime.parse(start);
		LocalTime fine = LocalTime.parse(end);
		int nslot = 0;

		Slot s = new Slot(data, inizio, fine, duration);
		Doctor d = this.doctors.get(code);
		d.addSlot(s);

		while(inizio.plusMinutes(duration).isBefore(fine)){
			nslot++;
		}
		
		
		return nslot;
	}

	public Map<String, List<String>> findSlots(String date, String speciality) {

		List<String> slot = new ArrayList<>();
		Map<String, List<String>> slotSpeciality = new TreeMap<>();

		LocalDate data = LocalDate.parse(date);

		for(Doctor d: this.doctors.values()){
			if(d.getSpeciality().equals(speciality)){
				for(Slot s: d.getSlots()){
					if(s.getDate().equals(data)){
						slot.add(s.toString());
					}
				}

				slotSpeciality.put(d.getID(), slot);
			}
		}
		return slotSpeciality;
	}


	public String setAppointment(String ssn, String name, String surname, String code, String date, String slot) throws MedException {
		
		int codeApp = this.appuntamenti.size() + 1;
		LocalDate data = LocalDate.parse(date);
		LocalTime slotOrario = LocalTime.parse(slot);
		String codA = null;

		if(!this.doctors.containsKey(code)){
			throw new MedException();
		}
		

		Patient p = new Patient(ssn, name, surname);
		Doctor d = this.doctors.get(code);
		d.addPatient(p);

		boolean isAvaible = false;
		for(Slot s: d.getSlots()){
			if(s.getDate().equals(data)){
				isAvaible = true;
			}
		}

		if(!isAvaible || d.isPrenotato()){
			throw new MedException();
		}

		codA = String.valueOf(codeApp);
		this.appuntamenti.put(codA, new Appuntamento(ssn, codA, data, slotOrario));
		d.setPrenotato(true);
		
		return codA;
	}

	
	public String getAppointmentDoctor(String idAppointment) {
		Appuntamento a = this.appuntamenti.get(idAppointment);
		return a.getDocID();
	}

	
	public String getAppointmentPatient(String idAppointment) {
		Appuntamento a = this.appuntamenti.get(idAppointment);
		return a.getSSN();
	}


	public String getAppointmentTime(String idAppointment) {
		Appuntamento a = this.appuntamenti.get(idAppointment);
		return a.getSlot().toString();
	}

	
	public String getAppointmentDate(String idAppointment) {
		Appuntamento a = this.appuntamenti.get(idAppointment);
		return a.getDate().toString();
	}

	
	public Collection<String> listAppointments(String code, String date) {

		Collection<String> appuntamentiDoc = new ArrayList<>();
		LocalDate data = LocalDate.parse(date);

		Doctor d = this.doctors.get(date);

		for(Appuntamento a : this.appuntamenti.values()){
			if(a.getDate().equals(data) && a.getDocID().equals(d.getID())){
				StringBuffer sb = new StringBuffer();
				sb.append(a.getSlot()).append("=").append(a.getSSN());
				appuntamentiDoc.add(sb.toString());
			}
		}
		return appuntamentiDoc;
	}

	
	public int setCurrentDate(String date) {

		int countApp = 0;
		LocalDate data = LocalDate.parse(date);

		for(Appuntamento a: this.appuntamenti.values()){
			if(a.getDate().equals(data)){
				countApp++;
			}
		}
		return countApp;
	}

	
	public void accept(String ssn) {

		for(Doctor d: this.doctors.values()){
			for(Patient p: d.getPatients()){
				if(p.getSSN().equals(ssn) && d.isPrenotato()){
					this.pazientiAccettati.add(p);
				}
			}
		}
	}

	
	public String nextAppointment(String code) {

		Appuntamento app = null;
		LocalDate now = LocalDate.now();

		for(Appuntamento a: this.appuntamenti.values()){
			if(a.getDocID().equals(code) && a.getDate().isAfter(now) && a.isCompleted()){
				app = a;
			}
		}

		for(Map.Entry<String, Appuntamento> appuntamento: this.appuntamenti.entrySet()){
			if(appuntamento.getValue().equals(app)){
				return appuntamento.getKey();
			}
		}
		return null;
	}

	
	public void completeAppointment(String code, String appId)  throws MedException {

		if(!this.appuntamenti.containsKey(appId)){
			throw new MedException();
		}
		if(!this.doctors.containsKey(code)){
			throw new MedException();
		}

		Appuntamento a = this.appuntamenti.get(appId);

		if(a.getDocID().equals(code)){
			a.setCompleted(true);
		}
	}

	
	public double showRate(String code, String date) {

		double countApp = 0.0;
		double countPat = 0.0;
		LocalDate data = LocalDate.parse(date);

		return 0.0;
	}

	
	public Map<String, Double> scheduleCompleteness() {
		Map<String, Double> completenessMap = new HashMap<>();

    for (Doctor d : doctors.values()) {
        int numSlots = d.getSlots().size();
        int numAppointments = 0;

        for (Appuntamento a : appuntamenti.values()) {
            if (a.getDocID().equals(d.getID())) {
                numAppointments++;
            }
        }

        double completeness = (double) numAppointments / numSlots;
        completenessMap.put(d.getID(), completeness);
    }

    return completenessMap;
	}


	
}
