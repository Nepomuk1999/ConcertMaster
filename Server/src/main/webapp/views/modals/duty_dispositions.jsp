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

<form id="modal-form" action="" class="form-group">
    <div class="modal-header">
        <h4 class="modal-title">Duty Disposition</h4>
    </div>
    <div class="modal-body container-fluid">
        <div class="control-group row">
            <div class="form-group col-xs-12 col-sm-6">
                <c:if test="${not empty DUTY_DISPOSITION_EVENT_DUTY}">
                <h4>Event Duty</h4>
                <div>${DUTY_DISPOSITION_EVENT_DUTY.name} (${DUTY_DISPOSITION_EVENT_DUTY.getStartDate("MM/dd/yyyy")} ${DUTY_DISPOSITION_EVENT_DUTY.getStartTime("HH:mm")} - ${DUTY_DISPOSITION_EVENT_DUTY.getEndDate("MM/dd/yyyy")} ${DUTY_DISPOSITION_EVENT_DUTY.getEndTime("HH:mm")})</div><hr class="col-xs-12"><hr class="col-xs-12">

                <h4>Musicians</h4>
                <div>
                    <c:forEach var="item" items="${DUTY_DISPOSITION_LIST}">
                        <label>Name</label>
                        <div>${item.musician.firstname} ${item.musician.lastname} (${item.musician.initials}), ${item.musician.email}, </div><br>

                        <label>Description</label><br/>
                        <div>${item.description}</div><br/>

                        <label>Points</label><br/>
                        <div>${item.points}</div>

                        <hr class="col-xs-12">
                    </c:forEach>
                </div>
                </c:if>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button id="modal-cancel" type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button><!--data-dismiss="modal"-->
    </div>
</form>