<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<form id="modal-form" action="/Date" class="form-group">
    <div class="modal-header">
        <h4 class="modal-title">Date</h4>
    </div>
    <div class="modal-body container-fluid">
        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <t:DatePicker>
                    <jsp:attribute name="label">${"Startdate"}</jsp:attribute>
                    <jsp:attribute name="id">${"startdate"}</jsp:attribute>
                    <jsp:attribute name="placeholder">${"Startdate"}</jsp:attribute>
                    <jsp:attribute name="inputName">${"startdate"}</jsp:attribute>
                    <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                    <jsp:attribute name="inputDateFormat">${"yyyy/MM/dd"}</jsp:attribute>
                    <jsp:attribute name="inputValue">${eventDuty.startDate}</jsp:attribute>
                </t:DatePicker>
            </div>

            <div class="form-group col-xs-8 col-sm-6">
                <t:DatePicker>
                    <jsp:attribute name="label">${"Enddate"}</jsp:attribute>
                    <jsp:attribute name="id">${"enddate"}</jsp:attribute>
                    <jsp:attribute name="placeholder">${"Enddate"}</jsp:attribute>
                    <jsp:attribute name="inputName">${"enddate"}</jsp:attribute>
                    <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                    <jsp:attribute name="inputDateFormat">${"dd/MM/yyyy"}</jsp:attribute>
                    <jsp:attribute name="inputValue">${eventDuty.endDate}</jsp:attribute>
                </t:DatePicker>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6 form-inline">
                <t:ClockPicker>
                    <jsp:attribute name="label">${"Starttime"}</jsp:attribute>
                    <jsp:attribute name="id">${"starttime"}</jsp:attribute>
                    <jsp:attribute name="placeholder">${"Starttime"}</jsp:attribute>
                    <jsp:attribute name="inputName">${"starttime"}</jsp:attribute>
                    <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                    <jsp:attribute name="inputClockFormat">${"HH:mm"}</jsp:attribute>
                    <jsp:attribute name="inputValue">${eventDuty.startTime}</jsp:attribute>
                </t:ClockPicker>
            </div>

            <div class="form-group col-xs-8 col-sm-6 form-inline">
                <t:ClockPicker>
                    <jsp:attribute name="label">${"Endtime"}</jsp:attribute>
                    <jsp:attribute name="id">${"endtime"}</jsp:attribute>
                    <jsp:attribute name="placeholder">${"Endtime"}</jsp:attribute>
                    <jsp:attribute name="inputName">${"endtime"}</jsp:attribute>
                    <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                    <jsp:attribute name="inputClockFormat">${"HH:mm"}</jsp:attribute>
                    <jsp:attribute name="inputValue">${eventDuty.endTime}</jsp:attribute>
                </t:ClockPicker>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <label for="name" class="control-label">Name</label><br>
                <input id="name" type="text" value="${eventDuty.name}" name="name" placeholder="Name" class="form-control" required><br>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <label for="location" class="control-label">Location</label><br>
                <input id="location" type="text" value="${eventDuty.location}" name="location" placeholder="Location" class="form-control" required><br>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <label for="conductor" class="control-label">Conductor</label><br>
                <input id="conductor" type="text" value="${eventDuty.conductor}" name="conductor" placeholder="Conductor" class="form-control" required><br>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <label for="standard-points" class="control-label">Standard Points</label><br>
                <input id="standard-points" type="number" value="${empty eventDuty.standardPoints ? 0 : eventDuty.standardPoints}" name="standard-points" placeholder="Standard Points" value="0" class="form-control" min="0" required><br>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-4">
                <label for="eventtype" class="control-label">Eventtype</label><br>
                <input id="eventtype" type="text" value="${eventDuty.getEventtype()}" name="eventtype" placeholder="Eventtype" class="form-control" required><br>
            </div>
            <div class="form-group col-xs-8 col-sm-4">
                <label for="musical-work" class="control-label">Musical Work</label><br>
                <input id="musical-work" type="text" value="${eventDuty.getMusicalWork()}" name="musical-work" placeholder="Musical Work" class="form-control" required><br>
            </div>
            <div class="form-group col-xs-8 col-sm-4">
                <label for="rehearsal-for" class="control-label">Rehearsal For</label><br>
                <input id="rehearsal-for" type="text" value="${eventDuty.getRehearsalFor()}" name="rehearsal-for" placeholder="Rehearsal For" class="form-control" required><br>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-4">
                <label for="eventstatus" class="control-label">Eventstatus</label><br>
                <input id="eventstatus" type="text" value="${eventDuty.getEventstatus()}" name="eventstatus" placeholder="Eventstatus" class="form-control" required><br>
            </div>
            <div class="form-group col-xs-8 col-sm-4">
                <label for="description" class="control-label">Description</label><br>
                <input id="description" type="text" value="${eventDuty.getDescription()}" name="description" placeholder="Description" class="form-control" required><br>
            </div>
            <div class="form-group col-xs-8 col-sm-4">
                <label for="instrumentation" class="control-label">Instrumentation</label><br>
                <input id="instrumentation" type="number" value="${empty eventDuty.getInstrumentation() ? 0 : eventDuty.getInstrumentation()}" name="instrumentation" placeholder="Instrumentation" class="form-control" min="0" required><br>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button id="modal-cancel" type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button><!--data-dismiss="modal"-->
        <button id="modal-edit" type="button" class="btn btn-default">Edit</button>
        <button id="modal-save" type="submit" class="btn btn-info pull-right">Save</button>
    </div>
</form>
