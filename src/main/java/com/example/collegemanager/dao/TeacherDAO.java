package com.example.collegemanager.dao;

import com.example.collegemanager.model.Teacher;
import java.util.List;

public interface TeacherDAO {
    Teacher findById(int id);

    List<Teacher> findAll();

    boolean save(Teacher teacher);

    boolean update(Teacher teacher);

    boolean delete(int id);
}
