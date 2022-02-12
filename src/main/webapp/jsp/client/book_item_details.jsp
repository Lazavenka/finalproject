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
<fmt:message var="assistant_label" key="details.assistant"/>
<fmt:message var="necessary_assistant" key="equipment.necessary_assistant"/>
<fmt:message var="not_found" key="common.not_found"/>
<fmt:message var="to_equipment" key="buttons.to_equipment"/>
<fmt:message var="book_equipment" key="buttons.book_equipment"/>
<fmt:message var="selected_date" key="message.selected_date"/>
<fmt:message var="necessary" key="assistant.necessary"/>
<fmt:message var="not_necessary" key="assistant.not_necessary"/>

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

                        <dt class="col-sm-3">${assistant}</dt>
                        <dd class="col-sm-9"><c:choose>
                            <c:when test="${equipmentItem.needAssistant}"><div class="text-decoration-underline">${necessary}</div></c:when>
                            <c:otherwise><div class="text-decoration-underline">${not_necessary}</div></c:otherwise>
                        </c:choose></dd>

                        <dt class="col-sm-3 <c:if test="${requestScope.selected_equipment.state.name() eq 'ACTIVE'}">text-success</c:if> <c:if test="${requestScope.selected_equipment.state.name() eq 'INACTIVE'}">text-warning</c:if>">${equipment_state}</dt>
                        <dd class="col-sm-9 <c:if test="${requestScope.selected_equipment.state.name() eq 'ACTIVE'}">text-success</c:if> <c:if test="${requestScope.selected_equipment.state.name() eq 'INACTIVE'}">text-warning</c:if>">${requestScope.selected_equipment.state.name()}</dd>
                    </dl>
                </div>
            </div>
        </div>
    </div>
    <div style="margin-top: 10px; margin-bottom: 20px">
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
                <div style="margin-bottom: 25px">
                    <a class="btn btn-primary" role="button"
                       href="${abs}/controller?command=find_equipment_by_type_command&equipment_type_id=0">${to_equipment}</a>
                </div>
            </div>
            <div class="col-sm-10">
                <c:choose>
                    <c:when test="${requestScope.error_message}">${wrong_date}</c:when>
                </c:choose>
                <c:if test="${timetable != null}">
                    <div class="text-center">${selected_date} ${requestScope.date}</div>
                    <form action="${abs}/controller" method="post">
                        <input type="hidden" name="command" value="book_equipment_command">
                        <input type="hidden" name="equipment_id" value="${requestScope.selected_equipment.id}">
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" role="switch" id="need_assistant"
                                   name="is_need_assistant" value="true"
                                   <c:if test="${requestScope.selected_equipment.needAssistant}">checked
                                   disabled</c:if> >
                            <label class="form-check-label" for="need_assistant">${necessary_assistant}</label>
                        </div>
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
                            <c:choose>
                                <c:when test="${!empty timetable.workTimePeriods }">
                                    <c:forEach var="timePeriod" items="${timetable.workTimePeriods}">
                                        <c:if test="${timePeriod.availability.name() eq 'AVAILABLE_WITHOUT_ASSISTANT' or timePeriod.availability.name() eq 'FULL_AVAILABLE'}">
                                            <c:set var="assistant_id"/>
                                            <c:set var="assistant_name"/>
                                            <c:choose>
                                                <c:when test="${timePeriod.availability.name() eq 'FULL_AVAILABLE'}">
                                                    <c:set var="availableAssistant"
                                                           value="${timePeriod.availableAssistantInPeriod}"/>
                                                    <c:set var="assistant_id"
                                                           value="${availableAssistant.get().assistantId}"/>
                                                    <c:set var="assistant_name"
                                                           value="${availableAssistant.get().lastName} ${availableAssistant.get().firstName}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="assistant_id" value="0"/>
                                                    <c:set var="assistant_name" value="${not_found}"/>
                                                </c:otherwise>
                                            </c:choose>
                                            <tr
                                                    <c:if test="${timePeriod.availability.name() eq 'AVAILABLE_WITHOUT_ASSISTANT'}">class="without_assistant"</c:if>
                                                    <c:if test="${timePeriod.availability.name() eq 'AVAILABLE_WITHOUT_ASSISTANT' and requestScope.selected_equipment.needAssistant}">style="display: none"</c:if>>
                                                <th scope="row" class="for_assistant_id">
                                                    <input type="checkbox" name="order_date_assistant"
                                                           value="${timePeriod.startOfPeriod}|${assistant_id}">
                                                </th>
                                                <td>${timePeriod.startOfPeriod.toLocalTime()}</td>
                                                <td>${timePeriod.endOfPeriod.toLocalTime()}</td>
                                                <td>${assistant_name}</td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <th></th>
                                        <td>${not_found}</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>

                            </tbody>
                        </table>
                        <button type="submit" class="btn btn-primary">${book_equipment}</button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function toggle() {
        Array.from(document.querySelectorAll('.without_assistant')).forEach(e => {
            e.style.display = this.checked ? 'none' : 'table-row';
        });
    }

    document.getElementById('need_assistant').onchange = toggle;
</script>
<ctg:print-footer/>
</body>
</html>
