<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="order_details_page" key="order.order_details_page"/>
<fmt:message var="to_all_orders" key="buttons.to_orders"/>
<fmt:message var="close" key="buttons.close"/>
<fmt:message var="not_found" key="common.not_found"/>
<fmt:message var="date" key="order.date"/>
<fmt:message var="price" key="order.price"/>
<fmt:message var="time_start" key="equipment.time_start"/>
<fmt:message var="time_end" key="equipment.time_end"/>
<fmt:message var="address" key="details.address"/>
<fmt:message var="equipment_label" key="header.equipment"/>
<fmt:message var="assistant_label" key="details.assistant"/>
<fmt:message var="depaprtment_label" key="common.manager_details.department"/>
<fmt:message var="state" key="equipment.state"/>
<fmt:message var="equipment_price" key="equipment.price"/>
<fmt:message var="equipment_avg_research_time" key="equipment.avg_time"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="equipment_state" key="equipment.state"/>
<fmt:message var="location" key="laboratory.lab_location"/>
<fmt:message var="error_message" key="message.error_message"/>
<fmt:message var="address_details_title" key="order.address_details_title"/>
<fmt:message var="equipment_details_title" key="order.equipment_details_title"/>
<fmt:message var="assistant_details_title" key="order.assistant_details_title"/>


<c:set var="order" value="${requestScope.selected_order}"/>
<c:set var="department" value="${requestScope.selected_department}"/>
<c:set var="laboratory" value="${requestScope.selected_laboratory}"/>
<c:set var="equipment" value="${requestScope.selected_equipment}"/>
<c:set var="assistant" value="${requestScope.selected_assistant}"/>

<html>
<head>
    <title>${order_details_page} Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="../../css/styles.css">

</head>
<body>

<jsp:include page="../header/header.jsp"/>
<div class="container" style="margin-top: 20px">
    <div style="margin-bottom: 20px">
        <figure class="text-center">
            <blockquote class="blockquote">
                <p>${order_details_page}</p>
            </blockquote>
        </figure>
    </div>

    <div class="w-75 mx-auto">
        <c:choose>
            <c:when test="${requestScope.order_not_found}">
                <div style="margin-top: 10px; margin-bottom: 10px">
                    <figure class="text-center">
                        <blockquote class="blockquote">
                            <c:if test="${requestScope.error_message}">
                                <p class="alert-warning">${error_message}</p>
                            </c:if>
                        </blockquote>
                    </figure>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th scope="col">${date}</th>
                        <th scope="col">${time_start}</th>
                        <th scope="col">${time_end}</th>
                        <th scope="col">${price}</th>
                        <th scope="col">${address}</th>
                        <th scope="col">${equipment_label}</th>
                        <th scope="col">${assistant_label}</th>
                        <th scope="col">${state}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th scope="row">${order.rentStartTime.toLocalDate()}</th>
                        <td>${order.rentStartTime.toLocalTime()}</td>
                        <td>${order.rentEndTime.toLocalTime()}</td>
                        <td>${order.totalCost.toString()}</td>
                        <td><c:choose>
                            <c:when test="${requestScope.laboratory_not_found or requestScope.department_not_found}">${not_found}</c:when>
                            <c:otherwise>
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                        data-bs-target="#addressDetails">
                                        ${address}</button>
                            </c:otherwise>
                        </c:choose></td>
                        <td><c:choose>
                            <c:when test="${requestScope.equipment_not_found}">${not_found}</c:when>
                            <c:otherwise>
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                        data-bs-target="#equipmentDetails">
                                        ${equipment_label}</button>
                            </c:otherwise>
                        </c:choose></td>
                        <td><c:choose>
                            <c:when test="${requestScope.assistant_not_found}">${not_found}</c:when>
                            <c:otherwise>
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                        data-bs-target="#assistantDetails">
                                        ${assistant_label}</button>
                            </c:otherwise>
                        </c:choose></td>
                        <td>${order.state.name()}</td>
                    </tr>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
    <div style="margin-bottom: 20px">
        <c:choose>
            <c:when test="${sessionScope.user.role.name() eq 'CLIENT'}"><a class="btn btn-primary" role="button"
                                                                           href="${abs}/controller?command=go_client_orders_page_command">${to_all_orders}</a></c:when>
            <c:when test="${sessionScope.user.role.name() eq 'MANAGER'}"><a class="btn btn-primary" role="button"
                                                                            href="${abs}/controller?command=go_laboratory_orders_command">${to_all_orders}</a></c:when>
        </c:choose>
    </div>
</div>

<div class="modal fade" id="addressDetails" tabindex="-1" aria-labelledby="addressLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addressLabel">${address_details_title}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row g-0">
                    <div class="col-md-5">
                        <img src="${abs}/${laboratory.imageFilePath}" width="300"
                             class="flex-shrink-0 me-3 rounded"
                             alt="${laboratory.name}">
                    </div>
                    <div class="col-md-7">
                        <h5 class="card-title">${laboratory.name}</h5>
                        <dl class="row">
                            <dt class="col-sm-3">${description}</dt>
                            <dd class="col-sm-9">${laboratory.description}</dd>

                            <dt class="col-sm-3">${depaprtment_label}</dt>
                            <dd class="col-sm-9">${department.name}</dd>

                            <dt class="col-sm-3">${location}</dt>
                            <dd class="col-sm-9">${department.address} ${laboratory.location}</dd>
                        </dl>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${close}</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="equipmentDetails" tabindex="-1" aria-labelledby="equipmentLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="equipmentLabel">${equipment_details_title}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row g-0" style="margin-bottom: 10px">
                    <div class="col-sm-5">
                        <img src="${abs}/${equipment.imageFilePath}" width="300" alt="${equipment.name}">
                    </div>
                    <div class="col-sm-7">
                        <div class="card-body">
                            <h5 class="card-title">${equipment.name}</h5>
                            <dl class="row">
                                <dt class="col-sm-3">${description}</dt>
                                <dd class="col-sm-9">${equipment.description}</dd>

                                <dt class="col-sm-3">${equipment_price}</dt>
                                <dd class="col-sm-9">${equipment.pricePerHour.floatValue()} BYN</dd>

                                <dt class="col-sm-3">${equipment_avg_research_time}</dt>
                                <dd class="col-sm-9">${equipment.averageResearchTime.toString()}</dd>

                                <dt class="col-sm-3 <c:if test="${equipment.state.name() eq 'ACTIVE'}">text-success</c:if> <c:if test="${equipment.state.name() eq 'INACTIVE'}">text-warning</c:if>">${equipment_state}</dt>
                                <dd class="col-sm-9 <c:if test="${equipment.state.name() eq 'ACTIVE'}">text-success</c:if> <c:if test="${equipment.state.name() eq 'INACTIVE'}">text-warning</c:if>">${equipment.state.name()}</dd>
                            </dl>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${close}</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="assistantDetails" tabindex="-1" aria-labelledby="assistantLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="assistantLabel">${assistant_details_title}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row g-0" style="margin-bottom: 10px">
                    <div class="col-lg-5">
                        <img src="${abs}/${assistant.imageFilePath}" width="300" class="flex-shrink-0 me-3"
                             alt="${assistant.lastName} ${assistant.firstName}">
                    </div>
                    <div class="col-lg-7">
                        <h5 class="mt-0">${assistant.lastName} ${assistant.firstName}</h5>
                        <p class="card-text">+${assistant.phone}</p>
                        <p class="card-text">${assistant.email}</p>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${close}</button>
            </div>
        </div>
    </div>
</div>


</body>
</html>
