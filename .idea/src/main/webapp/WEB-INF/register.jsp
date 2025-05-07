<%--
  Created by IntelliJ IDEA.
  User: dptoa/zehna
  Date: 4/21/2025
  Time: 6:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.collegemanager.model.CollegeManager" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    CollegeManager collegeManager = (CollegeManager) session.getAttribute("collegeManager");
    request.setAttribute("allStudents", collegeManager != null ? collegeManager.getAllStudents() : null);
    request.setAttribute("allCourses", collegeManager != null ? collegeManager.getAllCourses() : null);

    String flash = (String) session.getAttribute("message");
    if (flash == null) flash = (String) session.getAttribute("error");
    if (flash != null) {
        request.setAttribute("flash", flash);
        session.removeAttribute("message");
        session.removeAttribute("error");
    }
%>


<h2 style="color: #001933;">Register Student for Course</h2>
<form action="${pageContext.request.contextPath}/manager" method="post" style="margin-bottom: 2rem; font-size: 0.85rem; max-width: 400px;">
    <input type="hidden" name="action" value="registerStudentForCourse">

    <div style="margin-bottom: 1rem; display: flex; flex-direction: column;">
        <label for="studentId">Select Student:</label>
        <select id="studentId" name="studentId" required style="font-size: 0.85rem;">
            <c:forEach var="student" items="${allStudents}">
                <option value="${student.id}">${student.name} (ID: ${student.id})</option>
            </c:forEach>
        </select>
    </div>

    <div style="margin-bottom: 1rem; display: flex; flex-direction: column;">
        <label for="courseCode">Select Course:</label>
        <select id="courseCode" name="courseCode" required style="font-size: 0.85rem;">
            <c:forEach var="course" items="${allCourses}">
                <option value="${course.code}">${course.title}</option>
            </c:forEach>
        </select>
    </div>

    <button type="submit" style="font-size: 0.85rem; background-color: #007BFF; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer;">Register</button>
</form>

<br>
<!-- Flash Message -->
<c:if test="${not empty flash}">
    <div style="color: #333; background-color: #fff8cc; border: 1px solid #ffd700; padding: 8px; margin-bottom: 1rem; border-radius: 4px;">
            ${flash}
    </div>
</c:if>


<h2 style="color: #001933;">Registered Courses per Student</h2>
<c:choose>
    <c:when test="${empty allStudents}">
        <p>No students available to show registrations.</p>
    </c:when>
    <c:otherwise>
        <table border="1" style="font-size: 0.9rem;">
            <thead>
            <tr>
                <th>Student</th>
                <th>Course Code</th>
                <th>Course Title</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="student" items="${allStudents}">
                <c:forEach var="course" items="${student.registeredCourses}">
                    <tr>
                        <td>${student.name} (ID: ${student.id})</td>
                        <td>${course.code}</td>
                        <td>${course.title}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/manager" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="dropCourse">
                                <input type="hidden" name="studentId" value="${student.id}">
                                <input type="hidden" name="courseCode" value="${course.code}">
                                <button type="submit" style="font-size: 0.75rem; background-color: #dc3545; color: white; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer;">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>



