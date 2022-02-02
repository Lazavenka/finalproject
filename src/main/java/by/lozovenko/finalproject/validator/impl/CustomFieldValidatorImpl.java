package by.lozovenko.finalproject.validator.impl;

import by.lozovenko.finalproject.model.entity.EquipmentState;
import by.lozovenko.finalproject.validator.CustomFieldValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomFieldValidatorImpl implements CustomFieldValidator {
    private static CustomFieldValidator instance;

    private static final String USER_LOGIN_PATTERN = "^[A-Za-zА-Яа-я0-9_.]{4,20}$";
    private static final String USER_PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!#%*?&_])[A-Za-z\\d@$!#%*?&_]{8,20}$";
    private static final String USER_NAME_PATTERN = "[A-Za-zА-Яа-я]{2,20}";
    private static final String EMAIL_PATTERN = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,6}$";
    private static final String EMAIL_SYMBOL_PATTERN = ".{8,55}";
    private static final String PHONE_PATTERN = "(25|29|33|44)\\d{7}";
    private static final String ID_PATTERN = "^\\d{1,18}$";
    private static final String BALANCE_PATTERN = "^\\d{1,3}\\.?\\d{0,2}$";
    private static final String MANAGER_DEGREE_PATTERN = "B\\.Sc\\.|M\\.Sc\\.|Ph\\.D\\.";
    private static final String EQUIPMENT_PRICE_PER_HOUR_PATTERN = "^\\d{1,4}\\.?\\d{0,2}$";
    private static final int LABORATORY_LOCATION_STRING_LENGTH = 255;
    private static final int LABORATORY_NAME_STRING_LENGTH = 255;
    private static final int DEPARTMENT_NAME_STRING_LENGTH = 200;
    private static final int DEPARTMENT_ADDRESS_STRING_LENGTH = 255;
    private static final int EQUIPMENT_TYPE_NAME_STRING_LENGTH = 200;
    private static final int EQUIPMENT_NAME_STRING_LENGTH = 255;
    private static final int COMMON_DESCRIPTION_LENGTH = 65535;
    private static final String EQUIPMENT_RESEARCH_TIME_PATTERN = "^(([0-1]?[0-9])|(2[0-3])):[0-5]?[0-9]:[0-5]?[0-9]$";

    private CustomFieldValidatorImpl() {
    }

    public static CustomFieldValidator getInstance() {
        if (instance == null) {
            instance = new CustomFieldValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean isCorrectLogin(String login) {
        return notNullOrEmpty(login) && login.matches(USER_LOGIN_PATTERN);
    }

    @Override
    public boolean isCorrectPassword(String password) {
        return notNullOrEmpty(password) && password.matches(USER_PASSWORD_PATTERN);
    }

    @Override
    public boolean isCorrectName(String name) {
        return notNullOrEmpty(name) && name.matches(USER_NAME_PATTERN);
    }

    @Override
    public boolean isMatchesPasswords(String password, String confirmedPassword) {
        return notNullOrEmpty(password) && notNullOrEmpty(confirmedPassword) && password.equals(confirmedPassword);
    }

    @Override
    public boolean isCorrectEmail(String email) {
        return notNullOrEmpty(email) && email.matches(EMAIL_PATTERN) && email.matches(EMAIL_SYMBOL_PATTERN);
    }

    @Override
    public boolean isCorrectPhone(String phone) {
        return notNullOrEmpty(phone) && phone.matches(PHONE_PATTERN);
    }

    @Override
    public boolean isCorrectBalance(String balance) {
        return notNullOrEmpty(balance) && balance.matches(BALANCE_PATTERN);
    }

    @Override
    public boolean isCorrectManagerDescription(String description) {
        return notNullOrEmpty(description) && description.length() < COMMON_DESCRIPTION_LENGTH;
    }

    @Override
    public boolean isCorrectManagerDegree(String degree) {
        return notNullOrEmpty(degree) && degree.matches(MANAGER_DEGREE_PATTERN);
    }

    @Override
    public boolean isCorrectId(String id) {
        Logger logger = LogManager.getLogger();

        boolean result = notNullOrEmpty(id) && id.matches(ID_PATTERN);

        logger.log(Level.DEBUG, "isCorrectId method string id = {} result = {}", id, result);
        return result;
    }

    @Override
    public boolean isCorrectDepartmentName(String departmentName) {
        return notNullOrEmpty(departmentName) && departmentName.length() < DEPARTMENT_NAME_STRING_LENGTH;
    }

    @Override
    public boolean isCorrectDepartmentDescription(String departmentDescription) {
        return notNullOrEmpty(departmentDescription) && departmentDescription.length() < COMMON_DESCRIPTION_LENGTH;
    }

    @Override
    public boolean isCorrectDepartmentAddress(String departmentAddress) {
        return notNullOrEmpty(departmentAddress) && departmentAddress.length() < DEPARTMENT_ADDRESS_STRING_LENGTH;
    }

    @Override
    public boolean isCorrectEquipmentTypeId(String equipmentTypeId) {
        return notNullOrEmpty(equipmentTypeId) && equipmentTypeId.matches(ID_PATTERN);
    }

    @Override
    public boolean isCorrectEquipmentTypeName(String equipmentTypeName) {
        return notNullOrEmpty(equipmentTypeName) && equipmentTypeName.length() < EQUIPMENT_TYPE_NAME_STRING_LENGTH;
    }

    @Override
    public boolean isCorrectEquipmentTypeDescription(String equipmentTypeDescription) {
        return notNullOrEmpty(equipmentTypeDescription) && equipmentTypeDescription.length() < COMMON_DESCRIPTION_LENGTH;
    }

    @Override
    public boolean isCorrectEquipmentName(String equipmentName) {
        return notNullOrEmpty(equipmentName) && equipmentName.length() < EQUIPMENT_NAME_STRING_LENGTH;
    }

    @Override
    public boolean isCorrectEquipmentDescription(String equipmentDescription) {
        return notNullOrEmpty(equipmentDescription) && equipmentDescription.length() < COMMON_DESCRIPTION_LENGTH;
    }

    @Override
    public boolean isCorrectEquipmentPricePerHour(String equipmentPrice) {
        return notNullOrEmpty(equipmentPrice) && equipmentPrice.matches(EQUIPMENT_PRICE_PER_HOUR_PATTERN);
    }

    @Override
    public boolean isCorrectEquipmentResearchTime(String researchTime) {
        return notNullOrEmpty(researchTime) && researchTime.matches(EQUIPMENT_RESEARCH_TIME_PATTERN);
    }

    @Override
    public boolean isCorrectEquipmentState(String equipmentState) {
        return notNullOrEmpty(equipmentState) && equipmentState.equals(EquipmentState.ACTIVE.name()) || equipmentState.equals(EquipmentState.INACTIVE.name());
    }

    @Override
    public boolean isCorrectLaboratoryName(String laboratoryName) {
        return notNullOrEmpty(laboratoryName) && laboratoryName.length() < LABORATORY_NAME_STRING_LENGTH;
    }

    @Override
    public boolean isCorrectLaboratoryDescription(String laboratoryDescription) {
        return notNullOrEmpty(laboratoryDescription) && laboratoryDescription.length() < COMMON_DESCRIPTION_LENGTH;
    }

    @Override
    public boolean isCorrectLaboratoryLocation(String laboratoryLocation) {
        return notNullOrEmpty(laboratoryLocation) && laboratoryLocation.length() < LABORATORY_LOCATION_STRING_LENGTH;
    }


    public boolean notNullOrEmpty(String stringLine) {
        return stringLine != null && !stringLine.isEmpty();
    }

}
