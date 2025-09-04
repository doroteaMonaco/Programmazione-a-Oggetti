package clinic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.print.Doc;



public class Clinic {


	public static class Patient{
		String ssn, name, surname;

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

		public String toString(){
			return "<" + surname + "> <" + name + "> (<" + ssn + ">)";
		}
	}

	
	Map<String, Patient> patients = new TreeMap<>();

	public void addPatient(String first, String last, String ssn) {
   		if(!this.patients.containsKey(ssn)){
			this.patients.put(ssn, new Patient(ssn, first, last));
		}
 	}

	public String getPatient(String ssn) throws NoSuchPatient {
   		
		if(!this.patients.containsKey(ssn)){
			throw new NoSuchPatient();
		}

		return this.patients.get(ssn).toString();
	}

	public static class Doctor{
		String ssn, name, surname, speciality;
		int docID;
		List<Patient> patientsAssigned;
		Map<Integer,Integer> numeroPazientiPerDottore;
	

		public Doctor(String ssn, String name, String surname, String speciality, int docID){
			this.ssn = ssn;
			this.name = name;
			this.surname = surname;
			this.docID = docID;
			this.speciality = speciality;
			this.patientsAssigned = new ArrayList<>();
			this.numeroPazientiPerDottore = new TreeMap<>();
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

		public int getDocID(){
			return docID;
		}

		public String getSpeciality(){
			return speciality;
		}

		public String toString(){
			return "<" + surname + "> <" + name + "> (<" + ssn + ">) [" + docID + "]: <" + speciality + ">";
		}

		public void addPatient(Patient p){
			if(!this.patientsAssigned.contains(p)){
				this.patientsAssigned.add(p);
			}
		}

		public List<Patient> getPatients(){
			return this.patientsAssigned;
		}

		public void addNumeroPazienti(Doctor d){
			int somma = 0;
			for(Patient p: d.getPatients()){
				somma++;
			}

			if(!d.numeroPazientiPerDottore.containsKey(d.getDocID())){
				this.numeroPazientiPerDottore.put(d.getDocID(), somma);
			}
		}

		public Integer getNumeroPazienti(Doctor d){
			return this.numeroPazientiPerDottore.get(d.getDocID());
		}

		public double getMedia(int id){

			int numPazienti = 0;

			for(Integer i: this.numeroPazientiPerDottore.values()){
				numPazienti++;
			}

			return this.numeroPazientiPerDottore.get(id)/numPazienti;
		}
	}

	Map<Integer, Doctor> doctors = new TreeMap<>();

	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
   		if(!this.doctors.containsKey(docID)){
			this.doctors.put(docID, new Doctor(ssn, first, last, specialization, docID));
		}
		if(!this.patients.containsKey(ssn)){
			this.patients.put(ssn, new Patient(ssn, first, last));
		}
	}

	public String getDoctor(int docID) throws NoSuchDoctor {
		if(!this.doctors.containsKey(docID)){
			throw new NoSuchDoctor();
		}
		return this.doctors.get(docID).toString();
	}
	
	
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		if(!this.doctors.containsKey(docID)){
			throw new NoSuchDoctor();
		}
		if(!this.patients.containsKey(ssn)){
			throw new NoSuchPatient();
		}

