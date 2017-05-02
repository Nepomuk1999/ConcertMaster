<%@ tag description="clock picker" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="inputName" type="java.lang.String" required="true" %>
<%@ attribute name="inputValue" type="java.lang.String" required="false" %>
<%@ attribute name="inputRequired" type="java.lang.Boolean" required="false" %>
<%@ attribute name="inputClockFormat" type="java.lang.String" required="true" %>
<%@ attribute name="label" type="java.lang.String" required="false" %>
<%@ attribute name="inputClass" type="java.lang.String" required="false" %>

<label for="${id}" class="control-label">${label}</label><br>
<div id="${id}" class="controls input-group clockpicker">
    <input id="${id}-input" type="text" value="${inputValue}" name="${inputName}" class="form-control" placeholder="${inputClockFormat}" ${inputRequired ? "required" : ""} class="${inputClass}">
    <span class="input-group-addon">
        <span class="glyphicon glyphicon-time"></span>
    </span>
    <p class="help-block"></p>
</div>
<script type="text/javascript">
    $('#${id}').clockpicker({
        placement: 'bottom',
        align: 'left',
        donetext: 'Done',
        autoclose: true
    });

    $('#${id}').on("click", function () {
        if($("#${id}-input")[0].hasAttribute("disabled")) {
            $('#${id}').clockpicker('hide');
        }
    });

    $('#${id}').focusout(function(){
        $('#${id}').clockpicker('hide');
    });
</script>
