<%@ tag description="clock picker" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="label" type="java.lang.String" required="false" %>
<%@ attribute name="standardItem" type="java.lang.String" required="false" %>
<%@ attribute name="selectedItem" type="java.lang.String" required="false" %>
<%@ attribute name="items" rtexprvalue="true" type="java.util.Collection<String>"  required="true" %>

<label for="${id}" class="control-label">${label}</label><br>
<select id="${id}" class="form-control" name="${id}">
    <option value="">${standardItem}</option>
    <c:forEach var="item" items="${items}">
        <option value="${item}" ${item eq selectedItem ? "selected" : ""}>${item}</option>
    </c:forEach>
</select>