package com.example.collegemanager.dao;

import com.example.collegemanager.model.Course;
import java.util.List;

public interface CourseDAO {

    /**
     * Find a course by code
     * @param code the course code
     * @return the course or null if not found
     */
    Course findByCode(String code);

    /**
     * Get all courses
     * @return list of all courses
     */
    List<Course> findAll();

    /**
     * Save a course (insert if new, update if existing)
     * @param course the course to save
     * @return true if successful
     */
    boolean save(Course course);

    /**
     * Delete a course
     * @param code the course code
     * @return true if successful
     */
    boolean delete(String code);

    boolean update(Course course);

}
