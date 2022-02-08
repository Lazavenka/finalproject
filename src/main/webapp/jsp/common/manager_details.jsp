<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="no_managers" key="common.manager_details.no_managers_found"/>
<fmt:message var="phone" key="common.manager_details.phone"/>
<fmt:message var="email" key="common.manager_details.email"/>
<fmt:message var="degree" key="common.manager_details.degree"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="laboratory" key="common.manager_details.laboratory"/>
<fmt:message var="department" key="common.manager_details.department"/>
<fmt:message var="contacts" key="common.manager_details.contacts"/>
<fmt:message var="field_not_found" key="common.details.field_not_found"/>
<fmt:message var="manager_details_page" key="message.manager_details_page"/>
<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Manager details page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>
<div class="container">
    <br>
    <figure class="text-center">
        <blockquote class="blockquote">
            <p>${manager_details_page}</p>
        </blockquote>
    </figure>
    <div class="w-75 mx-auto">
        <c:choose>
        <c:when test="${requestScope.no_managers}"><div class="alert alert-danger">${no_managers}</div></c:when>
        <c:otherwise><c:set var="manager" value="${requestScope.manager}"/>
        <div class="d-flex position-relative">
            <img src="${abs}/${manager.imageFilePath}" width="400" class="flex-shrink-0 me-3" alt="...">

            <div class="card-body">
                <h5 class="card-title">${manager.lastName} ${manager.firstName}</h5>
                <dl class="row">
                    <dt class="col-sm-3">${degree}</dt>
                    <dd class="col-sm-9">${manager.managerDegree.value}</dd>

                    <dt class="col-sm-3">${department}</dt>
                    <c:choose>
                        <c:when test="${requestScope.department_not_found}">
                            <dd class="col-sm-9">${field_not_found}</dd>
                        </c:when>
                        <c:otherwise>
                            <dd class="col-sm-9"><a
                                    href="${abs}/controller?command=find_department_details_by_id_command&department_id=${manager.departmentId}">${requestScope.department_name}</a>
                            </dd>
                        </c:otherwise>
                    </c:choose>

                    <dt class="col-sm-3">${laboratory}</dt>
                    <c:choose>
                        <c:when test="${requestScope.laboratory_not_found}">
                            <dd class="col-sm-9">${field_not_found}</dd>
                        </c:when>
                        <c:otherwise>
                            <dd class="col-sm-9"><a
                                    href="${abs}/controller?command=find_laboratory_details_by_id_command&laboratory_id=${manager.laboratoryId}">${requestScope.laboratory_name}</a>
                            </dd>
                        </c:otherwise>
                    </c:choose>

                    <dt class="col-sm-3">${description}</dt>
                    <dd class="col-sm-9">${manager.description}</dd>

                    <dt class="col-sm-3">${contacts}</dt>
                    <dd class="col-sm-9">
                        <dl class="row">
                            <dt class="col-sm-4">${phone}</dt>
                            <dd class="col-sm-8">${manager.phone}</dd>
                        </dl>
                        <dl class="row">
                            <dt class="col-sm-4">${email}</dt>
                            <dd class="col-sm-8">${manager.email}</dd>
                        </dl>
                    </dd>
                </dl>
            </div>
        </div>
        </c:otherwise>
        </c:choose>
        <br>
    </div>
</div>
<ctg:print-footer/>
</body>
</html>
