<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="search" key="buttons.search"/>
<fmt:message var="department" key="common.manager_details.department"/>
<fmt:message var="choose_department" key="common.choose_department"/>
<fmt:message var="department_description" key="common.description"/>
<fmt:message var="department_address" key="common.department_address"/>
<fmt:message var="laboratories" key="header.laboratories"/>
<fmt:message var="not_found" key="common.not_found"/>
<fmt:message var="details" key="common.details"/>
<fmt:message var="home" key="header.home"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Department details page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>
<div class="container-fluid">
    <div class="col-xs-2">
        <form action="${abs}/controller" method="get">
            <div>
                <input type="hidden" name="command" value="find_department_details_by_id_command"/>
                <input type="hidden" name="current_department_id" value="${requestScope.selected_department.id}"/>
                <div class="select-form">
                    <span>${department}</span>
                    <select id="department_id" name="department_id" class="form-control">
                        <c:if test="${requestScope.selected_department != null}">
                            <option selected disabled>${requestScope.selected_department.name}</option>
                        </c:if>
                        <c:if test="${requestScope.selected_department == null}">
                            <option selected disabled>${choose_department}</option>
                        </c:if>
                        <c:forEach var="department" items="${requestScope.departments}">
                            <option value="${department.id}">${department.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <input type="submit" value="${search}"/>
                </div>
            </div>
        </form>
    </div>
    <c:if test="${requestScope.selected_department != null}">
        <dl class="row">
            <dt class="col-sm-3">${department}</dt>
            <dd class="col-sm-9">${requestScope.selected_department.name}</dd>
            <dt class="col-sm-3">${department_description}</dt>
            <dd class="col-sm-9">${requestScope.selected_department.description}</dd>
            <dt class="col-sm-3">${department_address}</dt>
            <dd class="col-sm-9">${requestScope.selected_department.address}</dd>
        </dl>
    </c:if>
    <c:choose>
        <c:when test="${requestScope.empty_list}">${not_found}</c:when>
        <c:otherwise>
            <div class="justify-content-center">${laboratories}</div>
            <c:forEach var="laboratory" items="${requestScope.laboratories}">
                <div class="d-flex position-relative">
                    <img src="${laboratory.imageFilePath}" class="flex-shrink-0 me-3" alt="...">
                    <div>
                        <h5 class="mt-0">${laboratory.name}</h5>
                        <p class="card-text">${laboratory.description}</p>
                        <p class="card-text">${laboratory.location}</p>
                        <a href="${abs}/controller?command=find_laboratory_details_by_id_command&laboratory_id=${laboratory.id}"
                           class="stretched-link">${details}</a>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<a class="btn btn-primary" href="${abs}/controller?command=go_home_command" role="button">${home}</a>
</body>
</html>
