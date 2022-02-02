package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.EquipmentDao;
import by.lozovenko.finalproject.model.dao.impl.EquipmentDaoImpl;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentType;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.validator.CustomMapDataValidator;
import by.lozovenko.finalproject.validator.impl.UserMapDataValidator;

import java.util.List;
import java.util.Optional;

public class EquipmentServiceImpl implements EquipmentService {
    private static EquipmentService instance;

    private final EquipmentDao equipmentDao = EquipmentDaoImpl.getInstance();
    private final CustomMapDataValidator validator = UserMapDataValidator.getInstance();

    private EquipmentServiceImpl(){
    }
    public static EquipmentService getInstance() {
        if(instance == null){
            instance = new EquipmentServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Equipment> findAll() throws ServiceException {
        try {
            return equipmentDao.findAll();
        }catch (DaoException e){
            throw new ServiceException("Can't handle findAllByType request at EquipmentService", e);
        }
    }

    @Override
    public List<Equipment> findAllByType(EquipmentType type) throws ServiceException {
        List<Equipment> equipmentList;
        try {
            equipmentList = equipmentDao.findEquipmentByType(type);
        }catch (DaoException e){
            throw new ServiceException("Can't handle findAllByType request at EquipmentService", e);
        }
        return equipmentList;
    }

    @Override
    public Optional<Equipment> findById(long equipmentId) throws ServiceException {
        try {
            return equipmentDao.findEntityById(equipmentId);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Equipment> findEquipmentByLaboratoryId(long laboratoryId) throws ServiceException {
        List<Equipment> equipmentList;
        try {
            equipmentList = equipmentDao.findEquipmentByLaboratoryId(laboratoryId);
        }catch (DaoException e){
            throw new ServiceException("Can't handle findEquipmentByLaboratoryId request at EquipmentService", e);
        }
        return equipmentList;
    }


}
