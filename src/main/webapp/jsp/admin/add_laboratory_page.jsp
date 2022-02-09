<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="invalid_lab_name" key="message.invalid_lab_name"/>
<fmt:message var="invalid_lab_location" key="message.invalid_lab_location"/>
<fmt:message var="invalid_description" key="message.invalid_description"/>
<fmt:message var="lab_location" key="laboratory.lab_location"/>
<fmt:message var="department" key="common.manager_details.department"/>
<fmt:message var="choose_department" key="common.choose_department"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="correct" key="message.correct"/>
<fmt:message var="lab_name" key="laboratory.name"/>
<fmt:message var="not_found" key="common.not_found"/>
<fmt:message var="add" key="buttons.add"/>
<fmt:message var="home" key="header.home"/>
<fmt:message var="add_laboratory_page" key="admin.add_laboratory_page"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>
<c:set var="laboratory_data" value="${requestScope.laboratory_data}"/>
<c:set var="name_param" value="laboratory_name"/>
<c:set var="description_param" value="description"/>
<c:set var="location_param" value="lab_location"/>

<html>
<head>
    <title>Add new laboratory page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="../../css/styles.css">

</head>
<body>
<jsp:include page="../header/header.jsp"/>
<div class="container">
    <div class="spaced">
    <figure class="text-center">
        <blockquote class="blockquote">
            <p>${add_laboratory_page}</p>
        </blockquote>
    </figure>
    </div>
    <div class="w-75 mx-auto">
        <div class="container-fluid">
            <form action="${abs}/controller" method="post" class="needs-validation" novalidate>
                <input type="hidden" name="command" value="add_new_laboratory_command"/>
                <div class="row mb-3">
                    <div class="col-xs-2">
                        <div class="select-form">
                            <span>${department}</span>
                            <select id="department_id" name="department_id" class="form-control">
                                <c:if test="${requestScope.selected_department != null}">
                                    <option selected disabled>${requestScope.selected_department.name}</option>
                                </c:if>
                                <c:if test="${requestScope.selected_department == null}">
                                    <option selected disabled>${choose_department}</option>
                                </c:if>
                                <c:forEach var="department" items="${requestScope.departments}">
                                    <option value="${department.id}">${department.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row mb-3">
                    <label for="validationLabName" class="col-sm-2 col-form-label">${lab_name}</label>
                    <div class="col-sm-10">
                        <input type="text" name="laboratory_name" class="form-control"
                               value="<c:if test="${!empty laboratory_data and laboratory_data.get(name_param) != 'invalid_laboratory_name' }">${laboratory_data.get(name_param)}</c:if>"
                               id="validationLabName" required pattern="[A-Za-zА-Яа-я0-9 ]{2,255}">
                        <c:if test="${requestScope.invalid_laboratory_name}">
                            <div style="color: red">${invalid_lab_name}</div>
                        </c:if>
                        <div class="valid-feedback">
                            ${correct}
                        </div>
                        <div class="invalid-feedback">
                            ${invalid_lab_name}
                        </div>
                    </div>
                </div>
                <div class="row mb-3">
                    <label for="validationLabLocation" class="col-sm-2 col-form-label">${lab_location}</label>
                    <div class="col-sm-10">
                        <input type="text" name="lab_location" class="form-control"
                               value="<c:if test="${!empty laboratory_data and laboratory_data.get(location_param) != 'invalid_location' }">${laboratory_data.get(location_param)}</c:if>"
                               id="validationLabLocation" required pattern="[A-Za-zА-Яа-я ]{2,255}">
                        <c:if test="${requestScope.invalid_location}">
                            <div style="color: red">${invalid_lab_location}</div>
                        </c:if>
                        <div class="valid-feedback">
                            ${correct}
                        </div>
                        <div class="invalid-feedback">
                            ${invalid_lab_location}
                        </div>
                    </div>
                </div>
                <div class="row mb-3">
                    <label for="validationDescription" class="col-sm-2 col-form-label">${description}</label>
                    <div class="col-sm-10">
                        <input type="text" name="description" class="form-control"
                               value="<c:if test="${!empty laboratory_data and laboratory_data.get(description_param) != 'invalid_description'}">${laboratory_data.get(description_param)}</c:if>"
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
                <div class="spaced">
                    <input type="submit" class="btn btn-primary" value="${add}"/>
                </div>
            </form>
        </div>
        <div class="spaced">
            <a class="btn btn-primary" href="${abs}/controller?command=go_about_page_command" role="button">${home}</a>
        </div>
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
<ctg:print-footer/>
</body>
</html>

