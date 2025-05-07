package com.example.collegemanager.dao;
import com.example.collegemanager.model.Student;
import java.util.List;

public interface StudentDAO {

    /**
     * Find a student by ID
     * @param id the student ID
     * @return the student or null if not found
     */
    Student findById(int id);

    /**
     * Get all students
     * @return list of all students
     */
    List<Student> findAll();

    /**
     * Save a student (insert if new, update if existing)
     * @param student the student to save
     * @return true if successful
     */
    boolean save(Student student);

    /**
     * Delete a student
     * @param id the student ID
     * @return true if successful
     */
    boolean delete(int id);

    boolean update(Student student);

}
