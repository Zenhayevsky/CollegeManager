package com.example.collegemanager.dao.impl;
import com.example.collegemanager.dao.StudentDAO;
import com.example.collegemanager.db.DbUtil;
import com.example.collegemanager.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public Student findById(int id) {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                return new Student(id, name, email);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error finding student: " + e.getMessage());
            return null;
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                students.add(new Student(id, name, email));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all students: " + e.getMessage());
        } finally {
            DbUtil.closeQuietly(conn);
        }
        return students;
    }

    @Override
    public boolean save(Student student) {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();

            // Check if student exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM students WHERE id = ?");
            checkStmt.setInt(1, student.getId());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            PreparedStatement stmt;
            if (count > 0) {
                // Update existing student
                stmt = conn.prepareStatement("UPDATE students SET name = ?, email = ? WHERE id = ?");
                stmt.setString(1, student.getName());
                stmt.setString(2, student.getEmail());
                stmt.setInt(3, student.getId());
            } else {
                // Insert new student
                stmt = conn.prepareStatement("INSERT INTO students (id, name, email) VALUES (?, ?, ?)");
                stmt.setInt(1, student.getId());
                stmt.setString(2, student.getName());
                stmt.setString(3, student.getEmail());
            }

            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error saving student: " + e.getMessage());
            return false;
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Student student) {
        String sql = "UPDATE students SET name = ?, email = ? WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setInt(3, student.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}