package by.lozovenko.finalproject.controller.filter;

import by.lozovenko.finalproject.controller.command.CommandType;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static by.lozovenko.finalproject.controller.RequestAttribute.USER;
import static by.lozovenko.finalproject.controller.RequestParameter.COMMAND;


@WebFilter(urlPatterns = {"/controller"})
public class CommandPermissionFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.log(Level.INFO, "CommandPermissionFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String commandName = request.getParameter(COMMAND);

        try {
            CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
            Optional<Object> optionalSessionUser = Optional.ofNullable(session.getAttribute(USER));
            UserRole sessionUserRole;
            if (optionalSessionUser.isPresent()){
                User user = (User) optionalSessionUser.get();
                sessionUserRole = user.getRole();
            }else {
                sessionUserRole = UserRole.GUEST;
            }
            LOGGER.log(Level.INFO, "CommandPermissionFilter: command = {}, role = {}", commandType.name(), sessionUserRole.name());
            Set<UserRole> commandTypeAllowedRoles = commandType.getAllowedRoles();
            boolean allowed = commandTypeAllowedRoles.contains(sessionUserRole);
            if (allowed){
                LOGGER.log(Level.DEBUG, "Allowed");
                filterChain.doFilter(servletRequest, servletResponse);
            }else {
                response.sendError(403);
            }
        }catch (IllegalArgumentException e){
            LOGGER.log(Level.WARN, "Command {} not found", commandName);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
