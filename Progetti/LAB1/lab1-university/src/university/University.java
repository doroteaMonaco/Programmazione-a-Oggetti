package university;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Logger;


public class University {

// R1
    
	private static final int MAX_STUDENTS = 1000;
	private static final int FIRST_STUDENT = 10000;
	private static final int FIRST_COURSE = 10;
	private static final int MAX_COURSES = 50;
	

	private String nameUniversity;
	private String NameRector;
	private String SurnameRector;

	private Student[] students = new Student[MAX_STUDENTS];
	private int nextStudentID = FIRST_STUDENT;

	private Course[] courses = new Course[MAX_COURSES];
    private int nextCourseCode = FIRST_COURSE;

	public University(String name){
		this.nameUniversity = name;
	}
	
	
	public String getName(){
		return nameUniversity;
	}
	

	public void setRector(String first, String last){
		this.NameRector = first;
		this.SurnameRector = last;
	}
	

	public String getRector(){
		return NameRector + " " + SurnameRector;
	}
	
// R2

	public int enroll(String first, String last){
		Student s = new Student(nextStudentID, first, last);
		students[nextStudentID - FIRST_STUDENT] = s;
		return nextStudentID++;
	}
	
	public String student(int id){
		return getStudent(id).toString();
	}

	private Student getStudent(int id){
		return students[id - FIRST_STUDENT];
	}
	
// R3
	
	public int activate(String title, String teacher){
		Course c = new Course(nextCourseCode, title, teacher);
		courses[nextCourseCode - FIRST_COURSE] = c;
		return nextCourseCode++;
	}
	
	public String course(int code){
		return getCourse(code).toString();
	}

	private Course getCourse(int code){
		return courses[code - FIRST_COURSE];
	}
	
// R4
	
	public void register(int studentID, int courseCode){
		Student s = getStudent(studentID);
		Course c = getCourse(courseCode);
		s.addStudyPlan(c);
		c.enrolledStudent(s);
	}
	
	public String listAttendees(int courseCode){
		return getCourse(courseCode).getEnrolledStudent();
	}

	public String studyPlan(int studentID){
		return getStudent(studentID).getStudyPlan();
	}

	protected Student[] top(int n, Comparator<Student> cmp){
		n=Math.min(n, nextStudentID - FIRST_STUDENT);
		Student[] sorted = Arrays.copyOf(students, nextStudentID - FIRST_STUDENT);
		Arrays.sort(sorted, cmp.reversed());
		return Arrays.copyOf(sorted, n);
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
		if(avg == Double.NaN){
			return "Student" + studentId + "hasn't taken any exam";
		}
		else{
			return "Student" + studentId + ":" + avg;
		}
	}
	
	public String courseAvg(int courseId) {
		Course c = getCourse(courseId);
		double avg = c.average();
		if(avg == Double.NaN){
			return "No students ha taken the exam in" + c.getTitle();
		}
		else{
			return "The average for the course" + c.getTitle() + "is:" + avg;
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

// R7
    
    public static final Logger logger = Logger.getLogger("University");
	public int enroll(String first, String last){
		int id = super.enroll(first,last);
		Logger.info("New student enrolled: " + id + " " + first + " " + last);
		return id;
	}

	public int activate(String title, String teacher){
		int code = super.activate(title, teacher);
		Logger.info("New course activate: " + code + " " + title + " " + teacher);
		return code;
	}

	public void register(int studentID, int courseId){
		super.register(studentID, courseId);
		Logger.info("Student" + studentID + "signed up for course" + courseId);
	}

	public void Exam(int studentID, int courseID, int grade){
		Student s = getStudent(studentID);
		Course c = getCourse(courseID);
		Exam e = new Exam(s, c, grade);
		s.addExam(e);
		c.addExam(e);
		Logger.info("Student" + studentID + "took and exam in course" + courseID + "with grade" + grade);
	}

}
