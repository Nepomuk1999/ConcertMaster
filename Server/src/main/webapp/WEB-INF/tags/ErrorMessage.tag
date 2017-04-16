<%@ tag description="clock picker" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="errorMessage" type="java.lang.String" required="true" %>

<div class="control-group ${empty errorMessage ? "" : "error"}">
    <jsp:doBody />

    <div class="alert alert-danger" role="alert">${errorMessage}</div>
</div>