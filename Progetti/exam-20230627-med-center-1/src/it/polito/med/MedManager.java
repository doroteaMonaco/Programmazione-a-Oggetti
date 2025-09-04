package it.polito.med;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.print.Doc;

public class MedManager {

	Collection<String> speciality = new ArrayList<>();

	public void addSpecialities(String... specialities) {
		for(String s: specialities){
			if(!this.speciality.contains(s)){
				this.speciality.add(s);
			}
		}
	}

	
	public Collection<String> getSpecialities() {
		return this.speciality;
	}
	
	
	public static class Doctor{
		private String id, name, surname, speciality;
		private Map<LocalDate, List<String>> slots;
		private List<Appointment> appointments;

		public Doctor(String id, String name, String surname, String speciality){
			this.id = id;
			this.name = name;
			this.surname = surname;
			this.speciality = speciality;
			this.slots = new TreeMap<>();
			this.appointments = new ArrayList<>();
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

		public void addSlot(SlotAppuntamenti s){
			if(!this.slots.containsKey(s.getData())){
				this.slots.put(s.getData(), new ArrayList<>());
			}
		}

		public Map<LocalDate, List<String>> getSlots(){
			return this.slots;
		}

		public void addAppointement(Appointment a){
			this.appointments.add(a);
		}

		public List<Appointment> getAppointments(){
			return this.appointments;
		}
	}

	Map<String, Doctor> doctors = new HashMap<>();

	public void addDoctor(String id, String name, String surname, String speciality) throws MedException {
		if(this.doctors.containsKey(id)){
			throw new MedException();
		}

		if(!this.speciality.contains(speciality)){
			throw new MedException();
		}

		Doctor d = new Doctor(id, name, surname, speciality);
		this.doctors.put(id, d);

	}

	
	public Collection<String> getSpecialists(String speciality) {

		Collection<String> doctorSpecility = new ArrayList<>();

		for(Doctor d : this.doctors.values()){
			if(d.getSpeciality().equals(speciality)){
				doctorSpecility.add(d.getID());
			}
		}
		return doctorSpecility;
	}

	
	public String getDocName(String code) {
		Doctor d = this.doctors.get(code);
		return d.getName();
	}

	
	public String getDocSurname(String code) {
		Doctor d = this.doctors.get(code);
		return d.getSurname();
	}

	public static class SlotAppuntamenti{
		private LocalTime orainizio, orafine;
		private LocalDate data;
		private int duration;

		public SlotAppuntamenti(LocalDate data, LocalTime orainizio, LocalTime orafine, int duration){
			this.data = data;
			this.orafine = orafine;
			this.orainizio = orainizio;
			this.duration = duration;
		}

		public LocalDate getData(){
			return data;
		}

		public LocalTime getOraInizio(){
			return orainizio;
		}

		public LocalTime getOraFine(){
			return orafine;
		}

		public int getDuration(){
			return duration;
		}

	}


	public int addDailySchedule(String code, String date, String start, String end, int duration) {

		List<String> slots = new ArrayList<>();
		Doctor d = this.doctors.get(code);
		LocalDate data = LocalDate.parse(date);
		LocalTime inizio = LocalTime.parse(start);
		LocalTime fine = LocalTime.parse(end);
		LocalTime newFine = null;

		if(!d.getSlots().containsKey(data)){
			while(inizio.plusMinutes(duration).isBefore(fine) || inizio.plusMinutes(duration).equals(fine)){
				newFine = inizio.plusMinutes(duration);
				slots.add(inizio + "-" + newFine);
				inizio = inizio.plusMinutes(duration);
			}
			d.getSlots().put(data, slots);
			
		}
		return slots.size();
	}

	
	public Map<String, List<String>> findSlots(String date, String speciality) {
		Map<String, List<String>> slotsForSpeciality = new TreeMap<>();

		LocalDate data = LocalDate.parse(date);

		for(Doctor d: this.doctors.values()){
			if(d.getSpeciality().equals(speciality) && !d.getSlots().isEmpty()){
				slotsForSpeciality.put(d.getID(), d.getSlots().get(data));
			}
		}
		return slotsForSpeciality;
	}

	public static class Patient{
		private String ssn, name, surname;
		private boolean isAccepted;
		

		public Patient(String ssn, String name, String surname){
			this.ssn = ssn;
			this.name = name;
			this.surname = surname;
			this.isAccepted = false;
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

		public boolean isAccepted(){
			return isAccepted;
		}

		public void setAccepted(boolean a){
			this.isAccepted = a;
		}
	}

	public static class Appointment{

		private Patient p;
		private Doctor d;
		private String idApp;
		private String slot;
		private LocalDate date;
		private boolean isCompleted;

		public Appointment(String id, Patient p, Doctor d, LocalDate date, String slot){
			this.idApp = id;
			this.d = d;
			this.p = p;
			this.date = date;
			this.slot = slot;
			this.isCompleted = false;
		}

		public String getIDAPP(){
			return idApp;
		}

		public Doctor getDoctor(){
			return d;
		}

		public Patient getPatient(){
			return p;
		}

		public LocalDate getDate() {
			return date;
		}
	
		public String getSlot() {
			return slot;
		}

		public boolean isCompleted(){
			return isCompleted;
		}

		public void setCompleted(boolean b){
			this.isCompleted = b;
		}
	}

