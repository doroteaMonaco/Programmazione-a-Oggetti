package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Topic {

	private String keyword;
	List<Topic> subTopics;

	public Topic(String keyword){
		this.keyword = keyword;
		this.subTopics = new ArrayList<>();
	}

	public String getKeyword() {
        return keyword;
	}
	
	@Override
	public String toString() {
	    return keyword;
	}

	public boolean addSubTopic(Topic topic) {

		boolean flag = false;
		if(!this.subTopics.contains(topic)){
			this.subTopics.add(topic);
			flag = true;
		}
        return flag;
	}

	
	public List<Topic> getSubTopics() {
        return this.subTopics.stream().sorted(Comparator.comparing(Topic::getKeyword)).toList();
	}
}
