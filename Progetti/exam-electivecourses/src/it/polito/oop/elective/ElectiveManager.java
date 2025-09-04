package it.polito.oop.elective;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.text.StyledEditorKit;


public class ElectiveManager {

    Map<String, Course> corsi = new HashMap<>();
    Map<String, Student> students = new HashMap<>();
    Map<String, List<String>> enrolled = new HashMap<>();
    List<Notifier> notifiers = new ArrayList<>();


    public static class Course{
        String nameCourse;
        int numPosti;
        List<Student> studenti;

        public Course(String nameCourse, int numPosti){
            this.nameCourse = nameCourse;
            this.numPosti = numPosti;
            this.studenti = new ArrayList<>();
        }

        public String getNameCourse(){
            return nameCourse;
        }

        public int getNumPosti(){
            return numPosti;
        }

        public List<Student> getStudentsforCourse(){
            return studenti;
        }

        public void setStudentsforCourse(List<Student> s){
            this.studenti = s;
        }

        public void addStudent(Student s){
            if(studenti.size() < numPosti){
                studenti.add(s);
            }
        }

    }

    public static class Student{

        String id;
        Double average;
        List<Course> couesesEnrolled;

        public Student(String id, Double avg){
            this.id = id;
            this.average = avg;
            this.couesesEnrolled = new ArrayList<>();
        }

        public String getIDStudent(){
            return id;
        }

        public Double getMedia(){
            return average;
        }

        public List<Course> getCoursesEnrolled(){
            return couesesEnrolled;
        }

    }

    public void addCourse(String name, int availablePositions) {
        
        if(!this.corsi.containsKey(name)){
            this.corsi.put(name, new Course(name, availablePositions));
        }
    }
    
    public SortedSet<String> getCourses(){

        SortedSet<String> corsi = new TreeSet<>();

        for(Map.Entry<String, Course> c : this.corsi.entrySet()){
            Course corso = c.getValue();
            corsi.add(corso.getNameCourse());
        }

        return corsi;
    }
    
    
    public void loadStudent(String id, double gradeAverage){
        
        if(!this.students.containsKey(id)){
            this.students.put(id, new Student(id, gradeAverage));
        }
    }

   
    public Collection<String> getStudents(){

        Collection<String> studenti = new ArrayList<>();

        for(Map.Entry<String, Student> s : this.students.entrySet()){
            Student studente = s.getValue();
            studenti.add(studente.getIDStudent());
        }
        return studenti;
    }
    
    
    public Collection<String> getStudents(double inf, double sup){

        Collection<String> mediaStudenti = new ArrayList<>();

        for(Map.Entry<String, Student> s : this.students.entrySet()){
            Double media = s.getValue().getMedia();
            if(media <= sup && media >= inf){
                mediaStudenti.add(s.getKey());
            }
        }

        return mediaStudenti;
    }


    
    public int requestEnroll(String id, List<String> courses)  throws ElectiveException {

        int countCorsi = 0;

        if(courses.size() == 0 || courses.size() > 3){
            throw new ElectiveException();
        }

        if(!this.students.containsKey(id)){
            throw new ElectiveException();
        }

        for(String stringa : courses){
            if(!this.corsi.containsKey(stringa)){
                throw new ElectiveException();
            }
        }

        Student s = this.students.get(id);
        
        for(Map.Entry<String, List<String>> en : this.enrolled.entrySet()){
            if(!en.getKey().equals(id)){
                countCorsi++;
                this.enrolled.put(id, courses);
                for(Notifier n : this.notifiers){
                    n.requestReceived(id);
                }
            }
        }
        
        return countCorsi;
    }
    
    
    public Map<String,List<Long>> numberRequests(){

        Map<String, List<Long>> result = new HashMap<>();

        for(Map.Entry<String, Course> c: this.corsi.entrySet()){
            for(Map.Entry<String, List<Long>> r: result.entrySet()){
                if(r.getKey().equals(c.getKey())){
                    List<Long> values = new ArrayList<>(Collections.nCopies(3, 0L));
                    result.put(c.getKey(), values);
                }
            }
        }
        for (List<String> preferences : this.enrolled.values()) {
            for (int i = 0; i < preferences.size(); i++) {
                String course = preferences.get(i);
                result.get(course).set(i, result.get(course).get(i) + 1);
            }
        }
        return result;
    }
    
    
    
    public long makeClasses() {

        int numStudenti = 0;

        for(Map.Entry<String, Student> s: this.students.entrySet()){
            if(s.getValue().getCoursesEnrolled().size() == 0){
                for(Notifier n: notifiers){
                    Course c = this.corsi.get(s.getValue().getCoursesEnrolled());
                    n.assignedToCourse(s.getKey(), c.getNameCourse());
                }
                numStudenti++;
            }
        }
        return numStudenti;
    }
    
    
    public Map<String,List<String>> getAssignments(){

        Map<String, Double> studentSorted = new HashMap<>();
        Map<String, List<String>> assignment = new HashMap<>();
        List<String> student = new ArrayList<>();

        studentSorted = this.students.values().stream().sorted(Comparator.comparing(Student::getMedia).reversed())
        .collect(Collectors.toMap(Student::getIDStudent, Student::getMedia));

        
        for(Map.Entry<String, Double> stud: studentSorted.entrySet()){
            student.add(stud.getKey());
        }

        for(Map.Entry<String, List<String>> ass: assignment.entrySet()){
            for(Map.Entry<String, Course> c: this.corsi.entrySet()){
                if(ass.getKey().equals(c.getKey())){
                    for(String s: student){
                        if(c.getValue().getNumPosti() > 0){
                            ass.getValue().add(s);
                        }
                    }
                }
            }
        }
        
        return assignment;
    }
    
    

    public void addNotifier(Notifier listener) {
        notifiers.add(listener);
    }
    
  
    public double successRate(int choice){
        return -1;
    }

 
    public List<String> getNotAssigned(){
        return null;
    }
    
    
}
