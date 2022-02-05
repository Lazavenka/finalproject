<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="user_id" key="common.manager_details.no_managers_found"/>
<fmt:message var="first_name" key="registration.first_name"/>
<fmt:message var="last_name" key="registration.last_name"/>
<fmt:message var="login" key="registration.login"/>
<fmt:message var="phone" key="registration.phone"/>
<fmt:message var="email" key="registration.email"/>
<fmt:message var="user_role" key="admin.role"/>
<fmt:message var="user_state" key="admin.state"/>
<fmt:message var="edit" key="button.edit"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Manage users page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>

<c:choose>
    <c:when test="${requestScope.success_user_management}">
        <figure class="text-center">
            <blockquote class="blockquote">
                <p class="alert-success">${success_message}</p>
            </blockquote>
        </figure>
    </c:when>
    <c:when test="${requestScope.error_user_management}">
        <figure class="text-center">
            <blockquote class="blockquote">
                <p class="alert-warning">${error_message}</p>
                <p class="alert-warning">${requestScope.exception.message}</p>
            </blockquote>
        </figure>
    </c:when>
</c:choose>

<table class="table-light">
    <thead>
    <tr>
        <th scope="col">ID</th>
        <th scope="col">${first_name}</th>
        <th scope="col">${last_name}</th>
        <th scope="col">${phone}</th>
        <th scope="col">${email}</th>
        <th scope="col">${login}</th>
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
            <td>
                <form action="${abs}/controller" method="post" class="form-control">
                    <input type="hidden" name="command" value="update_user_state_command"/>
                    <input type="hidden" name="user_id" value="${user.id}"/>
                    <div class="select-form">
                        <select id="equipment_state" name="equipment_state" class="form-control">
                            <option
                                    <c:if test="${user.state.name() eq 'ACTIVE'}">selected</c:if>
                                    value="ACTIVE">${state_active}</option>
                            <option
                                    <c:if test="${user.state.name() eq 'INACTIVE'}">selected</c:if>
                                    value="BLOCKED">${state_blocked}</option>
                        </select>
                        <c:if test="${requestScope.invalid_enum}">
                            <div class="alert alert-danger">${invalid_enum_message}</div>
                        </c:if>
                        <button type="submit" class="btn btn-primary btn-sm">${sign_up}</button>
                    </div>
                </form>
            </td>
            <td><a role="button" class="btn btn-primary <c:if test="${sessionScope.user.id eq user.id}">disabled</c:if>"
                   href="${abs}/controller?command=delete_user_command&user_id=${user.id}">${delete}</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
