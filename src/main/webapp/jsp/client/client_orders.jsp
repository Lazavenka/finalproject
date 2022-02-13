<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="success_message" key="message.success_message"/>
<fmt:message var="client_orders_page" key="message.client_orders_page"/>
<fmt:message var="details" key="buttons.details"/>
<fmt:message var="pay" key="buttons.pay"/>
<fmt:message var="cancel" key="buttons.cancel"/>
<fmt:message var="date" key="order.date"/>
<fmt:message var="price" key="order.price"/>
<fmt:message var="time_start" key="equipment.time_start"/>
<fmt:message var="time_end" key="equipment.time_end"/>
<fmt:message var="error_message" key="message.error_message"/>
<fmt:message var="lack_of_money_message" key="message.lack_of_money_message"/>
<fmt:message var="state" key="equipment.state"/>


<c:set var="client_orders" value="${requestScope.order_list}"/>

<html>
<head>
    <title>${client_orders_page} Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="../../css/styles.css">

</head>
<body>

<jsp:include page="../header/header.jsp"/>
<div class="container" style="margin-top: 20px">
    <div style="margin-bottom: 20px">
        <figure class="text-center">
            <blockquote class="blockquote">
                <p>${client_orders_page}</p>
            </blockquote>
        </figure>
    </div>

    <div class="w-75 mx-auto">
        <div style="margin-top: 10px; margin-bottom: 10px">
            <figure class="text-center">
                <blockquote class="blockquote">
                    <c:choose>
                        <c:when test="${requestScope.error_message}">
                            <p class="alert-warning">${error_message}</p>
                        </c:when>
                        <c:when test="${requestScope.lack_of_money}">
                            <p class="alert-warning">${lack_of_money_message}</p>
                        </c:when>
                    </c:choose>


                </blockquote>
            </figure>
        </div>
        <table class="table table-striped" style="margin-bottom: 10px">
            <thead>
            <tr>
                <th scope="col">${date}</th>
                <th scope="col">${time_start}</th>
                <th scope="col">${time_end}</th>
                <th scope="col">${price}</th>
                <th scope="col">${state}</th>
                <th scope="col">${pay}</th>
                <th scope="col">${cancel}</th>
                <th scope="col">${details}</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${client_orders}">
                <tr>
                    <th scope="row">${order.rentStartTime.toLocalDate()}</th>
                    <td>${order.rentStartTime.toLocalTime()}</td>
                    <td>${order.rentEndTime.toLocalTime()}</td>
                    <td>${order.totalCost.toString()}</td>
                    <td>${order.state.name()}</td>
                    <td>
                        <form action="${abs}/controller" method="post">
                            <input type="hidden" name="command" value="pay_order_command">
                            <input type="hidden" name="order_id" value="${order.id}">
                            <button type="submit" class="btn btn-success"
                                    <c:if test="${order.state.name() != 'BOOKED'}">disabled</c:if> >${pay}</button>
                        </form>
                    </td>
                    <td>
                        <form action="${abs}/controller" method="post">
                            <input type="hidden" name="command" value="cancel_order_command">
                            <input type="hidden" name="order_id" value="${order.id}">
                            <button type="submit" class="btn btn-danger"
                                    <c:if test="${order.state.name() != 'BOOKED'}">disabled</c:if> >${cancel}</button>
                        </form>
                    </td>
                    <td><a class="btn btn-secondary" role="button"
                           href="${abs}/controller?command=go_order_details_page_command&order_id=${order.id}">${details}</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <nav aria-label="Page navigation example" style="margin-bottom: 30px">
            <ul class="pagination justify-content-center">
                <li class="page-item  <c:if test="${requestScope.pagination_page eq 1}">disabled</c:if>">
                    <a class="page-link" href="${abs}/controller?command=go_client_orders_page_command&page=${requestScope.pagination_page - 1}">Previous</a>
                </li>
                <c:forEach begin="1" end="${requestScope.number_of_pages}" var="i">
                    <c:choose>
                        <c:when test="${requestScope.pagination_page eq i}">
                            <li class="page-item"><a class="page-link disabled" href="#">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="${abs}/controller?command=go_client_orders_page_command&page=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <li class="page-item <c:if test="${requestScope.pagination_page >= requestScope.number_of_pages}">disabled</c:if>">
                    <a class="page-link"  href="${abs}/controller?command=go_client_orders_page_command&page=${requestScope.pagination_page + 1}">Next</a>
                </li>
            </ul>
        </nav>
    </div>
</div>



<ctg:print-footer/>
</body>
</html>
