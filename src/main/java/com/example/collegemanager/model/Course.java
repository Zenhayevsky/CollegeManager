package com.example.collegemanager.model;

public class Course {
    private String code;
    private String title;
    private int credits;
    private int teacherId;
    //private String instructor;

    public Course(String code, String title, int credits, int teacherid) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.teacherId = teacherid;
        //this.instructor = instructor;
    }

    // Getters for our properties
    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

//    public String getInstructor() {
//        return instructor;
//    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return code + ": " + title + " (" + credits + " credits, taught by " + teacherId + ")";
    }

    //Methods
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return code.equals(course.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

}
