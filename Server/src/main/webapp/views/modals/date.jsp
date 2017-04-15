<%@ page import="team_f.domain.enums.EventDutyProperty" %>
<%@ page import="team_f.domain.helper.EnumHelper" %>
<%@ page import="team_f.domain.enums.EventType" %>
<%@ page import="team_f.domain.enums.EventStatus" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="modal-form" action="/Date" class="form-group">
    <div class="modal-header">
        <h4 class="modal-title">Date</h4>
    </div>
    <div class="modal-body container-fluid">
        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <t:DatePicker>
                    <jsp:attribute name="label">${"Startdate"}</jsp:attribute>
                    <jsp:attribute name="id">${EventDutyProperty.START_DATE}</jsp:attribute>
                    <jsp:attribute name="inputName">${EventDutyProperty.START_DATE}</jsp:attribute>
                    <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                    <jsp:attribute name="inputDateFormat">${"MM/dd/yyyy"}</jsp:attribute>
                    <jsp:attribute name="inputValue">${eventDuty.startDate}</jsp:attribute>
                </t:DatePicker>
            </div>

            <div class="form-group col-xs-8 col-sm-6">
                <t:DatePicker>
                    <jsp:attribute name="label">${"Startdate"}</jsp:attribute>
                    <jsp:attribute name="id">${EventDutyProperty.END_DATE}</jsp:attribute>
                    <jsp:attribute name="inputName">${EventDutyProperty.END_DATE}</jsp:attribute>
                    <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                    <jsp:attribute name="inputDateFormat">${"MM/dd/yyyy"}</jsp:attribute>
                    <jsp:attribute name="inputValue">${eventDuty.endDate}</jsp:attribute>
                </t:DatePicker>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6 form-inline">
                <t:ClockPicker>
                    <jsp:attribute name="label">${"Starttime"}</jsp:attribute>
                    <jsp:attribute name="id">${EventDutyProperty.START_TIME}</jsp:attribute>
                    <jsp:attribute name="inputName">${EventDutyProperty.START_TIME}</jsp:attribute>
                    <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                    <jsp:attribute name="inputClockFormat">${"HH:mm"}</jsp:attribute>
                    <jsp:attribute name="inputValue">${eventDuty.startTime}</jsp:attribute>
                </t:ClockPicker>
            </div>

            <div class="form-group col-xs-8 col-sm-6 form-inline">
                <t:ClockPicker>
                    <jsp:attribute name="label">${"Endtime"}</jsp:attribute>
                    <jsp:attribute name="id">${EventDutyProperty.END_TIME}</jsp:attribute>
                    <jsp:attribute name="inputName">${EventDutyProperty.END_TIME}</jsp:attribute>
                    <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                    <jsp:attribute name="inputClockFormat">${"HH:mm"}</jsp:attribute>
                    <jsp:attribute name="inputValue">${eventDuty.endTime}</jsp:attribute>
                </t:ClockPicker>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <label for="${EventDutyProperty.NAME}" class="control-label">Name</label><br>
                <input id="${EventDutyProperty.NAME}" type="text" value="${eventDuty.name}" name="${EventDutyProperty.NAME}" placeholder="Name" class="form-control" required><br>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <label for="${EventDutyProperty.LOCATION}" class="control-label">Location</label><br>
                <input id="${EventDutyProperty.LOCATION}" type="text" value="${eventDuty.location}" name="${EventDutyProperty.LOCATION}" placeholder="Location" class="form-control" required><br>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <label for="${EventDutyProperty.CONDUCTOR}" class="control-label">Conductor</label><br>
                <input id="${EventDutyProperty.CONDUCTOR}" type="text" value="${eventDuty.conductor}" name="${EventDutyProperty.CONDUCTOR}" placeholder="Conductor" class="form-control" required><br>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <label for="${EventDutyProperty.DEFAULT_POINTS}" class="control-label">Standard Points</label><br>
                <input id="${EventDutyProperty.DEFAULT_POINTS}" type="number" value="${empty eventDuty.standardPoints ? 0 : eventDuty.standardPoints}" name="${EventDutyProperty.DEFAULT_POINTS}" placeholder="Standard Points" value="0" class="form-control" min="0" required><br>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <label for="${EventDutyProperty.EVENT_TYPE}" class="control-label">Eventtype</label><br>
                <select class="form-control" name="${EventDutyProperty.EVENT_TYPE}">
                    <option value="">Select Eventtype</option>
                    <c:forEach var="item" items="${EnumHelper.getEventTypeList()}">
                        <option value="${item}">${item}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <label for="${EventDutyProperty.EVENT_STATUS}" class="control-label">Eventstatus</label><br>
                <select class="form-control" name="${EventDutyProperty.EVENT_STATUS}">
                    <option value="">Select Eventstatus</option>
                    <c:forEach var="item" items="${EnumHelper.getEventStatusList()}">
                        <option value="${item}">${item}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <label for="${EventDutyProperty.MUSICAL_WORK_LIST}" class="control-label">Musical Work</label><br>
                <input id="${EventDutyProperty.MUSICAL_WORK_LIST}" type="text" value="${eventDuty.musicalWork}" name="${EventDutyProperty.MUSICAL_WORK_LIST}" placeholder="Musical Work" class="form-control" required><br>
                <!-- add a new window popup -->
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <label for="${EventDutyProperty.INSTRUMENTATION}" class="control-label">Instrumentation</label><br>
                <input id="${EventDutyProperty.INSTRUMENTATION}" type="number" value="${empty eventDuty.instrumentation ? 0 : eventDuty.instrumentation}" name="${EventDutyProperty.INSTRUMENTATION}" placeholder="Instrumentation" class="form-control" min="0" required><br>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <label for="${EventDutyProperty.DESCRIPTION}" class="control-label">Description</label><br>
                <input id="${EventDutyProperty.DESCRIPTION}" type="text" value="${eventDuty.description}" name="${EventDutyProperty.DESCRIPTION}" placeholder="Description" class="form-control"><br>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <!--<label for="${EventDutyProperty.REHEARSAL_FOR}" class="control-label">Rehearsal For</label><br>
                <input id="${EventDutyProperty.REHEARSAL_FOR}" type="number" value="${eventDuty.rehearsalFor}" name="${EventDutyProperty.REHEARSAL_FOR}" placeholder="Rehearsal For" class="form-control"><br>-->
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button id="modal-cancel" type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button><!--data-dismiss="modal"-->
        <button id="modal-edit" type="button" class="btn btn-default">Edit</button>
        <button id="modal-save" type="submit" class="btn btn-info pull-right">Save</button>
    </div>
</form>
