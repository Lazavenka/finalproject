package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.EquipmentDao;
import by.lozovenko.finalproject.model.dao.impl.EquipmentDaoImpl;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentState;
import by.lozovenko.finalproject.model.entity.EquipmentType;
import by.lozovenko.finalproject.model.entity.Laboratory;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.validator.CustomMapDataValidator;
import by.lozovenko.finalproject.validator.impl.EquipmentMapDataValidator;
import by.lozovenko.finalproject.validator.impl.LaboratoryMapDataValidator;
import by.lozovenko.finalproject.validator.impl.UserMapDataValidator;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.RequestAttribute.LABORATORY_NAME;
import static by.lozovenko.finalproject.controller.RequestParameter.*;

public class EquipmentServiceImpl implements EquipmentService {
    private static EquipmentService instance;

    private final EquipmentDao equipmentDao = EquipmentDaoImpl.getInstance();
    private final CustomMapDataValidator validator = UserMapDataValidator.getInstance();
    private final CustomMapDataValidator dataValidator = EquipmentMapDataValidator.getInstance();

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

    @Override
    public boolean addNewEquipment(Map<String, String> equipmentData) throws ServiceException {
        try{
            boolean isValidData = dataValidator.validateMapData(equipmentData);
            if (!isValidData){
                return false;
            }
            long laboratoryId = Long.parseLong(equipmentData.get(LABORATORY_ID));
            long equipmentTypeId = Long.parseLong(equipmentData.get(EQUIPMENT_TYPE_ID));
            boolean needAssistant = Boolean.parseBoolean(equipmentData.get(IS_NEED_ASSISTANT));
            String equipmentName = equipmentData.get(EQUIPMENT_NAME);
            BigDecimal pricePerHour = new BigDecimal(equipmentData.get(PRICE_PER_HOUR));
            String description = equipmentData.get(DESCRIPTION);
            EquipmentState equipmentState = EquipmentState.valueOf(equipmentData.get(EQUIPMENT_STATE));
            LocalTime averageResearchTime = LocalTime.parse(equipmentData.get(AVERAGE_RESEARCH_TIME));

            Equipment equipment = new Equipment();
            equipment.setLaboratoryId(laboratoryId);
            equipment.setEquipmentTypeId(equipmentTypeId);
            equipment.setName(equipmentName);
            equipment.setDescription(description);
            equipment.setState(equipmentState);
            equipment.setPricePerHour(pricePerHour);
            equipment.setAverageResearchTime(averageResearchTime);
            equipment.setNeedAssistant(needAssistant);
            return equipmentDao.create(equipment) != 0;
        }catch (DaoException e){
            throw new ServiceException("Can't handle addNewEquipment request at EquipmentService", e);
        }
    }


}
