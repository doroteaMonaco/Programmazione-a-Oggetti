package university;
import java.util.Comparator;
import java.util.logging.Logger;

public class University {

// R1
	
	private String nameUniversity;
	private String nameRector;
	private String surnameRector;

	public University(String name){
		this.nameUniversity = name;
	}

	public String getUniversity(){
		return nameUniversity;
	}

	public void setRector(String first, String last){
		this.nameRector = first;
		this.surnameRector = last;
	}
	
	public String getRector(){
		return nameRector + " " + surnameRector;
	}
	
// R2

	private static final int MAX_STUDENTS = 100;
	private static final int FIRST_STUDENT = 10000;

	private Student[] students = new Student[MAX_STUDENTS];
	private int nextStudent = FIRST_STUDENT;

	public int enroll(String first, String last){
		Student s = new Student(first, last, nextStudent);
		students[nextStudent - FIRST_STUDENT] = s;
		return nextStudent++;
	}
	
	public Student getStudent(int id){
		return students[id - FIRST_STUDENT];
	}
	
	public String student(int id){
		Student s = students[id - FIRST_STUDENT];
		return s.toString();
	}
	


	// R3
	
	private static final int MAX_COURSES = 50;
	private static final int FIRST_COURSE = 10;

	private Course[] courses = new Course[MAX_COURSES];
	private int nextCourse = FIRST_COURSE;

	public int activate(String title, String teacher){
		Course c = new Course(title, teacher, nextCourse);
		courses[nextCourse - FIRST_COURSE] = c;
		return nextCourse++;
	}
	
	public Course getCourse(int code){
		return courses[code - FIRST_COURSE];
	}

	public String course(int code){
		Course c = courses[code - FIRST_COURSE];
		return c.toString();
	}
	
// R4
	
	
	public void register(int studentID, int courseCode){
		Student s = getStudent(studentID);
		Course c = getCourse(courseCode);
		s.addStudyPlan(c);
		c.studentEnrolled(s);
	}
	
	
	public String listAttendees(int courseCode){
		return getCourse(courseCode).getStudentEnrolled();
	}


	public String studyPlan(int studentID){
		return getStudent(studentID).getStudyPlan();
	}

// R5
	
	public void exam(int studentId, int courseID, int grade) {
		Student s = getStudent(studentId);
		Course c = getCourse(courseID);
		Exam e = new Exam(s, c, grade);
		s.addExam(e);
		c.addExam(e);
	}

	
	public String studentAvg(int studentId) {
	
		Student s = getStudent(studentId);
		double avg = s.average();
		if(avg != Double.NaN){
			return "Student" + studentId + ":" + avg;
		}
		else{
			return "Student" + studentId + "hasn't taken any exam";
		}
	}
	
	
	public String courseAvg(int courseId) {

		Course c = getCourse(courseId);
		double avg = c.average();
		if(avg != Double.NaN){
			return "The average for the course" + getCourse(courseId).getNameCourse() + "is:" + avg;
		}
		else{
			return "No student has taken the exam for the course" + getCourse(courseId).getNameCourse();
		}

		
	}
	

// R6
	
	public String topThreeStudents() {
		Student[] top = top(3, Comparator.comparingDouble(Student::getScore));
		String res = " ";
		for(Student s : top){
			if(Double.NEGATIVE_INFINITY == s.getScore()){
				continue;
			}
			else{
				res += s.getFirst() + " " + s.getLast() + " " + s.getScore() + "\n";
			}
		}
		return res;
	}

private Student[] top(int i, Comparator<Student> comparingDouble) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'top'");
	}

	// R7
    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
    public static final Logger logger = Logger.getLogger("University");

}
