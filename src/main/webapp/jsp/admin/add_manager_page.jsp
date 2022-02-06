<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="sign_up" key="header.sign_up"/>
<fmt:message var="add_manager_page" key="admin.add_manager_page"/>
<fmt:message var="add" key="buttons.add"/>
<fmt:message var="invalid_description" key="message.invalid_description"/>
<fmt:message var="laboratory" key="common.manager_details.laboratory"/>
<fmt:message var="doctor" key="manager.degree_doctor"/>
<fmt:message var="master" key="manager.degree_master"/>
<fmt:message var="bachelor" key="manager.degree_bachelor"/>
<fmt:message var="manager_degree" key="common.manager_details.degree"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="invalid_enum_message" key="message.invalid_enum_message"/>




<c:set var="abs">${pageContext.request.contextPath}</c:set>
<c:set var="reg_data" value="${requestScope.user_registration_data}"/>
<c:set var="description_param" value="description"/>

<html>
<head>
    <title>${add_manager_page} Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>
<div class="container">
    <br>
    <figure class="text-center">
        <blockquote class="blockquote">
            <p>${add_manager_page}</p>
        </blockquote>
    </figure>
    <div class="w-75 mx-auto">
        <form action="${abs}/controller" method="post" class="needs-validation" novalidate>
            <input type="hidden" name="command" value="add_manager_command">
            <br>
            <%@ include file="../common/fragment/register_form.jspf"%>
            <br>
            <div class="row mb-3">
                <div class="select-form">
                    <label for="laboratory_id">${laboratory}</label>
                    <select id="laboratory_id" name="laboratory_id" class="form-control">
                        <c:if test="${sessionScope.user.role.name() eq 'ADMIN'}">
                            <c:forEach var="laboratory" items="${requestScope.available_laboratories}">
                                <option value="${laboratory.id}">${laboratory.name}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
            </div>
            <div class="row mb-3">
                <label for="validationDescription" class="col-sm-2 col-form-label">${description}</label>
                <div class="col-sm-10">
                    <input type="text" name="description" class="form-control"
                           value="<c:if test="${!empty reg_data and reg_data.get(description_param) != 'invalid_description' }">${reg_data.get(description_param)}</c:if>"
                           id="validationDescription" required>
                    <c:if test="${requestScope.invalid_description}">
                        <div style="color: red">${invalid_description}</div>
                    </c:if>
                    <div class="valid-feedback">
                        ${correct}
                    </div>
                    <div class="invalid-feedback">
                        ${invalid_description}
                    </div>
                </div>
            </div>
            <div class="row mb-3">
                <div class="select-form">
                    <label for="manager_degree">${manager_degree}</label>
                    <select id="manager_degree" name="manager_degree" class="form-control">
                        <option value="Ph.D.">${doctor}</option>
                        <option value="M.Sc.">${master}</option>
                        <option value="B.Sc.">${bachelor}</option>
                    </select>
                    <c:if test="${requestScope.invalid_enum}">
                        <div class="alert alert-danger">${invalid_enum_message}</div>
                    </c:if>
                </div>
            </div>

            <button type="submit" class="btn btn-primary" <c:if test="${empty requestScope.available_laboratories}">disabled</c:if>>${add}</button>
        </form>
    </div>
</div>
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