	Map<String, Patient> patients = new HashMap<>();
	int codeApp = 1;

	public String setAppointment(String ssn, String name, String surname, String code, String date, String slot) throws MedException {

		if(!this.patients.containsKey(ssn)){
			this.patients.put(ssn, new Patient(ssn, name, surname));
		}

		if(!this.doctors.containsKey(code)){
			throw new MedException();
		}

		Doctor d = this.doctors.get(code);
		LocalDate data = LocalDate.parse(date);
		if(!d.getSlots().containsKey(data)){
			throw new MedException();
		}

		List<String> slotsDisponibili = d.getSlots().get(data);

		if(!slotsDisponibili.contains(slot)){
			throw new MedException();
		}

		Patient p = this.patients.get(ssn);
		Appointment a = new Appointment(String.valueOf(codeApp++), p, d, data, slot);
		d.addAppointement(a);
		slotsDisponibili.remove(slot);
		
		return a.getIDAPP();
	}

	
	public String getAppointmentDoctor(String idAppointment) {
		
		for(Doctor d : this.doctors.values()){
			for(Appointment a : d.getAppointments()){
				if(a.getIDAPP().equals(idAppointment)){
					return d.getID();
				}
			}
		}
		return null;
	}

	
	public String getAppointmentPatient(String idAppointment) {
		for(Doctor d : this.doctors.values()){
			for(Appointment a : d.getAppointments()){
				if(a.getIDAPP().equals(idAppointment)){
					return a.getPatient().getSSN();
				}
			}
		}
		return null;
	}

	
	public String getAppointmentTime(String idAppointment) {

		String slot = null;
		for(Doctor d : this.doctors.values()){
			for(Appointment a : d.getAppointments()){
				if(a.getIDAPP().equals(idAppointment)){
					slot = a.getSlot();
					String[] parts = slot.split("-");
					return parts[0];
				}
			}
		}
		return null;
	}

	
	public String getAppointmentDate(String idAppointment) {
		for(Doctor d : this.doctors.values()){
			for(Appointment a : d.getAppointments()){
				if(a.getIDAPP().equals(idAppointment)){
					return a.getDate().toString();
				}
			}
		}
		return null;
	}

	
	public Collection<String> listAppointments(String code, String date) {

		Collection<String> appuntamenti = new ArrayList<>();

		String slot = null;
		Doctor d = this.doctors.get(code);
		LocalDate data = LocalDate.parse(date);
		for(Appointment a : d.getAppointments()){
			if(a.getDate().equals(data)){
				slot = a.getSlot();
				String[] parts = slot.split("-");
				appuntamenti.add(parts[0] + "=" + a.getPatient().getSSN());
			}
		}
		return appuntamenti;
	}

	LocalDate currentDate = null;
	public int setCurrentDate(String date) {

		int count = 0;
		currentDate = LocalDate.parse(date);
		for(Doctor d: this.doctors.values()){
			for(Appointment a: d.getAppointments()){
				if(a.getDate().equals(currentDate)){
					count++;
				}
			}
		}
		return count;
	}

	List<Patient> accepted = new ArrayList<>();
	public void accept(String ssn) {
		Patient p = this.patients.get(ssn);
		p.setAccepted(true);

		if(!this.accepted.contains(p)){
			this.accepted.add(p);
		}
	}

	
	public String nextAppointment(String code) {
		Doctor d = this.doctors.get(code);
		for(Appointment a : d.getAppointments()){
			if(a.getDate().equals(currentDate) && this.accepted.contains(a.getPatient()) && a.isCompleted() == false){
				return a.getIDAPP();
			}
			
		}
		return null;
	}

	
	public void completeAppointment(String code, String appId)  throws MedException {
		if (!doctors.containsKey(code)) {
            throw new MedException("Medico non trovato");
        }

        Doctor doctor = doctors.get(code);
        Appointment appointmentToComplete = null;

        for (Appointment appointment : doctor.getAppointments()) {
            if (appointment.getIDAPP().equals(appId) && appointment.getDate().equals(currentDate)) {
                appointmentToComplete = appointment;
                break;
            }
        }

        if (appointmentToComplete == null) {
            throw new MedException();
        }

        if (!accepted.contains(appointmentToComplete.getPatient())) {
            throw new MedException();
        }

        if (!appointmentToComplete.getDoctor().getID().equals(code)) {
            throw new MedException();
        }

        appointmentToComplete.setCompleted(true);
    }

		

	

	
	public double showRate(String code, String date) {

		double numPazienti = 0.0;
		double numAppuntamenti = 0.0;
		LocalDate data = LocalDate.parse(date);
		Doctor d = this.doctors.get(code);
		for(Appointment a : d.getAppointments()){
			if(a.getDate().equals(data)){
				if(a.getPatient().isAccepted() == true){
					numPazienti++;
				}
				numAppuntamenti++;
			}
		}
		return numPazienti/numAppuntamenti;
	}

	
	public Map<String, Double> scheduleCompleteness() {

		Map<String, Double> completeness = new TreeMap<>();
		double numSlots = 0.0;
		double numAppuntamenti = 0.0;

		for(Doctor d: this.doctors.values()){
			numAppuntamenti = d.getAppointments().size();
			for(List<String> slot: d.getSlots().values()){
				numSlots++;
			}
			completeness.put(d.getID(), numSlots/numAppuntamenti);
		}

		return completeness;
	}


	
}
