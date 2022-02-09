<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>


<fmt:message var="check_mail" key="registration.check_mail_message"/>
<fmt:message var="sign_in" key="common.sign_in"/>

<html>
<head>
    <title>Registration confirm page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="../../css/styles.css">

</head>
<body>
<jsp:include page="../header/header.jsp"/>
<div class="spaced">
    <figure class="text-center">
        <blockquote class="blockquote">
            <p>${check_mail}</p>
        </blockquote>
    </figure>
</div>
<div class="spaced align-self-center">
    <a role="button" class="btn btn-primary"
       href="${abs}/controller?command=go_sign_in_page_command">${sign_in}</a>
</div>
<ctg:print-footer/>
</body>
</html>