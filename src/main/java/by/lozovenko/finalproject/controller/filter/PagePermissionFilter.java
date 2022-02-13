package by.lozovenko.finalproject.controller.filter;

import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

import static by.lozovenko.finalproject.controller.RequestAttribute.USER;
import static by.lozovenko.finalproject.controller.PagePath.INDEX;

public class PagePermissionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        String requestURI = httpRequest.getServletPath();

        UserRole userRole = UserRole.GUEST;
        User user = (User) session.getAttribute(USER);
        if(user != null){
            userRole = user.getRole();
        }
        boolean isCorrect;
        Set<String> pages;
        switch (userRole){
            case ADMIN -> {
                pages = UserRolePagePermission.ADMIN.getAllowedPages();
                isCorrect = pages.stream().anyMatch(requestURI::contains);
            }
            case CLIENT -> {
                pages = UserRolePagePermission.CLIENT.getAllowedPages();
                isCorrect = pages.stream().anyMatch(requestURI::contains);
            }
            case MANAGER -> {
                pages = UserRolePagePermission.MANAGER.getAllowedPages();
                isCorrect = pages.stream().anyMatch(requestURI::contains);
            }
            case ASSISTANT -> {
                pages = UserRolePagePermission.ASSISTANT.getAllowedPages();
                isCorrect = pages.stream().anyMatch(requestURI::contains);
            }
            default -> {
                pages = UserRolePagePermission.GUEST.getAllowedPages();
                isCorrect = pages.stream().anyMatch(requestURI::contains);
            }
        }
        if(!isCorrect && user == null){
            user = new User();
            user.setRole(UserRole.GUEST);
            session.setAttribute(USER,user);
            httpRequest.getRequestDispatcher(INDEX)
                    .forward(httpRequest,httpResponse);
            return;
        }else if(!isCorrect && user != null){
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
