package university;

public class Course {

    private static final int MAX_ENROLLED = 100;

    private Integer code;
    private String title;
    private String teacher;

    private Student[] enrolled = new Student[MAX_ENROLLED];
	private int nextEnrolled = 0;

    private Exam[] exams = new Exam[MAX_ENROLLED];
    private int nextExam = 0;

    public Course(Integer code, String title, String teacher){
        this.code = code;
        this.title = title;
        this.teacher = teacher;
    }

    public Integer getCode(){
        return code;
    }

    public String getTitle(){
        return title;
    }

    public String getTeacher(){
        return teacher;
    }

    public int enrolledStudent(Student s){  
        enrolled[nextEnrolled] = s;
        return nextEnrolled++;
    }

    public String getEnrolledStudent(){
        String result = " ";

        for(int i = 0; i< nextEnrolled; i++){
            result += enrolled[i].toString();
        }

        return result;
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

    public String toString(){
        return code + "," + title + "," + teacher;
    }
}
