<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>
<fmt:message var="login" key="registration.login"/>
<fmt:message var="password" key="registration.password"/>
<fmt:message var="confirm_pass" key="registration.confirm_password"/>
<fmt:message var="first_name" key="registration.first_name"/>
<fmt:message var="last_name" key="registration.last_name"/>
<fmt:message var="phone" key="registration.phone"/>
<fmt:message var="email" key="registration.email"/>
<fmt:message var="login" key="registration.login"/>

<fmt:message var="not_unique_email" key="message.not_unique_email"/>
<fmt:message var="not_unique_login" key="message.not_unique_login"/>
<fmt:message var="invalid_login" key="message.invalid_login"/>
<fmt:message var="invalid_password" key="message.invalid_password"/>
<fmt:message var="password_mismatch" key="message.password_mismatch"/>
<fmt:message var="invalid_name" key="message.invalid_name"/>
<fmt:message var="invalid_phone" key="message.invalid_phone"/>
<fmt:message var="invalid_email" key="message.invalid_email"/>
<fmt:message var="correct" key="message.correct"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>
<html>
<head>
    <title>Register page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../header/header.jsp"/>

<form action="${abs}/controller" method="post" class="row g-3 needs-validation" novalidate>
    <input type="hidden" name="command" value="registration_command">
    <div class="col-md-4">
        <label for="validationCustom01" class="form-label">${first_name}</label>
        <input type="text" name="first_name" class="form-control" id="validationCustom01"  required pattern="^[A-Za-zА-Яа-я]{2,20}">
        <c:if test="${requestScope.invalid_first_name}">
            <div class="invalid-feedback-backend">${invalid_name}</div>
        </c:if>
        <div class="valid-feedback">
            ${correct}
        </div>
        <div class="invalid-feedback">
            ${invalid_name}
        </div>
    </div>
    <div class="col-md-4">
        <label for="validationCustom02" class="form-label">${last_name}</label>
        <input type="text" name="last_name" class="form-control" id="validationCustom02" required pattern="[A-Za-zА-Яа-я]{2,20}">
        <c:if test="${requestScope.invalid_last_name}">
            <div class="invalid-feedback-backend">${invalid_name}</div>
        </c:if>
        <div class="valid-feedback">
            ${correct}
        </div>
        <div class="invalid-feedback">
            ${invalid_name}
        </div>
    </div>
    <div class="col-md-4">
        <label for="validationCustomUsername" class="form-label">${login}</label>
        <div class="input-group has-validation">
            <input type="text" name="login" class="form-control" id="validationCustomUsername" aria-describedby="inputGroupPrepend" required pattern="^[A-Za-zА-Яа-я0-9_.]{4,20}$">
            <c:choose>
                <c:when test="${requestScope.login_exists}">
                    <div class="invalid-feedback-backend">${not_unique_login}</div>
                </c:when>
                <c:when test="${requestScope.invalid_login}">
                    <div class="invalid-feedback-backend">${invalid_login}</div>
                </c:when>
            </c:choose>
            <div class="valid-feedback">
                ${correct}
            </div>
            <div class="invalid-feedback">
                ${invalid_login}
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <label for="validationCustom03" class="form-label">${password}</label>
        <input type="password" name="password" class="form-control" id="validationCustom03" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!#%*?&_])[A-Za-z\d@$!#%*?&_]{8,20}$">
        <c:if test="${requestScope.invalid_password}">
            <div class="invalid-feedback-backend">${invalid_password}</div>
        </c:if>
        <div class="valid-feedback">
            ${correct}
        </div>
        <div class="invalid-feedback">
            ${invalid_password}
        </div>
    </div>
    <div class="col-md-4">
        <label for="validationCustom04" class="form-label">${confirm_pass}</label>
        <input type="password" name="confirmed_password" class="form-control" id="validationCustom04" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!#%*?&_])[A-Za-z\d@$!#%*?&_]{8,20}$">
        <c:if test="${requestScope.passwords_mismatch}">
            <div class="invalid-feedback-backend">${password_mismatch}</div>
        </c:if>
        <div class="valid-feedback">
            ${correct}
        </div>
        <div class="invalid-feedback">
            ${invalid_password}
        </div>
    </div>
    <div class="col-md-4">
        <label for="validationCustom05" class="form-label">${email}</label>
        <div class="input-group has-validation">
            <input type="text" name="email" class="form-control" id="validationCustom05" aria-describedby="inputGroupPrepend" required pattern="^[\w-.]+@([\w-]+\.)+[\w-]{2,6}$" maxlength="55">
            <c:if test="${requestScope.invalid_email}">
                <div class="invalid-feedback-backend">${invalid_email}</div>
            </c:if>
            <div class="valid-feedback">
                ${correct}
            </div>
            <div class="invalid-feedback">
                ${invalid_email}
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <label for="validationCustom06" class="form-label">${phone}</label>
        <div class="input-group has-validation">
            <input type="text" name="phone_number" class="form-control" id="validationCustom06" aria-describedby="inputGroupPrepend" required pattern="(25|29|33|44)\d{7}">
            <c:if test="${requestScope.invalid_phone}">
                <div class="invalid-feedback-backend">${invalid_phone}</div>
            </c:if>
            <div class="valid-feedback">
                ${correct}
            </div>
            <div class="invalid-feedback">
                ${invalid_phone}
            </div>
        </div>
    </div>
    <div class="col-12">
        <button class="btn btn-primary" type="submit">Submit form</button>
    </div>
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
