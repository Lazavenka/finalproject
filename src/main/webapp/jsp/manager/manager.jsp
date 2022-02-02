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

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Manager page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>
<div class="d-flex position-relative">
    <img src="${requestScope.manager.imageFilePath}" class="flex-shrink-0 me-3" alt="...">
    <div>
        <h5 class="mt-0">${requestScope.manager.lastName} ${requestScope.manager.firstName}</h5>
        <p class="card-text">${requestScope.manager.managerDegree.value}</p>
        <p class="card-text">${requestScope.manager.description}</p>
        <p class="card-text">+${requestScope.manager.phone}</p>
        <p class="card-text">${requestScope.manager.email}</p>
        <a href="${abs}/controller?command=edit_profile_command&manager_id=${sessionScope.user.id}" class="btn btn-primary">${edit}</a>
    </div>
</div>
<div class="d-flex position-relative">
    <img src="${requestScope.selected_laboratory.imageFilePath}" class="flex-shrink-0 me-3" alt="...">
    <div>
        <h5 class="card-title">${requestScope.selected_laboratory.name}</h5>
        <dl class="row">
            <dt class="col-sm-3">${description}</dt>
            <dd class="col-sm-9">${requestScope.selected_laboratory.description}</dd>

            <dt class="col-sm-3">${location}</dt>
            <dd class="col-sm-9">${requestScope.selected_laboratory.location}</dd>

            <dt class="col-sm-3"></dt>
            <dd class="col-sm-9">
                <a href="${abs}/controller?command=go_edit_laboratory_page_command&laboratory_id=${requestScope.selected_laboratory.id}" class="btn btn-primary">${edit}</a>
            </dd>
        </dl>
    </div>
</div>
<div class="justify-content-center">${equipment_list}</div>
<jsp:include page="/jsp/common/equipment_table.jsp"/>

</body>
</html>