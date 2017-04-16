<%@ tag description="clock picker" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="errorMessage" type="java.lang.String" required="true" %>

<div class="control-group ${empty errorMessage ? "" : "error"}">
    <jsp:doBody />

    <c:choose>
        <c:when test="${not empty errorMessage}">
            <!--<div class="alert alert-danger" role="alert">${errorMessage}</div>-->
            <!--<p class="text-error">${errorMessage}</p>-->
            <div class="alert alert-danger">
                <span class="glyphicon glyphicon-exclamation-sign"></span>
                    ${errorMessage}
            </div>
        </c:when>
    </c:choose>
</div>