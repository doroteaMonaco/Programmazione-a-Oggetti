package university;

public class Course {
    
    private String name;
    private String teacher;
    private Integer code;

    private String result;
    public static final Integer MAX_ENROLLED = 100;

    private Student[] enrolled = new Student[MAX_ENROLLED];
    private int nextEnrolled = 0;

    private Exam[] exams = new Exam[MAX_ENROLLED];
    private int nextExam = 0;

    public Course(String name, String teacher, Integer code){
        this.name = name;
        this.code = code;
        this.teacher = teacher;
    }

    public String getNameCourse(){
        return name;
    }

    public String getTeacher(){
        return teacher;
    }

    public Integer getCode(){
        return code;
    }

    public int studentEnrolled(Student s){
        enrolled[nextEnrolled] = s;
        return nextEnrolled++;
    }

    public String getStudentEnrolled(){
        result = " ";

        for(int i = 0; i < MAX_ENROLLED; i++){
            result += enrolled[i].toString();
        }
        return result;
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

    @Override
    public String toString(){
        return code + "," + name + "," + teacher;
    }
}
