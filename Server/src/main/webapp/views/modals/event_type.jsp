<%@ page import="team_f.domain.enums.properties.EventDutyProperty" %>
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

<form id="modal-form" action="${IS_DATE ? "/Date" : ""} ${IS_REHEARSAL_FOR ? "/RehearsalFor" : ""}" class="form-group">
    <div class="modal-header">
        <h4 class="modal-title">
            <c:if test="${IS_DATE}">Event</c:if>
            <c:if test="${IS_REHEARSAL_FOR}">Rehearsal</c:if>
        </h4>
    </div>
    <div class="modal-body container-fluid">
        <input type="number" name="${EventDutyProperty.ID}" value="${eventDuty.eventDutyID}" class="hidden">

        <div class="control-group row">
            <c:if test="${IS_REHEARSAL_FOR}">
                <div class="form-group col-xs-12 col-sm-6">
                    <t:ErrorMessage errorMessage="${PROBLEM_REHEARSAL_FOR}">
                        <label for="${EventDutyProperty.REHEARSAL_FOR}" class="control-label">For Event</label><br>
                        <input type="number" id="${EventDutyProperty.REHEARSAL_FOR}_" class="hidden" name="${EventDutyProperty.REHEARSAL_FOR}" value="${eventDuty.rehearsalFor.eventDutyID}">
                        <label id="${EventDutyProperty.REHEARSAL_FOR}" class="form-control">${eventDuty.rehearsalFor.name}</label>
                        <%--<select class="selectpicker form-control" data-live-search="true" name="${EventDutyProperty.REHEARSAL_FOR}" disabled>
                            <option value="${eventDuty.rehearsalFor.eventDutyID}" selected disabled>${eventDuty.rehearsalFor.name}</option>
                        </select>--%>
                    </t:ErrorMessage>
                </div>
            </c:if>
            <div class="form-group col-xs-12 col-sm-6">
                <c:if test="${IS_DATE}">
                    <t:ErrorMessage errorMessage="${PROBLEM_EVENT_TYPE}">
                        <label for="${EventDutyProperty.EVENT_TYPE}" class="control-label">Event Type</label><br>
                        <select id="${EventDutyProperty.EVENT_TYPE}" class="selectpicker form-control ${EventDutyProperty.EVENT_TYPE}" data-live-search="true" name="${EventDutyProperty.EVENT_TYPE}">
                            <option value="">Select Event Type</option>
                            <c:forEach var="item" items="${EnumHelper.getBasicEventTypeList()}">
                                <option data-divider="true"></option>
                                <option value="${item}" ${item eq eventDuty.eventType ? "selected" : ""}>${item}</option>
                            </c:forEach>
                        </select>
                    </t:ErrorMessage>
                </c:if>
                <c:if test="${IS_REHEARSAL_FOR}">
                    <label for="${EventDutyProperty.EVENT_TYPE}_" class="control-label">Eventtype</label><br>
                    <label id="${EventDutyProperty.EVENT_TYPE}_" class="form-control">${eventDuty.rehearsalFor.eventStatus}</label>
                </c:if>
            </div>
        </div>

        <c:if test="${IS_REHEARSAL_FOR}">
            <div class="control-group row">
                <div class="form-group col-xs-12 col-sm-6">
                    <label for="${EventDutyProperty.START_TIME}_" class="control-label">Start Time</label><br>
                    <label id="${EventDutyProperty.START_TIME}_" class="form-control">${eventDuty.rehearsalFor.getStartDate("MM/dd/yyyy")} ${eventDuty.rehearsalFor.getStartTime("HH:mm")}</label>
                </div>

                <div class="form-group col-xs-12 col-sm-6">
                    <label for="${EventDutyProperty.END_TIME}_" class="control-label">End Time</label><br>
                    <label id="${EventDutyProperty.END_TIME}_" class="form-control">${eventDuty.rehearsalFor.getEndDate("MM/dd/yyyy")} ${eventDuty.rehearsalFor.getEndTime("HH:mm")}</label>
                </div>
            </div>
            <hr />
        </c:if>

        <div class="control-group row">
            <div class="form-group col-xs-12 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_START_DATE}">
                    <t:DatePicker>
                        <jsp:attribute name="label">${"Start Date"}</jsp:attribute>
                        <jsp:attribute name="id">${EventDutyProperty.START_DATE}</jsp:attribute>
                        <jsp:attribute name="inputName">${EventDutyProperty.START_DATE}</jsp:attribute>
                        <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                        <jsp:attribute name="inputDateFormat">${"mm/dd/yyyy"}</jsp:attribute>
                        <jsp:attribute name="inputValue">${eventDuty.getStartDate("MM/dd/yyyy")}</jsp:attribute>
                        <jsp:attribute name="inputClass">${"ValidateStartDate"}</jsp:attribute>
                    </t:DatePicker>
                </t:ErrorMessage>
            </div>

            <div class="form-group col-xs-12 col-sm-6 ${EventDutyProperty.END_DATE}">
                <t:ErrorMessage errorMessage="${PROBLEM_END_DATE}">
                    <t:DatePicker>
                        <jsp:attribute name="label">${"End Date"}</jsp:attribute>
                        <jsp:attribute name="id">${EventDutyProperty.END_DATE}</jsp:attribute>
                        <jsp:attribute name="inputName">${EventDutyProperty.END_DATE}</jsp:attribute>
                        <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                        <jsp:attribute name="inputDateFormat">${"mm/dd/yyyy"}</jsp:attribute>
                        <jsp:attribute name="inputValue">${eventDuty.getEndDate("MM/dd/yyyy")}</jsp:attribute>
                        <jsp:attribute name="inputClass">${"ValidateEndDate"}</jsp:attribute>
                    </t:DatePicker>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group row">
            <div class="form-group col-xs-12 col-sm-6 form-inline">
                <t:ErrorMessage errorMessage="${PROBLEM_START_TIME}">
                    <t:ClockPicker>
                        <jsp:attribute name="label">${"Start Time"}</jsp:attribute>
                        <jsp:attribute name="id">${EventDutyProperty.START_TIME}</jsp:attribute>
                        <jsp:attribute name="inputName">${EventDutyProperty.START_TIME}</jsp:attribute>
                        <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                        <jsp:attribute name="inputClockFormat">${"HH:mm"}</jsp:attribute>
                        <jsp:attribute name="inputValue">${eventDuty.getStartTime("HH:mm")}</jsp:attribute>
                        <jsp:attribute name="inputClass">${"ValidateStartTime"}</jsp:attribute>
                    </t:ClockPicker>
                </t:ErrorMessage>
            </div>

            <div class="form-group col-xs-12 col-sm-6 form-inline">
                <t:ErrorMessage errorMessage="${PROBLEM_END_TIME}">
                    <t:ClockPicker>
                        <jsp:attribute name="label">${"End Time"}</jsp:attribute>
                        <jsp:attribute name="id">${EventDutyProperty.END_TIME}</jsp:attribute>
                        <jsp:attribute name="inputName">${EventDutyProperty.END_TIME}</jsp:attribute>
                        <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                        <jsp:attribute name="inputClockFormat">${"HH:mm"}</jsp:attribute>
                        <jsp:attribute name="inputValue">${eventDuty.getEndTime("HH:mm")}</jsp:attribute>
                        <jsp:attribute name="inputClass">${"ValidateEndTime"}</jsp:attribute>
                    </t:ClockPicker>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group row">
            <div class="control-group form-group col-xs-12 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_NAME}">
                    <label for="${EventDutyProperty.NAME}" class="control-label">Name</label><br>
                    <div class="controls">
                        <input id="${EventDutyProperty.NAME}" type="text" value="${eventDuty.name}" name="${EventDutyProperty.NAME}" placeholder="Name" class="form-control" minlength="2" required><br>
                        <p class="help-block"></p>
                    </div>
                </t:ErrorMessage>
            </div>
            <div class="form-group col-xs-12 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_LOCATION}">
                    <label for="${EventDutyProperty.LOCATION}" class="control-label">Location</label><br>
                    <div class="controls">
                        <input id="${EventDutyProperty.LOCATION}" type="text" value="${eventDuty.location}" name="${EventDutyProperty.LOCATION}" placeholder="Location" class="form-control" minlength="2" required><br>
                        <p class="help-block"></p>
                    </div>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group row">
            <div class="form-group col-xs-12 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_CONDUCTOR}">
                    <label for="${EventDutyProperty.CONDUCTOR}" class="control-label">Conductor</label><br>
                    <div class="controls">
                        <input id="${EventDutyProperty.CONDUCTOR}" type="text" value="${eventDuty.conductor}" name="${EventDutyProperty.CONDUCTOR}" placeholder="Conductor" class="form-control" minlength="2" required><br>
                        <p class="help-block"></p>
                    </div>
                </t:ErrorMessage>
            </div>
            <div class="form-group col-xs-12 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_DEFAULT_POINTS}">
                    <label for="${EventDutyProperty.DEFAULT_POINTS}" class="control-label">Standard Points</label><br>
                    <div class="controls">
                        <input id="${EventDutyProperty.DEFAULT_POINTS}" type="number" value="" name="${EventDutyProperty.DEFAULT_POINTS}" placeholder="Standard Points" value="0" class="form-control" min="0" required><br>
                        <p class="help-block"></p>
                    </div>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group row">
            <div class="form-group col-xs-12 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_DESCRIPTION}">
                    <label for="${EventDutyProperty.DESCRIPTION}" class="control-label">Description</label><br>
                    <div class="controls">
                        <input id="${EventDutyProperty.DESCRIPTION}" type="text" value="${eventDuty.description}" name="${EventDutyProperty.DESCRIPTION}" placeholder="Description" class="form-control"><br>
                        <p class="help-block"></p>
                    </div>
                </t:ErrorMessage>
            </div>
            <div class="form-group col-xs-12 col-sm-6">
                <c:choose>
                    <c:when test="${not empty eventDuty.eventStatus}">
                        <t:ErrorMessage errorMessage="${PROBLEM_STATUS}">
                            <label for="${EventDutyProperty.EVENT_STATUS}" class="control-label">Eventstatus</label><br>
                            <label id="${EventDutyProperty.EVENT_STATUS}" class="form-control">${eventDuty.eventStatus}</label>
                            <%--<select class="selectpicker form-control" data-live-search="true" name="${EventDutyProperty.EVENT_STATUS}">
                                <option value="">Select Eventstatus</option>
                                <c:forEach var="item" items="${EnumHelper.getEventStatusList()}">
                                    <option data-divider="true"></option>
                                    <option value="${item}" ${item eq eventDuty.eventStatus ? "selected" : ""}>${item}</option>
                                </c:forEach>
                            </select>--%>
                        </t:ErrorMessage>
                    </c:when>
                </c:choose>
            </div>
        </div>

        <c:if test="${IS_DATE}">
            <div class="control-group row musical-work-instrumentation-list">
                <div class="form-group col-xs-12 col-sm-12">
                    <div class="panel panel-primary add-remove-list-main">
                        <div class="panel-heading panel-heading-sm">
                            <label>Add Musical Works</label>
                        </div>
                        <div class="panel-body">
                            <div class="input-group add-list-item-content">
                                <div class="row add-list-item-keep">
                                    <div class="col-xs-5 col-sm-5 form-group add-list-item-keep">
                                        <label class="control-label">Musical Work</label><br>
                                        <select class="selectpicker add-list-item-check add-list-item-keep musical-work-select" data-live-search="true" add-list-item-name="${EventDutyProperty.MUSICAL_WORK_LIST}">
                                            <option class="add-list-item-keep" value="">Select Musical Work</option>
                                            <c:forEach var="item" items="${DomainEntityHelper.getMusicalWorkList()}">
                                                <option class="add-list-item-keep" data-divider="true"></option>
                                                <option class="add-list-item-keep" value="${item.musicalWorkID}"
                                                        <c:forEach var="musicalWork" items="${eventDuty.musicalWorkList}">
                                                            <c:if test="${musicalWork.musicalWorkID eq item.musicalWorkID}">
                                                                selected
                                                            </c:if>
                                                        </c:forEach>
                                                        data-subtext="${item.composer}" instrumentation-id="${item.instrumentation.instrumentationID}">${item.name}</option>
                                            </c:forEach>
                                        </select>
                                        <!-- @TODO: add a new popup window to edit the instrumentations -->
                                    </div>

                                    <div class="col-xs-5 col-sm-5 form-group add-list-item-keep">
                                        <label class="control-label">Instrumentation</label><br>
                                        <select class="selectpicker add-list-item-check add-list-item-keep instrumentation-select" data-live-search="true" add-list-item-name="${EventDutyProperty.ALTERNATIVE_INSTRUMENTATION_LIST}">
                                            <option class="add-list-item-keep" value="">Select Instrumentation</option>
                                            <c:forEach var="item" items="${DomainEntityHelper.getInstrumentationList()}">
                                                <option class="add-list-item-keep" data-divider="true"></option>
                                                <option class="add-list-item-keep" value="${item.instrumentationID}" ${item.instrumentationID eq eventDuty.instrumentation.instrumentationID ? "selected" : ""}>${item.instrumentationText}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-xs-1 col-sm-1 form-group add-list-item-keep">
                                        <label></label>
                                        <button type="button" class="btn btn-default add-list-item add-list-item-keep"></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer add-list-item-target">
                        </div>
                    </div>
                </div>
            </div>

            <div class="control-group row musical-work-instrumentation-list">
                <div class="form-group col-xs-12 col-sm-12">
                    <div class="col-xs-5 col-sm-5">
                        <t:ErrorMessage errorMessage="${PROBLEM_MUSICAL_WORK_LIST}">
                        </t:ErrorMessage>
                    </div>

                    <div class="col-xs-6 col-sm-6">
                        <t:ErrorMessage errorMessage="${PROBLEM_INSTRUMENTATION}">
                        </t:ErrorMessage>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
    <div class="modal-footer">
        <button id="modal-cancel" type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
        <button id="modal-edit" type="button" class="btn btn-default">Edit</button>
        <button id="modal-save" type="submit" class="btn btn-info pull-right">Save</button>
    </div>
