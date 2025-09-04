package jobOffers; 
import java.util.*;
import java.util.stream.Collectors;


public class JobOffers  {

	
	List<String> skills = new ArrayList<>();

	public static class Offer{
		private String position;
		private List<String> skill;

		public Offer(String position){
			this.position = position;
			this.skill = new ArrayList<>();
		}

		public String getPosition(){
			return position;
		}

		public List<String> getSkillsRequired(){
			return this.skill;
		}

		public void addSkillRequired(String s){
			if(!this.skill.contains(s)){
				this.skill.add(s);
			}
		}


	}

//R1
	public int addSkills (String... skills) {

		for(String s : skills){
			if(!this.skills.contains(s)){
				this.skills.add(s);
			}
		}
		return this.skills.size();
	}
	
	Map<String, Offer> offers = new TreeMap<>();

	public int addPosition (String position, String... skillLevels) throws JOException {

		String skill;
		int level = 0;

		for(String s: skillLevels){
			String[] parts = s.split(":");
			skill = parts[0];
			level = Integer.parseInt(parts[1]);
			if(!this.skills.contains(skill) || (level < 4 && level > 8)){
				throw new JOException("Error");
			}
		}

		if(this.offers.containsKey(position)){
			throw new JOException("Error");
		}


		int somma = 0;
		int count = 0;
		for(String s: skillLevels){
			String[] parts = s.split(":");
			skill = parts[0];
			level = Integer.parseInt(parts[1]);
			this.offers.put(position, new Offer(position));
			this.offers.get(position).addSkillRequired(s);
			somma += level;
			count++;
		}

		return somma/count;
	}
	
//R2	

	public static class Candidato{
		private String nome;
		private List<String> skill;

		public Candidato(String nome){
			this.nome = nome;
			this.skill = new ArrayList<>();
		}

		public String getNome(){
			return nome;
		}

		public List<String> getSkillsRequired(){
			return this.skill;
		}

		public void addSkillRequired(String s){
			if(!this.skill.contains(s)){
				this.skill.add(s);
			}
		}


	}

	Map<String, Candidato> candidati = new TreeMap<>();
	List<String> applications = new ArrayList<>();

	public int addCandidate (String name, String... skills) throws JOException {

		if(this.candidati.containsKey(name)){
			throw new JOException(name);
		}

		for(String s: skills){
			this.candidati.put(name, new Candidato(name));
			this.candidati.get(name).addSkillRequired(s);
		}

		return this.candidati.size();
	}
	
	public List<String> addApplications (String candidate, String... positions) throws JOException {

		if(!this.candidati.containsKey(candidate)){
			throw new JOException(candidate);
		}

		for(String s: positions){
			if(!this.offers.containsKey(s)){
				throw new JOException(candidate);
			}
		}

		List<String> skillsCandidato = this.candidati.get(candidate).getSkillsRequired();
		List<String> skillsRichieste = new ArrayList<>();

		for(String s: positions){
			skillsRichieste = this.offers.get(s).getSkillsRequired();
		}

		for(String s: skillsRichieste){
			for(String s1: skillsCandidato){
				if(!s.equals(s1) || !this.skills.contains(s) || !this.skills.contains(s1)){
					throw new JOException(candidate);
				}
			}
		}

		StringBuffer sb = new StringBuffer();

		for(String s: positions){
			sb.append("[").append(candidate).append(":").append(s).append("]");
			this.applications.add(sb.toString());
		}
		
		

		return this.applications;
	} 
	
	public TreeMap<String, List<String>> getCandidatesForPositions() {

		TreeMap<String, List<String>> candidateForPosition = new TreeMap<>();
		for(Candidato c : this.candidati.values()){
			for(String s: this.applications){
				String[] parts = s.split(":");
				if(parts[0].equals(c.getNome())){
					candidateForPosition.put(c.getNome(), new ArrayList<>());
					candidateForPosition.get(c.getNome()).add(parts[1]);
				}
			}
		}

		
		return candidateForPosition;
	}
	
	
//R3

	public static class Consulente{
		private String nome;
		private List<String> skill;
		Map<String, Integer> ratings = new TreeMap<>();

		public Consulente(String nome){
			this.nome = nome;
			this.skill = new ArrayList<>();
		}

		public String getNome(){
			return nome;
		}

		public List<String> getSkillsRequired(){
			return this.skill;
		}

		public void addSkillRequired(String s){
			if(!this.skill.contains(s)){
				this.skill.add(s);
			}
		}

		public Map<String, Integer> getRatings(){
			return this.ratings;
		}

		public void addRatings(String s, int rat){
			if(!this.ratings.containsKey(s)){
				this.ratings.put(s, rat);
			}
		}


	}

	Map<String, Consulente> consulenti = new TreeMap<>();

	public int addConsultant (String name, String... skills) throws JOException {
		if(this.consulenti.containsKey(name)){
			throw new JOException(name);
		}

		for(String s: skills){
			this.consulenti.put(name, new Consulente(name));
			this.consulenti.get(name).addSkillRequired(s);
		}

		return this.consulenti.size();
	}
	
	public Integer addRatings (String consultant, String candidate, String... skillRatings)  throws JOException {
		
		if(!this.consulenti.containsKey(consultant)){
			throw new JOException(consultant);
		}
		if(!this.candidati.containsKey(candidate)){
			throw new JOException(candidate);
		}

		for(String s: skillRatings){
			if(!this.offers.containsKey(s)){
				throw new JOException(candidate);
			}
		}

		List<String> skillsCandidato = this.candidati.get(candidate).getSkillsRequired();
		List<String> skillsConsulente = this.consulenti.get(consultant).getSkillsRequired();

		for(String s: skillsCandidato){
			for(String s1: skillsConsulente){
				if(!s.equals(s1) || !this.skills.contains(s) || !this.skills.contains(s1)){
					throw new JOException(candidate);
				}
			}
		}

		String skill;
		int rating = 0;
		int somma = 0;
		int count = 0;
		for(String s: skillRatings){
			String[] parts = s.split(":");
			skill = parts[0];
			rating = Integer.parseInt(parts[1]);
			if(!this.skills.contains(skill) || (rating < 4 && rating > 10)){
				throw new JOException("Error");
			}
			else{
				somma += rating;
				this.consulenti.get(consultant).addRatings(skill, rating);
				count++;
			}
		}
		

		

		return somma/count;
	}
	
//R4
	public List<String> discardApplications() {
		return null;
	}
	
	 
	public List<String> getEligibleCandidates(String position) {
		return null;
	}
	

	
}

		
