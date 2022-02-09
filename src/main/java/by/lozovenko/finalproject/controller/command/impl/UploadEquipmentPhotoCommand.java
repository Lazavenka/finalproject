package by.lozovenko.finalproject.controller.command.impl;

import by.lozovenko.finalproject.controller.Router;
import by.lozovenko.finalproject.controller.command.CustomCommand;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.impl.EquipmentServiceImpl;
import by.lozovenko.finalproject.validator.FileValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
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
import static by.lozovenko.finalproject.controller.RequestAttribute.EXCEPTION;
import static by.lozovenko.finalproject.controller.RequestParameter.CONTENT;
import static by.lozovenko.finalproject.controller.RequestParameter.EQUIPMENT_ID;

public class UploadEquipmentPhotoCommand implements CustomCommand {
    private static final String UPLOAD_ROOT = "C:\\Users\\Roger\\IdeaProjects\\finalproject\\src\\main\\webapp\\static\\images\\";
    private static final String EQUIPMENT_SUBDIRECTORY = "equipment" + File.separator;
    private static final String DATABASE_FILEPATH = "static/images/equipment/";

    private static final String FILENAME_PART_IMAGE = "image";
    private static final String FILENAME_PART_LABORATORY_ID = "labId";
    private static final String FILENAME_PART_EQUIPMENT_ID = "eqId";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(EDIT_EQUIPMENT_PAGE, Router.DispatchType.FORWARD);
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();
        String currentEquipmentId = request.getParameter(EQUIPMENT_ID);
        InputStream inputStream = null;
        try {
            Optional<Equipment> optionalEquipment = equipmentService.findById(currentEquipmentId);
            if (optionalEquipment.isPresent()) {
                Equipment selectedEquipment = optionalEquipment.get();
                request.setAttribute(SELECTED_EQUIPMENT, selectedEquipment);
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

                String filename = FILENAME_PART_IMAGE + FILENAME_PART_EQUIPMENT_ID + selectedEquipment.getId()
                        + FILENAME_PART_LABORATORY_ID + selectedEquipment.getLaboratoryId() + extension;

                if (!FileValidator.checkSize(fileSize)) {
                    logger.log(Level.INFO, "Invalid file size: size = {}", fileSize);
                    request.setAttribute(INVALID_FILE_SIZE, true);
                    return router;
                }
                if (FileValidator.checkContentType(contentType) && FileValidator.checkExtension(extension)) {
                    String path = UPLOAD_ROOT + EQUIPMENT_SUBDIRECTORY + filename;
                    Path imagePath = new File(path).toPath();
                    long bytes = Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
                    String databasePath = DATABASE_FILEPATH + filename;
                    if (equipmentService.updateImageByEquipmentId(selectedEquipment.getId(), databasePath)) {
                        request.setAttribute(UPLOAD_SUCCESS, true);

                        logger.log(Level.INFO, "Upload file filesize = {}, contentType = {}, imagePath = {}", bytes, contentType, path);
                    }else {
                        router.setPage(ERROR_500_PAGE);
                        router.setRedirect();
                        return router;
                    }
                } else {
                    logger.log(Level.INFO, "Invalid file extension ({}) or content type ({})", extension, contentType);
                    request.setAttribute(WRONG_FILE_EXTENSION, true);
                    return router;
                }
                request.setAttribute(SELECTED_EQUIPMENT, selectedEquipment);
                request.setAttribute(SUCCESS_MESSAGE, true);
            }
        } catch (IOException | ServiceException | ServletException e) {
            logger.log(Level.ERROR, "Error in UploadEquipmentPhotoCommand", e);
            request.setAttribute(EXCEPTION, e);
            router.setPage(ERROR_404_PAGE);
            router.setRedirect();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.log(Level.ERROR, "IO exception during upload imgage command");
                }
            }
        }
        return router;
    }


}

