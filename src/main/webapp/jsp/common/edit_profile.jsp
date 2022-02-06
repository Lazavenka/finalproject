<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>
<fmt:message var="edit_priofile" key="user.edit_profile"/>
<fmt:message var="change_pass" key="user.change_pass"/>
<fmt:message var="upload_avatar" key="user.upload_avatar"/>
<fmt:message var="old_password" key="user.old_password"/>
<fmt:message var="pass_helper" key="user.pass_helper"/>
<fmt:message var="invalid_password" key="message.invalid_password"/>
<fmt:message var="incorrect_old_password" key="message.old_password_incorrect"/>
<fmt:message var="correct" key="message.correct"/>
<fmt:message var="new_password" key="user.new_password"/>
<fmt:message var="confirm_pass" key="registration.confirm_password"/>
<fmt:message var="password_mismatch" key="message.password_mismatch"/>
<fmt:message var="invalid_phone" key="message.invalid_phone"/>
<fmt:message var="invalid_description" key="message.invalid_description"/>
<fmt:message var="success_message" key="message.success_message"/>
<fmt:message var="upload" key="buttons.upload"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="edit" key="button.edit"/>
<fmt:message var="greetings" key="message.greetings"/>
<fmt:message var="profile_edit_page" key="message.profile_edit_page"/>
<fmt:message var="invalid_name" key="message.invalid_name"/>
<fmt:message var="first_name" key="registration.first_name"/>
<fmt:message var="last_name" key="registration.last_name"/>
<fmt:message var="phone" key="registration.phone"/>
<fmt:message var="edit_managers_data" key="buttons.edit_manager"/>


<c:set var="pass_data" value="${requestScope.change_password_data}"/>
<c:set var="profile_data" value="${requestScope.profile_data}"/>
<c:set var="f_name_param" value="first_name"/>
<c:set var="l_name_param" value="last_name"/>
<c:set var="phone_param" value="phone_number"/>
<c:set var="description_data" value="${requestScope.description}"/>
<c:set var="abs">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <title>Edit profile page. Research center.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>

<jsp:include page="../header/header.jsp"/>

