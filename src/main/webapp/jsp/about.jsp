<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="about" key="header.about"/>
<fmt:message var="research_center_message" key="about.message.research_center_message"/>
<fmt:message var="departments" key="about.message.departments_count"/>
<fmt:message var="quality" key="about.message.quality"/>
<fmt:message var="laboratories" key="about.message.laboratories_count"/>
<fmt:message var="equipment" key="about.message.equipment_count"/>
<fmt:message var="personal" key="about.message.personal"/>
<fmt:message var="doctors" key="about.message.doctors_count"/>
<fmt:message var="masters" key="about.message.masters_count"/>
<fmt:message var="bachelors" key="about.message.bachelor_count"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>${about}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

</head>
<body>
    <jsp:include page="header/header.jsp"/>
    <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1" aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2" aria-label="Slide 3"></button>
        </div>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="${abs}/static/images/about/researchCenter.jpg" class="d-block w-100" alt="Research Center">
                <div class="carousel-caption d-none d-md-block">
                    <h5 class="bg-light text-dark">${research_center_message}</h5>
                    <p class="bg-light text-dark">${departments} - ${requestScope.departments_count}</p>
                </div>
            </div>
            <div class="carousel-item">
                <img src="${abs}/static/images/about/laboratory.jpg" class="d-block w-100" alt="Laboratories">
                <div class="carousel-caption d-none d-md-block">
                    <h5 class="bg-light text-dark">${quality}</h5>
                    <p class="bg-light text-dark">${laboratories} - ${requestScope.laboratories_count}</p>
                    <p class="bg-light text-dark">${equipment} - ${requestScope.equipment_count}</p>
                </div>
            </div>
            <div class="carousel-item">
                <img src="${abs}/static/images/about/managers.jpg" class="d-block w-100" alt="Managers">
                <div class="carousel-caption d-none d-md-block">
                    <h5 class="bg-light text-dark">${personal}</h5>
                    <p class="bg-light text-dark">${doctors} - ${requestScope.doctors_count}</p>
                    <p class="bg-light text-dark">${masters} - ${requestScope.masters_count}</p>
                    <p class="bg-light text-dark">${bachelors} - ${requestScope.bachelor_count}</p>
                </div>
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>

</body>
</html>
