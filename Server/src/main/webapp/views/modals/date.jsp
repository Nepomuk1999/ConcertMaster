<%@ page import="team_f.domain.enums.EventDutyProperty" %>
<%@ page import="team_f.domain.helper.EnumHelper" %>
<%@ page import="team_f.domain.enums.EventType" %>
<%@ page import="team_f.domain.enums.EventStatus" %>
<%@ page import="team_f.domain.entities.EventDuty" %>
<%@ page import="team_f.application.helper.DomainEntityHelper" %>
<%@ page import="team_f.domain.entities.MusicalWork" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form id="modal-form" action="/Date" class="form-group">
    <div class="modal-header">
        <h4 class="modal-title">Date</h4>
    </div>
    <div class="modal-body container-fluid">
        <input type="number" name="${EventDutyProperty.ID}" value="${eventDuty.eventDutyId}" class="hidden">

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_START_DATE}">
                    <t:DatePicker>
                        <jsp:attribute name="label">${"Startdate"}</jsp:attribute>
                        <jsp:attribute name="id">${EventDutyProperty.START_DATE}</jsp:attribute>
                        <jsp:attribute name="inputName">${EventDutyProperty.START_DATE}</jsp:attribute>
                        <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                        <jsp:attribute name="inputDateFormat">${"mm/dd/yyyy"}</jsp:attribute>
                        <jsp:attribute name="inputValue">${eventDuty.getStartDate("MM/dd/yyyy")}</jsp:attribute>
                    </t:DatePicker>
                </t:ErrorMessage>
            </div>

            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_END_DATE}">
                    <t:DatePicker>
                        <jsp:attribute name="label">${"Startdate"}</jsp:attribute>
                        <jsp:attribute name="id">${EventDutyProperty.END_DATE}</jsp:attribute>
                        <jsp:attribute name="inputName">${EventDutyProperty.END_DATE}</jsp:attribute>
                        <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                        <jsp:attribute name="inputDateFormat">${"mm/dd/yyyy"}</jsp:attribute>
                        <jsp:attribute name="inputValue">${eventDuty.getEndDate("MM/dd/yyyy")}</jsp:attribute>
                    </t:DatePicker>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6 form-inline">
                <t:ErrorMessage errorMessage="${PROBLEM_START_TIME}">
                    <t:ClockPicker>
                        <jsp:attribute name="label">${"Starttime"}</jsp:attribute>
                        <jsp:attribute name="id">${EventDutyProperty.START_TIME}</jsp:attribute>
                        <jsp:attribute name="inputName">${EventDutyProperty.START_TIME}</jsp:attribute>
                        <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                        <jsp:attribute name="inputClockFormat">${"HH:mm"}</jsp:attribute>
                        <jsp:attribute name="inputValue">${eventDuty.getStartTime("HH:mm")}</jsp:attribute>
                    </t:ClockPicker>
                </t:ErrorMessage>
            </div>

            <div class="form-group col-xs-8 col-sm-6 form-inline">
                <t:ErrorMessage errorMessage="${PROBLEM_END_TIME}">
                    <t:ClockPicker>
                        <jsp:attribute name="label">${"Endtime"}</jsp:attribute>
                        <jsp:attribute name="id">${EventDutyProperty.END_TIME}</jsp:attribute>
                        <jsp:attribute name="inputName">${EventDutyProperty.END_TIME}</jsp:attribute>
                        <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                        <jsp:attribute name="inputClockFormat">${"HH:mm"}</jsp:attribute>
                        <jsp:attribute name="inputValue">${eventDuty.getEndTime("HH:mm")}</jsp:attribute>
                    </t:ClockPicker>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_NAME}">
                    <label for="${EventDutyProperty.NAME}" class="control-label">Name</label><br>
                    <input id="${EventDutyProperty.NAME}" type="text" value="${eventDuty.name}" name="${EventDutyProperty.NAME}" placeholder="Name" class="form-control" required><br>
                </t:ErrorMessage>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_LOCATION}">
                    <label for="${EventDutyProperty.LOCATION}" class="control-label">Location</label><br>
                    <input id="${EventDutyProperty.LOCATION}" type="text" value="${eventDuty.location}" name="${EventDutyProperty.LOCATION}" placeholder="Location" class="form-control" required><br>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_CONDUCTOR}">
                    <label for="${EventDutyProperty.CONDUCTOR}" class="control-label">Conductor</label><br>
                    <input id="${EventDutyProperty.CONDUCTOR}" type="text" value="${eventDuty.conductor}" name="${EventDutyProperty.CONDUCTOR}" placeholder="Conductor" class="form-control" required><br>
                </t:ErrorMessage>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_DEFAULT_POINTS}">
                    <label for="${EventDutyProperty.DEFAULT_POINTS}" class="control-label">Standard Points</label><br>
                    <input id="${EventDutyProperty.DEFAULT_POINTS}" type="number" value="${empty eventDuty.defaultPoints ? 0 : eventDuty.defaultPoints}" name="${EventDutyProperty.DEFAULT_POINTS}" placeholder="Standard Points" value="0" class="form-control" min="0" required><br>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_EVENT_TYPE}">
                    <label for="${EventDutyProperty.EVENT_TYPE}" class="control-label">Eventtype</label><br>
                    <select class="selectpicker form-control" data-live-search="true" name="${EventDutyProperty.EVENT_TYPE}">
                        <option value="">Select Eventtype</option>
                        <c:forEach var="item" items="${EnumHelper.getEventTypeList()}">
                            <option data-divider="true"></option>
                            <option value="${item}" ${item eq eventDuty.eventType ? "selected" : ""}>${item}</option>
                        </c:forEach>
                    </select>
                </t:ErrorMessage>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_STATUS}">
                    <label for="${EventDutyProperty.EVENT_STATUS}" class="control-label">Eventstatus</label><br>
                    <select class="selectpicker form-control" data-live-search="true" name="${EventDutyProperty.EVENT_STATUS}">
                        <option value="">Select Eventstatus</option>
                        <c:forEach var="item" items="${EnumHelper.getEventStatusList()}">
                            <option data-divider="true"></option>
                            <option value="${item}" ${item eq eventDuty.eventStatus ? "selected" : ""}>${item}</option>
                        </c:forEach>
                    </select>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_MUSICAL_WORK_LIST}">
                    <label for="${EventDutyProperty.MUSICAL_WORK_LIST}" class="control-label">Musical Work</label><br>
                    <select class="selectpicker form-control" data-live-search="true" name="${EventDutyProperty.MUSICAL_WORK_LIST}">
                        <option value="">Select Musical Work</option>
                        <c:forEach var="item" items="${DomainEntityHelper.getMusicalWorkList()}">
                            <option data-divider="true"></option>
                            <option value="${item.musicalWorkID}"
                                <c:forEach var="musicalWork" items="${eventDuty.musicalWorkList}">
                                    <c:if test="${musicalWork.musicalWorkID eq item.musicalWorkID}">
                                        selected
                                    </c:if>
                                </c:forEach>
                            data-subtext="${item.composer}">${item.name}</option>
                        </c:forEach>
                    </select>
                    <input type="text" name="${EventDutyProperty.ALTERNATIVE_INSTRUMENTATION_LIST}" value="" class="hidden">
                    <!-- add a new popup window -->
                </t:ErrorMessage>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_INSTRUMENTATION}">
                    <label for="${EventDutyProperty.INSTRUMENTATION}" class="control-label">Instrumentation</label><br>
                    <select class="selectpicker form-control" data-live-search="true" name="${EventDutyProperty.INSTRUMENTATION}">
                        <option value="">Select Instrumentation</option>
                        <c:forEach var="item" items="${DomainEntityHelper.getInstrumentationList()}">
                            <option data-divider="true"></option>
                            <option value="${item.instrumentationID}" ${item.instrumentationID eq eventDuty.instrumentationId ? "selected" : ""}>Flute: ${item.flute}, Oboe: ${item.oboe}, Clarinet: ${item.clarinet}, Bassoon: ${item.bassoon}, Violin 1: ${item.violin1}, Violin 2: ${item.violin2}, Viola: ${item.viola}, Violincello: ${item.violincello}, Doublebass: ${item.doublebass}, Horn: ${item.horn}, Trumpet: ${item.trumpet}, Trombone: ${item.trombone}, Tube: ${item.tube}, Kettledrum: ${item.kettledrum}, Percussion: ${item.percussion}, Harp: ${item.harp}</option>
                        </c:forEach>
                    </select>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group rows">
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_DESCRIPTION}">
                    <label for="${EventDutyProperty.DESCRIPTION}" class="control-label">Description</label><br>
                    <input id="${EventDutyProperty.DESCRIPTION}" type="text" value="${eventDuty.description}" name="${EventDutyProperty.DESCRIPTION}" placeholder="Description" class="form-control"><br>
                </t:ErrorMessage>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <!--
                <t:ErrorMessage errorMessage="${PROBLEM_REHEARSAL_FOR}">
                    <label for="${EventDutyProperty.REHEARSAL_FOR}" class="control-label">Rehearsal For</label><br>
                    <input id="${EventDutyProperty.REHEARSAL_FOR}" type="number" value="${eventDuty.rehearsalFor}" name="${EventDutyProperty.REHEARSAL_FOR}" placeholder="Rehearsal For" class="form-control"><br>
                </t:ErrorMessage>
                -->
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button id="modal-cancel" type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button><!--data-dismiss="modal"-->
        <button id="modal-edit" type="button" class="btn btn-default">Edit</button>
        <button id="modal-save" type="submit" class="btn btn-info pull-right">Save</button>
    </div>
</form>