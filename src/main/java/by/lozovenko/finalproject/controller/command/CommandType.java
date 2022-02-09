package by.lozovenko.finalproject.controller.command;

import by.lozovenko.finalproject.controller.command.impl.*;
import by.lozovenko.finalproject.model.entity.UserRole;


import java.util.EnumSet;
import java.util.Set;

import static by.lozovenko.finalproject.model.entity.UserRole.*;

public enum CommandType {
    CHANGE_LOCALE_COMMAND(new ChangeLocaleCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT, GUEST)),

    LOGIN_COMMAND(new LoginCommand(), EnumSet.of(GUEST)),
    GO_REGISTER_PAGE_COMMAND(new GoRegisterPageCommand(), EnumSet.of(GUEST)),
    REGISTRATION_COMMAND(new RegistrationCommand(), EnumSet.of(GUEST)),
    CONFIRM_REGISTRATION_COMMAND(new ConfirmRegistrationCommand(), EnumSet.of(GUEST)),

    LOGOUT_COMMAND(new LogoutCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT)),

    GO_HOME_COMMAND(new GoHomeCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT, GUEST)),
    GO_ABOUT_PAGE_COMMAND(new GoAboutPageCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT, GUEST)),
    GO_SIGN_IN_PAGE_COMMAND(new GoSignInPageCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT, GUEST)),
    GO_EQUIPMENT_PAGE_COMMAND(new GoEquipmentPageCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT, GUEST)),
    FIND_EQUIPMENT_BY_TYPE_COMMAND(new FindEquipmentByTypeCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT, GUEST)),
    FIND_ALL_MANAGERS_COMMAND(new FindAllManagersCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT, GUEST)),
    FIND_MANAGER_DETAILS_BY_ID_COMMAND(new FindManagerDetailsByIdCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT, GUEST)),
    FIND_ALL_DEPARTMENTS_COMMAND(new FindAllDepartmentsCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT, GUEST)),
    FIND_DEPARTMENT_DETAILS_BY_ID_COMMAND(new FindDepartmentDetailsByIdCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT, GUEST)),
    FIND_LABORATORY_DETAILS_BY_ID_COMMAND(new FindLaboratoryDetailsByIdCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT, GUEST)),

    GO_EDIT_USER_PAGE_COMMAND(new GoEditUserPageCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT)),
    CHANGE_USER_PASSWORD_COMMAND(new ChangePasswordCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT)),
    UPDATE_USER_PROFILE_COMMAND(new UpdateUserProfileCommand(), EnumSet.of(ADMIN, MANAGER, ASSISTANT, CLIENT)),

    CHECK_BALANCE_COMMAND(new CheckBalanceCommand(), EnumSet.of(CLIENT)),
    ADD_EQUIPMENT_TO_CART_COMMAND(new AddEquipmentToCartCommand(), EnumSet.of(CLIENT)),
    GO_BALANCE_PAGE_COMMAND(new GoBalancePageCommand(), EnumSet.of(CLIENT)),
    ADD_BALANCE_COMMAND(new AddBalanceCommand(), EnumSet.of(CLIENT)),
    GO_BOOK_EQUIPMENT_DETAILS_PAGE_COMMAND(new GoBookEquipmentDetailsPageCommand(), EnumSet.of(CLIENT)),
    SHOW_EQUIPMENT_TIMETABLE_COMMAND(new ShowEquipmentTimeTableCommand(), EnumSet.of(CLIENT)),

    SHOW_SCHEDULE_COMMAND(new ShowScheduleCommand(), EnumSet.of(ASSISTANT)),
    UPLOAD_AVATAR_COMMAND(new UploadAvatarCommand(), EnumSet.of(ASSISTANT, MANAGER)),

    GO_MANAGERS_LAB_COMMAND(new GoManagersLabCommand(), EnumSet.of(MANAGER)),
    UPDATE_MANAGER_DESCRIPTION_COMMAND(new UpdateManagerDescriptionCommand(), EnumSet.of(MANAGER)),
    UPDATE_LABORATORY_COMMAND(new UpdateLaboratoryCommand(), EnumSet.of(MANAGER)),
    UPDATE_LABORATORY_PHOTO_COMMAND(new UploadLaboratoryPhotoCommand(), EnumSet.of(MANAGER)),
    ADD_ASSISTANT_COMMAND(new AddAssistantCommand(), EnumSet.of(MANAGER)),
    GO_ADD_ASSISTANT_PAGE_COMMAND(new GoAddAssistantPageCommand(), EnumSet.of(MANAGER)),

    GO_ADD_NEW_EQUIPMENT_PAGE_COMMAND(new GoAddNewEquipmentPageCommand(), EnumSet.of(ADMIN, MANAGER)),
    GO_EDIT_EQUIPMENT_PAGE_COMMAND(new GoEditEquipmentPageCommand(), EnumSet.of(ADMIN, MANAGER)),
    GO_EDIT_LABORATORY_PAGE_COMMAND(new GoEditLaboratoryPageCommand(), EnumSet.of(ADMIN, MANAGER)),

    ADD_NEW_EQUIPMENT_COMMAND(new AddNewEquipmentCommand(), EnumSet.of(ADMIN, MANAGER)),
    UPDATE_EQUIPMENT_COMMAND(new UpdateEquipmentCommand(), EnumSet.of(ADMIN, MANAGER)),
    UPLOAD_EQUIPMENT_PHOTO_COMMAND(new UploadEquipmentPhotoCommand(), EnumSet.of(ADMIN, MANAGER)),

    GO_USER_MANAGEMENT_PAGE_COMMAND(new GoUserManagementPageCommand(), EnumSet.of(ADMIN)),
    DELETE_USER_COMMAND(new DeleteUserCommand(), EnumSet.of(ADMIN)),
    UPDATE_USER_STATE_COMMAND(new UpdateUserStateCommand(), EnumSet.of(ADMIN)),

    GO_ADD_NEW_DEPARTMENT_PAGE_COMMAND(new GoAddNewDepartmentPageCommand(), EnumSet.of(ADMIN)),
    GO_ADD_NEW_LABORATORY_PAGE_COMMAND(new GoAddNewLaboratoryPageCommand(), EnumSet.of(ADMIN)),
    GO_ADD_NEW_EQUIPMENT_TYPE_PAGE_COMMAND(new GoAddNewEquipmentTypePageCommand(), EnumSet.of(ADMIN)),
    GO_ADD_MANAGER_PAGE_COMMAND(new GoAddManagerPageCommand(), EnumSet.of(ADMIN)),
    GO_ADD_ADMIN_PAGE_COMMAND(new GoAddAdminPageCommand(), EnumSet.of(ADMIN)),

    ADD_ADMIN_COMMAND(new AddAdminCommand(), EnumSet.of(ADMIN)),
    ADD_EQUIPMENT_TYPE_COMMAND(new AddEquipmentTypeCommand(), EnumSet.of(ADMIN)),
    ADD_NEW_LABORATORY_COMMAND(new AddNewLaboratoryCommand(), EnumSet.of(ADMIN)),
    ADD_NEW_MANAGER_COMMAND(new AddNewManagerCommand(), EnumSet.of(ADMIN)),
    ADD_NEW_DEPARTMENT_COMMAND(new AddNewDepartmentCommand(), EnumSet.of(ADMIN));


    private final CustomCommand command;
    private final EnumSet<UserRole> allowedRoles;

    CommandType(CustomCommand command, EnumSet<UserRole> allowedRoles){
        this.command = command;
        this.allowedRoles = allowedRoles;
    }

    public CustomCommand getCommand(){
        return command;
    }
    public Set<UserRole> getAllowedRoles() {
        return allowedRoles;
    }
}
