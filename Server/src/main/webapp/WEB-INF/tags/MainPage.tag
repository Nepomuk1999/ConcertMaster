<%@ tag description="main page" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="includes" fragment="true" required="false" %>
<%@ attribute name="includeCalendar" type="java.lang.Boolean" required="false" %>
<%@ attribute name="includeClockPicker" type="java.lang.Boolean" required="false" %>
<%@ attribute name="includeOrchestraDate" type="java.lang.Boolean" required="false" %>
<%@ attribute name="content" fragment="true" required="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Plan</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/main.css" />
    <script src='${pageContext.request.contextPath}/assets/javascripts/jquery/jquery.js'></script>
    <script src='${pageContext.request.contextPath}/assets/javascripts/jquery-ui/jquery-ui.js'></script>
    <script src='${pageContext.request.contextPath}/assets/javascripts/bootstrap/bootstrap.js'></script>
    <script src='${pageContext.request.contextPath}/assets/javascripts/moment/moment-with-locales.js'></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/load-animation.css" />
    <script src='${pageContext.request.contextPath}/assets/javascripts/load-animation.js'></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/modal.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/dialog.css" />
    <script src='${pageContext.request.contextPath}/assets/javascripts/dialog.js'></script>
    <script src='${pageContext.request.contextPath}/assets/javascripts/modal.js'></script>
    <script src='${pageContext.request.contextPath}/assets/javascripts/bootstrap-select/bootstrap-select.js'></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/bootstrap-select.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/bootstrap.css" />
    <script src='${pageContext.request.contextPath}/assets/javascripts/add-remove-list.js'></script>
<c:choose>
    <c:when test="${includeCalendar}">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/fullcalendar.css" />
    <script src='${pageContext.request.contextPath}/assets/javascripts/fullcalendar/fullcalendar.js'></script>
    <script src='${pageContext.request.contextPath}/assets/javascripts/calendar.js'></script>
    </c:when>
</c:choose>
<c:choose>
    <c:when test="${includeClockPicker}">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/clockpicker/bootstrap-clockpicker.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/clockpicker/jquery-clockpicker.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/clockpicker.css" />
    <script src='${pageContext.request.contextPath}/assets/javascripts/clockpicker/bootstrap-clockpicker.js'></script>
    <script src='${pageContext.request.contextPath}/assets/javascripts/clockpicker/jquery-clockpicker.js'></script>
    </c:when>
</c:choose>
<c:choose>
    <c:when test="${includeOrchestraDate}">
    <script src='${pageContext.request.contextPath}/assets/javascripts/orchestra-date.js'></script>
    </c:when>
</c:choose>
    <jsp:invoke fragment="includes" />
</head>
<body>
    <div id="header">
    </div>

    <aside id="sidebar">
    </aside>

    <div id="main_content">
<c:choose>
    <c:when test="${includeCalendar}">
        <div id="calendar"></div>
    </c:when>
</c:choose>
        <jsp:invoke fragment="content" />
    </div>

    <div id="footer">
    </div>
</body>
</html>