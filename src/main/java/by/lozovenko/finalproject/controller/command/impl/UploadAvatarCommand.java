package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.CommandException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Manager;
import by.lozovenko.finalproject.model.entity.User;
import by.lozovenko.finalproject.model.entity.UserRole;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.model.service.impl.UserServiceImpl;
import by.lozovenko.finalproject.validator.FileValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.PagePath.*;
import static by.lozovenko.finalproject.controller.RequestAttribute.*;
import static by.lozovenko.finalproject.controller.RequestParameter.CONTENT;
import static by.lozovenko.finalproject.controller.RequestParameter.DESCRIPTION;

public class UploadAvatarCommand implements CustomCommand {
    private static final String UPLOAD_ROOT = "C:\\Users\\Roger\\IdeaProjects\\finalproject\\src\\main\\webapp\\static\\images\\";
    private static final String UPLOAD_MANAGER_SUBDIRECTORY = "managers" + File.separator;
    private static final String MANAGER_DATABASE_FILEPATH = "static/images/managers/";
    private static final String UPLOAD_ASSISTANT_SUBDIRECTORY = "assistants" + File.separator;
    private static final String ASSISTANT_DATABASE_FILEPATH = "static/images/assistants/";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        Router router = new Router();
        HttpSession session = request.getSession();
        Optional<Object> optionalUser = Optional.ofNullable(session.getAttribute(USER));
        UserService userService = UserServiceImpl.getInstance();
        if (optionalUser.isPresent()) {
            User user = (User) optionalUser.get();
            UserRole role = user.getRole();
            String directory = UPLOAD_ROOT;
            String databaseDirectory = "";
            router.setPage(EDIT_PROFILE_PAGE);
            switch (role) {
                case MANAGER -> {
                    directory = directory + UPLOAD_MANAGER_SUBDIRECTORY;
                    databaseDirectory = MANAGER_DATABASE_FILEPATH;
                }
                case ASSISTANT -> {
                    directory = directory + UPLOAD_ASSISTANT_SUBDIRECTORY;
                    databaseDirectory = ASSISTANT_DATABASE_FILEPATH;
                }
            }
            logger.log(Level.DEBUG, "File directory = {}", directory);

            InputStream inputStream = null;
            try {
                Part part = request.getPart(CONTENT);
                inputStream = part.getInputStream();
                String submittedFilename = part.getSubmittedFileName();
                if (submittedFilename == null || submittedFilename.isEmpty()){
                    request.setAttribute(EMPTY_IMAGE, true);
                    request.setAttribute(ERROR_MESSAGE, true);
                    return router;
                }
                String extension = submittedFilename.substring(submittedFilename.toLowerCase().lastIndexOf('.'));
                long fileSize = part.getSize();
                String contentType = part.getContentType();

                String filename = user.getLogin() + user.getId() + extension;

                if (!FileValidator.checkSize(fileSize)) {
                    logger.log(Level.INFO, "Invalid file size: size = {}", fileSize);
                    request.setAttribute(INVALID_FILE_SIZE, true);
                    request.setAttribute(ERROR_MESSAGE, true);
                    return router;
                }
                if (FileValidator.checkContentType(contentType) && FileValidator.checkExtension(extension)) {
                    String path = directory + filename;
                    Path imagePath = new File(path).toPath();
                    long bytes = Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
                    String databasePath = databaseDirectory + filename;
                    if (!userService.updateAvatar(user, databasePath)) {
                        router.setPage(ERROR_500_PAGE);
                        router.setRedirect();
                        return router;
                    }
                    logger.log(Level.INFO, "Upload file filesize = {}, contentType = {}, imagePath = {}", bytes, contentType, path);
                } else {
                    logger.log(Level.INFO, "Invalid file extension ({}) or content type ({})", extension, contentType);
                    request.setAttribute(WRONG_FILE_EXTENSION, true);
                    request.setAttribute(ERROR_MESSAGE, true);
                    return router;
                }
                if (role == UserRole.MANAGER){
                    request.setAttribute(DESCRIPTION, ((Manager)user).getDescription());
                }
                Optional<User> updatedUser = userService.findUserById(user.getId());
                updatedUser.ifPresent(newSessionUser -> session.setAttribute(USER, newSessionUser));
                request.setAttribute(SUCCESS_MESSAGE, true);
            } catch (IOException | ServiceException | ServletException e) {
                throw new CommandException("Error in UploadAvatarCommand", e);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        logger.log(Level.ERROR, "IO exception during upload avatar command");
                    }
                }
            }
        }
        return router;

    }

}
