package it.polito.oop.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Question {
	
	String question;
	Topic t;
	Map<String, Boolean> answers;
	List<String> correctAnswers;
	List<String> wrongAnswers;

	public Question(String question, Topic t){
		this.question = question;
		this.t = t;
		this.answers = new HashMap<>();
		this.correctAnswers = new ArrayList<>();
		this.wrongAnswers = new ArrayList<>();
	}

	public String getQuestion() {
		return question;
	}
	
	public Topic getMainTopic() {
		return t;
	}

	public void addAnswer(String answer, boolean correct) {
		if(correct == true){
			if(!this.correctAnswers.contains(answer)){
				this.correctAnswers.add(answer);
			}
		}

		if(correct == false){
			if(!this.wrongAnswers.contains(answer)){
				this.wrongAnswers.add(answer);
			}
		}
		
		if(!this.answers.containsKey(answer)){
			this.answers.put(answer, correct);
		}
	}
	
    @Override
    public String toString() {
        return question + "?" + " " + "(" + t + ")";
    }

	public long numAnswers() {
	    return this.answers.size();
	}

	public Set<String> getCorrectAnswers() {

		Set<String> ca = new HashSet<>();

		for(String c: this.correctAnswers){
			ca.add(c);
		}
		return ca;
	}

	public List<String> getAnswers() {

		List<String> ans = new ArrayList<>();

		for(String s : this.answers.keySet()){
			ans.add(s);
		}
		return ans;
	}

	public Set<String> getIncorrectAnswers() {
        Set<String> wa = new HashSet<>();

		for(String w: this.wrongAnswers){
			wa.add(w);
		}
		return wa;
	}
}
