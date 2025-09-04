package university;

public class Student {

    private static final int MAX_STUDY_PLAN = 25;

    private Integer id;
    private String first;
    private String last;

    private Course[] studyPlan = new Course[MAX_STUDY_PLAN];
	private int nextStudyPlan = 0;

    private Exam[] exams = new Exam[MAX_STUDY_PLAN];
    private int nextExam = 0;

    public Student(Integer id, String first, String last){
        this.id = id;
        this.first = first;
        this.last = last;
    }

    public Integer getId(){
        return id;
    }

    public String getFirst(){
        return first;
    }

    public String getLast(){
        return last;
    }

    public int addStudyPlan(Course c){  //Aggiunge il piano di studi
        studyPlan[nextStudyPlan] = c;
        return nextStudyPlan++;
    }

    public String getStudyPlan(){
        StringBuffer bf = new StringBuffer();

        for (Course c : studyPlan){
            if(c != null){
                bf.append(c.toString()).append('\n');
            }
        }
        return bf.toString().trim(); //trim toglie tutti i caratteri speciali
    }

    public String toString(){
        return id + " " + first + " " + last;
    }

    public int addExam(Exam e){
        exams[nextExam] = e;
        return nextExam++;
    }

    public double average(){
        if(nextExam == 0){
            return Double.NaN;
        }
        double sum = 0.0;
        for(Exam e : exams){
            if( e == null){
                break;
            }
            sum += e.getGrade();
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

}
