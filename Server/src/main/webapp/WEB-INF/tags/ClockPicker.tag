<%@ tag description="clock picker" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="inputName" type="java.lang.String" required="true" %>
<%@ attribute name="inputValue" type="java.lang.String" required="false" %>
<%@ attribute name="inputRequired" type="java.lang.Boolean" required="false" %>
<%@ attribute name="inputClockFormat" type="java.lang.String" required="true" %>
<%@ attribute name="label" type="java.lang.String" required="false" %>

<label for="${id}" class="control-label">${label}</label><br>
<div id="${id}" class="input-group clockpicker">
    <input id="${id}-input" type="text" value="${inputValue}" name="${inputName}" class="form-control" placeholder="${inputClockFormat}" ${inputRequired ? "required" : ""}>
    <span class="input-group-addon">
        <span class="glyphicon glyphicon-time"></span>
    </span>
</div>
<script type="text/javascript">
    $('#${id}').clockpicker({
        placement: 'bottom',
        align: 'left',
        donetext: 'Done'
    });

    $('.clockpicker .input-group-addon').on("click", function () {
        if($("#${id}-input")[0].hasAttribute("disabled")) {
            $('#${id}').clockpicker('hide');
        }
    });
</script>
