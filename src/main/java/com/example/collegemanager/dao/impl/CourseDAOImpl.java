package com.example.collegemanager.dao.impl;


import com.example.collegemanager.dao.CourseDAO;
import com.example.collegemanager.db.DbUtil;
import com.example.collegemanager.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public Course findByCode(String code) {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM courses WHERE code = ?");
            stmt.setString(1, code);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String title = rs.getString("title");
                int credits = rs.getInt("credits");
                int teacher_id = rs.getInt("teacher_id");
                return new Course(code, title, credits, teacher_id);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error finding course: " + e.getMessage());
            return null;
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM courses");

            while (rs.next()) {
                String code = rs.getString("code");
                String title = rs.getString("title");
                int credits = rs.getInt("credits");
                int teacher_id = rs.getInt("teacher_id");
                courses.add(new Course(code, title, credits, teacher_id));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all courses: " + e.getMessage());
        } finally {
            DbUtil.closeQuietly(conn);
        }
        return courses;
    }

    @Override
    public boolean save(Course course) {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();

            // Check if course exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM courses WHERE code = ?");
            checkStmt.setString(1, course.getCode());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            PreparedStatement stmt;
            if (count > 0) {
                // Update existing course
                stmt = conn.prepareStatement("UPDATE courses SET title = ?, credits = ?, teacher_id = ? WHERE code = ?");
                stmt.setString(1, course.getTitle());
                stmt.setInt(2, course.getCredits());
                stmt.setInt(3, course.getTeacherId());
                stmt.setString(4, course.getCode());
            } else {
                // Insert new course
                stmt = conn.prepareStatement("INSERT INTO courses (code, title, credits, teacher_id) VALUES (?, ?, ?, ?)");
                stmt.setString(1, course.getCode());
                stmt.setString(2, course.getTitle());
                stmt.setInt(3, course.getCredits());
                stmt.setInt(4, course.getTeacherId());
            }

            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error saving course: " + e.getMessage());
            return false;
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

    @Override
    public boolean delete(String code) {
        String sql = "DELETE FROM courses WHERE code = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Course course) {
        String sql = "UPDATE courses SET title = ?, credits = ?, teacher_id = ? WHERE code = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getTitle());
            stmt.setInt(2, course.getCredits());
            stmt.setInt(3, course.getTeacherId());
            stmt.setString(4, course.getCode());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}