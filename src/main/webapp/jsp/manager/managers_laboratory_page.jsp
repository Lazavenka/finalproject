<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

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
<fmt:message var="my_lab" key="manager.my_lab"/>
<fmt:message var="show_equipment" key="buttons.show_equipment"/>
<fmt:message var="show_assistants" key="buttons.show_assistants"/>
<fmt:message var="add_assistant" key="buttons.add"/>
<fmt:message var="assistants" key="laboratory.assistants"/>
<fmt:message var="manager_lab_page" key="manager.manager_lab_page"/>
<fmt:message var="greetings" key="message.greetings"/>


<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Manager page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="../../css/styles.css">

</head>
<body>
<jsp:include page="../header/header.jsp"/>
<div class="container" style="margin-bottom: 20px; margin-top: 20px">
    <div style="margin-bottom: 20px">
        <figure class="text-center">
            <blockquote class="blockquote">
                <p>${greetings} ${sessionScope.user.lastName} ${sessionScope.user.firstName}</p>
                <p>${manager_lab_page} ${requestScope.selected_laboratory.name}</p>
            </blockquote>
        </figure>
    </div>
    <div class="w-75 mx-auto">
        <div class="row g-0" style="margin-bottom: 10px">
            <div class="col-sm-5">
            <img src="${abs}/${requestScope.manager.imageFilePath}" width="300" class="flex-shrink-0 me-3" alt="${requestScope.manager.lastName}">
            </div>
            <div class="col-sm-7">
                <h5 class="mt-0">${requestScope.manager.lastName} ${requestScope.manager.firstName}</h5>
                <p class="card-text">${requestScope.manager.managerDegree.value}</p>
                <p class="card-text">${requestScope.manager.description}</p>
                <p class="card-text">+${requestScope.manager.phone}</p>
                <p class="card-text">${requestScope.manager.email}</p>
                <a href="${abs}/controller?command=go_edit_user_page_command&user_id=${sessionScope.user.id}"
                   class="btn btn-primary">${edit}</a>
            </div>
        </div>
    </div>
    <div class="w-75 mx-auto">
        <div class="row g-0" style="margin-bottom: 10px">
            <div class="col-sm-5">
            <img src="${abs}/${requestScope.selected_laboratory.imageFilePath}" width="300" class="flex-shrink-0 me-3"
                 alt="${requestScope.selected_laboratory.name}">
            </div>
            <div class="col-sm-7">
                <h5 class="card-title">${requestScope.selected_laboratory.name}</h5>
                <dl class="row">
                    <dt class="col-sm-3">${description}</dt>
                    <dd class="col-sm-9">${requestScope.selected_laboratory.description}</dd>

                    <dt class="col-sm-3">${location}</dt>
                    <dd class="col-sm-9">${requestScope.selected_laboratory.location}</dd>

                    <dt class="col-sm-3"></dt>
                    <dd class="col-sm-9">
                        <a href="${abs}/controller?command=go_edit_laboratory_page_command"
                           class="btn btn-primary">${edit}</a>
                    </dd>
                </dl>
            </div>
        </div>
    </div>
    <div class="container">
        <div style="margin-top: 10px; margin-bottom: 10px">
            <figure class="text-center">
                <blockquote class="blockquote">
                    <p>${my_lab}</p>
                </blockquote>
            </figure>
        </div>
        <div class="row">
            <div class="col-sm-2 justify-content-center">
                <div class="btn-group-vertical">
                    <div style="margin-bottom: 10px">
                        <button class="btn btn-primary" type="button" data-bs-toggle="collapse"
                                data-bs-target="#show_equipment_table"
                                aria-expanded="false" aria-controls="show_equipment_table">
                            ${show_equipment}
                        </button>
                    </div>
                    <div style="margin-bottom: 40px">
                        <button class="btn btn-primary" type="button" data-bs-toggle="collapse"
                                data-bs-target="#show_assistants"
                                aria-expanded="false" aria-controls="show_assistants">
                            ${show_assistants}
                        </button>
                    </div>
                </div>
            </div>
            <div class="col-sm-10">
                <div class="collapse" id="show_equipment_table">
                    <div style="margin-bottom: 10px">
                        <h4 class="h4 align-self-center">${equipment_list}</h4>
                    </div>
                    <c:if test="${requestScope.manager.laboratoryId eq requestScope.selected_laboratory.id}">
                        <div class="col-xs-2">
                            <div style="margin-bottom: 10px">
                                <a class="btn btn-primary"
                                   href="${abs}/controller?command=go_add_new_equipment_page_command">${add_equipment}</a>
                            </div>
                        </div>
                    </c:if>
                    <div style="margin-bottom: 20px">
                    <%@include file="../common/fragment/equipment_table.jspf" %>
                    </div>
                </div>
                <div class="collapse" id="show_assistants">
                    <div style="margin-bottom: 10px">
                        <h4 class="h4 align-self-center">${assistants}</h4>
                    </div>
                    <div class="col-xs-2">
                        <div style="margin-bottom: 10px">
                            <a class="btn btn-primary"
                               href="${abs}/controller?command=go_add_assistant_page_command">${add_assistant}</a>
                        </div>
                    </div>
                    <c:if test="${requestScope.empty_assistant_list}">
                        <div class="align-self-center">${not_found}</div>
                    </c:if>
                    <div style="margin-bottom: 20px">
                    <c:forEach var="availableAssistant" items="${requestScope.assistant_list}">
                        <div class="row g-0" style="margin-bottom: 10px">
                            <div class="col-lg-5">
                            <img src="${abs}/${availableAssistant.imageFilePath}" width="300" class="flex-shrink-0 me-3"
                                 alt="${availableAssistant.lastName} ${availableAssistant.firstName}">
                            </div>
                                <div class="col-lg-5">
                                <h5 class="mt-0">${availableAssistant.lastName} ${availableAssistant.firstName}</h5>
                                <p class="card-text">+${availableAssistant.phone}</p>
                                <p class="card-text">${availableAssistant.email}</p>
                            </div>
                        </div>
                    </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<ctg:print-footer/>
</body>
</html>