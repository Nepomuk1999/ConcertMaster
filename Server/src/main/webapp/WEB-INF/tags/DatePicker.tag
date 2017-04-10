<%@ tag description="clock picker" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="label" type="java.lang.String" required="true" %>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="placeholder" type="java.lang.String" required="true" %>
<%@ attribute name="inputName" type="java.lang.String" required="true" %>
<%@ attribute name="inputValue" type="java.lang.String" required="false" %>
<%@ attribute name="inputRequired" type="java.lang.Boolean" required="false" %>
<%@ attribute name="check" type="java.lang.Boolean" required="false" %>
<%@ attribute name="errorMessage" type="java.lang.String" required="false" %>

<div class="control-group ${empty errorMessage ? "" : "error"}">
    <label for="${id}" class="control-label">${label}</label><br>
    <div class="controls">
        <input type = "text" id="${id}" value="${inputValue}" name="${inputName}" placeholder="${placeholder}" class="form-control"  ${inputRequired ? "required" : ""}>
        <span class="help-block">${errorMessage}</span>
    </div>
</div>

<script type="text/javascript">
    $( "#${id}" ).datepicker();
    $( "#${id}" ).datepicker("show");

    $('#${id}').clockpicker({
        placement: 'auto',
        align: 'left',
        donetext: 'Done'
    });
</script>