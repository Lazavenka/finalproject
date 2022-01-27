package by.lozovenko.finalproject.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.lozovenko.finalproject.controller.RequestAttribute.CURRENT_PAGE;

@WebFilter(urlPatterns = {"*.jsp"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class CurrentPageFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CONTROLLER_PATTERN = "/controller?";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();
        String query = request.getQueryString();

        if (query != null){
            requestURI = CONTROLLER_PATTERN.concat(query);

        }

        LOGGER.log(Level.INFO, "RequestURI = {}", requestURI);
        session.setAttribute(CURRENT_PAGE, requestURI);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
