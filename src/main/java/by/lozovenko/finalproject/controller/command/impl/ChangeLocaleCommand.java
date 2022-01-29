package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.validator.LocaleValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import static by.lozovenko.finalproject.controller.PagePath.ABOUT;
import static by.lozovenko.finalproject.controller.RequestAttribute.CURRENT_PAGE;
import static by.lozovenko.finalproject.controller.RequestParameter.LOCALE;

public class ChangeLocaleCommand implements CustomCommand {
    @Override
    public Router execute(HttpServletRequest request){
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String)session.getAttribute(CURRENT_PAGE);
        String newLocale = request.getParameter(LOCALE);
        String oldLocale = (String)session.getAttribute(LOCALE);
        logger.log(Level.INFO, "Get request parameter {}", newLocale);
        logger.log(Level.INFO, "Old locale {}", oldLocale);
        if (!newLocale.equals(oldLocale)) {
            if (LocaleValidator.isLocaleExist(newLocale)) {
                logger.log(Level.DEBUG, "Set attribute to session - locale = {}", newLocale);
                session.setAttribute(LOCALE, newLocale);
            } else {
                logger.log(Level.WARN, "Locale {} not exist", newLocale);
            }
            logger.log(Level.DEBUG, "send router page = {}", currentPage);
            router.setPage(currentPage);
        }else {
            logger.log(Level.DEBUG, "Old locale equals new locale. Send page 'About'");
            router.setPage(ABOUT);
        }
        return router;
    }
}
