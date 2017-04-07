<%@ tag description="clock picker" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="inputName" type="java.lang.String" required="true" %>
<%@ attribute name="inputRequired" type="java.lang.Boolean" required="false" %>
<%@ attribute name="check" type="java.lang.Boolean" required="false" %>

<input type = "text" id="${id}" name="${inputName}" class="form-control"
<c:choose>
    <c:when test="${inputRequired}">
 required
    </c:when>
</c:choose>
>

<script type="text/javascript">
    $( "#${id}" ).datepicker();
    $( "#${id}" ).datepicker("show");

    $('#${id}').clockpicker({
        placement: 'auto',
        align: 'left',
        donetext: 'Done'
    });
</script>