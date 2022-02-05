package by.lozovenko.finalproject.validator;

public interface CustomFieldValidator {
    boolean isCorrectLogin(String login);

    boolean isCorrectPassword(String password);

    boolean isMatchesPasswords(String password, String confirmedPassword);

    boolean isCorrectName(String name);

    boolean isCorrectEmail(String email);

    boolean isCorrectPhone(String phone);

    boolean isCorrectBalance(String balance);

    boolean isCorrectManagerDescription(String description);

    boolean isCorrectManagerDegree(String degree);

    boolean isCorrectId(String id);

    boolean isCorrectDepartmentName(String departmentName);

    boolean isCorrectDepartmentDescription(String departmentDescription);

    boolean isCorrectDepartmentAddress(String departmentAddress);

    boolean isCorrectEquipmentTypeId(String equipmentTypeId);

    boolean isCorrectEquipmentTypeName(String equipmentTypeName);

    boolean isCorrectEquipmentTypeDescription(String equipmentTypeDescription);

    boolean isCorrectEquipmentName(String equipmentName);

    boolean isCorrectEquipmentDescription(String equipmentDescription);

    boolean isCorrectEquipmentPricePerHour(String equipmentPrice);

    boolean isCorrectEquipmentResearchTime(String researchTime);

    boolean isCorrectEquipmentState(String equipmentState);

    boolean isCorrectLaboratoryName(String laboratoryName);

    boolean isCorrectLaboratoryDescription(String laboratoryDescription);

    boolean isCorrectLaboratoryLocation(String laboratoryLocation);

}
