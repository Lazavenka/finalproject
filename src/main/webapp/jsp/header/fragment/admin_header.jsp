<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

<fmt:setBundle basename="locale/language"/>

<fmt:message var="user_management" key="admin.user_management"/>
<fmt:message var="department_management" key="admin.department_management"/>
<fmt:message var="order_management" key="admin.orders_management"/>
<fmt:message var="add_laboratory" key="admin.add_laboratory"/>
<fmt:message var="add_equipment_type" key="admin.add_equipment_type"/>
<fmt:message var="add_equipment" key="admin.add_equipment"/>
<fmt:message var="menu" key="admin.menu"/>
<c:set var="abs">${pageContext.request.contextPath}</c:set>

<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">${menu}</a>
    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
        <li><a class="dropdown-item" href="${abs}/controller?command=go_user_management_page_command">${user_management}</a></li>
        <li><a class="dropdown-item" href="${abs}/controller?command=find_all_departments_command">${department_management}</a></li>
        <li><a class="dropdown-item" href="${abs}/controller?command=order_management_command">${order_management}</a></li>
        <li><hr class="dropdown-divider"></li>
        <li><a class="dropdown-item" href="${abs}/controller?command=go_add_new_laboratory_page_command">${add_laboratory}</a></li>
        <li><hr class="dropdown-divider"></li>
        <li><a class="dropdown-item" href="${abs}/controller?command=go_add_new_equipment_type_page_command">${add_equipment_type}</a></li>
        <li><a class="dropdown-item" href="${abs}/controller?command=go_add_new_equipment_page_command">${add_equipment}</a></li>
    </ul>
</li>