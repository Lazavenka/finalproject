<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:choose>
    <c:when test="${not empty sessionScope.locale}"><fmt:setLocale value="${sessionScope.locale}"/></c:when>
    <c:when test="${empty sessionScope.locale}"><fmt:setLocale value="${sessionScope.locale = 'ru_RU'}"/></c:when>
</c:choose>

<fmt:setBundle basename="locale/language"/>

<fmt:message var="equipment_type" key="equipment.equipment_type"/>
<fmt:message var="choose_type" key="equipment.choose_type"/>
<fmt:message var="search" key="buttons.search"/>
<fmt:message var="clear" key="buttons.clear"/>
<fmt:message var="all_equipment" key="equipment.all_equipment"/>
<fmt:message var="all_items" key="equipment.all_equipment"/>
<fmt:message var="all_types" key="equipment.all_types"/>
<fmt:message var="equipment_price" key="equipment.price"/>
<fmt:message var="equipment_avg_research_time" key="equipment.avg_time"/>
<fmt:message var="not_found" key="common.not_found"/>
<fmt:message var="booking_details" key="equipment.booking_details"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="edit" key="button.edit"/>
<fmt:message var="equipment_state" key="equipment.state"/>
<fmt:message var="add_equipment" key="admin.add_equipment"/>
<fmt:message var="equipment_page" key="message.equipment_page"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Equipment page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>

<div class="container">
    <br>
    <figure class="text-center">
        <blockquote class="blockquote">
            <p>${equipment_page}</p>
        </blockquote>
    </figure>
    <br>
    <div class="w-75 mx-auto">
        <div class="row">
            <div class="col-xs-8">
                <h2 class="section-headline">
                    <span>${all_items}</span>
                </h2>
            </div>
            <div class="col-xs-4">

            </div>
        </div>

        <div class="col-xs-2">
            <form action="${abs}/controller" method="get">
                <div>
                    <input type="hidden" name="command" value="find_equipment_by_type_command"/>
                    <input type="hidden" name="current_equipment_type_id"
                           value="${requestScope.selected_equipment_type.id}">
                    <div class="select-form">
                        <span>${equipment_type}</span>
                        <select id="equipment_type_id" name="equipment_type_id" class="form-control">
                            <c:if test="${requestScope.selected_equipment_type != null}">
                                <option selected disabled>${requestScope.selected_equipment_type.name}</option>
                            </c:if>
                            <c:if test="${requestScope.selected_equipment_type == null}">
                                <option selected disabled>${choose_type}</option>
                            </c:if>
                            <option value="0">${all_types}</option>
                            <c:forEach var="equipmentType" items="${requestScope.equipment_types}">
                                <option value="${equipmentType.id}">${equipmentType.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <br>
                    <div>
                        <input type="submit" class="btn btn-primary" value="${search}"/>
                    </div>
                </div>
            </form>
        </div>
        <br>

        <c:if test="${sessionScope.user.role.name() eq 'ADMIN'}">
            <div class="col-xs-2">
                <a class="btn btn-primary"
                   href="${abs}/controller?command=go_add_new_equipment_page_command">${add_equipment}</a>
            </div>
        </c:if>
        <br>
        <%@include file="fragment/equipment_table.jspf" %>
    </div>
</div>
<ctg:print-footer/>
</body>
</html>
