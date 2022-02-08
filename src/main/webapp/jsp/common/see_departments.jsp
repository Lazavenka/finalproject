<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="details" key="common.details"/>
<fmt:message var="add_department" key="buttons.add"/>
<fmt:message var="edit" key="button.edit"/>
<fmt:message var="all_departments_page" key="message.all_departments_page"/>
<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Departments page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>
<div class="container">
    <br>
    <figure class="text-center">
        <blockquote class="blockquote">
            <p>${all_departments_page}</p>
        </blockquote>
    </figure>
    <div class="w-75 mx-auto">
        <c:if test="${sessionScope.user.role.name() eq 'ADMIN'}">
        <a href="${abs}/controller?command=add_new_department_command" class="btn btn-primary">${add_department}</a>
        </c:if>
        <c:forEach var="department" items="${requestScope.departments}">
        <div class="d-flex position-relative">
            <div>
                <h5 class="mt-0">${department.name}</h5>
                <p class="card-text">${department.description}</p>
                <p class="card-text">${department.address}</p>
                <a href="${abs}/controller?command=find_department_details_by_id_command&department_id=${department.id}"
                   class="stretched-link">${details}</a>
            </div>
            <c:if test="${sessionScope.user.role.name() eq 'ADMIN'}">
                <a href="${abs}/controller?command=edit_department_command&department_id=${department}"
                   class="btn btn-primary">${edit}</a>
            </c:if>
        </div>
        </c:forEach>
    </div>
</div>
<ctg:print-footer/>
</body>
</html>