<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Managers page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="${abs}header/header.jsp"/>

<c:forEach var="manager" items="${requestScope.managers}">
    <div class="card" style="width: 18rem;">
        <img src="${manager.}" class="card-img-top" alt="...">
        <div class="card-body">
            <h5 class="card-title">${sessionScope.user.role} LOGGED</h5>
            <p class="card-text">${sessionScope.user.phone}</p>
            <a href="#" class="btn btn-primary">Go somewhere</a>
        </div>
    </div>
</c:forEach>


</body>
</html>
