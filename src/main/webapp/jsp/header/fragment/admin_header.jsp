<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="message" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="user_management" key=""/>
<fmt:message var="department_management" key=""/>
<fmt:message var="order_management" key=""/>
<fmt:message var="menu" key=""/>

<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${menu}<span class="caret"></span></a>
    <ul class="dropdown-menu">
        <li><a href="controller?command=user_management_command">${user_management}</a></li>
        <li><a href="controller?command=department_management_command">${department_management}</a></li>
        <li><a href="controller?command=order_management_command">${order_management}</a></li>
        <li role="separator" class="divider"></li>
        <li><a href="#">Separated link</a></li>
        <li role="separator" class="divider"></li>
        <li><a href="#">One more separated link</a></li>
    </ul>
</li>
