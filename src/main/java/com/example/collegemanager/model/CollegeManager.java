package com.example.collegemanager.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollegeManager {

    private List<Student> students;
     private List<Course> courses;
    private List<Teacher> teachers;
    private int nextStudentId;
    private int nextTeacherId;

    public CollegeManager() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        teachers = new ArrayList<>();
        nextStudentId = 1000; // Start student IDs at 1000
        nextTeacherId = 1000; // Start teacher IDs at 1000
    }

    // Method to add a new student
    public Student addStudent(String name, String email) {
        Student student = new Student(nextStudentId++, name, email);
        students.add(student);
        return student;
    }

    // Method to add a new teacher (NUEVO)
    public Teacher addTeacher(String name, String email, String department) {
        Teacher teacher = new Teacher(nextTeacherId++, name, email, department);
        teachers.add(teacher);
        return teacher;
    }

    // Method to add a new course
    public Course addCourse(String code, String title, int credits, int teacherId) {
        Course course = new Course(code, title, credits, teacherId);
        courses.add(course);
        return course;
    }

    // Method to register a student for a course
    public boolean registerStudentForCourse(int studentId, String courseCode) {
        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);

        if (student != null && course != null) {
            student.registerForCourse(course);
            return true;
        }
        return false;
    }

    // Helper method to find a student by ID
    public Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    // Helper method to find a teacher by ID
    public Teacher findTeacherById(int id) {
        for (Teacher teacher : teachers) {
            if (teacher.getId() == id) {
                return teacher;
            }
        }
        return null;
    }

    // Helper method to find a course by code
    public Course findCourseByCode(String code) {
        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                return course;
            }
        }
        return null;
    }

    // Methods to get all students and courses
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(teachers);
    }

    public void setAllCourses (List<Course> courses){
        this.courses = courses;
    }

    public void setAllStudents(List<Student> students) {
        this.students = students;
    }

    public void setAllTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Map<String, Integer> getCoursesRegistrationStats() {
        Map<String, Integer> stats = new HashMap<>();

        for (Course course : courses) {
            int count = 0;
            for (Student student : students) {
                if (student.isRegisteredFor(course)) {
                    count++;
                }
            }
            stats.put(course.getCode(), count);
        }

        return stats;
    }

}
