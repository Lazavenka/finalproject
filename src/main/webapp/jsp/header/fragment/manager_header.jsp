<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

<fmt:setBundle basename="locale/language"/>

<fmt:message var="my_lab" key="manager.my_lab"/>
<fmt:message var="orders" key="manager.orders"/>

<li><a class="nav-link active" href="${abs}/controller?command=go_managers_lab_command">${my_lab}</a></li>
<li><a class="nav-link active" href="${abs}/controller?command=go_laboratory_orders_command">${orders}</a></li>
