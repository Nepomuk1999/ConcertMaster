<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="calendar-action-chooser" class="modal-header">
    <h4 class="modal-title">Calendar Action Chooser</h4>
</div>
<div class="modal-body container-fluid">
    <div class="control-group row">
        <div class="form-group col-xs-3 col-sm-12">
            <button id="calendar-action-chooser-date" type="button" class="btn btn-warning btn-block" data-dismiss="modal">Show Event</button>
        </div>
    </div>
    <div class="control-group row">
        <div class="form-group col-xs-3 col-sm-12">
            <button id="calendar-action-chooser-plan" type="button" class="btn btn-info btn-block" data-dismiss="modal">Add Rehearsal</button>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button id="modal-cancel" type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button><!--data-dismiss="modal"-->
</div>

<script type="text/javascript">
    $( document ).ready(function() {
        $('#calendar-action-chooser-plan').click(function () {
            showModal("rehearsal-for", "/RehearsalFor", ${eventData}, false, isFurtherEditable(JSON.parse('${eventData}')['start']), function () {
            });
        });

        $('#calendar-action-chooser-date').click(function () {
            showModal("date", "/Date", ${eventData}, false, isFurtherEditable(JSON.parse('${eventData}')['start']), function () {
            });
        });
    });
</script>