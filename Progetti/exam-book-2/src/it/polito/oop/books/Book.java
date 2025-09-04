package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Book {

	Map<String, Topic> topics = new HashMap<>();
	Map<String, Question> questions = new HashMap<>();
	Map<String, TheoryChapter> theories = new HashMap<>();
	Map<String, ExerciseChapter> exercises = new HashMap<>();
    
	public Topic getTopic(String keyword) throws BookException {

		if(keyword.isEmpty() || keyword == null){
			throw new BookException();
		}

		Topic topic = new Topic(keyword);
		
		if(!this.topics.containsKey(keyword)){
			this.topics.put(keyword, topic);
			return topic;
		}

	    return this.topics.get(keyword);
	}

	public Question createQuestion(String question, Topic mainTopic) {

		Question quest = new Question(question, mainTopic);
		if(!this.questions.containsKey(question)){
			this.questions.put(question, quest);
		}
        return quest;
	}

	public TheoryChapter createTheoryChapter(String title, int numPages, String text) {
        
		TheoryChapter tc = new TheoryChapter(text, title, numPages);

		if(!this.theories.containsKey(text)){
			this.theories.put(text, tc);
			
		}
		return tc;
	}

	public ExerciseChapter createExerciseChapter(String title, int numPages) {
        
		ExerciseChapter ec = new ExerciseChapter(title, numPages);

		if(!this.exercises.containsKey(title)){
			this.exercises.put(title, ec);
		}
		return ec;
	}

	public List<Topic> getAllTopics() {

		List<Topic> allTopics = new ArrayList<>();

		for(TheoryChapter tc: this.theories.values()){
			for(Topic t : tc.getTopics()){
				if(!allTopics.contains(t)){
					allTopics.add(t);
				}
			}
		}

		for(ExerciseChapter ec: this.exercises.values()){
			for(Topic t : ec.getTopics()){
				if(!allTopics.contains(t)){
					allTopics.add(t);
				}
			}
		}

        return allTopics.stream().sorted(Comparator.comparing(Topic::getKeyword)).toList();
	}

	public boolean checkTopics() {

		boolean flag = false;
		for(ExerciseChapter ec: this.exercises.values()){
			for(Topic tec: ec.getTopics()){
				for(TheoryChapter tc : this.theories.values()){
					for(Topic ttc : tc.getTopics()){
						if(ttc.equals(tec)){
							flag = true;
						}
					}
				}
			}
		}
        return flag;
	}

	List<Assignment> compiti = new ArrayList<>();
	public Assignment newAssignment(String ID, ExerciseChapter chapter) {

		Assignment a = new Assignment(ID, chapter);

		if(!this.compiti.contains(a)){
			this.compiti.add(a);
		}

        return a;
	}
	
    public Map<Long,List<Question>> questionOptions(){

		Map<Long, List<Question>> options = new TreeMap<>();
		long numAnswers = 0;

		for(Question q : this.questions.values()){
			for(String s: q.getAnswers()){
				numAnswers += 1;
			}
			options.put(numAnswers, new ArrayList<>());
			options.get(numAnswers).add(q);
			numAnswers = 0;
		}

        return options;
    }
}
