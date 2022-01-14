package ua.com.rtim.academy.domain;

import java.util.ArrayList;
import java.util.List;

public class Academy {

	private String name;
	private String address;
	private Integer phone;
	private List<Teacher> teachers = new ArrayList<>();
	private List<Course> courses = new ArrayList<>();
	private List<Audience> audiences = new ArrayList<>();
	private List<Group> groups = new ArrayList<>();
	private List<Student> students = new ArrayList<>();
	private List<Lesson> lessons = new ArrayList<>();
	private List<Holiday> holidays = new ArrayList<>();

	public Academy(String name, String address, Integer phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public void addTeacher(Teacher teacher) {
		this.teachers.add(teacher);
	}

	public Teacher getTeacherById(int id) {
		return teachers.get(id);
	}

	public void deleteTeacherById(int id) {
		teachers.remove(id);
	}

	public void addStudent(Student student) {
		this.students.add(student);
	}

	public Student getStudentById(int id) {
		return students.get(id);
	}

	public void deleteStudentById(int id) {
		students.remove(id);
	}

	public void addCourse(Course course) {
		this.courses.add(course);
	}

	public Course getCourseById(int id) {
		return courses.get(id);
	}

	public void deleteCourseById(int id) {
		courses.remove(id);
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void addGroup(Group group) {
		this.groups.add(group);
	}

	public Group getGroupById(int id) {
		return groups.get(id);
	}

	public void deleteGroupById(int id) {
		groups.remove(id);
	}

	public List<Holiday> getHolidays() {
		return holidays;
	}

	public void addHoliday(Holiday holiday) {
		this.holidays.add(holiday);
	}

	public Holiday getHolidayById(int id) {
		return this.holidays.get(id);
	}

	public void deleteHolidayById(int id) {
		holidays.remove(id);
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void addLesson(Lesson lesson) {
		this.lessons.add(lesson);
	}

	public Lesson getLessonById(int id) {
		return this.lessons.get(id);
	}

	public void deleteLessonById(int id) {
		this.lessons.remove(id);
	}

	public void addAudience(Audience audience) {
		this.audiences.add(audience);
	}

	public Audience getAudienceById(int id) {
		return audiences.get(id);
	}

	public void deleteAudienceById(int id) {
		this.audiences.remove(id);
	}
}