</form>

<script type="text/javascript">
    $("input, select, .selectpicker, textarea").not("[type=submit]").jqBootstrapValidation();

    $(document).ready(function() {
        $('#${EventDutyProperty.EVENT_TYPE}').on('change', function(event) {
            var selectedItem = $(event.target).find("option:selected").val();

            if(selectedItem !== '${EventType.Tour}') {
                $('#${EventDutyProperty.END_DATE}').val($('#${EventDutyProperty.START_DATE}').val())
                $('.${EventDutyProperty.END_DATE}').toggle(false);
            } else {
                $('.${EventDutyProperty.END_DATE}').toggle(true);
            }

            if(selectedItem === '${EventType.NonMusicalEvent}') {
                addRemoveList(false, 'btn, div', 0);
                removeAddRemoveList();
                $('.musical-work-instrumentation-list').toggle(false);
            } else {
                addRemoveList(false, 'btn, div', Number.MAX_VALUE);
                $('.musical-work-instrumentation-list').toggle(true);
            }
        });

        $('#${EventDutyProperty.EVENT_TYPE}').trigger("change");

        $('.musical-work-select').on('change', function(event){
            var selectedItem = $(event.target).find("option:selected");
            var selected = selectedItem.val();
            var instrumentation = _getInstrumentation($(event.target));
            var musicalWork = $(_getMusicalWork($(event.target))).find('option[value="' + selected + '"]');
            $(instrumentation).val(musicalWork.attr('instrumentation-id'));
            $(instrumentation).trigger("change");
        });

        var predefinedInstrumentationIdList = [
        <c:forEach var="musicalWork" items="${eventDuty.musicalWorkList}">
            <c:choose>
                <c:when test="${not empty musicalWork.alternativeInstrumentation.instrumentationID}">
                    ${musicalWork.alternativeInstrumentation.instrumentationID}
                </c:when>
                <c:otherwise>
                    ${musicalWork.instrumentation.instrumentationID}
                </c:otherwise>
            </c:choose>
            ${","}
        </c:forEach>
        ];

        var predefinedMusicalIdList = [
            <c:forEach var="musicalWork" items="${eventDuty.musicalWorkList}">
                ${musicalWork.musicalWorkID} ${","}
            </c:forEach>
        ];

        var instrumentation = _getInstrumentation($('.add-list-item'));
        var musicalWork = _getMusicalWork($('.add-list-item'));

        // set the predefined musical works and instrumentations
        for(var i = 0; i < predefinedInstrumentationIdList.length && i < predefinedMusicalIdList.length; i++) {
            musicalWork.val(predefinedMusicalIdList[i]);
            musicalWork.trigger("change");

            instrumentation.val(predefinedInstrumentationIdList[i]);
            instrumentation.trigger("change");

            $('.add-list-item').trigger("click");
        }

        // set the selectpickers to an empty value
        musicalWork.val("");
        musicalWork.trigger("change");

        instrumentation.val("");
        instrumentation.trigger("change");
    });

    $('.musical-work-select').on('change', function(event){
        var selectedItem = $(event.target).find("option:selected");
        var selected = selectedItem.val();
        var instrumentation = _getInstrumentation($(event.target));
        var musicalWork = $(_getMusicalWork($(event.target))).find('option[value="' + selected + '"]');
        $(instrumentation).val(musicalWork.attr('instrumentation-id'));
        $(instrumentation).trigger("change");
    });

    function _getInstrumentation(currentElement) {
        var row = _getRow(currentElement);
        return $(row).find('select.instrumentation-select');
    }

    function _getMusicalWork(currentElement) {
        var row = _getRow(currentElement);
        return $(row).find('.musical-work-select select');
    }

    function _getRow(currentElement) {
        return $(currentElement).closest('.row');
    }
</script>