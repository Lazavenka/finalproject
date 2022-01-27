<%--
  Created by IntelliJ IDEA.
  User: Roger
  Date: 16.01.2022
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="locale_login" key="common.login"/>
<fmt:message var="locale_password" key="common.password"/>
<fmt:message var="sign_in" key="common.sign_in"/>
<fmt:message var="incorrect_message" key="common.incorrect_login_or_password"/>

<html>
<head>
    <title>${sign_in}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<header>
    <jsp:include page="/jsp/header/header.jsp"/>
</header>
<form action="${pageContext.request.contextPath}/controller" method="post" name="LoginForm">
    <input type="hidden" name="command" value="login_command">
    <c:if test="${requestScope.incorrect_login_or_password}">
        <div class="alert alert-danger">${incorrect_message}</div>
    </c:if>
    <div class="form-floating mb-3">
        <input type="text" class="form-control" id="floatingInput" name="login" placeholder=${locale_login}>
        <label for="floatingInput">${locale_login}</label>
    </div>
    <div class="form-floating">
        <input type="password" class="form-control" id="floatingPassword" name="password" placeholder=${locale_password}>
        <label for="floatingPassword">${locale_password}</label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">${sign_in}</button>
</form>
<jsp:include page="/jsp/footer/footer.jsp"/>
</body>
</html>