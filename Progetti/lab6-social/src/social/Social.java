package social;

import java.util.*;


public class Social {


	public static class Person{
		String code, name, surname;
		List<Person> friendsOfPerson;
		List<Post> postOfPerson;

		public Person(String code, String name, String surname){
			this.code = code;
			this.name = name;
			this.surname = surname;
			this.friendsOfPerson = new ArrayList<>();
			this.postOfPerson = new ArrayList<>();
		}

		public String getCode(){
			return code;
		}

		public String getName(){
			return name;
		}

		public String getSurname(){
			return surname;
		}

		public String toString(){
			return code + " " + name + " " + surname;
		}

		public void addPerson(Person p){
			if(!this.friendsOfPerson.contains(p)){
				this.friendsOfPerson.add(p);
			}
		}

		public List<Person> getFriendsOfPerson(){
			return this.friendsOfPerson;
		}

		public void addPost(Post p){
			if(!this.postOfPerson.contains(p)){
				this.postOfPerson.add(p);
			}
		}

		public List<Post> getPosts(){
			return this.postOfPerson;
		}
	}

	public static class Group{
		private String name;
		private List<Person> personOfGroup;

		public Group(String name){
			this.name = name;
			this.personOfGroup = new ArrayList<>();
		}

		public String getName(){
			return name;
		}

		public void addPersonGroup(Person p){
			if(!this.personOfGroup.contains(p)){
				this.personOfGroup.add(p);
			}
		}

		public List<Person> getPersonInGroup(){
			return this.personOfGroup;
		}
	}
	
	public Map<String, Person> person = new HashMap<>();

	public void addPerson(String code, String name, String surname)
			throws PersonExistsException {

		if(this.person.containsKey(code)){
			throw new PersonExistsException();
		}

		this.person.put(code, new Person(code, name, surname));

	}

	public String getPerson(String code) throws NoSuchCodeException {

		if(!this.person.containsKey(code)){
			throw new NoSuchCodeException();
		}

		return this.person.get(code).toString();
	}

	
	public void addFriendship(String codePerson1, String codePerson2)
			throws NoSuchCodeException {

		if(!this.person.containsKey(codePerson1)){
			throw new NoSuchCodeException();
		}
		if(!this.person.containsKey(codePerson2)){
			throw new NoSuchCodeException();
		}

		Person p1 = this.person.get(codePerson1);
		Person p2 = this.person.get(codePerson2);

		p1.addPerson(p2);
		p2.addPerson(p1);

	}

	
	public Collection<String> listOfFriends(String codePerson)
			throws NoSuchCodeException {

		if(!this.person.containsKey(codePerson)){
			throw new NoSuchCodeException();
		}

		Collection<String> friends = new ArrayList<>();
		Person p = this.person.get(codePerson);

		for(Person friend: p.getFriendsOfPerson()){
			friends.add(friend.getCode());
		}
		return friends;
	}

	
	public Collection<String> friendsOfFriends(String codePerson)
			throws NoSuchCodeException {

		if(!this.person.containsKey(codePerson)){
			throw new NoSuchCodeException();
		}

		Collection<String> fof = new ArrayList<>();
		Person p = this.person.get(codePerson);

		for(Person pers : p.getFriendsOfPerson()){
			for(Person persona : pers.getFriendsOfPerson()){
				fof.add(persona.getCode());
			}
		}

		return fof;
	}

	
	public Collection<String> friendsOfFriendsNoRepetition(String codePerson)
			throws NoSuchCodeException {
		if(!this.person.containsKey(codePerson)){
			throw new NoSuchCodeException();
		}

		Collection<String> friendsofFriends = new ArrayList<>();

		friendsofFriends = listOfFriends(codePerson);
		return friendsofFriends.stream().distinct().toList();
	}

	List<Group> groups = new ArrayList<>();
	public void addGroup(String groupName) {

		Group g = new Group(groupName);
		if(!this.groups.contains(g)){
			this.groups.add(g);
		}
	}

