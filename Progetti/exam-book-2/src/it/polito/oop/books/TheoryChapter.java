package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class TheoryChapter {

    private String text, title;
    private List<Topic> topicsTheory;
    private int numPages;

    public TheoryChapter(String text, String title, int numPages){
        this.text = text;
        this.title = title;
        this.numPages = numPages;
        this.topicsTheory = new ArrayList<>();
    }

    public String getText() {
		return text;
	}

    public void setText(String newText) {
        this.text = newText;
    }


	public List<Topic> getTopics() {
        return this.topicsTheory.stream().sorted(Comparator.comparing(Topic::getKeyword)).toList();
	}

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
    
    public void addTopic(Topic topic) {
        if (!topicsTheory.contains(topic)) {
            topicsTheory.add(topic);
            for (Topic subTopic : topic.getSubTopics()) {
                addTopic(subTopic);
            }
        }
    }
}
