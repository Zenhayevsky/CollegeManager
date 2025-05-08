package com.example.collegemanager.controller;
import com.example.collegemanager.dao.CourseDAO;
import com.example.collegemanager.dao.RegistrationDAO;
import com.example.collegemanager.dao.StudentDAO;
import com.example.collegemanager.dao.TeacherDAO;
import com.example.collegemanager.dao.impl.CourseDAOImpl;
import com.example.collegemanager.dao.impl.RegistrationDAOImpl;
import com.example.collegemanager.dao.impl.StudentDAOImpl;
import com.example.collegemanager.dao.impl.TeacherDAOImpl;
import com.example.collegemanager.model.CollegeManager;
import com.example.collegemanager.model.Course;
import com.example.collegemanager.model.Student;
import com.example.collegemanager.model.Teacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/manager")

public class ManagerServlet extends HttpServlet {

    // Get our DAOs from the factory
    //private StudentDAO studentDAO = DAOFactory.getStudentDAO();
    //private CourseDAO courseDAO = DAOFactory.getCourseDAO();
   // private RegistrationDAO registrationDAO = DAOFactory.getRegistrationDAO();


    private StudentDAO studentDAO = new StudentDAOImpl();
    private CourseDAO courseDAO = new CourseDAOImpl();
    private RegistrationDAO registrationDAO = new RegistrationDAOImpl();
    private TeacherDAO teacherDAO = new TeacherDAOImpl();

    @Override
    public void init() throws ServletException {
        // Check if we need to add sample data (if database is empty)
        if (studentDAO.findAll().isEmpty()) {
            addSampleStudents();
        }
        if (teacherDAO.findAll().isEmpty()) {
            addSampleTeachers();
        }

        if (courseDAO.findAll().isEmpty()) {
            addSampleCourses();
        }

    }

    private void addSampleStudents() {
        System.out.println("Adding sample students...");
        Student alice = new Student(1001, "Alice Smith", "alice@example.com");
        Student bob = new Student(1002, "Bob Johnson", "bob@example.com");
        studentDAO.save(alice);
        studentDAO.save(bob);
    }

    private void addSampleCourses() {
        System.out.println("Adding sample courses...");

        Course javaCourse = new Course("CS101", "Introduction to Java", 3, 2001);
        Course webDev = new Course("CS201", "Web Development", 4, 2001);
        Course math = new Course("MATH101", "Calculus I", 4, 2003);

        if (courseDAO.save(javaCourse)) {
            System.out.println("Saved course CS101 successfully!");
        } else {
            System.out.println("Failed to save course CS101!");
        }

        if (courseDAO.save(webDev)) {
            System.out.println("Saved course CS201 successfully!");
        } else {
            System.out.println("Failed to save course CS201!");
        }

        if (courseDAO.save(math)) {
            System.out.println("Saved course MATH101 successfully!");
        } else {
            System.out.println("Failed to save course MATH101!");
        }

        // Add registration
        if (registrationDAO.registerStudentForCourse(1001, "CS101")) {
            System.out.println("Registered student 1001 for CS101 successfully!");
        } else {
            System.out.println("Failed to register student 1001 for CS101!");
        }

        if (registrationDAO.registerStudentForCourse(1001, "CS201")) {
            System.out.println("Registered student 1001 for CS201 successfully!");
        } else {
            System.out.println("Failed to register student 1001 for CS201!");
        }

        if (registrationDAO.registerStudentForCourse(1002, "CS101")) {
            System.out.println("Registered student 1002 for CS101 successfully!");
        } else {
            System.out.println("Failed to register student 1002 for CS101!");
        }

        if (registrationDAO.registerStudentForCourse(1002, "MATH101")) {
            System.out.println("Registered student 1002 for MATH101 successfully!");
        } else {
            System.out.println("Failed to register student 1002 for MATH101!");
        }
    }

    private void addSampleTeachers() {
        System.out.println("Adding sample teachers...");
        Teacher t1 = new Teacher(2001, "Bobby Conolly", "bobby@example.com", "Information System");
        Teacher t2 = new Teacher(2002, "Fode Toure", "fode@example.com", "Information System");
        Teacher t3 = new Teacher(2003, "Alex Salcedo", "alex@example.com", "Math");
        teacherDAO.save(t1);
        teacherDAO.save(t2);
        teacherDAO.save(t3);
        System.out.println("Sample teachers added successfully!");
    }

