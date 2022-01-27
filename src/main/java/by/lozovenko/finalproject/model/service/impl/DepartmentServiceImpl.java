package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.DepartmentDao;
import by.lozovenko.finalproject.model.dao.impl.DepartmentDaoImpl;
import by.lozovenko.finalproject.model.service.DepartmentService;
import by.lozovenko.finalproject.model.service.UserService;

import java.util.Optional;

public class DepartmentServiceImpl implements DepartmentService {
    private static DepartmentService instance;

    private final DepartmentDao departmentDao = DepartmentDaoImpl.getInstance();

    private DepartmentServiceImpl(){
    }
    public static DepartmentService getInstance() {
        if(instance == null){
            instance = new DepartmentServiceImpl();
        }
        return instance;
    }
    @Override
    public Optional<String> findDepartmentNameById(Long id) throws ServiceException{
        try {
            return departmentDao.findDepartmentNameById(id);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
    }
}
