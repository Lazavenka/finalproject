package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.LaboratoryDao;
import by.lozovenko.finalproject.model.dao.impl.LaboratoryDaoImpl;
import by.lozovenko.finalproject.model.service.DepartmentService;
import by.lozovenko.finalproject.model.service.LaboratoryService;

import java.util.Optional;

public class LaboratoryServiceImpl implements LaboratoryService {
    private static LaboratoryService instance;

    private final LaboratoryDao laboratoryDao = LaboratoryDaoImpl.getInstance();

    private LaboratoryServiceImpl(){
    }
    public static LaboratoryService getInstance() {
        if(instance == null){
            instance = new LaboratoryServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<String> findLaboratoryNameById(Long id) throws ServiceException {
        try {
            return laboratoryDao.findLaboratoryNameById(id);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
    }
}
