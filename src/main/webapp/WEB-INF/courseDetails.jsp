<%--
  Created by IntelliJ IDEA.
  User: dptoa
  Date: 3/27/2025
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Course Details: ${course.code}</title>
  <style>
    /* Same CSS as manager.jsp */
  </style>
</head>
<body>
<h1>📚 Course Details: ${course.code}</h1>

<div class="card">
  <h2>${course.title}</h2>
  <p><strong>Course Code:</strong> ${course.code}</p>
  <p><strong>Credits:</strong> ${course.credits}</p>
  <p><strong>Instructor:</strong> ${course.instructor}</p>
  <p><strong>Enrolled Students:</strong> ${enrolledStudents.size()}</p>
</div>

<h2>Enrolled Students</h2>
<c:choose>
  <c:when test="${empty enrolledStudents}">
    <p>No students enrolled in this course yet.</p>
  </c:when>
  <c:otherwise>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="student" items="${enrolledStudents}">
        <tr>
          <td>${student.id}</td>
          <td>${student.name}</td>
          <td>${student.email}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:otherwise>
</c:choose>

<p><a href="${pageContext.request.contextPath}/manager"><< Back to College Manager</a></p>
</body>
</html>
