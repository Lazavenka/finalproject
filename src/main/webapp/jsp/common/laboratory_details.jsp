<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>
<fmt:message var="laboratory" key="common.manager_details.laboratory"/>
<fmt:message var="choose_laboratory" key="common.choose_laboratory"/>
<fmt:message var="search" key="buttons.search"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="department" key="common.manager_details.department"/>
<fmt:message var="not_found" key="common.not_found"/>
<fmt:message var="details" key="common.details"/>
<fmt:message var="home" key="header.home"/>
<fmt:message var="manager" key="common.manager"/>
<fmt:message var="location" key="common.location"/>
<fmt:message var="equipment_list" key="equipment.all_equipment"/>


<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Laboratory details page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>

<div class="container-fluid">
    <div class="col-xs-2">
        <form action="${abs}/controller" method="post">
            <div>
                <input type="hidden" name="command" value="find_laboratory_details_by_id_command"/>
                <div class="select-form">
                    <span>${laboratory}</span>
                    <select id="laboratory_id" name="laboratory_id" class="form-control">
                        <c:if test="${requestScope.selected_laboratory!= null}">
                            <option selected disabled>${requestScope.selected_laboratory.name}</option>
                        </c:if>
                        <c:if test="${requestScope.selected_laboratory == null}">
                            <option selected disabled>${choose_laboratory}</option>
                        </c:if>
                        <c:forEach var="laboratory" items="${requestScope.department_laboratories}">
                            <option value="${laboratory.id}">${laboratory.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <input type="submit" value="${search}"/>
                </div>
            </div>
        </form>
    </div>
    <div class="d-flex position-relative">
        <img src="${requestScope.selected_laboratory.imageFilePath}" class="flex-shrink-0 me-3" alt="...">
        <div>
            <h5 class="card-title">${requestScope.selected_laboratory.name}</h5>
            <dl class="row">
                <dt class="col-sm-3">${description}</dt>
                <dd class="col-sm-9">${requestScope.selected_laboratory.description}</dd>

                <dt class="col-sm-3">${department}</dt>
                <c:choose>
                    <c:when test="${requestScope.department_not_found}">
                        <dd class="col-sm-9">${not_found}</dd>
                    </c:when>
                    <c:otherwise>
                        <dd class="col-sm-9"><a href="${abs}/controller?command=find_department_details_by_id_command&department_id=${requestScope.department.id}">${requestScope.department.name}</a></dd>
                    </c:otherwise>
                </c:choose>
                <dt class="col-sm-3">${location}</dt>
                <dd class="col-sm-9"><c:if test="${requestScope.department != null}">${requestScope.department.address} </c:if>${requestScope.selected_laboratory.location}</dd>


                <dt class="col-sm-3">${manager}</dt>
                <c:choose>
                    <c:when test="${requestScope.manager_not_found}">
                        <dd class="col-sm-9">${not_found}</dd>
                    </c:when>
                    <c:otherwise>
                        <dd class="col-sm-9">
                            <div class="d-flex position-relative">
                                <img src="${requestScope.manager.imageFilePath}" class="flex-shrink-0 me-3" alt="...">
                                <div>
                                    <h5 class="mt-0">${requestScope.manager.lastName} ${requestScope.manager.firstName}</h5>
                                    <p class="card-text">${requestScope.manager.managerDegree.value}</p>
                                    <p class="card-text">${requestScope.manager.description}</p>
                                    <p class="card-text">+${requestScope.manager.phone}</p>
                                    <p class="card-text">${requestScope.manager.email}</p>
                                    <a href="${abs}/controller?command=find_manager_details_by_id_command&manager_id=${requestScope.manager.managerId}" class="stretched-link">${details}</a>
                                </div>
                            </div>
                        </dd>
                    </c:otherwise>
                </c:choose>
            </dl>
        </div>
    </div>
    <c:choose>
        <c:when test="${requestScope.empty_list}">${not_found}</c:when>
        <c:otherwise>
            <div class="justify-content-center">${equipment_list}</div>
            <jsp:include page="equipment_table.jsp"/>
        </c:otherwise>
    </c:choose>
</div>

<a class="btn btn-primary" href="${abs}/controller?command=go_home_command" role="button">${home}</a>
</body>
</html>
