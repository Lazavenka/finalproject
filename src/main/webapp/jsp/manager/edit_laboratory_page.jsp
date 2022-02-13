<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="invalid_description" key="message.invalid_description"/>
<fmt:message var="invalid_laboratory_name" key="message.invalid_lab_name"/>
<fmt:message var="invalid_laboratory_location" key="message.invalid_lab_location"/>
<fmt:message var="laboratory_edit_page" key="message.laboratory_edit_page"/>
<fmt:message var="lab_location" key="laboratory.lab_location"/>
<fmt:message var="laboratory" key="common.laboratory"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="correct" key="message.correct"/>
<fmt:message var="laboratory_name" key="laboratory.name"/>
<fmt:message var="edit_laboratory" key="button.edit"/>
<fmt:message var="upload_photo" key="buttons.upload_photo"/>
<fmt:message var="not_found" key="common.not_found"/>
<fmt:message var="upload" key="buttons.upload"/>
<fmt:message var="home" key="header.home"/>
<fmt:message var="update" key="buttons.update"/>
<fmt:message var="greetings" key="message.greetings"/>

<fmt:message var="empty_image_message" key="message.empty_image_message"/>
<fmt:message var="invalid_file_size" key="message.invalid_file_size"/>
<fmt:message var="wrong_file_extension" key="message.wrong_file_extension"/>
<fmt:message var="success_message" key="message.success_message"/>
<fmt:message var="error_message" key="message.error_message"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>

<c:set var="laboratory_data" value="${requestScope.laboratory_data}"/>
<c:set var="name_param" value="laboratory_name"/>
<c:set var="description_param" value="description"/>
<c:set var="location_param" value="lab_location"/>


<html>
<head>
    <title>Edit laboratory page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="../../css/styles.css">

