<%--
  Created by IntelliJ IDEA.
  User: dptoa
  Date: 4/21/2025
  Time: 6:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.collegemanager.model.CollegeManager" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    CollegeManager collegeManager = (CollegeManager) session.getAttribute("collegeManager");
    request.setAttribute("allStudents", collegeManager != null ? collegeManager.getAllStudents() : null);
%>
<h2 style="color: #001933;">Manage Students</h2>
<form action="${pageContext.request.contextPath}/manager" method="post" style="margin-bottom: 2rem; font-size: 0.85rem; max-width: 400px;">
    <input type="hidden" name="action" value="addStudent">
    <div style="margin-bottom: 1rem; display: flex; flex-direction: column;">
        <label for="studentName">Name:</label>
        <input type="text" id="studentName" name="studentName" required style="font-size: 0.85rem;">
    </div>
    <div style="margin-bottom: 1rem; display: flex; flex-direction: column;">
        <label for="studentEmail">Email:</label>
        <input type="email" id="studentEmail" name="studentEmail" required style="font-size: 0.85rem;">
    </div>
    <button type="submit" style="font-size: 0.85rem; background-color: #007BFF; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer;">Add Student</button>
</form>

<h2 style="color: #001933;">Registered Students - <%= collegeManager.getAllStudents().size() %></h2>
<c:choose>
    <c:when test="${empty allStudents}">
        <p>No students registered yet.</p>
    </c:when>
    <c:otherwise>
        <table border="1" style="font-size: 0.9rem;">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="student" items="${allStudents}">
                <tr>
                    <td><input type="text" name="editId" value="${student.id}" readonly style="width: 60px; font-size: 0.9rem;"></td>
                    <td><input type="text" name="editName" value="${student.name}" required placeholder="Name" style="font-size: 0.9rem;"></td>
                    <td><input type="email" name="editEmail" value="${student.email}" required placeholder="Email" style="font-size: 0.9rem;"></td>
                    <td style="display: flex; gap: 4px;">
                        <form action="${pageContext.request.contextPath}/manager" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="updateStudent">
                            <input type="hidden" name="editId" value="${student.id}">
                            <input type="hidden" name="editName" value="${student.name}">
                            <input type="hidden" name="editEmail" value="${student.email}">
                            <button type="submit" style="font-size: 0.75rem; background-color: #fbc26d; color: white; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer;">Update</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/manager" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="deleteStudent">
                            <input type="hidden" name="studentId" value="${student.id}">
                            <button type="submit" style="font-size: 0.75rem; background-color: #dc3545; color: white; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer;">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
    </c:otherwise>
</c:choose>
