<%@ page import="team_f.domain.enums.properties.RequestProperty" %>
<%@ page import="team_f.domain.helper.EnumHelper" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form id="modal-form" action="/Request" class="form-group">
    <div class="modal-header">
        <h4 class="modal-title">Request</h4>
    </div>
    <div class="modal-body container-fluid">
        <input type="number" name="${RequestProperty.EVENT_DUTY}" value="${eventDuty.eventDutyID}" class="hidden">

        <div class="control-group row">
            <div class="form-group col-xs-12 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_PERSON}">
                    <label for="${RequestProperty.PERSON}" class="control-label">Person</label><br>
                    <select id="${RequestProperty.PERSON}" class="selectpicker form-control ${RequestProperty.PERSON}" data-live-search="true" name="${RequestProperty.PERSON}">
                        <option value="">Select Person</option>
                        <c:forEach var="item" items="${PERSON_LIST}">
                            <option data-divider="true"></option>
                            <option value="${item.personID}" ${item.personID eq request.person.personID ? "selected" : ""}>${item.firstname} ${item.lastname}</option>
                        </c:forEach>
                    </select>
                </t:ErrorMessage>
            </div>
            <div class="form-group col-xs-12 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_REQUEST_TYPE}">
                    <label for="${RequestProperty.REQUEST_TYPE}" class="control-label">Request Type</label><br>
                    <select id="${RequestProperty.REQUEST_TYPE}" class="selectpicker form-control ${RequestProperty.REQUEST_TYPE}" data-live-search="true" name="${RequestProperty.REQUEST_TYPE}">
                        <option value="">Select Request Type</option>
                        <c:forEach var="item" items="${EnumHelper.getRequestTypeList()}">
                            <option data-divider="true"></option>
                            <option value="${item}" ${item eq request.requestType ? "selected" : ""}>${item}</option>
                        </c:forEach>
                    </select>
                </t:ErrorMessage>
            </div>
        </div>
        <div class="control-group row">
            <div class="form-group col-xs-12 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_DESCRIPTION}">
                    <label for="${RequestProperty.DESCRIPTION}" class="control-label">Description</label><br>
                    <div class="controls">
                        <input id="${EventDutyProperty.DESCRIPTION}" type="text" value="${request.description}" name="${RequestProperty.DESCRIPTION}" placeholder="Description" class="form-control"><br>
                        <p class="help-block"></p>
                    </div>
                </t:ErrorMessage>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button id="modal-cancel" type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
        <button id="modal-edit" type="button" class="btn btn-default">Edit</button>
        <button id="modal-save" type="submit" class="btn btn-info pull-right">Save</button>
    </div>
</form>