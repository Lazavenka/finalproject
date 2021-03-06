<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

<fmt:setBundle basename="locale/language"/>

<fmt:message var="schedule" key="assistant.schedule"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<li><a class="nav-link active" href="${abs}/controller?command=show_schedule_command">${schedule}</a></li>