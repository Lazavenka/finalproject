<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="locale_login" key="common.login"/>
<fmt:message var="locale_password" key="common.password"/>
<fmt:message var="sign_in" key="common.sign_in"/>
<fmt:message var="incorrect_message" key="common.incorrect_login_or_password"/>
<fmt:message var="blocked_message" key="login.blocked_user_message"/>
<fmt:message var="unconfirmed_message" key="login.unconfirmed_user_message"/>

<html>
<head>
    <title>${sign_in}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="../../css/styles.css">

</head>
<body>
<header>
    <jsp:include page="/jsp/header/header.jsp"/>
</header>
<div class="container" style="margin-top: 10px">
    <figure class="text-center">
        <blockquote class="blockquote">
            <p>${sign_in}</p>
        </blockquote>
    </figure>
    <div class="w-50 mx-auto">
        <form class="form-floating mb-3" action="${pageContext.request.contextPath}/controller" method="post"
              name="LoginForm">
            <input type="hidden" name="command" value="login_command">
            <c:choose>
                <c:when test="${requestScope.incorrect_login_or_password}">
                    <div class="alert alert-danger">${incorrect_message}</div>
                </c:when>
                <c:when test="${requestScope.blocked_user}">
                    <div class="alert alert-danger">${blocked_message}</div>
                </c:when>
                <c:when test="${requestScope.unconfirmed_user}">
                    <div class="alert alert-danger">${unconfirmed_message}</div>
                </c:when>
            </c:choose>
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="floatingInput" name="login" placeholder=${locale_login}>
                <label for="floatingInput">${locale_login}</label>
            </div>
            <div class="form-floating">
                <input type="password" class="form-control" id="floatingPassword" name="password"
                       placeholder=${locale_password}>
                <label for="floatingPassword">${locale_password}</label>
            </div>
            <div class="justify-content-center" style="margin-top: 25px">
                <button class="btn btn-primary" type="submit">${sign_in}</button>
            </div>
        </form>
    </div>
</div>

<ctg:print-footer/>
</body>
</html>
