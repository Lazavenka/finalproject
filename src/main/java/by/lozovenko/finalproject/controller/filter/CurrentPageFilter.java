package by.lozovenko.finalproject.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.lozovenko.finalproject.controller.RequestAttribute.CURRENT_PAGE;

public class CurrentPageFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CONTROLLER_PATTERN = "/controller?";
    private static final String ROOT_PAGES_DIRECTORY = "jsp";
    private static final String INDEX_PAGE = "/index.jsp";
    private static final String LOCALE_COMMAND = "command=change_locale";


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.log(Level.INFO, "CurrentPageFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();
        LOGGER.log(Level.DEBUG, requestURI);
        String currentPage = INDEX_PAGE;
        int rootIndex = requestURI.indexOf(ROOT_PAGES_DIRECTORY);
        if (rootIndex!= -1){
            currentPage = requestURI.substring(rootIndex);
        }
        String query = request.getQueryString();
        LOGGER.log(Level.INFO, "Query = {}", query);
        if (query != null && !query.contains(LOCALE_COMMAND)){
            currentPage = CONTROLLER_PATTERN.concat(query);
        }
        LOGGER.log(Level.INFO, "Session.currentPage = {}", currentPage);
        session.setAttribute(CURRENT_PAGE, currentPage);
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