		this.doctors.get(docID).addPatient(this.patients.get(ssn));

	}

	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		
		if(!this.patients.containsKey(ssn)){
			throw new NoSuchPatient();
		}
		
		int docID = 0;
		boolean flag = false;

		for(Doctor d: this.doctors.values()){
			for(Patient p: d.getPatients()){
				if(p.getSSN().equals(ssn)){
					flag = true;
					docID = d.getDocID();
				}
			}
		}

		if(flag == false){
			throw new NoSuchDoctor();
		}

		return docID;
	}
	

	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		if(!this.doctors.containsKey(id)){
			throw new NoSuchDoctor();
		}

		Collection<String> patientsDoc = new ArrayList<>();

		for(Patient p: this.doctors.get(id).getPatients()){
			patientsDoc.add(p.getSSN());
		}
		return patientsDoc;
	}
	
	
	public int loadData(Reader reader) throws IOException {
   		
		BufferedReader bfr = new BufferedReader(reader);
		String line;
		int countLine = 0;
		boolean flag = false;

		while((line = bfr.readLine()) != null){
			countLine++;
			String[] parts = line.trim().split(";");
			if(parts[0] == "P"){
				if(line.length() == 4){
					flag = true;
					String name = parts[1];
					String surname = parts[2];
					String ssn = parts[3];

					if(!this.patients.containsKey(ssn)){
						this.patients.put(ssn, new Patient(ssn, name, surname));
					}
				}
			}
			if(parts[0] == "M"){
				if(line.length() == 6){
					flag = true;
					int docID = Integer.parseInt(parts[1]);
					String name = parts[2];
					String surname = parts[3];
					String ssn = parts[4];
					String speciality = parts[5];

					if(!this.doctors.containsKey(docID)){
						this.doctors.put(docID, new Doctor(ssn, name, surname, speciality, docID));
					}
					if(!this.patients.containsKey(ssn)){
						this.patients.put(ssn, new Patient(ssn, name, surname));
					}
				}
			}
		}

		if(flag = false){
			throw new IOException();
		}
		
		return countLine;
	}



	public int loadData(Reader reader, ErrorListener listener) throws IOException {
		BufferedReader bfr = new BufferedReader(reader);
		String line;
		int countLine = 0;


		while((line = bfr.readLine()) != null){
			countLine++;
			String[] parts = line.trim().split(";");
			try{
				if(parts[0] == "P"){
					if(line.length() == 4){
						String name = parts[1];
						String surname = parts[2];
						String ssn = parts[3];

						if(!this.patients.containsKey(ssn)){
							this.patients.put(ssn, new Patient(ssn, name, surname));
						}
					}
					else{
						throw new IOException();
					}
				}
				if(parts[0] == "M"){
					if(line.length() == 6){
						int docID = Integer.parseInt(parts[1]);
						String name = parts[2];
						String surname = parts[3];
						String ssn = parts[4];
						String speciality = parts[5];

						if(!this.doctors.containsKey(docID)){
							this.doctors.put(docID, new Doctor(ssn, name, surname, speciality, docID));
						}
						if(!this.patients.containsKey(ssn)){
							this.patients.put(ssn, new Patient(ssn, name, surname));
						}
					}
					else{
						throw new IOException();
					}
				}
			}
			catch(Exception e){
				listener.offending(countLine, line);
			}
		}

		return countLine;
	}
	

	public Collection<Integer> idleDoctors(){
   		
		Collection<Doctor> docWithoutPatients = new ArrayList<>();
		Collection<Integer> docIDWithoutPatients = new ArrayList<>();
		
		for(Doctor d: this.doctors.values()){
			if(d.getPatients().size() == 0){
				docWithoutPatients.add(d);
			}
		}

		docIDWithoutPatients = docWithoutPatients.stream()
		.sorted(Comparator.comparing(Doctor::getSurname).thenComparing(Doctor::getName))
		.map(Doctor::getDocID).toList();

		return docIDWithoutPatients;
	}

	
	public Collection<Integer> busyDoctors(){
   		Collection<Integer> busy = new ArrayList<>();

		for(Doctor d: this.doctors.values()){
			if(d.getNumeroPazienti(d) > d.getMedia(d.getDocID())){
				busy.add(d.getDocID());
			}
		}
		   return busy;
	}

	public Collection<String> doctorsByNumPatients(){

		List<Doctor> docList = new ArrayList<>(doctors.values());
        docList.sort(Comparator.comparingInt(Doctor::getNumeroPazienti).reversed()
                .thenComparing(Doctor::getSurname)
                .thenComparing(Doctor::getName));
        List<String> result = new ArrayList<>();
        for (Doctor d : docList) {
            result.add(String.format("%03d : %d %s %s", d.getNumeroPazienti(), d.getDocID(), d.getSurname(), d.getName()));
        }
        return result;
	}
	
	
	public Collection<String> countPatientsPerSpecialization(){
   		Map<String, Integer> specializationCount = new HashMap<>();
        for (Doctor d : doctors.values()) {
            specializationCount.put(d.getSpecialty(), specializationCount.getOrDefault(d.getSpecialty(), 0) + d.getNumeroPazienti());
        }
        List<Map.Entry<String, Integer>> sortedSpecializations = new ArrayList<>(specializationCount.entrySet());
        sortedSpecializations.sort((e1, e2) -> {
            int cmp = e2.getValue().compareTo(e1.getValue());
            if (cmp != 0) return cmp;
            return e1.getKey().compareTo(e2.getKey());
        });
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedSpecializations) {
            result.add(String.format("%3d - %s", entry.getValue(), entry.getKey()));
        }
        return result;
	}

}
