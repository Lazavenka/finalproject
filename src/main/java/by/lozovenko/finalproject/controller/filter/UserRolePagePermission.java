package by.lozovenko.finalproject.controller.filter;

import java.util.Set;

import static by.lozovenko.finalproject.controller.PagePath.*;

public enum UserRolePagePermission {
    GUEST(Set.of(
            GUEST_PAGE
    )),
    CLIENT(Set.of(
        CLIENT_PAGE
    )),
    ASSISTANT(Set.of(
        ASSISTANT_PAGE
    )),
    MANAGER(Set.of(
        MANAGER_PAGE
    )),
    ADMIN(Set.of(
        ADMIN_PAGE
    ));
    private final Set<String> allowedPages;

    UserRolePagePermission(Set<String> allowedPages){
        this.allowedPages = allowedPages;
    }

    public Set<String> getAllowedPages() {
        return allowedPages;
    }
}
