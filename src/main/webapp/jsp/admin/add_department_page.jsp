<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="department_name" key="department.department_name"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="department_address" key="department.department_address"/>
<fmt:message var="invalid_description" key="message.invalid_description"/>
<fmt:message var="invalid_department_address" key="message.invalid_department_address"/>
<fmt:message var="invalid_department_name" key="message.invalid_department_name"/>
<fmt:message var="correct" key="message.correct"/>
<fmt:message var="add" key="buttons.add"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>
<c:set var="department_data" value="${requestScope.department_data}"/>
<c:set var="name_param" value="department_name"/>
<c:set var="description_param" value="description"/>
<c:set var="address_param" value="department_address"/>

<html>
<head>
    <title>Add department page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>

<form action="${abs}/controller" method="post" class="needs-validation" novalidate>
    <input type="hidden" name="command" value="add_department_command">
    <div class="row mb-3">
        <label for="validationDepartmentName" class="col-sm-2 col-form-label">${department_name}</label>
        <div class="col-sm-10">
            <input type="text" name="department_name" class="form-control"
                   value="<c:if test="${!empty department_data and department_data.get(name_param) != 'invalid_department_name' }">${department_data.get(name_param)}</c:if>"
                   id="validationDepartmentName"  required pattern="[A-Za-zА-Яа-я0-9]{2,200}">
            <c:if test="${requestScope.invalid_department_name}">
                <div class="alert alert-danger">${invalid_department_name}</div>
            </c:if>
            <div class="valid-feedback">
                ${correct}
            </div>
            <div class="invalid-feedback">
                ${invalid_department_name}
            </div>
        </div>
    </div>
    <div class="row mb-3">
        <label for="validationDepartAddress" class="col-sm-2 col-form-label">${department_address}</label>
        <div class="col-sm-10">
            <input type="text" name="department_address" class="form-control"
                   value="<c:if test="${!empty department_data and department_data.get(address_param) != 'invalid_department_address' }">${department_data.get(address_param)}</c:if>"
                   id="validationDepartAddress" required pattern="^.{2,255}$">
            <c:if test="${requestScope.invalid_last_name}">
                <div class="alert alert-danger">${invalid_department_address}</div>
            </c:if>
            <div class="valid-feedback">
                ${correct}
            </div>
            <div class="invalid-feedback">
                ${invalid_department_address}
            </div>
        </div>
    </div>
    <div class="row mb-3">
        <label for="validationDepartDescription" class="col-sm-2 col-form-label">${description}</label>
        <div class="col-sm-10">
            <input type="text" name="description" class="form-control"
                   value="<c:if test="${!empty department_data and department_data.get(description_param) != 'invalid_description' }">${department_data.get(description_param)}</c:if>"
                   id="validationDepartDescription" required>
            <c:if test="${requestScope.invalid_department_address}">
                <div class="alert alert-danger">${invalid_description}</div>
            </c:if>
            <div class="valid-feedback">
                ${correct}
            </div>
            <div class="invalid-feedback">
                ${invalid_description}
            </div>
        </div>
    </div>
    <button type="submit" class="btn btn-primary">${add}</button>
</form>

<script>
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function () {
        'use strict'

        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        let forms = document.querySelectorAll('.needs-validation')

        // Loop over them and prevent submission
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }

                    form.classList.add('was-validated')
                }, false)
            })
    })()
</script>
</body>
</html>
