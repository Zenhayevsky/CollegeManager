package com.example.collegemanager.dao.impl;

import com.example.collegemanager.dao.TeacherDAO;
import com.example.collegemanager.model.Teacher;
import com.example.collegemanager.db.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAOImpl implements TeacherDAO {
    @Override
    public Teacher findById(int id) {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM teachers WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String department = rs.getString("department");
                return new Teacher(id, name, email, department);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error finding teacher: " + e.getMessage());
            return null;
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Teacher> findAll() {
        List<Teacher> teachers = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM teachers");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String department = rs.getString("department");
                teachers.add(new Teacher(id, name, email, department));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all teachers: " + e.getMessage());
        } finally {
            DbUtil.closeQuietly(conn);
        }
        return teachers;
    }

    @Override
    public boolean save(Teacher teacher) {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();

            // Check if teacher exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM teachers WHERE id = ?");
            checkStmt.setInt(1, teacher.getId());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            PreparedStatement stmt;
            if (count > 0) {
                // Update existing teacher
                stmt = conn.prepareStatement("UPDATE teachers SET name = ?, email = ?, department = ? WHERE id = ?");
                stmt.setString(1, teacher.getName());
                stmt.setString(2, teacher.getEmail());
                stmt.setString(3, teacher.getDepartment());
                stmt.setInt(4, teacher.getId());
            } else {
                // Insert new teacher
                stmt = conn.prepareStatement("INSERT INTO teachers (id, name, email, department) VALUES (?, ?, ?, ?)");
                stmt.setInt(1, teacher.getId());
                stmt.setString(2, teacher.getName());
                stmt.setString(3, teacher.getEmail());
                stmt.setString(4, teacher.getDepartment());
            }

            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error saving teacher: " + e.getMessage());
            return false;
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM teachers WHERE id = ?";
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
    public boolean update(Teacher teacher) {
        String sql = "UPDATE teachers SET name = ?, email = ?, department = ? WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getEmail());
            stmt.setString(3, teacher.getDepartment());
            stmt.setInt(4, teacher.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
