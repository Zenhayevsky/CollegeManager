<%--
  Created by IntelliJ IDEA.
  User: dptoa
  Date: 4/21/2025
  Time: 6:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.collegemanager.model.CollegeManager" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    CollegeManager collegeManager = (CollegeManager) session.getAttribute("collegeManager");
%>
<h2 style="color: #001933;">System Information</h2>
<p>Total Students: <%= collegeManager.getAllStudents().size() %></p>
<p>Total Courses: <%= collegeManager.getAllCourses().size() %></p>

