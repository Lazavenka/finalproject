<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="details" key="common.details"/>
<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Managers page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>

<c:forEach var="manager" items="${requestScope.managers}">
    <div class="d-flex position-relative">
        <img src="${abs}/${manager.imageFilePath}" width="300" class="flex-shrink-0 me-3" alt="...">
        <div>
            <h5 class="mt-0">${manager.lastName} ${manager.firstName}</h5>
            <p class="card-text">${manager.description}</p>
            <a href="${abs}/controller?command=find_manager_details_by_id_command&manager_id=${manager.managerId}" class="stretched-link">${details}</a>
        </div>
    </div>
</c:forEach>


</body>
</html>
