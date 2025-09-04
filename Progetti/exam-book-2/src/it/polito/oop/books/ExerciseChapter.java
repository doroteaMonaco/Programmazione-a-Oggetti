package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ExerciseChapter {

    private List<Topic> topicsExercise;
    private String title;
    private int numPages;

    public ExerciseChapter(String title, int numPages){
        this.title = title;
        this.numPages = numPages;
        this.topicsExercise = new ArrayList<>();
    }

    public List<Topic> getTopics() {
        return this.topicsExercise.stream().sorted(Comparator.comparing(Topic::getKeyword)).toList();
	};
	

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public int getNumPages() {
        return numPages;
    }
    
    public void setNumPages(int newPages) {
        this.numPages = newPages;
    }
    

	public void addQuestion(Question question) {
        Topic t = question.getMainTopic();

        if(!this.topicsExercise.contains(t)){
            this.topicsExercise.add(t);
        }
	}	
}