	public Collection<String> listOfGroups() {

		Collection<String> g = new ArrayList<>();

		for(Group group : this.groups){
			g.add(group.getName());
		}
		return g;
	}

	
	public void addPersonToGroup(String codePerson, String groupName) throws NoSuchCodeException {


		Person p = this.person.get(codePerson);
		boolean flag = false;

		if(!this.person.containsKey(codePerson)){
			throw new NoSuchCodeException();
		}
		

		for(Group g : this.groups){
			if(g.getName().equals(groupName)){
				flag = true;
				g.addPersonGroup(p);

			}
		}

		if(flag == false){
			throw new NoSuchCodeException();
		}
		
	}

	
	public Collection<String> listOfPeopleInGroup(String groupName) {

		
		Collection<String> peopleGroup = new ArrayList<>();

		for(Group g: this.groups){
			if(g.getName().equals(groupName)){
				for(Person p: g.getPersonInGroup()){
					peopleGroup.add(p.getCode());
				}
			}
		}

		return peopleGroup;
	}

	
	public String personWithLargestNumberOfFriends() {

		int max = 0, num = 0;
		String code = null;

		for(Person p : this.person.values()){
			num = p.getFriendsOfPerson().size();
			if(num >= max){
				max = num;
				code = p.getCode();
			}
		}
		return code;
	}

	
	public String personWithMostFriendsOfFriends() {
		int max = 0, num = 0;
		String code = null;

		for(Person p : this.person.values()){
			for(Person pers: p.getFriendsOfPerson()){
				num = pers.getFriendsOfPerson().size();
			    if(num >= max){
					max = num;
					code = p.getCode();
				}
			}
		}
		return code;
	}

	public String largestGroup() {

		int max = 0, num = 0;
		String nome = null;

		for(Group g: this.groups){
			num = g.getPersonInGroup().size();
			if(num >= max){
				max = num;
				nome = g.getName();
			}
		}
		return nome;
	}

	/**
	 * Find the code of the person that is member of
	 * the largest number of groups
	 * 
	 * @return the code of the person
	 */
	public String personInLargestNumberOfGroups() {
		return null;
	}

	public static class Post{

		private Person p;
		private String text;

		public Post(Person p, String text){
			this.p = p;
			this.text = text;
		}

		public Person getPersona(){
			return p;
		}

		public String getText(){
			return text;
		}
	}

	Map<String, Post> posts = new HashMap<>();

    public String post(String author, String text) {

		int codePost = this.posts.size() + 1;
		String code = null;

		
		if(this.person.containsKey(author)){
			Person p = this.person.get(author);
			code = String.valueOf(codePost);
			Post post = new Post(p, text);
			if(!this.posts.containsValue(post)){
				this.posts.put(code, post);
			}
		}
		return code;
    }

	
    public String getPostContent(String author, String pid) {

		Post p = this.posts.get(pid);
		String text = null;

		if(this.posts.containsValue(p)){
			text = p.getText();
		}
		return text;
    }

	
    public long getTimestamp(String author, String pid) {

		long time = 0;

		if(!this.posts.containsKey(pid)){
			time = System.currentTimeMillis();
		}
		return time;
    }


	/**
	 * returns the list of post of a given author paginated 
	 * 
	 * @param author	author of the post
	 * @param pageNo	page number (starting at 1)
	 * @param pageLength page length
	 * @return the list of posts id
	 */
    public List<String> getPaginatedUserPosts(String author, int pageNo, int pageLength) {
		return null;
    }

	/**
	 * returns the paginated list of post of friends 
	 * 
	 * the returned list contains the author and the id of a post separated by ":"
	 * 
	 * @param author	author of the post
	 * @param pageNo	page number (starting at 1)
	 * @param pageLength page length
	 * @return the list of posts key elements
	 */
	public List<String> getPaginatedFriendPosts(String author, int pageNo, int pageLength) {
		return null;
	}
}