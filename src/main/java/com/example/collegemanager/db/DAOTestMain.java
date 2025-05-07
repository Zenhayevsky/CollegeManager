package com.example.collegemanager.db;

import com.example.collegemanager.dao.CourseDAO;
import com.example.collegemanager.dao.RegistrationDAO;
import com.example.collegemanager.dao.StudentDAO;
import com.example.collegemanager.dao.impl.CourseDAOImpl;
import com.example.collegemanager.dao.impl.RegistrationDAOImpl;
import com.example.collegemanager.dao.impl.StudentDAOImpl;
import com.example.collegemanager.model.Course;
import com.example.collegemanager.model.Student;

import java.util.List;

public class DAOTestMain {

    public static void main(String[] args) {
        // Initialize the database schema
        SchemaInitializer.initializeSchema();

        // Create our DAOs
        StudentDAO studentDAO = new StudentDAOImpl();
        CourseDAO courseDAO = new CourseDAOImpl();
        RegistrationDAO registrationDAO = new RegistrationDAOImpl();

        // Test student DAO
        testStudentDAO(studentDAO);

        // Test course DAO
        testCourseDAO(courseDAO);

        // Test registration
        testRegistration(studentDAO, courseDAO, registrationDAO);
    }

    private static void testStudentDAO(StudentDAO studentDAO) {
        System.out.println("===== Testing StudentDAO =====");

        // Create a test student
        Student alice = new Student(1001, "Alice Smith", "alice@example.com");

        // Save the student
        boolean saved = studentDAO.save(alice);
        System.out.println("Student saved: " + saved);

        // Find the student by ID
        Student foundStudent = studentDAO.findById(1001);
        if (foundStudent != null) {
            System.out.println("Found student: " + foundStudent.getName());
        } else {
            System.out.println("Student not found!");
        }

        // List all students
        List<Student> allStudents = studentDAO.findAll();
        System.out.println("All students: " + allStudents.size());
        for (Student s : allStudents) {
            System.out.println("  - " + s.getId() + ": " + s.getName());
        }
    }

    private static void testCourseDAO(CourseDAO courseDAO) {
        System.out.println("\n===== Testing CourseDAO =====");

        // Create test courses
        Course java = new Course("CS101", "Introduction to Java", 3, 2001);
        Course web = new Course("CS201", "Web Development", 4, 2001);

        // Save courses
        boolean javaSaved = courseDAO.save(java);
        boolean webSaved = courseDAO.save(web);
        System.out.println("Java course saved: " + javaSaved);
        System.out.println("Web course saved: " + webSaved);

        // Find course by code
        Course foundCourse = courseDAO.findByCode("CS101");
        if (foundCourse != null) {
            System.out.println("Found course: " + foundCourse.getTitle());
        } else {
            System.out.println("Course not found!");
        }

        // List all courses
        List<Course> allCourses = courseDAO.findAll();
        System.out.println("All courses: " + allCourses.size());
        for (Course c : allCourses) {
            System.out.println("  - " + c.getCode() + ": " + c.getTitle());
        }
    }

    private static void testRegistration(StudentDAO studentDAO, CourseDAO courseDAO, RegistrationDAO registrationDAO) {
        System.out.println("\n===== Testing Registration =====");

        // Register a student for courses
        boolean registered1 = registrationDAO.registerStudentForCourse(1001, "CS101");
        boolean registered2 = registrationDAO.registerStudentForCourse(1001, "CS201");
        System.out.println("Registered for Java: " + registered1);
        System.out.println("Registered for Web: " + registered2);

        // Get courses for student
        List<Course> courses = registrationDAO.getCoursesForStudent(1001);
        System.out.println("Courses for student ID 1001: " + courses.size());
        for (Course c : courses) {
            System.out.println("  - " + c.getCode() + ": " + c.getTitle());
        }

        // Count students for course
        int javaCount = registrationDAO.countStudentsForCourse("CS101");
        int webCount = registrationDAO.countStudentsForCourse("CS201");
        System.out.println("Students in Java course: " + javaCount);
        System.out.println("Students in Web course: " + webCount);

        // Unregister student from a course
        boolean unregistered = registrationDAO.unregisterStudentFromCourse(1001, "CS201");
        System.out.println("Unregistered from Web: " + unregistered);

        // Verify courses after unregistering
        courses = registrationDAO.getCoursesForStudent(1001);
        System.out.println("Courses after unregistering: " + courses.size());
        for (Course c : courses) {
            System.out.println("  - " + c.getCode() + ": " + c.getTitle());
        }
    }
}