</head>
<body>
<jsp:include page="../header/header.jsp"/>
<div class="container" style="margin-top: 20px; margin-bottom: 20px">
    <div style="margin-bottom: 20px">
        <figure class="text-center">
            <blockquote class="blockquote">
                <p>${greetings} ${sessionScope.user.lastName} ${sessionScope.user.firstName}</p>
                <p>${laboratory_edit_page} ${requestScope.selected_laboratory.name}</p>
                <c:choose>
                    <c:when test="${requestScope.success_message}"><p
                            class="alert-success">${success_message}</p></c:when>
                    <c:when test="${requestScope.error_message}"><p class="alert-warning">${error_message}</p></c:when>
                </c:choose>
            </blockquote>
        </figure>
    </div>
    <div class="w-75 mx-auto">
        <c:choose>
            <c:when test="${requestScope.laboratory_not_found}">
                <div class="alert alert-danger">${not_found}</div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="col-sm-2 justify-content-center">
                        <div class="btn-group-vertical">
                            <div style="margin-bottom: 10px">
                                <button class="btn btn-primary" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#editLaboratory"
                                        aria-expanded="false" aria-controls="editLaboratory">
                                        ${edit_laboratory}
                                </button>
                            </div>
                            <br>
                            <button class="btn btn-primary" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#uploadPhoto"
                                    aria-expanded="false" aria-controls="uploadPhoto">
                                    ${upload_photo}
                            </button>
                        </div>
                    </div>
                    <div class="col-sm-10">
                        <div class="collapse" id="editLaboratory">
                            <form method="post" action="${abs}/controller" class="needs-validation" novalidate>
                                <input type="hidden" name="command" value="update_laboratory_command">
                                <input type="hidden" name="laboratory_id"
                                       value="${requestScope.selected_laboratory.id}">
                                <input type="hidden" name="department_id"
                                       value="${requestScope.selected_laboratory.departmentId}">
                                <div class="row mb-3">
                                    <label for="validationLaboratoryName"
                                           class="col-sm-2 col-form-label">${laboratory_name}</label>
                                    <div class="col-sm-10">
                                        <input type="text" name="laboratory_name" class="form-control"
                                               value="<c:choose><c:when test="${!empty laboratory_data and laboratory_data.get(name_param) != 'invalid_laboratory_name' }">${laboratory_data.get(name_param)}</c:when><c:otherwise>${requestScope.selected_laboratory.name}</c:otherwise></c:choose>"
                                               id="validationLaboratoryName" required
                                               pattern="[A-Za-zА-Яа-я0-9]{2,255}">
                                        <c:if test="${requestScope.invalid_laboratory_name}">
                                            <div style="color: red">${invalid_laboratory_name}</div>
                                        </c:if>
                                        <div class="valid-feedback">
                                                ${correct}
                                        </div>
                                        <div class="invalid-feedback">
                                                ${invalid_laboratory_name}
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <label for="validationDescription"
                                           class="col-sm-2 col-form-label">${description}</label>
                                    <div class="col-sm-10">
                                        <input type="text" name="description" class="form-control"
                                               value="<c:choose><c:when test="${!empty laboratory_data and laboratory_data.get(description_param) != 'invalid_description' }">${laboratory_data.get(description_param)}</c:when><c:otherwise>${requestScope.selected_laboratory.description}</c:otherwise></c:choose>"
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
                                    <label for="validationLocation"
                                           class="col-sm-2 col-form-label">${lab_location}</label>
                                    <div class="col-sm-10">
                                        <input type="text" name="lab_location" class="form-control"
                                               value="<c:choose><c:when test="${!empty laboratory_data and laboratory_data.get(location_param) != 'invalid_location' }">${laboratory_data.get(location_param)}</c:when><c:otherwise>${requestScope.selected_laboratory.location}</c:otherwise></c:choose>"
                                               id="validationLocation" required>
                                        <c:if test="${requestScope.invalid_location}">
                                            <div style="color: red">${invalid_laboratory_location}</div>
                                        </c:if>
                                        <div class="valid-feedback">
                                                ${correct}
                                        </div>
                                        <div class="invalid-feedback">
                                                ${invalid_laboratory_location}
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-5 justify-content-center">
                                    <button type="submit" class="btn btn-primary"
                                            style="margin-top: 10px">${update}</button>

                                </div>
                            </form>
                        </div>

                        <div class="collapse" id="uploadPhoto">
                            <form method="post" action="${abs}/controller" enctype="multipart/form-data" id="fileForm">
                                <input type="hidden" name="command" value="update_laboratory_photo_command">
                                <input type="hidden" name="laboratory_id"
                                       value="${requestScope.selected_laboratory.id}">
                                <div class="input-group">
                                    <input type="file" class="form-control" id="inputGroupFile04" name="content"
                                           aria-describedby="inputGroupFileAddon04" aria-label="${upload}"
                                           onchange="fileValidation()">
                                    <button class="btn btn-outline-secondary" type="submit"
                                            id="inputGroupFileAddon04">${upload}</button>
                                </div>
                                <c:choose>
                                    <c:when test="${requestScope.empty_image}">
                                        <div style="color: red">${empty_image_message}</div>
                                    </c:when>
                                    <c:when test="${requestScope.invalid_file_size}">
                                        <div style="color: red">${invalid_file_size}</div>
                                    </c:when>
                                    <c:when test="${requestScope.wrong_file_extension}">
                                        <div style="color: red">${wrong_file_extension}</div>
                                    </c:when>
                                </c:choose>
                                <div id="imagePreview" style="margin-top: 10px; position: center"></div>
                                <script>
                                    function fileValidation() {
                                        const fileInput = document.getElementById('inputGroupFile04');

                                        const filePath = fileInput.value;

                                        // Allowing file type
                                        const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.bmp)$/i;
                                        const fileSize = fileInput.files.item(0).size;
                                        if (!allowedExtensions.exec(filePath) || fileSize > (1024 * 1024)) {
                                            alert('Invalid file type or file size. Maximum size 1 Mb');
                                            fileInput.value = '';
                                            return false;
                                        } else {
                                            // Image preview
                                            if (fileInput.files && fileInput.files[0]) {
                                                const reader = new FileReader();
                                                reader.onload = function (e) {
                                                    document.getElementById(
                                                        'imagePreview').innerHTML =
                                                        '<img src="' + e.target.result
                                                        + '" width="300" class="rounded-start" alt="Preview image"/>';
                                                };
                                                reader.readAsDataURL(fileInput.files[0]);
                                            }
                                        }
                                    }
                                </script>
                            </form>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<ctg:print-footer/>
</body>
</html>