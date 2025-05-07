package com.example.collegemanager.db;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {
    //No fields
    //No constructor

    public static void initializeSchema() {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();

            // Create tables
            Statement stmt = conn.createStatement();

            // Create students table
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS students (" +
                            "  id INT PRIMARY KEY, " +
                            "  name VARCHAR(100) NOT NULL, " +
                            "  email VARCHAR(100) NOT NULL UNIQUE" +
                            ")"
            );
            System.out.println("Students table created.");

            // Create courses table
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS courses (" +
                            "  code VARCHAR(10) PRIMARY KEY, " +
                            "  title VARCHAR(100) NOT NULL, " +
                            "  credits INT NOT NULL, " +
                            "  instructor VARCHAR(100) NOT NULL" +
                            ")"
            );
            System.out.println("Courses table created.");

            // Create registrations table (for the many-to-many relationship)
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS registrations (" +
                            "  student_id INT, " +
                            "  course_code VARCHAR(10), " +
                            "  PRIMARY KEY (student_id, course_code), " +
                            "  FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE, " +
                            "  FOREIGN KEY (course_code) REFERENCES courses(code) ON DELETE CASCADE" +
                            ")"
            );
            System.out.println("Registrations table created.");

            System.out.println("Database schema setup complete!");

        } catch (SQLException e) {
            System.err.println("Error setting up database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtil.closeQuietly(conn);
        }
    }

    public static void main(String[] args) {
        // Run this method to create the database schema
        initializeSchema();
    }
}