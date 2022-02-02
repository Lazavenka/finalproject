package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.EquipmentTypeDao;
import by.lozovenko.finalproject.model.dao.impl.EquipmentTypeDaoImpl;
import by.lozovenko.finalproject.model.entity.EquipmentType;
import by.lozovenko.finalproject.model.service.EquipmentTypeService;
import by.lozovenko.finalproject.validator.CustomFieldValidator;
import by.lozovenko.finalproject.validator.CustomMapDataValidator;
import by.lozovenko.finalproject.validator.impl.CustomFieldValidatorImpl;
import by.lozovenko.finalproject.validator.impl.EquipmentTypeMapDataValidator;
import by.lozovenko.finalproject.validator.impl.UserMapDataValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.RequestParameter.EQUIPMENT_TYPE_DESCRIPTION;
import static by.lozovenko.finalproject.controller.RequestParameter.EQUIPMENT_TYPE_NAME;

public class EquipmentTypeServiceImpl implements EquipmentTypeService{
    private static EquipmentTypeService instance;

    private final EquipmentTypeDao equipmentTypeDao = EquipmentTypeDaoImpl.getInstance();

    private final CustomFieldValidator inputFieldValidator = CustomFieldValidatorImpl.getInstance();
    private CustomMapDataValidator dataValidator = EquipmentTypeMapDataValidator.getInstance();
    private EquipmentTypeServiceImpl(){
    }
    public static EquipmentTypeService getInstance() {
        if(instance == null){
            instance = new EquipmentTypeServiceImpl();
        }
        return instance;
    }

    @Override
    public List<EquipmentType> findAll() throws ServiceException {
        try {
            return equipmentTypeDao.findAll();
        }catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<EquipmentType> findById(String equipmentTypeId) throws ServiceException {
        if (!inputFieldValidator.isCorrectEquipmentTypeId(equipmentTypeId)){
            throw new ServiceException("EquipmentTypeId didn't pass validation.");
        }
        Optional<EquipmentType> optionalEquipmentType;
        try {
            Long id = Long.parseLong(equipmentTypeId);
            optionalEquipmentType = equipmentTypeDao.findEntityById(id);
        }catch (DaoException e){
            throw new ServiceException("Can't handle findEntityById request at EquipmentTypeService", e);
        }
        return optionalEquipmentType;
    }

    @Override
    public boolean addNewEquipmentType(Map<String, String> equipmentTypeData) throws ServiceException {
        try{
            boolean isValidData = dataValidator.validateMapData(equipmentTypeData);
            if (!isValidData){
                return false;
            }
            String equipmentTypeName = equipmentTypeData.get(EQUIPMENT_TYPE_NAME);
            String equipmentTypeDescription = equipmentTypeData.get(EQUIPMENT_TYPE_DESCRIPTION);
            EquipmentType equipmentType = new EquipmentType();
            equipmentType.setName(equipmentTypeName);
            equipmentType.setDescription(equipmentTypeDescription);
            return equipmentTypeDao.create(equipmentType) != 0;
        }catch (DaoException e){
            throw new ServiceException("Can't handle addNewEquipmentType request at EquipmentTypeService", e);
        }
    }

}
