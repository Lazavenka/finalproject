<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:choose>
    <c:when test="${not empty sessionScope.locale}"><fmt:setLocale value="${sessionScope.locale}"/></c:when>
    <c:when test="${empty sessionScope.locale}"><fmt:setLocale value="${sessionScope.locale = 'ru_RU'}"/></c:when>
</c:choose>

<fmt:setBundle basename="locale/language"/>

<fmt:message var="equipment_type" key="equipment.equipment_type"/>
<fmt:message var="choose_type" key="equipment.choose_type"/>
<fmt:message var="search" key="buttons.search"/>
<fmt:message var="clear" key="buttons.clear"/>
<fmt:message var="all_equipment" key="equipment.all_equipment"/>
<fmt:message var="all_items" key="equipment.all_equipment"/>
<fmt:message var="all_types" key="equipment.all_types"/>
<fmt:message var="equipment_price" key="equipment.price"/>
<fmt:message var="equipment_avg_research_time" key="equipment.avg_time"/>
<fmt:message var="not_found" key="common.not_found"/>
<fmt:message var="booking_details" key="equipment.booking_details"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="edit" key="button.edit"/>
<fmt:message var="equipment_state" key="equipment.state"/>
<fmt:message var="add_equipment" key="admin.add_equipment"/>
<fmt:message var="equipment_page" key="message.equipment_page"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>


<c:choose>
    <c:when test="${requestScope.selected_equipment_type != null}"><c:set var="current_type_id" value="${requestScope.selected_equipment_type.id}"/></c:when>
    <c:otherwise><c:set var="current_type_id" value='0'/></c:otherwise>
</c:choose>
<html>
<head>
    <title>Equipment page. Research center.</title>
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
                <p>${equipment_page}</p>
            </blockquote>
        </figure>
    </div>

    <div class="w-75 mx-auto">
        <div class="row">
            <div class="col-xs-8">
                <div style="margin-bottom: 10px">
                    <div class="d-flex justify-content-center">
                        <h4 class="h4">${all_items}</h4>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-2">
            <form action="${abs}/controller" method="get">
                <div>
                    <input type="hidden" name="command" value="find_equipment_by_type_command"/>
                    <input type="hidden" name="current_equipment_type_id"
                           value="${requestScope.selected_equipment_type.id}">
                    <div class="select-form">
                        <div class="d-flex justify-content-center">
                            <h4 class="h4">${equipment_type}</h4>
                        </div>
                        <div style="margin-top: 10px; margin-bottom: 10px">
                            <select id="equipment_type_id" name="equipment_type_id" class="form-control">
                                <c:if test="${requestScope.selected_equipment_type != null}">
                                    <option selected disabled>${requestScope.selected_equipment_type.name}</option>
                                </c:if>
                                <c:if test="${requestScope.selected_equipment_type == null}">
                                    <option selected disabled>${choose_type}</option>
                                </c:if>
                                <option value="0">${all_types}</option>
                                <c:forEach var="equipmentType" items="${requestScope.equipment_types}">
                                    <option value="${equipmentType.id}">${equipmentType.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div style="margin-top: 10px; margin-bottom: 10px">
                        <div class="d-flex justify-content-center">
                            <input type="submit" class="btn btn-primary" value="${search}"/>
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <c:if test="${sessionScope.user.role.name() eq 'ADMIN'}">
            <div class="col-xs-2">
                <a class="btn btn-primary"
                   href="${abs}/controller?command=go_add_new_equipment_page_command">${add_equipment}</a>
            </div>
        </c:if>
        <div style="margin-bottom: 10px; margin-top: 10px">
            <%@include file="fragment/equipment_table.jspf" %>
        </div>
        <nav aria-label="Page navigation example" style="margin-bottom: 30px">
            <ul class="pagination justify-content-center">
                <li class="page-item  <c:if test="${requestScope.pagination_page eq 1}">disabled</c:if>">
                    <a class="page-link" href="${abs}/controller?command=find_equipment_by_type_command&page=${requestScope.pagination_page - 1}&equipment_type_id=${current_type_id}">Previous</a>
                </li>
                <c:forEach begin="1" end="${requestScope.number_of_pages}" var="i">
                    <c:choose>
                        <c:when test="${requestScope.pagination_page eq i}">
                            <li class="page-item"><a class="page-link disabled" href="#">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="${abs}/controller?command=find_equipment_by_type_command&page=${i}&equipment_type_id=${current_type_id}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <li class="page-item <c:if test="${requestScope.pagination_page >= requestScope.number_of_pages}">disabled</c:if>">
                    <a class="page-link"  href="${abs}/controller?command=find_equipment_by_type_command&page=${requestScope.pagination_page + 1}&equipment_type_id=${current_type_id}">Next</a>
                </li>
            </ul>
        </nav>
    </div>
</div>


<ctg:print-footer/>
</body>
</html>
