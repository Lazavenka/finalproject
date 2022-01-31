 page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="user_id" key="common.manager_details.no_managers_found"/>
<fmt:message var="first_name" key="common.manager_details.phone"/>
<fmt:message var="last_name" key="common.manager_details.email"/>
<fmt:message var="login" key="common.manager_details.degree"/>
 <fmt:message var="phone" key="common.manager_details.contacts"/>
 <fmt:message var="email" key="common.details.field_not_found"/>
<fmt:message var="user_role" key="common.description"/>
<fmt:message var="user_state" key="common.manager_details.laboratory"/>
<fmt:message var="edit" key="common.manager_details.department"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Manage users page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>
<table class="table-light">
    <thead>
    <tr>
        <th scope="col">${user_id}</th>
        <th scope="col">${first_name}</th>
        <th scope="col">${last_name}</th>
        <th scope="col">${login}</th>
        <th scope="col">${phone}</th>
        <th scope="col">${email}</th>
        <th scope="col">${user_role}</th>
        <th scope="col">${user_state}</th>
        <th scope="col">${edit}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${requestScope.users}">
        <tr>
            <th scope="row">${user.id}</th>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.phone}</td>
            <td>${user.email}</td>
            <td>${user.login}</td>
            <td>${user.role}</td>
            <td>${user.state}</td>
            <td><a role="button" class="btn btn-primary" href="${abs}/controller?command=edit_user_command&userId=${user.id}">${edit}</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
