package university;

public class Student {
    
    private String first;
    private String last;
    private Integer id;

    public static final int MAX_STUDYPLAN = 25;

    private Course[] studyplan = new Course[MAX_STUDYPLAN];
    private int nextStudyPlan = 0;

    private Exam[] exams = new Exam[MAX_STUDYPLAN];
    private int nextExam = 0;

    public Student(String first, String last, Integer ID){
        this.first = first;
        this.last = last;
        this.id = ID;
    }

    public String getFirst(){
        return first;
    }

    public String getLast(){
        return last;
    }

    public Integer getID(){
        return id;
    }

    public int addStudyPlan(Course c){
        studyplan[nextStudyPlan] = c;
        return nextStudyPlan++;
    }

    public String getStudyPlan(){
        StringBuffer bf = new StringBuffer();
        for(Course c : studyplan){
            if(c != null){
                bf.append(c.toString()).append("\n");
            } 
        }
        return bf.toString();
    }

    public Integer addExam(Exam e){
        exams[nextExam] = e;
        return nextExam++;
    }
    
    public Double average(){
        Double sum = 0.0;
        for(Exam e : exams){
            if(e != null){
                sum += e.getScore();
            }
        }
        return sum / nextExam;
    }

    public double getScore(){
        double avg = average();
        if(Double.isNaN(avg)){
            return Double.NEGATIVE_INFINITY;
        }
        else{
            avg += nextExam / nextStudyPlan * 10;
        }
        return avg;
    }

    @Override
    public String toString(){
        return id + " " + first + " " + last;
    }

}
