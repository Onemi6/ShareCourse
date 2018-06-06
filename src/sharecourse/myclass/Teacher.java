package sharecourse.myclass;

public class Teacher {
	private String teacherNum;
	private String teacherName;
	private String teacherSex;
	private String teacherCourse;
	public Teacher()
	{
		
	}
	
	public Teacher(String teacherNum,String teacherName,String teacherSex,String teacherCourse)
	{
		this.teacherNum = teacherNum;
		this.teacherName = teacherName;
		this.teacherSex = teacherSex;
		this.teacherCourse = teacherCourse;

	}
	public String getTeacherNum() {
		return teacherNum;
	}
	public void setTeacherNum(String teacherNum) {
		this.teacherNum = teacherNum;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherSex() {
		return teacherSex;
	}
	public void setTeacherSex(String teacherSex) {
		this.teacherSex = teacherSex;
	}
	public String getTeacherCourse() {
		return teacherCourse;
	}
	public void setTeacherCourse(String teacherCourse) {
		this.teacherCourse = teacherCourse;
	}

}
