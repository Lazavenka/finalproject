<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="equipment_price" key="equipment.price"/>
<fmt:message var="equipment_avg_research_time" key="equipment.avg_time"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="equipment_state" key="equipment.state"/>


<html>
<head>
    <title>${book_equipment_details}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

</head>
<body>

<jsp:include page="../header/header.jsp"/>
<div class="container">
    <br>
    <figure class="text-center">
        <blockquote class="blockquote">
            <p>${book_equipment_details}</p>
        </blockquote>
    </figure>
    <br>
    <div class="w-75 mx-auto">
        <div class="d-flex position-relative">
            <img src="${abs}/${requestScope.selected_equipment.imageFilePath}" width="300" class="img-fluid rounded-start"
                 alt="${requestScope.selected_equipment.name}">
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">${requestScope.selected_equipment.name}</h5>
                    <dl class="row">
                        <dt class="col-sm-3">${description}</dt>
                        <dd class="col-sm-9">${requestScope.selected_equipment.description}</dd>

                        <dt class="col-sm-3">${equipment_price}</dt>
                        <dd class="col-sm-9">${requestScope.selected_equipment.pricePerHour.floatValue()} BYN</dd>

                        <dt class="col-sm-3">${equipment_avg_research_time}</dt>
                        <dd class="col-sm-9">${requestScope.selected_equipment.averageResearchTime.toString()}</dd>

                        <dt class="col-sm-3 <c:if test="${requestScope.selected_equipment.state.name() eq 'ACTIVE'}">text-success</c:if> <c:if test="${requestScope.selected_equipment.state.name() eq 'INACTIVE'}">text-warning</c:if>">${equipment_state}</dt>
                        <dd class="col-sm-9 <c:if test="${requestScope.selected_equipment.state.name() eq 'ACTIVE'}">text-success</c:if> <c:if test="${requestScope.selected_equipment.state.name() eq 'INACTIVE'}">text-warning</c:if>">${requestScope.selected_equipment.state.name()}</dd>
                    </dl>
                </div>
            </div>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-sm-2">
            <form action="${abs}/controller" method="get">
                <input type="hidden" name="command" value="show_equipment_timetable_command">
                <input type="hidden" name="equipment_id" value="${requestScope.selected_equipment.id}">
            </form>
        </div>
        <div class="col-sm-10">
            <c:if test="${requestScope.equipment_timetable != null}">
                <form action="${abs}/controller" method="post">
                    <input type="hidden" name="command" value="show_equipment_timetable_command">

                </form>
            </c:if>
        </div>
    </div>
</div>

<ctg:print-footer/>
</body>
</html>
