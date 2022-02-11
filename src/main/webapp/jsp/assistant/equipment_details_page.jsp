<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="not_found" key="common.not_found"/>
<fmt:message var="equipment_price" key="equipment.price"/>
<fmt:message var="equipment_avg_research_time" key="equipment.avg_time"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="equipment_state" key="equipment.state"/>
<fmt:message var="equipment_details" key="message.equipment_details_page"/>
<fmt:message var="to_sсhedule" key="buttons.to_schedule"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>
<c:set var="equipment" value="${requestScope.selected_equipment}"/>

<html>
<head>
    <title>${equipment_details}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="../../css/styles.css">

</head>
<body>
<header>
    <jsp:include page="/jsp/header/header.jsp"/>
</header>
<div class="container" style="margin-top: 10px">
    <figure class="text-center">
        <blockquote class="blockquote">
            <p>${equipment_details}</p>
        </blockquote>
    </figure>
    <div class="w-75 mx-auto">
        <div style="margin-bottom: 10px; margin-top: 10px">
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
    </div>
    <div style="margin-bottom: 40px">
        <a class="btn btn-primary" role="button" href="${abs}/controller?command=show_schedule_command">${to_sсhedule}</a>
    </div>
</div>

<ctg:print-footer/>
</body>
</html>
