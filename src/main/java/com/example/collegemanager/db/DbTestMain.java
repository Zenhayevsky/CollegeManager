package com.example.collegemanager.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbTestMain {
    public static void main(String[] args) {
        // 1. Initialize the schema
        // SchemaInitializer is idempotent, which means you can run it again and again
        // and it will not cause problems
        SchemaInitializer.initializeSchema();

        // 2. Insert a test student
        insertTestStudent();

        // 3. Verify the student was inserted
        verifyTestStudent();
    }

    private static void insertTestStudent() {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();

            // Check if test student already exists
            PreparedStatement checkStmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM students WHERE id = 9999");
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                // Insert a test student
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO students (id, name, email) VALUES (?, ?, ?)");
                stmt.setInt(1, 9999);
                stmt.setString(2, "Test Student");
                stmt.setString(3, "test@example.com");
                stmt.executeUpdate();

                System.out.println("Test student inserted.");
            } else {
                System.out.println("Test student already exists.");
            }

        } catch (SQLException e) {
            System.err.println("Error inserting test student: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

    private static void verifyTestStudent() {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();

            // Query the test student
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM students WHERE id = 9999");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");

                System.out.println("Found test student: " + name + " (" + email + ")");
            } else {
                System.out.println("Test student not found!");
            }

        } catch (SQLException e) {
            System.err.println("Error verifying test student: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

}
