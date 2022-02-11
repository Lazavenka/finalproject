<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="assistant_sсhedule_page" key="message.assistant_sсhedule_page"/>
<fmt:message var="details" key="buttons.details"/>
<fmt:message var="date" key="order.date"/>
<fmt:message var="time_start" key="equipment.time_start"/>
<fmt:message var="time_end" key="equipment.time_end"/>
<fmt:message var="orders_not_found" key="common.not_found"/>
<fmt:message var="state" key="equipment.state"/>
<fmt:message var="equipment" key="header.equipment"/>


<c:set var="assistant_orders" value="${requestScope.order_list}"/>

<html>
<head>
    <title>${assistant_sсhedule_page} Research center.</title>
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
                <p>${assistant_sсhedule_page}</p>
            </blockquote>
        </figure>
    </div>

    <div class="w-75 mx-auto">
        <div style="margin-top: 10px; margin-bottom: 10px">
            <figure class="text-center">
                <blockquote class="blockquote">
                    <c:choose>
                        <c:when test="${requestScope.empty_list}">
                            <p class="alert-success">${orders_not_found}</p>
                        </c:when>
                    </c:choose>
                </blockquote>
            </figure>
        </div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">${date}</th>
                <th scope="col">${time_start}</th>
                <th scope="col">${time_end}</th>
                <th scope="col">${equipment}</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${assistant_orders}">
                <tr>
                    <th scope="row">${order.rentStartTime.toLocalDate()}</th>
                    <td>${order.rentStartTime.toLocalTime()}</td>
                    <td>${order.rentEndTime.toLocalTime()}</td>
                    <td><a class="btn btn-primary" role="button" href="${abs}/controller?command=go_equipment_details_page_command&equipment_id=${order.equipmentId}">${details}</a></td>
                    </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<ctg:print-footer/>
</body>
</html>