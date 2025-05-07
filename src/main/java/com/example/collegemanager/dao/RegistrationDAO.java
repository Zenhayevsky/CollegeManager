package com.example.collegemanager.dao;
import com.example.collegemanager.model.Course;
import com.example.collegemanager.model.Student;
import java.util.List;


public interface RegistrationDAO {

    /**
     * Register a student for a course
     * @param studentId the student ID
     * @param courseCode the course code
     * @return true if successful
     */
    boolean registerStudentForCourse(int studentId, String courseCode);

    /**
     * Unregister a student from a course
     * @param studentId the student ID
     * @param courseCode the course code
     * @return true if successful
     */
    boolean unregisterStudentFromCourse(int studentId, String courseCode);

    /**
     * Get all courses a student is registered for
     * @param studentId the student ID
     * @return list of courses
     */
    List<Course> getCoursesForStudent(int studentId);

    /**
     * Get all students registered for a course
     * @param courseCode the course code
     * @return list of students
     */
    List<Student> getStudentsForCourse(String courseCode);

    /**
     * Count the number of students registered for a course
     * @param courseCode the course code
     * @return the count of students
     */
    int countStudentsForCourse(String courseCode);
}
