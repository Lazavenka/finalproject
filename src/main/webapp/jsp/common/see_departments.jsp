<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="details" key="common.details"/>
<fmt:message var="all_departments_page" key="message.all_departments_page"/>
<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Departments page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="../../css/styles.css">

</head>
<body>
<jsp:include page="../header/header.jsp"/>
<div class="container" style="margin-top: 20px; margin-bottom: 20px">
    <div style="margin-bottom: 20px">
        <figure class="text-center">
            <blockquote class="blockquote">
                <p>${all_departments_page}</p>
            </blockquote>
        </figure>
    </div>
    <div class="w-75 mx-auto">
        <c:forEach var="department" items="${requestScope.departments}">
            <div style="margin-bottom: 20px">
                <div class="d-flex position-relative">
                    <div>
                        <h5 class="mt-0">${department.name}</h5>
                        <p class="card-text">${department.description}</p>
                        <p class="card-text">${department.address}</p>
                        <a href="${abs}/controller?command=find_department_details_by_id_command&department_id=${department.id}"
                           class="btn btn-primary">${details}</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<ctg:print-footer/>
</body>
</html>