    private void addSampleRegistrations() {
        System.out.println("Registering sample students to courses...");
        registrationDAO.registerStudentForCourse(1001, "CS101");
        registrationDAO.registerStudentForCourse(1001, "CS201");
        registrationDAO.registerStudentForCourse(1002, "CS101");
        registrationDAO.registerStudentForCourse(1002, "MATH101");
        System.out.println("Sample registrations added successfully!");
    }


 /*   private void addSampleData() {
        System.out.println("Adding sample data to the database...");

        // Add sample students
        Student alice = new Student(1001, "Alice Smith", "alice@example.com");
        Student bob = new Student(1002, "Bob Johnson", "bob@example.com");
        studentDAO.save(alice);
        studentDAO.save(bob);

        //Add sample teachers
        Teacher t1 = new Teacher(2001, "Bobby Conolly", "bobby@example.com", "Information System");
        Teacher t2 = new Teacher(2002, "Fode Toure", "fode@example.com", "Information System");
        teacherDAO.save(t1);
        teacherDAO.save(t2);

        // Add sample courses
        Course javaCourse = new Course("CS101", "Introduction to Java", 3, "Dr. Java");
        Course webDev = new Course("CS201", "Web Development", 4, "Prof. Web");
        Course math = new Course("MATH101", "Calculus I", 4, "Dr. Calculus");
        courseDAO.save(javaCourse);
        courseDAO.save(webDev);
        courseDAO.save(math);

        // Register some students for courses
        registrationDAO.registerStudentForCourse(alice.getId(), javaCourse.getCode());
        registrationDAO.registerStudentForCourse(alice.getId(), webDev.getCode());
        registrationDAO.registerStudentForCourse(bob.getId(), javaCourse.getCode());
        registrationDAO.registerStudentForCourse(bob.getId(), math.getCode());

        System.out.println("Sample data added successfully!");
    }//end addSample
*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if we're viewing course details
        String courseCode = request.getParameter("courseCode");
        if (courseCode != null && !courseCode.isEmpty()) {
            showCourseDetails(request, response, courseCode);
            return;
        }

        // Load all students, courses, teachers from the database
        List<Student> students = studentDAO.findAll();
        List<Course> courses = courseDAO.findAll();
        List<Teacher> teachers = teacherDAO.findAll();

        // For each student, load their registered courses
        for (Student student : students) {
            List<Course> registeredCourses = registrationDAO.getCoursesForStudent(student.getId());
            for (Course course : registeredCourses) {
                student.registerForCourse(course);
            }
        }

        // Calculate enrollment statistics
        Map<String, Integer> enrollmentStats = new HashMap<>();
        for (Course course : courses) {
            int count = registrationDAO.countStudentsForCourse(course.getCode());
            enrollmentStats.put(course.getCode(), count);
        }

        // Update collegeManager en la session
        HttpSession session = request.getSession();
        CollegeManager manager = (CollegeManager) session.getAttribute("collegeManager");
        if (manager == null) {
            manager = new CollegeManager();
            session.setAttribute("collegeManager", manager);
        }
        manager.setAllStudents(students);
        manager.setAllCourses(courses);
        manager.setAllTeachers(teachers);
        session.setAttribute("collegeManager", manager); // Important update the session

        // Add data to the request
        request.setAttribute("students", students);
        request.setAttribute("courses", courses);
        request.setAttribute("teachers", teachers);
        request.setAttribute("enrollmentStats", enrollmentStats);

        // Forward to the JSP
        request.getRequestDispatcher("/manager.jsp").forward(request, response);
    } //end void doGet

    private void showCourseDetails(HttpServletRequest request, HttpServletResponse response, String courseCode)
            throws ServletException, IOException {

        // Get the course from the database
        Course course = courseDAO.findByCode(courseCode);
        if (course == null) {
            // Course not found - redirect to the main page
            response.sendRedirect(request.getContextPath() + "/manager");
            return;
        }

        // Get students enrolled in this course (we'll need to get all students and filter)
        List<Student> allStudents = studentDAO.findAll();
        List<Student> enrolledStudents = new java.util.ArrayList<>();

        for (Student student : allStudents) {
            // Get the courses for this student
            List<Course> courses = registrationDAO.getCoursesForStudent(student.getId());
            for (Course c : courses) {
                if (c.getCode().equals(courseCode)) {
                    enrolledStudents.add(student);
                    break;
                }
            }
        }

        // Add data to the request
        request.setAttribute("course", course);
        request.setAttribute("enrolledStudents", enrolledStudents);

        // Forward to the course details JSP
        request.getRequestDispatcher("/WEB-INF/courseDetails.jsp").forward(request, response);
    } //end showCourseDetails

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the session and college manager
        HttpSession session = request.getSession();
        CollegeManager manager = (CollegeManager) session.getAttribute("collegeManager");

        // If somehow we don't have a manager, create one
        if (manager == null) {
            manager = new CollegeManager();
            session.setAttribute("collegeManager", manager);
        }

        // Get the action parameter to determine what operation to perform
        String action = request.getParameter("action");

        if ("addStudent".equals(action)) {
            addStudent(request, manager);
        } else if ("addCourse".equals(action)) {
            addCourse(request, manager);
        } else if ("registerStudentForCourse".equals(action)) {
            registerStudentForCourse(request, manager);
        }
        else if ("updateCourse".equals(action)) {
            updateCourse(request, manager);
            response.sendRedirect(request.getContextPath() + "/manager?view=courses");
            return;
        }
        else if ("updateStudent".equals(action)) {
            updateStudent(request, manager);
            response.sendRedirect(request.getContextPath() + "/manager?view=students");
            return;
        }
        else if ("deleteCourse".equals(action)) {
            deleteCourse(request);
            response.sendRedirect(request.getContextPath() + "/manager?view=courses");
            return;
        } else if ("deleteStudent".equals(action)) {
            deleteStudent(request);
            response.sendRedirect(request.getContextPath() + "/manager?view=students");
            return;
        }
        else if ("dropCourse".equals(action)) {
            dropCourse(request);
            response.sendRedirect(request.getContextPath() + "/manager?view=register");
            return;
        }
        else if ("addTeacher".equals(action)) {
            addTeacher(request, manager);
            response.sendRedirect(request.getContextPath() + "/manager?view=teachers"); // CAMBIO AQUI
            return;
        } else if ("updateTeacher".equals(action)) {
            updateTeacher(request, manager);
            response.sendRedirect(request.getContextPath() + "/manager?view=teachers"); // CAMBIO AQUI
            return;
        } else if ("deleteTeacher".equals(action)) {
            deleteTeacher(request);
            response.sendRedirect(request.getContextPath() + "/manager?view=teachers"); // CAMBIO AQUI
            return;
        }


        // Redirect back to the manager page to see the updated data
        //response.sendRedirect(request.getContextPath() + "/manager");
        if ("addStudent".equals(action)) {
            addStudent(request, manager);
            response.sendRedirect(request.getContextPath() + "/manager?view=students");
            return;
        } else if ("addCourse".equals(action)) {
            addCourse(request, manager);
            response.sendRedirect(request.getContextPath() + "/manager?view=courses");
            return;
        } else if ("registerStudentForCourse".equals(action)) {
            registerStudentForCourse(request, manager);
            response.sendRedirect(request.getContextPath() + "/manager?view=register");
            return;
        }
    } //end doPost

    private void addStudent(HttpServletRequest request, CollegeManager manager) {
        // Get parameters
        String name = request.getParameter("studentName");
        String email = request.getParameter("studentEmail");
        HttpSession session = request.getSession();

        if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            session.setAttribute("error", "Name and email are required!");
            return;
        }

        // Generate a unique ID (get max ID + 1)
        int nextId = 1000;
        List<Student> students = studentDAO.findAll();
        for (Student s : students) {
            if (s.getId() >= nextId) {
                nextId = s.getId() + 1;
            }
        }

        // Create and save the student
        Student student = new Student(nextId, name, email);
        boolean success = studentDAO.save(student);

        if (success) {
            session.setAttribute("message", "Student " + name + " added successfully!");
        } else {
            session.setAttribute("error", "Failed to add student. Please try again.");
        }
    }//end addStudent

    private void addCourse(HttpServletRequest request, CollegeManager manager) {
        // Get parameters
        String code = request.getParameter("courseCode");
        String title = request.getParameter("courseTitle");
        String creditsStr = request.getParameter("courseCredits");
        String teacherIdStr = request.getParameter("teacherId");
        HttpSession session = request.getSession();

        if (code == null || code.trim().isEmpty() ||
                title == null || title.trim().isEmpty() ||
                creditsStr == null || creditsStr.trim().isEmpty() ||
                teacherIdStr == null || teacherIdStr.trim().isEmpty()) {
            session.setAttribute("error", "All course fields are required!");
            return;
        }

        try {
            int credits = Integer.parseInt(creditsStr);
            int teacherId = Integer.parseInt(teacherIdStr);

            // Check if course code already exists
            if (courseDAO.findByCode(code) != null) {
                session.setAttribute("error", "Course code " + code + " already exists!");
                return;
            }

            // Create and save the course
            Course course = new Course(code, title, credits, teacherId);
            boolean success = courseDAO.save(course);

            if (success) {
                session.setAttribute("message", "Course " + code + " added successfully!");
            } else {
                session.setAttribute("error", "Failed to add course. Please try again.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Credits must be a number!");
        }
    }// end addcourse

    private void registerStudentForCourse(HttpServletRequest request, CollegeManager manager) {
        // Get parameters
        String studentIdStr = request.getParameter("studentId");
        String courseCode = request.getParameter("courseCode");
        HttpSession session = request.getSession();

        if (studentIdStr == null || studentIdStr.trim().isEmpty() ||
                courseCode == null || courseCode.trim().isEmpty()) {
            session.setAttribute("error", "Student and course are required!");
            return;
        }

        try {
            int studentId = Integer.parseInt(studentIdStr);

            // Get the student and course
            Student student = studentDAO.findById(studentId);
            Course course = courseDAO.findByCode(courseCode);

            if (student == null || course == null) {
                session.setAttribute("error", "Student or course not found!");
                return;
            }

            // Check if already registered
            List<Course> registeredCourses = registrationDAO.getCoursesForStudent(studentId);
            for (Course c : registeredCourses) {
                if (c.getCode().equals(courseCode)) {
                    session.setAttribute("error", "Student is already registered for this course!");
                    return;
                }
            }

            // Register the student
            boolean success = registrationDAO.registerStudentForCourse(studentId, courseCode);

            if (success) {
                session.setAttribute("message", "Student registered for course successfully!");
            } else {
                session.setAttribute("error", "Failed to register student. Please try again.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid student ID!");
        }
    }//end registerStudentForCourse

    private void dropCourse(HttpServletRequest request) {
        // Get parameters
        String studentIdStr = request.getParameter("studentId");
        String courseCode = request.getParameter("courseCode");
        HttpSession session = request.getSession();

        if (studentIdStr == null || studentIdStr.trim().isEmpty() ||
                courseCode == null || courseCode.trim().isEmpty()) {
            session.setAttribute("error", "Student and course are required!");
            return;
        }

        try {
            int studentId = Integer.parseInt(studentIdStr);

            // Get the student and course
            Student student = studentDAO.findById(studentId);
            Course course = courseDAO.findByCode(courseCode);

            if (student == null || course == null) {
                session.setAttribute("error", "Student or course not found!");
                return;
            }

            // Unregister the student
            boolean success = registrationDAO.unregisterStudentFromCourse(studentId, courseCode);

            if (success) {
                session.setAttribute("message", "Student dropped from course successfully!");
            } else {
                session.setAttribute("error", "Failed to drop course. Please try again.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid student ID!");
        }
    }//end dropCourse

    private void updateCourse(HttpServletRequest request, CollegeManager manager) {
        String code = request.getParameter("editCode");
        String title = request.getParameter("editTitle");
        String creditsStr = request.getParameter("editCredits");
        String teacherIdStr = request.getParameter("editTeacherId");
        HttpSession session = request.getSession();

        if (code == null || title == null || creditsStr == null || teacherIdStr == null ||
                code.trim().isEmpty() || title.trim().isEmpty() || creditsStr.trim().isEmpty() || teacherIdStr.trim().isEmpty()) {
            session.setAttribute("error", "All fields are required to update the course.");
            return;
        }

        try {
            int credits = Integer.parseInt(creditsStr);
            int teacherId = Integer.parseInt(teacherIdStr);

            // Find course
            Course course = courseDAO.findByCode(code);
            if (course == null) {
                session.setAttribute("error", "Course with code " + code + " not found.");
                return;
            }

            // Update course values
            Course updatedCourse = new Course(code, title, credits, teacherId);
            boolean success = courseDAO.update(updatedCourse);

            if (success) {
                session.setAttribute("message", "Course " + code + " updated successfully.");
            } else {
                session.setAttribute("error", "Failed to update course.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Credits and Teacher ID must be valid numbers.");
        }
    }//end update courses

    private void updateStudent(HttpServletRequest request, CollegeManager manager) {
        String idStr = request.getParameter("editId");
        String name = request.getParameter("editName");
        String email = request.getParameter("editEmail");
        HttpSession session = request.getSession();

        if (idStr == null || name == null || email == null ||
                idStr.trim().isEmpty() || name.trim().isEmpty() || email.trim().isEmpty()) {
            session.setAttribute("error", "All fields are required to update the student.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            Student student = studentDAO.findById(id);
            if (student == null) {
                session.setAttribute("error", "Student with ID " + id + " not found.");
                return;
            }

            // Create updated student and save
            Student updatedStudent = new Student(id, name, email);
            boolean success = studentDAO.update(updatedStudent);

            if (success) {
                session.setAttribute("message", "Student updated successfully.");
            } else {
                session.setAttribute("error", "Failed to update student.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid student ID.");
        }
    }//end update Students

    private void deleteCourse(HttpServletRequest request) {
        String code = request.getParameter("courseCode");
        HttpSession session = request.getSession();

        if (code != null && !code.trim().isEmpty()) {
            boolean success = courseDAO.delete(code);
            if (success) {
                session.setAttribute("message", "Course " + code + " deleted successfully.");
            } else {
                session.setAttribute("error", "Failed to delete course.");
            }
        }
    }

    private void deleteStudent(HttpServletRequest request) {
        String idStr = request.getParameter("studentId");
        HttpSession session = request.getSession();

        try {
            int id = Integer.parseInt(idStr);
            boolean success = studentDAO.delete(id);
            if (success) {
                session.setAttribute("message", "Student deleted successfully.");
            } else {
                session.setAttribute("error", "Failed to delete student.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid student ID.");
        }
    }

    private void addTeacher(HttpServletRequest request, CollegeManager manager) {
        String name = request.getParameter("teacherName");
        String email = request.getParameter("teacherEmail");
        String department = request.getParameter("teacherDepartment");
        HttpSession session = request.getSession();

        if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            session.setAttribute("error", "Name and email are required for the teacher!");
            return;
        }

        // Generate unique ID (like for students)
        int nextId = 2000;
        List<Teacher> teachers = teacherDAO.findAll();
        for (Teacher t : teachers) {
            if (t.getId() >= nextId) {
                nextId = t.getId() + 1;
            }
        }

        Teacher teacher = new Teacher(nextId, name, email, department);
        boolean success = teacherDAO.save(teacher);

        if (success) {
            session.setAttribute("message", "Teacher " + name + " added successfully!");
        } else {
            session.setAttribute("error", "Failed to add teacher. Please try again.");
        }
    }

    private void updateTeacher(HttpServletRequest request, CollegeManager manager) {
        String idStr = request.getParameter("editId");
        String name = request.getParameter("editName");
        String email = request.getParameter("editEmail");
        String department = request.getParameter("editDepartment");
        HttpSession session = request.getSession();

        if (idStr == null || name == null || email == null ||
                idStr.trim().isEmpty() || name.trim().isEmpty() || email.trim().isEmpty()) {
            session.setAttribute("error", "All fields are required to update the teacher.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            Teacher teacher = teacherDAO.findById(id);
            if (teacher == null) {
                session.setAttribute("error", "Teacher with ID " + id + " not found.");
                return;
            }

            // Update values
            teacher.setName(name);
            teacher.setEmail(email);
            teacher.setDepartment(department);

            boolean success = teacherDAO.update(teacher);

            if (success) {
                session.setAttribute("message", "Teacher updated successfully.");
            } else {
                session.setAttribute("error", "Failed to update teacher.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid teacher ID.");
        }
    }//update teacher

    private void deleteTeacher(HttpServletRequest request) {
        String idStr = request.getParameter("teacherId");
        HttpSession session = request.getSession();

        try {
            int id = Integer.parseInt(idStr);

            CollegeManager collegeManager = (CollegeManager) session.getAttribute("collegeManager");
            boolean hasCourses = collegeManager.getAllCourses().stream()
                    .anyMatch(course -> course.getTeacherId() == id);  // Comparando int com int

            if (hasCourses) {
                // Se o professor tem cursos associados, não permitir a exclusão
                session.setAttribute("error", "Cannot delete teacher because he/her has courses assigned.");
            } else {
                // Caso contrário, excluir o professor
                boolean success = teacherDAO.delete(id);  // Substitua pelo método correto de exclusão do professor
                if (success) {
                    session.setAttribute("message", "Teacher deleted successfully.");
                } else {
                    session.setAttribute("error", "Failed to delete teacher.");
                }
            }
        } catch (NumberFormatException e) {
            // Caso o ID seja inválido
            session.setAttribute("error", "Invalid teacher ID.");
        }
    } // end deleteTeacher


} //end public servlet
