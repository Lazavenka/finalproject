package by.lozovenko.finalproject.controller.filter;

import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.RequestAttribute.USER;

@WebFilter(urlPatterns = {"/*", "*.jsp"})
public class NewUserFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.log(Level.INFO, "NewUserFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        Optional<Object> optionalSessionUser = Optional.ofNullable(session.getAttribute(USER));

        if (optionalSessionUser.isEmpty()){
            UserRole currentRole = UserRole.GUEST;
            User user = new User();
            user.setRole(currentRole);
            session.setAttribute(USER, user);
            LOGGER.log(Level.INFO, "Add User with UserRole = {} to session.", currentRole);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
