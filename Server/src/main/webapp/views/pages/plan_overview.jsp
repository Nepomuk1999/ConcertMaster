<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<t:MainPage>
    <jsp:attribute name="includeCalendar">${true}</jsp:attribute>
    <jsp:attribute name="includeClockPicker">${true}</jsp:attribute>
    <jsp:attribute name="includeOrchestraDate">${true}</jsp:attribute>
    
    <jsp:attribute name="content">
    </jsp:attribute>
</t:MainPage>