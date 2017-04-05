<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:MainPage>
    <jsp:attribute name="includes">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/fullcalendar.css" />
    <script src='${pageContext.request.contextPath}/assets/javascripts/fullcalendar/fullcalendar.js'></script>
    <script src='${pageContext.request.contextPath}/assets/javascripts/Calendar.js'></script>
    </jsp:attribute>

    <jsp:attribute name="content">
    <div id="calendar"></div>
    </jsp:attribute>
</t:MainPage>