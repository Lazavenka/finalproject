<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="sign_up" key="header.sign_up"/>
<fmt:message var="registration_page" key="registration.registration_page"/>
<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>${registration_page} Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="../../css/styles.css">

</head>
<body>
<jsp:include page="../header/header.jsp"/>
<div class="container">
    <div style="margin-top: 30px; margin-bottom: 20px">
        <figure class="text-center">
            <blockquote class="blockquote">
                <p>${registration_page}</p>
            </blockquote>
        </figure>
    </div>
    <div class="w-75 mx-auto">
        <form action="${abs}/controller" method="post" class="needs-validation" novalidate>
            <input type="hidden" name="command" value="registration_command">
            <%@ include file="fragment/register_form.jspf" %>
            <div class="d-flex justify-content-center" style="margin-bottom: 20px; margin-top: 20px">
                <button type="submit" class="btn btn-primary">${sign_up}</button>
            </div>
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
<ctg:print-footer/>
</body>
</html>
