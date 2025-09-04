package university;

public class Exam {
    private Student student;
    private Course course;
    private Integer score;

    public Exam(Student student, Course course, Integer score){
        this.student = student;
        this.score = score;
        this.course = course;
    }

    
    public Student getStudent(){
        return student;
    } 

    public Course getCourse(){
        return course;
    } 

    public Integer getScore(){
        return score;
    } 

}