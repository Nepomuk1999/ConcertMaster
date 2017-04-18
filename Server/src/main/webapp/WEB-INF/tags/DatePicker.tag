<%@ tag description="clock picker" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="inputName" type="java.lang.String" required="true" %>
<%@ attribute name="inputValue" type="java.lang.String" required="false" %>
<%@ attribute name="inputDateFormat" type="java.lang.String" required="true" %>
<%@ attribute name="inputRequired" type="java.lang.Boolean" required="false" %>
<%@ attribute name="check" type="java.lang.Boolean" required="false" %>
<%@ attribute name="label" type="java.lang.String" required="false" %>

<label for="${id}" class="control-label">${label}</label><br>
<input type = "text" id="${id}" name="${inputName}" class="form-control" placeholder="${inputDateFormat}" value="${inputValue}" ${inputRequired ? "required" : ""}>

<script type="text/javascript">
    $( "#${id}" ).datepicker({
        autoclose: true,
        <%-- todayHighlight: true,*/
        dateFormat: '${inputDateFormat}' --%>
    });
    $( "#${id}" ).datepicker("show");
</script>
