<%--
  Created by IntelliJ IDEA.
  User: dptoa/zehna
  Date: 4/21/2025
  Time: 6:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.collegemanager.model.CollegeManager" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    CollegeManager collegeManager = (CollegeManager) session.getAttribute("collegeManager");
    request.setAttribute("allStudents", collegeManager != null ? collegeManager.getAllStudents() : null);

    String flash = (String) session.getAttribute("message");
    if (flash == null) flash = (String) session.getAttribute("error");
    if (flash != null) {
        request.setAttribute("flash", flash);
        session.removeAttribute("message");
        session.removeAttribute("error");
    }

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

<!-- Flash Message -->
<c:if test="${not empty flash}">
    <div style="color: #333; background-color: #fff8cc; border: 1px solid #ffd700; padding: 8px; margin-bottom: 1rem; border-radius: 4px;">
            ${flash}
    </div>
</c:if>

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
                    <form action="${pageContext.request.contextPath}/manager" method="post" style="display: contents;">
                        <td><input type="text" name="editId" value="${student.id}" readonly style="width: 50px; font-size: 0.85rem;"></td>
                        <td><input type="text" name="editName" value="${student.name}" required placeholder="Name" style="width: 140px; font-size: 0.85rem;"></td>
                        <td><input type="email" name="editEmail" value="${student.email}" required placeholder="Email" style="width: 180px; font-size: 0.85rem;"></td>
                        <td style="white-space: nowrap;">
                            <!-- Button UPDATE -->
                            <button type="submit" name="action" value="updateStudent"
                                    style="font-size: 0.75rem; background-color: #fbc26d; color: white; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer; margin-right: 5px;">
                                Update
                            </button>

                            <!-- Button DELETE -->
                            <button type="submit" name="action" value="deleteStudent"
                                    style="font-size: 0.75rem; background-color: #dc3545; color: white; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer;">
                                Delete
                            </button>

                            <!-- hidden fields -->
                            <input type="hidden" name="studentId" value="${student.id}">
                            <input type="hidden" name="editName" value="${student.name}">
                            <input type="hidden" name="editEmail" value="${student.email}">
                        </td>
                    </form>
                </tr>
            </c:forEach>
            </tbody>

        </table>
    </c:otherwise>
</c:choose>
