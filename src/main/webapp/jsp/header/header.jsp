<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>
<c:set var="user" value="${sessionScope.user}"/>

<fmt:message var="about" key="header.about"/>
<fmt:message var="sign_in" key="header.sign_in"/>
<fmt:message var="sign_out" key="header.sign_out"/>
<fmt:message var="register" key="header.sign_up"/>
<fmt:message var="locale" key="header.language"/>
<fmt:message var="brand" key="header.brand"/>
<fmt:message var="home" key="header.home" />
<fmt:message var="managers" key="header.managers"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <a class="navbar-brand" href="${abs}/jsp/about.jsp">${brand}</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" href="${abs}/controller?command=go_home_command">${home}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="${abs}/jsp/about.jsp">${about}</a>
                </li>
                <c:choose>
                    <c:when test="${user.role eq 'ADMIN'}">
                        <jsp:include page="fragment/admin_header.jsp"/>
                    </c:when>
                    <c:when test="${user.role eq'MANAGER'}">
                        <jsp:include page="fragment/manager_header.jsp"/>
                    </c:when>
                    <c:when test="${user.role eq 'ASSISTANT'}">
                        <jsp:include page="fragment/assistant_header.jsp"/>
                    </c:when>
                </c:choose>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">Dropdown</a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">Departments</a></li>
                        <li><a class="dropdown-item" href="#">Laboratories</a></li>
                        <li><a class="dropdown-item" href="${abs}/controller?command=find_all_managers">${managers}</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#">Separated link</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">One more separated link</a></li>
                    </ul>
                </li>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

                <c:choose>
                    <c:when test="${user.role eq 'GUEST' or user.role eq null}">
                        <li><a class="nav-link" href="${abs}/jsp/common/login.jsp">${sign_in}</a></li>
                        <li><a class="nav-link" href="${abs}/jsp/common/register.jsp">${register}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a class="nav-link" href="${abs}/controller?command=logout_command">${sign_out}</a></li>
                    </c:otherwise>
                </c:choose>
                <li class="nav-item">
                    <a class="nav-link" href="${abs}/controller?command=change_locale_command&locale=ru_RU">РУ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${abs}/controller?command=change_locale_command&locale=en_US">EN</a>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

</body>
</html>
