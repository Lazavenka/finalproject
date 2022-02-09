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
<fmt:message var="show_timetable" key="equipment.show_timetable"/>
<fmt:message var="book_equipment_details" key="message.book_equipment_details_page"/>
<fmt:message var="choose_date" key="equipment.choose_date"/>
<fmt:message var="wrong_date" key="message.wrong_date"/>
<fmt:message var="time_start" key="equipment.time_start"/>
<fmt:message var="time_end" key="equipment.time_end"/>
<fmt:message var="assistants" key="equipment.assistants"/>

<c:set var="timetable" value="${requestScope.equipment_timetable}"/>

<html>
<head>
    <title>${book_equipment_details}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="../../css/styles.css">
</head>
<body>

<jsp:include page="../header/header.jsp"/>
<div class="container">
    <div class="spaced">
        <figure class="text-center">
            <blockquote class="blockquote">
                <p>${book_equipment_details}</p>
            </blockquote>
        </figure>
    </div>
    <div class="w-75 mx-auto">
        <div class="row g-0" style="margin-bottom: 10px">
            <div class="col-sm-5">
                <img src="${abs}/${requestScope.selected_equipment.imageFilePath}" width="300"
                     class="img-fluid rounded-start"
                     alt="${requestScope.selected_equipment.name}">
            </div>
            <div class="col-sm-7">
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
    <div class="spaced">
        <div class="row">
            <div class="col-sm-2">
                <form action="${abs}/controller" method="get">
                    <input type="hidden" name="command" value="show_equipment_timetable_command">
                    <input type="hidden" name="equipment_id" value="${requestScope.selected_equipment.id}">
                    <label for="bookingDate" class="col-sm-2 col-form-label">${choose_date}</label>
                    <div class="col-sm-2">
                        <input type="date" name="date" style="margin-top: 15px" id="bookingDate">
                    </div>
                    <button type="submit" class="btn btn-primary" style="margin-top: 10px">${show_timetable}</button>
                </form>
            </div>
            <div class="col-sm-10">
                <c:choose>
                    <c:when test="${requestScope.error_message}">${wrong_date}</c:when>
                </c:choose>
                <c:if test="${timetable != null}">
                    <form action="${abs}/controller" method="post">
                        <input type="hidden" name="command" value="book_equipment_command">
                        <input type="hidden" name="equipment_id" value="${requestScope.selected_equipment.id}">
                        <input type="hidden" name="date" value="${requestScope.date}">
                        <input type="hidden" name="average_research_time"
                               value="${requestScope.average_research_time.toString()}">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col"></th>
                                <th scope="col">${time_start}</th>
                                <th scope="col">${time_end}</th>
                                <th scope="col">${assistants}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="timePeriod" items="${timetable.timeTable}">
                                <tr>
                                    <th scope="row"><input type="checkbox" name="date_time_start"
                                                           value="${timePeriod.startOfPeriod}"></th>
                                    <td>${timePeriod.startOfPeriod.toLocalTime()}</td>
                                    <td>${timePeriod.endOfPeriod.toLocalTime()}</td>
                                    <td>${timePeriod.availableAssistantInPeriod}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</div>

<ctg:print-footer/>
</body>
</html>
