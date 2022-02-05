<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="location" key="common.location"/>
<fmt:message var="not_found" key="common.not_found"/>
<fmt:message var="equipment_list" key="equipment.all_equipment"/>
<fmt:message var="edit" key="button.edit"/>
<fmt:message var="equipment_price" key="equipment.price"/>
<fmt:message var="equipment_avg_research_time" key="equipment.avg_time"/>
<fmt:message var="not_found" key="common.not_found"/>
<fmt:message var="booking_details" key="equipment.booking_details"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="edit" key="button.edit"/>
<fmt:message var="equipment_state" key="equipment.state"/>
<fmt:message var="add_equipment" key="admin.add_equipment"/>
<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Manager page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>
<div class="d-flex position-relative">
    <img src="${abs}/${requestScope.manager.imageFilePath}" width="300" class="flex-shrink-0 me-3" alt="...">
    <div>
        <h5 class="mt-0">${requestScope.manager.lastName} ${requestScope.manager.firstName}</h5>
        <p class="card-text">${requestScope.manager.managerDegree.value}</p>
        <p class="card-text">${requestScope.manager.description}</p>
        <p class="card-text">+${requestScope.manager.phone}</p>
        <p class="card-text">${requestScope.manager.email}</p>
        <a href="${abs}/controller?command=edit_profile_command&manager_id=${sessionScope.user.id}"
           class="btn btn-primary">${edit}</a>
    </div>
</div>

<div class="d-flex position-relative">
    <img src="${abs}/${requestScope.selected_laboratory.imageFilePath}" class="flex-shrink-0 me-3" alt="...">
    <div>
        <h5 class="card-title">${requestScope.selected_laboratory.name}</h5>
        <dl class="row">
            <dt class="col-sm-3">${description}</dt>
            <dd class="col-sm-9">${requestScope.selected_laboratory.description}</dd>

            <dt class="col-sm-3">${location}</dt>
            <dd class="col-sm-9">${requestScope.selected_laboratory.location}</dd>

            <dt class="col-sm-3"></dt>
            <dd class="col-sm-9">
                <a href="${abs}/controller?command=go_edit_laboratory_page_command&laboratory_id=${requestScope.selected_laboratory.id}"
                   class="btn btn-primary">${edit}</a>
            </dd>
        </dl>
    </div>
</div>
<div class="justify-content-center">${equipment_list}</div>
<c:if test="${sessionScope.user.role.name() eq 'ADMIN' or sessionScope.user.role.name() eq 'MANAGER' }">
    <div class="col-xs-2">
        <a class="btn btn-primary"
           href="${abs}/controller?command=go_add_new_equipment_page_command">${add_equipment}</a>
    </div>
</c:if>
<div class="col-xs-10">
    <c:if test="${requestScope.empty_list}">
        <div class="justify-content-center">${not_found}</div>
    </c:if>
    <c:forEach var="equipmentItem" items="${requestScope.equipment_list}">
        <div class="d-flex position-relative">
            <img src="${abs}/${equipmentItem.imageFilePath}" width="300" class="img-fluid rounded-start"
                 alt="${equipmentItem.name}">
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">${equipmentItem.name}</h5>
                    <dl class="row">
                        <dt class="col-sm-3">${description}</dt>
                        <dd class="col-sm-9">${equipmentItem.description}</dd>

                        <dt class="col-sm-3">${equipment_price}</dt>
                        <dd class="col-sm-9">${equipmentItem.pricePerHour.floatValue()} BYN</dd>

                        <dt class="col-sm-3">${equipment_avg_research_time}</dt>
                        <dd class="col-sm-9">${equipmentItem.averageResearchTime.toString()}</dd>

                        <dt class="col-sm-3 <c:if test="${equipmentItem.state.name() eq 'ACTIVE'}">text-success</c:if> <c:if test="${equipmentItem.state.name() eq 'INACTIVE'}">text-warning</c:if>">${equipment_state}</dt>
                        <dd class="col-sm-9 <c:if test="${equipmentItem.state.name() eq 'ACTIVE'}">text-success</c:if> <c:if test="${equipmentItem.state.name() eq 'INACTIVE'}">text-warning</c:if>">${equipmentItem.state.name()}</dd>

                        <dt class="col-sm-3"></dt>
                        <c:choose>
                            <c:when test="${sessionScope.user.role.name() eq 'CLIENT'}">
                                <dd class="col-sm-9"><a
                                        href="${abs}/controller?command=go_book_equipment_details_page_command&equipment_id=${equipmentItem.id}"
                                        class="btn btn-primary <c:if test="${equipmentItem.state.name() eq 'INACTIVE'}">disabled</c:if>">${booking_details}</a>
                                </dd>
                            </c:when>
                            <c:when test="${sessionScope.user.role.name() eq 'ADMIN' or sessionScope.user.role.name() eq 'MANAGER'}">
                                <dd class="col-sm-9">
                                    <a href="${abs}/controller?command=go_edit_equipment_page_command&equipment_id=${equipmentItem.id}"
                                       class="btn btn-primary">${edit}</a>
                                </dd>
                            </c:when>
                        </c:choose>
                    </dl>
                </div>
            </div>
        </div>
    </c:forEach>
</div>


</body>
</html>