<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale/language"/>

<fmt:message var="invalid_price" key="message.invalid_price"/>
<fmt:message var="invalid_description" key="message.invalid_description"/>
<fmt:message var="invalid_equipment_name" key="message.invalid_equipment_name"/>
<fmt:message var="invalid_research_time" key="message.invalid_research_time"/>
<fmt:message var="invalid_enum_message" key="message.invalid_enum_message"/>
<fmt:message var="lab_location" key="laboratory.lab_location"/>
<fmt:message var="laboratory" key="common.laboratory"/>
<fmt:message var="choose_laboratory" key="common.choose_laboratory"/>
<fmt:message var="choose_equipment_type" key="equipment.choose_type"/>
<fmt:message var="description" key="common.description"/>
<fmt:message var="correct" key="message.correct"/>
<fmt:message var="lab_name" key="laboratory.name"/>
<fmt:message var="equipment_name" key="equipment.name"/>
<fmt:message var="equipment_state" key="equipment.state"/>
<fmt:message var="equipment_type" key="equipment.equipment_type"/>
<fmt:message var="edit_equipment" key="button.edit"/>
<fmt:message var="upload_photo" key="buttons.upload_photo"/>
<fmt:message var="state_active" key="state.active"/>
<fmt:message var="state_inactive" key="state.inactive"/>
<fmt:message var="necessary_assistant" key="equipment.necessary_assistant"/>
<fmt:message var="not_found" key="common.not_found"/>
<fmt:message var="price_per_hour" key="equipment.price"/>
<fmt:message var="average_research_time" key="equipment.avg_time"/>
<fmt:message var="add" key="buttons.add"/>
<fmt:message var="upload" key="buttons.upload"/>
<fmt:message var="home" key="header.home"/>
<fmt:message var="update" key="buttons.update"/>
<fmt:message var="hour" key="equipment.hours"/>
<fmt:message var="minute" key="equipment.minute"/>
<fmt:message var="greetings" key="message.greetings"/>
<fmt:message var="equipment_edit_page" key="message.edit_equipment"/>
<fmt:message var="old_research_time" key="message.current_research_time"/>

<c:set var="abs">${pageContext.request.contextPath}</c:set>
<c:set var="equipment_data" value="${requestScope.equipment_data}"/>
<c:set var="name_param" value="equipment_name"/>
<c:set var="description_param" value="description"/>
<c:set var="price_param" value="price_per_hour"/>
<c:set var="average_time_param" value="average_research_time"/>

