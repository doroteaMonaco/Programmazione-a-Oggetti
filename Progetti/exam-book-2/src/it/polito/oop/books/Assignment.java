package it.polito.oop.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Assignment {

    private String student;
	private ExerciseChapter ec;
    Map<Question, Double> risposte = new HashMap<>();

    public Assignment(String student, ExerciseChapter ec){
        this.student = student;
        this.ec = ec;
    }

    public String getID() {
        return student;
    }

    public ExerciseChapter getChapter() {
        return ec;
    }

    public double addResponse(Question q,List<String> answers) {

        int corrette = 0;
        int sbagliate = 0;

        int numTot = 0;
        double punteggio = 0.0;
        
        for(String s: answers){
            numTot++;
            for(String cor: q.getCorrectAnswers()){
                if(cor.equals(s)){
                    corrette++;
                }
            }
            for(String sba: q.getIncorrectAnswers()){
                if(sba.equals(s)){
                    sbagliate++;
                }
            }

            punteggio = (numTot-corrette-sbagliate)/numTot;
            risposte.put(q, punteggio);
        }


        return (numTot-corrette-sbagliate)/numTot;
    }
    
    public double totalScore() {

        double tot = 0.0;
        for(Double d: this.risposte.values()){
            tot += d;
        }
        return tot;
    }

}
