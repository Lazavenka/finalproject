<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
    <c:when test="${not empty sessionScope.locale}"><fmt:setLocale value="${sessionScope.locale}"/></c:when>
    <c:when test="${empty sessionScope.locale}"><fmt:setLocale value="${sessionScope.locale = 'ru_RU'}"/></c:when>
</c:choose>

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
<fmt:message var="equipment" key="header.equipment"/>
<fmt:message var="departments" key="header.departments"/>
<fmt:message var="laboratories" key="header.laboratories"/>
<fmt:message var="balance" key="header.balance"/>
<fmt:message var="add_balance" key="header.add_balance"/>
<fmt:message var="client_orders" key="header.client_orders"/>
<fmt:message var="profile" key="header.profile"/>

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
        <a class="navbar-brand" href="${abs}/controller?command=go_about_page_command">${brand}</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 d-flex justify-content-start">
                <li class="nav-item">
                    <a class="nav-link active" href="${abs}/controller?command=go_home_command">${home}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="${abs}/controller?command=go_about_page_command">${about}</a>
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
                        <li><a class="dropdown-item" href="#">${departments}</a></li>
                        <li><a class="dropdown-item" href="#">${laboratories}</a></li>
                        <li><a class="dropdown-item" href="${abs}/controller?command=find_all_managers">${managers}</a></li>
                        <li><a class="dropdown-item" href="${abs}/controller?command=go_equipment_page_command">${equipment}</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#">Separated link</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">One more separated link</a></li>
                    </ul>
                </li>

            </ul>
            <ul class="navbar-nav d-flex justify-content-end">
                <c:choose>
                    <c:when test="${user == null or user.role eq 'GUEST'}">
                        <li><a class="nav-link" href="${abs}/controller?command=go_sign_in_page_command">${sign_in}</a></li>
                        <li><a class="nav-link" href="${abs}/controller?command=go_register_page_command">${register}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">${user.login}</a>
                            <ul class="dropdown-menu" aria-labelledby="userDropdown">
                                <li><a class="dropdown-item" href="${abs}/controller?command=go_edit_profile_page_command">${profile}</a></li>
                                <c:if test="${user.role eq 'CLIENT'}">
                                    <li><a class="dropdown-item" href="${abs}/controller?command=check_balance_command">${balance}<c:if test="${sessionScope.user_balance != null}">: ${sessionScope.user_balance.floatValue()}</c:if></a></li>
                                    <li><a class="dropdown-item" href="${abs}/controller?command=go_balance_page_command">${add_balance}</a></li>
                                    <li><a class="dropdown-item" href="${abs}/controller?command=show_client_orders_command&userId=${user.id}">${client_orders}</a></li>
                                </c:if>
                                <li role="separator" class="divider"></li>
                                <li><a class="dropdown-item" href="${abs}/controller?command=logout_command">${sign_out}</a></li>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
                <li class="nav-item">
                    <a class="nav-link <c:if test="${sessionScope.locale eq 'ru_RU'}">disabled</c:if>" href="${abs}/controller?command=change_locale_command&locale=ru_RU">РУ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <c:if test="${sessionScope.locale eq 'en_US'}">disabled</c:if>" href="${abs}/controller?command=change_locale_command&locale=en_US">EN</a>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>
