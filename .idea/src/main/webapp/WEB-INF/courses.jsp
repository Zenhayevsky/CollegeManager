<%--
  Created by IntelliJ IDEA.
  User: dptoa/zehna
  Date: 4/21/2025
  Time: 6:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.collegemanager.model.CollegeManager" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    CollegeManager collegeManager = (CollegeManager) session.getAttribute("collegeManager");
    request.setAttribute("allCourses", collegeManager != null ? collegeManager.getAllCourses() : null);
%>

<h2 style="color: #001933;">Manage Courses</h2>
<form action="${pageContext.request.contextPath}/manager" method="post" style="margin-bottom: 2rem; font-size: 0.85rem; max-width: 400px;">
    <input type="hidden" name="action" value="addCourse">
    <div style="margin-bottom: 1rem; display: flex; flex-direction: column;">
        <label for="courseCode">Code:</label>
        <input type="text" id="courseCode" name="courseCode" required style="font-size: 0.85rem;">
    </div>
    <div style="margin-bottom: 1rem; display: flex; flex-direction: column;">
        <label for="courseTitle">Title:</label>
        <input type="text" id="courseTitle" name="courseTitle" required style="font-size: 0.85rem;">
    </div>
    <div style="margin-bottom: 1rem; display: flex; flex-direction: column;">
        <label for="courseCredits">Credits:</label>
        <input type="number" id="courseCredits" name="courseCredits" required style="font-size: 0.85rem; ; width: 60px;">
    </div>
    <div style="margin-bottom: 1rem; display: flex; flex-direction: column;">
        <label for="courseInstructor">Instructor:</label>
        <input type="text" id="courseInstructor" name="courseInstructor" required style="font-size: 0.85rem;">
    </div>
    <button type="submit" style="font-size: 0.85rem; background-color: #007BFF; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer;">Add Course</button>
</form>

<h2 style="color: #001933;">Available Courses</h2>
<c:choose>
    <c:when test="${empty allCourses}">
        <p>No courses available yet.</p>
    </c:when>
    <c:otherwise>
        <table border="1" style="font-size: 0.9rem;">
            <thead>
            <tr>
                <th>Code</th>
                <th>Title</th>
                <th>Credits</th>
                <th>Instructor</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="course" items="${allCourses}">
                <tr>
                    <form action="${pageContext.request.contextPath}/manager" method="post" style="display: contents;">
                        <td><input type="text" name="editCode" value="${course.code}" readonly style="width: 80px; font-size: 0.9rem;"></td>
                        <td><input type="text" name="editTitle" value="${course.title}" required placeholder="Title" style="width: 150px; font-size: 0.9rem;"></td>
                        <td><input type="number" name="editCredits" value="${course.credits}" required placeholder="Credits" style="width: 60px; font-size: 0.9rem;"></td>
                        <td><input type="text" name="editInstructor" value="${course.instructor}" required placeholder="Instructor" style="width: 140px; font-size: 0.9rem;"></td>
                        <td style="white-space: nowrap;">
                            <!-- Button Update -->
                            <button type="submit" name="action" value="updateCourse"
                                    style="font-size: 0.75rem; background-color: #fbc26d; color: white; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer; margin-right: 5px;">
                                Update
                            </button>

                            <!-- Button Delete -->
                            <button type="submit" name="action" value="deleteCourse"
                                    style="font-size: 0.75rem; background-color: #dc3545; color: white; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer;">
                                Delete
                            </button>

                            <!-- deleteCourse need courseCode -->
                            <input type="hidden" name="courseCode" value="${course.code}">
                        </td>
                    </form>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>
