<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

<fmt:setBundle basename="locale/language"/>

<fmt:message var="user_management" key="admin.user_management"/>
<fmt:message var="department_management" key="admin.department_management"/>
<fmt:message var="order_management" key="admin.orders_management"/>
<fmt:message var="menu" key="admin.menu"/>

<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">${menu}</a>
    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
        <li><a class="dropdown-item" href="controller?command=user_management_command">${user_management}</a></li>
        <li><a class="dropdown-item" href="controller?command=department_management_command">${department_management}</a></li>
        <li><a class="dropdown-item" href="controller?command=order_management_command">${order_management}</a></li>
        <li><hr class="dropdown-divider"></li>
        <li><a class="dropdown-item" href="#">Separated link</a></li>
        <li><hr class="dropdown-divider"></li>
        <li><a class="dropdown-item" href="#">One more separated link</a></li>
    </ul>
</li>