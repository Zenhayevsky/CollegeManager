<%--
  Created by IntelliJ IDEA.
  User: dptoa
  Date: 4/21/2025
  Time: 6:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>College Manager</title>
    <style>

        body {
            position: relative;
            font-family: sans-serif;
            margin: 0;
            display: flex;
            color: #fff;
        }
        body::before {
            content: "";
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-image: url('https://images.unsplash.com/photo-1523050854058-8df90110c9f1');
            background-size: cover;
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-position: center;
            opacity: 0.25;
            z-index: -1;
        }
        nav {
            width: 200px;
            background-color: rgba(44, 62, 80, 0.9);
            color: white;
            min-height: 100vh;
            padding: 1rem;
        }
        nav a {
            color: white;
            display: block;
            margin: 1rem 0;
            text-decoration: none;
        }
        main {
            flex-grow: 1;
            padding: 2rem;
            background-color: rgba(0, 0, 0, 0.6);
            min-height: 100vh;
        }
        h2, p, th, td, label {
            color: white;
        }
        table {
            background-color: rgba(255, 255, 255, 0.1);
        }
    </style>
</head>
<body>
<nav>
    <h2>Menu</h2>
    <a href="${pageContext.request.contextPath}/manager?view=dashboard">🏠 Dashboard</a>
    <a href="${pageContext.request.contextPath}/manager?view=courses">📚 Courses</a>
    <a href="${pageContext.request.contextPath}/manager?view=students">👨‍🎓 Students</a>
    <a href="${pageContext.request.contextPath}/manager?view=register">📝 Register</a>
</nav>
<main>
    <jsp:include page="${viewPage}" />
</main>
</body>
</html>