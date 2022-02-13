package by.lozovenko.finalproject.controller.filter;

import java.util.Set;

import static by.lozovenko.finalproject.controller.PagePath.*;

public enum UserRolePagePermission {
    GUEST(Set.of(
            INDEX,
            ABOUT,
            MANAGER_DETAILS_PAGE,
            ALL_DEPARTMENTS_PAGE,
            ALL_MANAGERS_PAGE,
            DEPARTMENT_DETAILS_PAGE,
            LABORATORY_DETAILS_PAGE,
            EQUIPMENT_PAGE,
            LOGIN_PAGE,
            CONFIRM_REGISTRATION_PAGE,
            CHECK_MAIL_PAGE,

            REGISTRATION_PAGE,
            ERROR_404_PAGE,
            ERROR_500_PAGE
    )),
    CLIENT(Set.of(
            INDEX,
            ABOUT,
            EDIT_PROFILE_PAGE,
            CLIENT_BALANCE_PAGE,
            CLIENT_ORDERS_PAGE,
            ORDER_DETAILS_PAGE,
            BOOK_ITEM_DETAILS_PAGE,
            MANAGER_DETAILS_PAGE,
            ALL_DEPARTMENTS_PAGE,
            ALL_MANAGERS_PAGE,
            DEPARTMENT_DETAILS_PAGE,
            LABORATORY_DETAILS_PAGE,
            EQUIPMENT_PAGE,
            LOGIN_PAGE,
            REGISTRATION_PAGE,
            SUCCESS_PAGE,
            ERROR_404_PAGE,
            ERROR_500_PAGE
    )),
    ASSISTANT(Set.of(
            INDEX,
            ABOUT,
            EDIT_PROFILE_PAGE,
            MANAGER_DETAILS_PAGE,
            ALL_DEPARTMENTS_PAGE,
            ALL_MANAGERS_PAGE,
            DEPARTMENT_DETAILS_PAGE,
            LABORATORY_DETAILS_PAGE,
            EQUIPMENT_PAGE,
            LOGIN_PAGE,
            SUCCESS_PAGE,
            REGISTRATION_PAGE,
            ASSISTANT_SCHEDULE_PAGE,
            EQUIPMENT_DETAILS_PAGE,
            ERROR_404_PAGE,
            ERROR_500_PAGE
    )),
    MANAGER(Set.of(
            INDEX,
            ABOUT,
            MANAGER_DETAILS_PAGE,
            EDIT_PROFILE_PAGE,
            EDIT_EQUIPMENT_PAGE,
            EDIT_LABORATORY_PAGE,
            LABORATORY_ORDERS_PAGE,
            ORDER_DETAILS_PAGE,
            ADD_ASSISTANT_PAGE,
            ALL_DEPARTMENTS_PAGE,
            ALL_MANAGERS_PAGE,
            DEPARTMENT_DETAILS_PAGE,
            LABORATORY_DETAILS_PAGE,
            EQUIPMENT_PAGE,
            MANAGER_PAGE,
            SUCCESS_PAGE,
            LOGIN_PAGE,
            REGISTRATION_PAGE,
            ERROR_404_PAGE,
            ERROR_500_PAGE
    )),
    ADMIN(Set.of(
            INDEX,
            ABOUT,
            EDIT_PROFILE_PAGE,
            ADD_ADMIN_PAGE,
            ADD_MANAGER_PAGE,
            ADD_EQUIPMENT_PAGE,
            ADD_EQUIPMENT_TYPE_PAGE,
            ADD_DEPARTMENT_PAGE,
            ADD_LABORATORY_PAGE,
            USER_MANAGEMENT_PAGE,
            SUCCESS_PAGE,
            MANAGER_DETAILS_PAGE,
            ALL_DEPARTMENTS_PAGE,
            ALL_MANAGERS_PAGE,
            DEPARTMENT_DETAILS_PAGE,
            LABORATORY_DETAILS_PAGE,
            EQUIPMENT_PAGE,
            LOGIN_PAGE,
            REGISTRATION_PAGE,
            ERROR_404_PAGE,
            ERROR_500_PAGE
    ));
    private final Set<String> allowedPages;

    UserRolePagePermission(Set<String> allowedPages){
        this.allowedPages = allowedPages;
    }

    public Set<String> getAllowedPages() {
        return allowedPages;
    }
}
