<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>
<fmt:message var="equipment_type" key="equipment.equipment_type"/>
<fmt:message var="choose_type" key="equipment.choose_type"/>
<fmt:message var="search" key="buttons.search"/>
<fmt:message var="clear" key="buttons.clear"/>
<fmt:message var="all_equipment" key="equipment.all_equipment"/>
<fmt:message var="all_items" key="equipment.all_equipment"/>


<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Department details page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-8">
            <h2 class="section-headline">
                <span>${all_items}</span>
            </h2>
        </div>
        <div class="col-xs-4">
            <img src="static/image/logo1.jpg" class="img-responsive" alt="...">
        </div>
    </div>

    <div class="col-xs-2">
        <form action="${abs}/controller" method="post">
            <div>
                <input type="hidden" name="command" value="find_equipment_by_type_command"/>
                <div class="select-form">
                    <span>${equipment_type}</span>
                    <select id="equipment_type_id" name="equipment_type_id" class="form-control">
                        <c:if test="${requestScope.selected_equipment_type != null}">
                            <option selected disabled>${requestScope.selected_equipment_type.name}</option>
                        </c:if>
                        <c:if test="${requestScope.selected_equipment_type == null}">
                            <option selected disabled>${choose_type}</option>
                        </c:if>
                        <c:forEach var="equipmentType" items="${requestScope.equipment_types}">
                            <option value="${equipmentType.id}">${equipmentType.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <input type="submit" value="${search}"/>
                    <input type="button" value="${clear}" id="buttonClear"/>
                </div>
            </div>
        </form>
    </div>
    <jsp:include page="equipment_table.jsp"/>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('#buttonClear').click(function () {
            window.location = "/controller?command=go_to_equipment_page_command";
        })
    })
</script>
</body>
</html>