<div class="container">
    <br>
    <figure class="text-center">
        <blockquote class="blockquote">
            <p>${greetings} ${sessionScope.user.lastName} ${sessionScope.user.firstName}</p>
            <p>${profile_edit_page}</p>
            <c:if test="${requestScope.success_message}"><p class="alert-success">${success_message}</p></c:if>
        </blockquote>
    </figure>
    <div class="row">
        <div class="col-sm-2 justify-content-center">
            <div class="btn-group-vertical">
                <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#editProfile"
                        aria-expanded="false" aria-controls="editProfile">
                    ${edit_priofile}
                </button>
                <br>
                <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#changePass"
                        aria-expanded="false" aria-controls="changePass">
                    ${change_pass}
                </button>
                <br>

                <c:if test="${sessionScope.user.role.name() eq 'MANAGER' or sessionScope.user.role.name() eq 'ASSISTANT'}">
                    <button class="btn btn-primary" type="button" data-bs-toggle="collapse"
                            data-bs-target="#uploadAvatarTab"
                            aria-expanded="false" aria-controls="uploadAvatarTab">
                            ${upload_avatar}
                    </button>
                </c:if>

                <c:if test="${sessionScope.user.role.name() eq 'MANAGER'}">
                    <button class="btn btn-primary" type="button" data-bs-toggle="collapse"
                            data-bs-target="#editManager"
                            aria-expanded="false" aria-controls="editManager">
                            ${edit_managers_data}
                    </button>
                </c:if>
            </div>
        </div>

        <div class="col-sm-10">
            <div class="collapse" id="editProfile">
                <form method="post" action="${abs}/controller" class="profileData"  onclick="validateProfileForm()">
                    <input type="hidden" name="command" value="update_user_profile_command" >
                    <div class="row mb-3">
                        <label for="firstName" class="col-sm-2 col-form-label">${first_name}</label>
                        <div class="col-sm-10">
                            <input type="text" name="first_name" class="form-control"
                                   value="<c:choose><c:when test="${!empty profile_data and profile_data.get(f_name_param) != 'invalid_name' }">${profile_data.get(f_name_param)}</c:when><c:otherwise>${sessionScope.user.firstName}</c:otherwise></c:choose>"
                                   id="firstName" required pattern="^[A-Za-zА-Яа-я]{2,20}">
                            <c:if test="${requestScope.invalid_first_name}">
                                <div style="color: red">${invalid_name}</div>
                            </c:if>
                            <div class="valid-feedback">
                                ${correct}
                            </div>
                            <div class="invalid-feedback">
                                ${invalid_name}
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="lastName" class="col-sm-2 col-form-label">${last_name}</label>
                        <div class="col-sm-10">
                            <input type="text" name="first_name" class="form-control"
                                   value="<c:choose><c:when test="${!empty profile_data and profile_data.get(l_name_param) != 'invalid_name' }">${profile_data.get(l_name_param)}</c:when><c:otherwise>${sessionScope.user.lastName}</c:otherwise></c:choose>" id="lastName" required pattern="^[A-Za-zА-Яа-я]{2,20}">
                            <c:if test="${requestScope.invalid_last_name}">
                                <div style="color: red">${invalid_name}</div>
                            </c:if>
                            <div class="valid-feedback">
                                ${correct}
                            </div>
                            <div class="invalid-feedback">
                                ${invalid_name}
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="phone" class="col-sm-2 col-form-label">${phone}</label>
                        <div class="col-sm-10">
                            <input type="text" name="phone_number" class="form-control"
                                   value="<c:choose><c:when test="${!empty profile_data and profile_data.get(phone_param) != 'invalid_phone' }">${profile_data.get(phone_param)}</c:when><c:otherwise>${sessionScope.user.phone}</c:otherwise></c:choose>"
                                   id="phone" required pattern="(25|29|33|44)\d{7}">
                            <c:if test="${requestScope.invalid_phone}">
                                <div style="color: red">${invalid_phone}</div>
                            </c:if>
                            <div class="valid-feedback">
                                ${correct}
                            </div>
                            <div class="invalid-feedback">
                                ${invalid_phone}
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">${edit}</button>
                </form>
            </div>
            <div class="collapse" id="changePass">
                <form method="post" action="${abs}/controller" id="changePasswordForm" onclick="validatePasswordForm()">
                    <input type="hidden" name="command" value="change_user_password_command">
                    <div class="row mb-3">
                        <label for="oldPass" class="col-sm-2 col-form-label">${old_password}</label>
                        <div class="col-sm-10">
                            <input type="password" name="old_password" class="form-control"
                                   id="oldPass" required
                                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!#%*?&_])[A-Za-z\d@$!#%*?&_]{8,20}$"
                                   aria-describedby="passwordHelpBlock">
                            <div id="passwordHelpBlock" class="form-text">${pass_helper}</div>
                            <c:if test="${requestScope.incorrect_old_password}">
                                <div style="color: red">${incorrect_old_password}</div>
                            </c:if>
                            <div class="valid-feedback">
                                ${correct}
                            </div>
                            <div class="invalid-feedback">
                                ${incorrect_old_password}
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="newPassword" class="col-sm-2 col-form-label">${new_password}</label>
                        <div class="col-sm-10">
                            <input type="password" name="new_password" class="form-control"
                                   id="newPassword" required
                                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!#%*?&_])[A-Za-z\d@$!#%*?&_]{8,20}$">
                            <c:if test="${requestScope.invalid_password}">
                                <div style="color: red">${invalid_password}</div>
                            </c:if>
                            <div class="valid-feedback">
                                ${correct}
                            </div>
                            <div class="invalid-feedback">
                                ${invalid_password}
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="confirmNewPass" class="col-sm-2 col-form-label">${confirm_pass}</label>
                        <div class="col-sm-10">
                            <input type="password" name="confirmed_password" class="form-control"
                                   id="confirmNewPass" required
                                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!#%*?&_])[A-Za-z\d@$!#%*?&_]{8,20}$">
                            <c:if test="${requestScope.passwords_mismatch}">
                                <div style="color: red">${password_mismatch}</div>
                            </c:if>
                            <div class="valid-feedback">
                                ${correct}
                            </div>
                            <div class="invalid-feedback">
                                ${invalid_password}
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">${change_pass}</button>
                </form>
            </div>
            <div class="collapse" id="uploadAvatarTab">
                <form method="post" action="${abs}/controller" enctype="multipart/form-data" id="fileForm">
                    <input type="hidden" name="command" value="upload_avatar_command">
                    <div class="input-group">
                        <input type="file" class="form-control" id="inputGroupFile04" name="content"
                               aria-describedby="inputGroupFileAddon04" aria-label="${upload}" onchange="fileValidation()">
                        <button class="btn btn-outline-secondary" type="submit"
                                id="inputGroupFileAddon04">${upload}</button>
                    </div>
                    <div id="imagePreview" class=""></div>
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
                                            + '"width="300" alt="Preview image"/>';
                                    };
                                    reader.readAsDataURL(fileInput.files[0]);
                                }
                            }
                        }
                    </script>
                </form>
            </div>
            <div class="collapse" id="editManager">
                <form method="post" action="${abs}/controller" id="descriptionForm" onclick="validateDescription()">
                    <input type="hidden" name="command" value="update_manager_description_command" >
                    <input type="hidden" name="user_id" value="${sessionScope.user.id}">
                    <div class="row mb-3">
                        <label for="description" class="col-sm-2 col-form-label">${description}</label>
                        <div class="col-sm-10">
                            <input type="text" name="description" class="form-control"
                                   value="<c:if test="${!empty description_data}">${description_data}</c:if>"
                                   id="description" required pattern="^[A-Za-zА-Яа-я]{2,20}">
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
                    <button type="submit" class="btn btn-primary">${edit}</button>
                </form>
            </div>
        </div>
    </div>
</div>


<script>
    function validateData (form){
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)

    }
    function validatePasswordForm(){
        const form = document.querySelector("#changePasswordForm")
        validateData(form)
    }
    function validateProfileForm(){
        const form = document.querySelector("#profileData")
        validateData(form)
    }
    function validateDescription(){
        const form = document.querySelector("#descriptionForm")
        validateData(form)
    }

</script>
</body>
</html>
