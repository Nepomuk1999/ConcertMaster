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

<form id="modal-form" action="/Instrumentation" class="form-group">
    <div class="modal-header">
        <h4 class="modal-title">Instrumentation</h4>
    </div>
    <div class="modal-body container-fluid">
        <%--<div class="control-group row">
            <div class="form-group col-xs-12 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_CONDUCTOR}">
                    <label for="${EventDutyProperty.CONDUCTOR}" class="control-label">Conductor</label><br>
                    <input id="${EventDutyProperty.CONDUCTOR}" type="text" value="${eventDuty.conductor}" name="${EventDutyProperty.CONDUCTOR}" placeholder="Conductor" class="form-control" required><br>
                </t:ErrorMessage>
            </div>
            <div class="form-group col-xs-12 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_DEFAULT_POINTS}">
                    <label for="${EventDutyProperty.DEFAULT_POINTS}" class="control-label">Standard Points</label><br>
                    <input id="${EventDutyProperty.DEFAULT_POINTS}" type="number" value="${empty eventDuty.defaultPoints ? 0 : eventDuty.defaultPoints}" name="${EventDutyProperty.DEFAULT_POINTS}" placeholder="Standard Points" value="0" class="form-control" min="0" required><br>
                </t:ErrorMessage>
            </div>
        </div>--%>

        <%--                             <option value="${item.instrumentationID}" ${item.instrumentationID eq eventDuty.instrumentationID ? "selected" : ""}>Flute: ${item.flute}, Oboe: ${item.oboe}, Clarinet: ${item.clarinet}, Bassoon: ${item.bassoon}, Violin 1: ${item.violin1}, Violin 2: ${item.violin2}, Viola: ${item.viola}, Violincello: ${item.violincello}, Doublebass: ${item.doublebass}, Horn: ${item.horn}, Trumpet: ${item.trumpet}, Trombone: ${item.trombone}, Tube: ${item.tube}, Kettledrum: ${item.kettledrum}, Percussion: ${item.percussion}, Harp: ${item.harp}</option>

--%>
    </div>
    <div class="modal-footer">
        <button id="modal-cancel" type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button><!--data-dismiss="modal"-->
        <button id="modal-save" type="submit" class="btn btn-info pull-right">Use</button>
    </div>
</form>