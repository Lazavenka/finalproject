package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.LaboratoryDao;
import by.lozovenko.finalproject.model.dao.impl.LaboratoryDaoImpl;
import by.lozovenko.finalproject.model.entity.Laboratory;
import by.lozovenko.finalproject.model.service.LaboratoryService;
import by.lozovenko.finalproject.validator.CustomFieldValidator;
import by.lozovenko.finalproject.validator.CustomMapDataValidator;
import by.lozovenko.finalproject.validator.impl.CustomFieldValidatorImpl;
import by.lozovenko.finalproject.validator.impl.LaboratoryMapDataValidator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.RequestAttribute.LABORATORY_NAME;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class LaboratoryServiceImpl implements LaboratoryService {
    private static LaboratoryService instance;

    private final LaboratoryDao laboratoryDao = LaboratoryDaoImpl.getInstance();
    private final CustomFieldValidator inputFieldValidator = CustomFieldValidatorImpl.getInstance();
    private final CustomMapDataValidator dataValidator = LaboratoryMapDataValidator.getInstance();

    private LaboratoryServiceImpl() {
    }

    public static LaboratoryService getInstance() {
        if (instance == null) {
            instance = new LaboratoryServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<String> findLaboratoryNameById(Long id) throws ServiceException {
        try {
            return laboratoryDao.findLaboratoryNameById(id);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findLaboratoryNameById request at LaboratoryService", e);
        }
    }

    @Override
    public List<Laboratory> findLaboratoriesByDepartmentId(String selectedDepartmentId) throws ServiceException {
        List<Laboratory> laboratoryList;
        if (inputFieldValidator.isCorrectId(selectedDepartmentId)) {
            Long id = Long.parseLong(selectedDepartmentId);
            laboratoryList = findLaboratoriesByDepartmentId(id);
        } else {
            laboratoryList = Collections.emptyList();
        }
        return laboratoryList;
    }

    @Override
    public List<Laboratory> findLaboratoriesByDepartmentId(Long selectedDepartmentId) throws ServiceException {
        List<Laboratory> laboratoryList;
        try {
            laboratoryList = laboratoryDao.findAllByDepartmentId(selectedDepartmentId);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findLaboratoriesByDepartmentId request at LaboratoryService", e);
        }
        return laboratoryList;
    }

    @Override
    public Optional<Laboratory> findLaboratoryById(String laboratoryIdString) throws ServiceException {
        Optional<Laboratory> optionalLaboratory;
        if (inputFieldValidator.isCorrectId(laboratoryIdString)) {
            Long id = Long.parseLong(laboratoryIdString);
            optionalLaboratory = findLaboratoryById(id);
        } else {
            optionalLaboratory = Optional.empty();
        }
        return optionalLaboratory;
    }

    @Override
    public Optional<Laboratory> findLaboratoryById(Long laboratoryId) throws ServiceException {
        try {
            return laboratoryDao.findEntityById(laboratoryId);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findLaboratoryById method in LaboratoryService. ", e);
        }
    }

    @Override
    public boolean addNewLaboratory(Map<String, String> laboratoryData) throws ServiceException {
        try {
            boolean isValidData = dataValidator.validateMapData(laboratoryData);
            if (!isValidData) {
                return false;
            }
            long departmentId = Long.parseLong(laboratoryData.get(DEPARTMENT_ID));
            String laboratoryName = laboratoryData.get(LABORATORY_NAME);
            String laboratoryLocation = laboratoryData.get(LABORATORY_LOCATION);
            String description = laboratoryData.get(DESCRIPTION);
            Laboratory laboratory = new Laboratory();
            laboratory.setDepartmentId(departmentId);
            laboratory.setName(laboratoryName);
            laboratory.setLocation(laboratoryLocation);
            laboratory.setDescription(description);
            return laboratoryDao.create(laboratory) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Can't handle addNewLaboratory request at LaboratoryService", e);
        }
    }

    @Override
    public List<Laboratory> findAll() throws ServiceException {
        List<Laboratory> laboratoryList;
        try {
            laboratoryList = laboratoryDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findAll request at LaboratoryService", e);
        }
        return laboratoryList;
    }

    @Override
    public List<Laboratory> findLaboratoriesWithoutManager() throws ServiceException {
        try {
            return laboratoryDao.findLaboratoriesWithoutManager();
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findAll request at LaboratoryService", e);
        }
    }

    @Override
    public long countLaboratories() throws ServiceException {
        long laboratoriesCount;
        try {
            laboratoriesCount = laboratoryDao.countLaboratories();
        } catch (DaoException e) {
            throw new ServiceException("Can't handle countLaboratories request at LaboratoryService", e);
        }
        return laboratoriesCount;
    }

    @Override
    public boolean updateLaboratoryById(String laboratoryToEditId, Map<String, String> laboratoryData) throws ServiceException {
        try {
            boolean isValidData = dataValidator.validateMapData(laboratoryData);
            if (!isValidData) {
                return false;
            }
            if (!inputFieldValidator.isCorrectId(laboratoryToEditId)) {
                return false;
            }
            long laboratoryId = Long.parseLong(laboratoryToEditId);
            long departmentId = Long.parseLong(laboratoryData.get(DEPARTMENT_ID));
            String laboratoryName = laboratoryData.get(LABORATORY_NAME);
            String laboratoryLocation = laboratoryData.get(LABORATORY_LOCATION);
            String description = laboratoryData.get(DESCRIPTION);
            Laboratory laboratory = new Laboratory();
            laboratory.setId(laboratoryId);
            laboratory.setDepartmentId(departmentId);
            laboratory.setName(laboratoryName);
            laboratory.setLocation(laboratoryLocation);
            laboratory.setDescription(description);

            return laboratoryDao.update(laboratory) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateLaboratoryById request at LaboratoryService", e);
        }
    }

    @Override
    public boolean updateImageByLaboratoryId(long id, String databasePath) throws ServiceException {
        boolean result;
        try {
            result = laboratoryDao.updateLaboratoryPhoto(id, databasePath) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateImageByLaboratoryId request at LaboratoryService", e);
        }
        return result;
    }
}
