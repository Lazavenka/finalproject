package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.DepartmentDao;
import by.lozovenko.finalproject.model.dao.impl.DepartmentDaoImpl;
import by.lozovenko.finalproject.model.entity.Department;
import by.lozovenko.finalproject.model.entity.EquipmentType;
import by.lozovenko.finalproject.model.service.DepartmentService;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.validator.CustomFieldValidator;
import by.lozovenko.finalproject.validator.CustomMapDataValidator;
import by.lozovenko.finalproject.validator.impl.CustomFieldValidatorImpl;
import by.lozovenko.finalproject.validator.impl.DepartmentMapDataValidator;
import by.lozovenko.finalproject.validator.impl.EquipmentTypeMapDataValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.RequestAttribute.DEPARTMENT_NAME;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class DepartmentServiceImpl implements DepartmentService {
    private static DepartmentService instance;

    private final DepartmentDao departmentDao = DepartmentDaoImpl.getInstance();
    private final CustomFieldValidator inputFieldValidator = CustomFieldValidatorImpl.getInstance();
    private CustomMapDataValidator dataValidator = DepartmentMapDataValidator.getInstance();


    private DepartmentServiceImpl(){
    }
    public static DepartmentService getInstance() {
        if(instance == null){
            instance = new DepartmentServiceImpl();
        }
        return instance;
    }
    @Override
    public Optional<String> findDepartmentNameById(String departmentId) throws ServiceException{
        Optional<String> optionalDepartmentName;
        if (inputFieldValidator.isCorrectId(departmentId)){
            Long id = Long.parseLong(departmentId);
            optionalDepartmentName = findDepartmentNameById(id);
        }else {
            optionalDepartmentName = Optional.empty();
        }
        return optionalDepartmentName;
    }

    @Override
    public Optional<String> findDepartmentNameById(Long id) throws ServiceException {
        try {
            return departmentDao.findDepartmentNameById(id);
        }catch (DaoException e){
            throw new ServiceException("Can't handle findDepartmentNameById method in DepartmentService. ", e);
        }
    }

    @Override
    public List<Department> findAll() throws ServiceException {
        try {
            return departmentDao.findAll();
        }catch (DaoException e){
            throw new ServiceException("Can't handle findAll method in DepartmentService. ", e);
        }
    }

    @Override
    public Optional<Department> findDepartmentById(String departmentId) throws ServiceException {
        Optional<Department> optionalDepartment;
        if (inputFieldValidator.isCorrectId(departmentId)){
            Long id = Long.parseLong(departmentId);
            optionalDepartment = findDepartmentById(id);
        }else {
            optionalDepartment = Optional.empty();
        }
        return optionalDepartment;
    }

    @Override
    public Optional<Department> findDepartmentById(Long departmentId) throws ServiceException {
        try {
            return departmentDao.findEntityById(departmentId);
        }catch (DaoException e){
            throw new ServiceException("Can't handle findDepartmentById method in DepartmentService. ", e);
        }
    }

    @Override
    public boolean addNewDepartment(Map<String, String> departmentData) throws ServiceException {
        try{
            boolean isValidData = dataValidator.validateMapData(departmentData);
            if (!isValidData){
                return false;
            }
            String departmentName = departmentData.get(DEPARTMENT_NAME);
            String departmentAddress = departmentData.get(DEPARTMENT_ADDRESS);
            String description = departmentData.get(DESCRIPTION);
            Department department = new Department();
            department.setName(departmentName);
            department.setAddress(departmentAddress);
            department.setDescription(description);
            return departmentDao.create(department) != 0;
        }catch (DaoException e){
            throw new ServiceException("Can't handle addNewDepartment request at DepartmentService", e);
        }
    }
}