<html>
<head>
    <title>Edit equipment page. Research center.</title>
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
            <p>${equipment_edit_page} ${requestScope.selected_equipment.name}</p>
        </blockquote>
    </figure>
    <div class="row">
        <div class="col-sm-2 justify-content-center">
            <div class="btn-group-vertical">
                <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#editEquipment"
                        aria-expanded="false" aria-controls="editEquipment">
                    ${edit_equipment}
                </button>
                <br>
                <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#uploadPhoto"
                        aria-expanded="false" aria-controls="uploadPhoto">
                    ${upload_photo}
                </button>
            </div>
        </div>
        <div class="col-sm-10">
            <div class="collapse" id="editEquipment">
                <form method="post" action="${abs}/controller" class="needs-validation" novalidate>
                    <input type="hidden" name="command" value="update_equipment_command" >
                    <input type="hidden" name="equipment_id" value="${requestScope.selected_equipment.id}">
                    <input type="hidden" name="laboratory_id" value="${requestScope.selected_equipment.laboratoryId}">
                    <input type="hidden" name="equipment_type_id" value="${requestScope.selected_equipment.equipmentTypeId}">
                    <div class="row mb-3">
                        <label for="validationEquipmentName" class="col-sm-2 col-form-label">${equipment_name}</label>
                        <div class="col-sm-10">
                            <input type="text" name="equipment_name" class="form-control"
                                   value="<c:choose><c:when test="${!empty equipment_data and equipment_data.get(equipment_name) != 'invalid_equipment_name' }">${equipment_data.get(equipment_name)}</c:when><c:otherwise>${requestScope.selected_equipment.name}</c:otherwise></c:choose>"
                                   id="validationEquipmentName" required pattern="[A-Za-zА-Яа-я0-9]{2,255}">
                            <c:if test="${requestScope.invalid_equipment_name}">
                                <div style="color: red">${invalid_equipment_name}</div>
                            </c:if>
                            <div class="valid-feedback">
                                ${correct}
                            </div>
                            <div class="invalid-feedback">
                                ${invalid_equipment_name}
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="validationDescription" class="col-sm-2 col-form-label">${description}</label>
                        <div class="col-sm-10">
                            <input type="text" name="description" class="form-control" height="100"
                                   value="<c:choose><c:when test="${!empty equipment_data and equipment_data.get(description_param) != 'invalid_description' }">${equipment_data.get(equipment_name)}</c:when><c:otherwise>${requestScope.selected_equipment.description}</c:otherwise></c:choose>"
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
                        <label for="validationPrice" class="col-sm-2 col-form-label">${price_per_hour}</label>
                        <div class="col-sm-10">
                            <input type="text" name="price_per_hour" class="form-control"
                                   value="<c:choose><c:when test="${!empty equipment_data and equipment_data.get(price_param) != 'invalid_description' }">${equipment_data.get(price_param)}</c:when><c:otherwise>${requestScope.selected_equipment.pricePerHour}</c:otherwise></c:choose>"
                                   id="validationPrice" placeholder="1.00" required pattern="^\d{1,4}(\.\d{0,2})?$">
                            <c:if test="${requestScope.invalid_price}">
                                <div style="color: red">${invalid_price}</div>
                            </c:if>
                            <div class="valid-feedback">
                                ${correct}
                            </div>
                            <div class="invalid-feedback">
                                ${invalid_price}
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="hstack gap-1" id="fallbackTimePicker">
                            <div>
                            <span>
                                <label for="hour">${hour}</label>
                                <select id="hour" name="research_time_h">
                                </select>
                            </span>
                            </div>
                            <div>
                            <span>
                                <label for="minute">${minute}</label>
                                <select id="minute" name="research_time_m">
                                </select>
                            </span>
                            </div>
                            <div>
                                ${old_research_time} -> ${requestScope.selected_equipment.averageResearchTime}
                            </div>
                        </div>
                        <c:if test="${requestScope.invalid_research_time}">
                            <div style="color: red">${invalid_research_time}</div>
                        </c:if>
                    </div>
                    <div class="row mb-3">
                        <div class="select-form">
                            <label for="equipment_state">${equipment_state}</label>
                            <select id="equipment_state" name="equipment_state" class="form-control">
                                <option
                                        <c:if test="${requestScope.selected_equipment.state.name() eq 'ACTIVE'}">selected</c:if>
                                        value="ACTIVE">${state_active}</option>
                                <option
                                        <c:if test="${requestScope.selected_equipment.state.name() eq 'INACTIVE'}">selected</c:if>
                                        value="INACTIVE">${state_inactive}</option>
                            </select>
                            <c:if test="${requestScope.invalid_enum}">
                                <div class="alert alert-danger">${invalid_enum_message}</div>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckChecked"
                               name="is_need_assistant" value="true"
                               <c:if test="${requestScope.selected_equipment.needAssistant}">checked</c:if> >
                        <label class="form-check-label" for="flexSwitchCheckChecked">${necessary_assistant}</label>
                    </div>
                    <div class="col-sm-5">
                        <button type="submit" class="btn btn-primary">${update}</button>
                    </div>
                </form>
            </div>

            <div class="collapse" id="uploadPhoto">
                <form method="post" action="${abs}/controller" enctype="multipart/form-data" id="fileForm">
                    <input type="hidden" name="command" value="upload_equipment_photo_command">
                    <input type="hidden" name="equipment_id" value="${requestScope.selected_equipment.id}">
                    <div class="input-group">
                        <input type="file" class="form-control" id="inputGroupFile04" name="content"
                               aria-describedby="inputGroupFileAddon04" aria-label="${upload}"
                               onchange="fileValidation()">
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
        </div>
        <div>
            <a class="btn btn-primary" href="${abs}/controller?command=go_about_page_command" role="button">${home}</a>
        </div>
    </div>
</div>
<script type="text/javascript">
    // define variables
    const hourSelect = document.querySelector('#hour');
    const minuteSelect = document.querySelector('#minute');
    populateHours();
    populateMinutes();

    function populateHours() {
        for (let i = 0; i <= 10; i++) {
            const option = document.createElement('option');
            option.textContent = (i < 10) ? ("0" + i) : i;
            hourSelect.appendChild(option);
        }
    }

    function populateMinutes() {
        for (let i = 0; i <= 59; i++) {
            const option = document.createElement('option');
            option.textContent = (i < 10) ? ("0" + i) : i;
            minuteSelect.appendChild(option);
        }
    }

    // make it so that if the hour is 18, the minutes value is set to 00
    // — you can't select times past 18:00
    function setMinutesToZero() {
        if (hourSelect.value === '10') {
            minuteSelect.value = '00';
        }
    }

    hourSelect.onchange = setMinutesToZero;
    minuteSelect.onchange = setMinutesToZero;

</script>
<ctg:print-footer/>
</body>
</html>
