package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.dao.EquipmentDao;
import by.lozovenko.finalproject.model.dao.impl.EquipmentDaoImpl;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.model.service.EquipmentService;
import by.lozovenko.finalproject.model.service.EquipmentTimeTableService;
import by.lozovenko.finalproject.model.service.OrderService;
import by.lozovenko.finalproject.model.service.UserService;
import by.lozovenko.finalproject.validator.CustomFieldValidator;
import by.lozovenko.finalproject.validator.CustomMapDataValidator;
import by.lozovenko.finalproject.validator.impl.CustomFieldValidatorImpl;
import by.lozovenko.finalproject.validator.impl.EquipmentMapDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.lozovenko.finalproject.controller.RequestParameter.*;
import static by.lozovenko.finalproject.model.service.impl.EquipmentTimeTableServiceImpl.ONE_DAY;

public class EquipmentServiceImpl implements EquipmentService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static EquipmentService instance;

    private final EquipmentDao equipmentDao = EquipmentDaoImpl.getInstance();
    private final CustomMapDataValidator dataValidator = EquipmentMapDataValidator.getInstance();
    private final CustomFieldValidator inputFieldValidator = CustomFieldValidatorImpl.getInstance();

    private EquipmentServiceImpl() {
    }

    public static EquipmentService getInstance() {
        if (instance == null) {
            instance = new EquipmentServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Equipment> findAll(int offset, int recordsPerPage) throws ServiceException {
        try {
            return equipmentDao.findAllLimited(offset, recordsPerPage);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findAllByType request at EquipmentService", e);
        }
    }

    @Override
    public List<Equipment> findAllByType(EquipmentType type, int offset, int recordsPerPage) throws ServiceException {
        List<Equipment> equipmentList;
        try {
            equipmentList = equipmentDao.findEquipmentByType(type, offset, recordsPerPage);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findAllByType request at EquipmentService", e);
        }
        return equipmentList;
    }

    @Override
    public Optional<Equipment> findById(String equipmentIdString) throws ServiceException {
        Optional<Equipment> optionalDepartment;
        if (inputFieldValidator.isCorrectId(equipmentIdString)) {
            long id = Long.parseLong(equipmentIdString);
            optionalDepartment = findById(id);
        } else {
            optionalDepartment = Optional.empty();
        }
        return optionalDepartment;
    }


    @Override
    public Optional<Equipment> findById(long equipmentId) throws ServiceException {
        try {
            return equipmentDao.findEntityById(equipmentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Equipment> findEquipmentByLaboratoryId(long laboratoryId) throws ServiceException {
        List<Equipment> equipmentList;
        try {
            equipmentList = equipmentDao.findEquipmentByLaboratoryId(laboratoryId);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle findEquipmentByLaboratoryId request at EquipmentService", e);
        }
        return equipmentList;
    }

    @Override
    public boolean addNewEquipment(Map<String, String> equipmentData) throws ServiceException {
        try {
            boolean isValidData = dataValidator.validateMapData(equipmentData);
            if (!isValidData) {
                return false;
            }
            if (equipmentData.get(RESEARCH_TIME_HOUR).equals("00") && equipmentData.get(RESEARCH_TIME_MINUTE).equals("00")) {
                return false;
            }
            Equipment equipment = createEquipmentFromMapData(equipmentData);
            return equipmentDao.create(equipment) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Can't handle addNewEquipment request at EquipmentService", e);
        }
    }

    @Override
    public boolean updateImageByEquipmentId(long id, String databasePath) throws ServiceException {
        boolean result;
        try {
            result = equipmentDao.updateEquipmentPhoto(id, databasePath) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Can't handle updateImageByEquipmentId request at EquipmentService", e);
        }
        return result;
    }

    @Override
    public boolean updateEquipmentById(String equipmentToEditId, Map<String, String> equipmentData) throws ServiceException {
        try {
            boolean isValidData = dataValidator.validateMapData(equipmentData);
            if (!isValidData) {
                return false;
            }
            if (!inputFieldValidator.isCorrectId(equipmentToEditId)) {
                LOGGER.log(Level.DEBUG, "equipmentId not correct id={}", equipmentToEditId);
                return false;
            }
            if (equipmentData.get(RESEARCH_TIME_HOUR).equals("00") && equipmentData.get(RESEARCH_TIME_MINUTE).equals("00")) {
                return false;
            }
            Equipment equipment = createEquipmentFromMapData(equipmentData);
            long equipmentId = Long.parseLong(equipmentToEditId);
            equipment.setId(equipmentId);
            return equipmentDao.update(equipment) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Can't handle addNewEquipment request at EquipmentService", e);
        }
    }

    @Override
    public int countEquipment() throws ServiceException {
        int equipmentCount;
        try {
            equipmentCount = equipmentDao.countEquipment();
        } catch (DaoException e) {
            throw new ServiceException("Can't handle countEquipment request at EquipmentService", e);
        }
        return equipmentCount;
    }

    @Override
    public Optional<EquipmentTimeTable> provideEquipmentTimeTable(String equipmentIdString, String date) throws ServiceException {
        Optional<EquipmentTimeTable> optionalEquipmentTimeTable = Optional.empty();
        if (!inputFieldValidator.isCorrectId(equipmentIdString) || !inputFieldValidator.isCorrectDate(date)) {
            LOGGER.log(Level.DEBUG, "Incorrect id={} or date={}", equipmentIdString, date);
            return optionalEquipmentTimeTable;
        }
        LocalDate selectedDay = LocalDate.parse(date);
        if (selectedDay.getDayOfWeek() == DayOfWeek.SATURDAY ||
                selectedDay.getDayOfWeek() == DayOfWeek.SUNDAY ||
                selectedDay.isBefore(LocalDate.now())) {
            LOGGER.log(Level.DEBUG, "Selected date={} is before now() or the day is SATURDAY or SUNDAY", selectedDay);
            return optionalEquipmentTimeTable;
        }
        OrderService orderService = OrderServiceImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        Optional<Equipment> optionalEquipment = findById(equipmentIdString);
        if (optionalEquipment.isPresent()) {
            Equipment equipment = optionalEquipment.get();
            List<Assistant> laboratoryAssistants = userService.findAssistantsByLaboratoryId(equipment.getLaboratoryId());
            EquipmentTimeTable equipmentTimeTable = new EquipmentTimeTable(equipment);
            List<Order> equipmentOrdersOnSelectedDay = orderService.findOrdersByEquipmentIdAtPeriod(equipment.getId(), selectedDay, selectedDay.plusDays(ONE_DAY));
            Map<Long, List<Order>> assistantsOrders = new HashMap<>();
            for (Assistant assistant : laboratoryAssistants) {
                long assistantId = assistant.getAssistantId();
                List<Order> assistantOrdersOnSelectedDay = orderService.findOrdersByAssistantIdAtPeriod(assistantId, selectedDay, selectedDay.plusDays(ONE_DAY));
                assistantsOrders.put(assistantId, assistantOrdersOnSelectedDay);
            }

            EquipmentTimeTableService timeTableService = EquipmentTimeTableServiceImpl.getInstance();
            timeTableService.buildTimeTable(laboratoryAssistants, equipmentTimeTable, selectedDay, ONE_DAY);
            timeTableService.setAvailability(equipmentTimeTable, equipmentOrdersOnSelectedDay, assistantsOrders);
            optionalEquipmentTimeTable = Optional.of(equipmentTimeTable);
        } else {
            LOGGER.log(Level.DEBUG, "Equipment with id={} not found", equipmentIdString);
        }
        return optionalEquipmentTimeTable;
    }


    @Override
    public int countEquipmentByType(EquipmentType equipmentType) throws ServiceException {
        try {
            return equipmentDao.countEquipmentByType(equipmentType);
        } catch (DaoException e) {
            throw new ServiceException("Can't handle countEquipmentByType method in OrderService. ", e);

        }
    }

    private Equipment createEquipmentFromMapData(Map<String, String> equipmentData) {
        long laboratoryId = Long.parseLong(equipmentData.get(LABORATORY_ID));
        long equipmentTypeId = Long.parseLong(equipmentData.get(EQUIPMENT_TYPE_ID));
        boolean needAssistant = Boolean.parseBoolean(equipmentData.get(IS_NEED_ASSISTANT));
        String equipmentName = equipmentData.get(EQUIPMENT_NAME);
        BigDecimal pricePerHour = new BigDecimal(equipmentData.get(PRICE_PER_HOUR));
        String description = equipmentData.get(DESCRIPTION);
        EquipmentState equipmentState = EquipmentState.valueOf(equipmentData.get(EQUIPMENT_STATE));

        int researchTimeHours = Integer.parseInt(equipmentData.get(RESEARCH_TIME_HOUR));
        int researchTimeMinutes = Integer.parseInt(equipmentData.get(RESEARCH_TIME_MINUTE));
        LocalTime averageResearchTime = LocalTime.of(researchTimeHours, researchTimeMinutes);

        Equipment equipment = new Equipment();
        equipment.setLaboratoryId(laboratoryId);
        equipment.setEquipmentTypeId(equipmentTypeId);
        equipment.setName(equipmentName);
        equipment.setDescription(description);
        equipment.setState(equipmentState);
        equipment.setPricePerHour(pricePerHour);
        equipment.setAverageResearchTime(averageResearchTime);
        equipment.setNeedAssistant(needAssistant);
        return equipment;
    }

}
