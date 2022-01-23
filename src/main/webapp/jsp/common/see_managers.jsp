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
    <div class="card" style="width: 25rem;">
        <img src="${manager.imageFilePath}" class="card-img-top" alt="...">
        <div class="card-body">
            <h5 class="card-title">${manager.lastName} ${manager.firstName}</h5>
            <p class="card-text">${manager.description}</p>
            <a href="${abs}/controller?command=find_manager_by_id&managerId=${manager.managerId}" class="btn btn-primary">${details}</a>
        </div>
    </div>
</c:forEach>


</body>
</html>
