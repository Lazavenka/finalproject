<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="invalid_balance" key="client.invalid_balance_message"/>
<fmt:message var="fill_balance" key="client.fill_balance"/>
<fmt:message var="add_balance_message" key="client.balance_message"/>
<fmt:message var="correct" key="message.correct"/>
<html>
<head>
    <title>Balance page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

</head>
<body>

<jsp:include page="../header/header.jsp"/>
<div class="container">
    <br>
    <figure class="text-center">
        <blockquote class="blockquote">
            <p>${add_balance_message}</p>
        </blockquote>
    </figure>
    <br>
    <div class="w-75 mx-auto">
        <form action="${abs}/controller" method="post" class="needs-validation" novalidate>
            <input type="hidden" name="command" value="add_balance_command">
            <input type="text" name="balance" class="form-control" id="validationBalance"
                   aria-describedby="button-addon2" required pattern="^\d{1,3}(\.\d{0,2})?$">

            <c:if test="${requestScope.invalid_balance}">
                <div style="color: red">${invalid_balance}</div>
            </c:if>
            <div class="valid-feedback">
                ${correct}
            </div>
            <div class="invalid-feedback">
                ${invalid_balance}
            </div>
            <button type="submit" class="btn btn-primary" id="button-addon2">${fill_balance}</button>
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
    })();
</script>
<ctg:print-footer/>
</body>
</html>
