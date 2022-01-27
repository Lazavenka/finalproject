package by.lozovenko.finalproject.controller.filter;

import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.RequestAttribute.USER;

@WebFilter(urlPatterns = {"/*"})
public class NewUserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        Optional<Object> optionalSessionUser = Optional.ofNullable(session.getAttribute(USER));

        if (optionalSessionUser.isEmpty()){
            UserRole currentRole = UserRole.GUEST;
            User user = new User();
            user.setRole(currentRole);
            session.setAttribute(USER, user);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
