<%
    String view = request.getParameter("view");
    String viewPage;
    if ("courses".equals(view)) {
        viewPage = "courses.jsp";
    } else if ("students".equals(view)) {
        viewPage = "students.jsp";
    } else if ("teachers".equals(view)) {
        viewPage = "teachers.jsp";
    } else if ("register".equals(view)) {
        viewPage = "register.jsp";
    } else {
        viewPage = "dashboard.jsp";
    }
    request.setAttribute("viewPage", viewPage);
%>
<jsp:include page="WEB-INF/layout.jsp" />


