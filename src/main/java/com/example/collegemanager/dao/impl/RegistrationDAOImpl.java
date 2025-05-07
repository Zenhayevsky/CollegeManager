package com.example.collegemanager.dao.impl;


import com.example.collegemanager.dao.RegistrationDAO;
import com.example.collegemanager.db.DbUtil;
import com.example.collegemanager.model.Course;
import com.example.collegemanager.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAOImpl implements RegistrationDAO {

    @Override
    public boolean registerStudentForCourse(int studentId, String courseCode) {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();

            // Check if already registered
            PreparedStatement checkStmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM registrations WHERE student_id = ? AND course_code = ?");
            checkStmt.setInt(1, studentId);
            checkStmt.setString(2, courseCode);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            // Only insert if not already registered
            if (count == 0) {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO registrations (student_id, course_code) VALUES (?, ?)");
                stmt.setInt(1, studentId);
                stmt.setString(2, courseCode);
                int result = stmt.executeUpdate();
                return result > 0;
            }

            return true; // Already registered
        } catch (SQLException e) {
            System.err.println("Error registering student for course: " + e.getMessage());
            return false;
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

    @Override
    public boolean unregisterStudentFromCourse(int studentId, String courseCode) {
        String sql = "DELETE FROM registrations WHERE student_id = ? AND course_code = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, courseCode);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Course> getCoursesForStudent(int studentId) {
        List<Course> courses = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();

            // Join registrations with courses to get course details
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT c.* FROM courses c " +
                            "JOIN registrations r ON c.code = r.course_code " +
                            "WHERE r.student_id = ?");
            stmt.setInt(1, studentId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String code = rs.getString("code");
                String title = rs.getString("title");
                int credits = rs.getInt("credits");
                String instructor = rs.getString("instructor");

                courses.add(new Course(code, title, credits, instructor));
            }
        } catch (SQLException e) {
            System.err.println("Error getting courses for student: " + e.getMessage());
        } finally {
            DbUtil.closeQuietly(conn);
        }
        return courses;
    }

    @Override
    public List<Student> getStudentsForCourse(String courseCode) {
        List<Student> students = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();

            // Join registrations with students to get student details
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT s.* FROM students s " +
                            "JOIN registrations r ON s.id = r.student_id " +
                            "WHERE r.course_code = ?");
            stmt.setString(1, courseCode);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                students.add(new Student(id, name, email));
            }
        } catch (SQLException e) {
            System.err.println("Error getting students for course: " + e.getMessage());
        } finally {
            DbUtil.closeQuietly(conn);
        }
        return students;
    }

    @Override
    public int countStudentsForCourse(String courseCode) {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM registrations WHERE course_code = ?");
            stmt.setString(1, courseCode);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            System.err.println("Error counting students for course: " + e.getMessage());
            return 0;
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }
}
