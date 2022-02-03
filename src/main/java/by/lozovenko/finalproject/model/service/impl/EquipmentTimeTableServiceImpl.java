package by.lozovenko.finalproject.model.service.impl;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.model.service.EquipmentTimeTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EquipmentTimeTableServiceImpl implements EquipmentTimeTableService {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void buildTimeTable(EquipmentTimeTable equipmentTimeTable) throws ServiceException {
        if (equipmentTimeTable.getEquipment() == null) {
            throw new ServiceException("Error in EquipmentTimeTableService. Equipment parameter can't be null!");
        }
        int dayCount = EquipmentTimeTable.COUNT_OF_DAYS;
        LocalDate currentDate = LocalDate.now();
        buildTimeTable(equipmentTimeTable, currentDate, dayCount);
    }

    @Override
    public void buildTimeTable(EquipmentTimeTable equipmentTimeTable, LocalDate startDate, int daysCount) throws ServiceException {
        LocalTime averageResearchTime = equipmentTimeTable.getEquipment().getAverageResearchTime();
        int averageResearchTimeHours = averageResearchTime.getHour();
        int averageResearchTimeMinutes = averageResearchTime.getMinute();
        LocalTime endWorkingDay = EquipmentTimeTable.END_WORKING_TIME;
        LocalDate currentDate = startDate;
        for (int i = 0; i < daysCount; i++) {
            LocalTime startCurrentPeriod = EquipmentTimeTable.START_WORKING_TIME;
            boolean dayNotFinished = true;
            while (dayNotFinished) {
                LocalDateTime startDateTimePeriod = currentDate.atTime(startCurrentPeriod);
                System.out.println(startDateTimePeriod);
                LocalTime endCurrentTimePeriod = startCurrentPeriod.plus(averageResearchTimeHours, ChronoUnit.HOURS)
                        .plus(averageResearchTimeMinutes, ChronoUnit.MINUTES);
                if (endCurrentTimePeriod.isAfter(endWorkingDay)) {
                    dayNotFinished = false;
                } else {
                    EquipmentAvailability availability;
                    if (startDateTimePeriod.isBefore(LocalDateTime.now())){
                        availability = EquipmentAvailability.PAST;
                    }else {
                        availability = EquipmentAvailability.FULL_AVAILABLE;
                    }
                    LocalDateTime endDateTimePeriod = currentDate.atTime(endCurrentTimePeriod);
                    System.out.println(endDateTimePeriod);
                    EquipmentWorkTimePeriod currentPeriod = new EquipmentWorkTimePeriod(startDateTimePeriod, endDateTimePeriod, availability);
                    equipmentTimeTable.getTimeTable().add(currentPeriod);
                }
                startCurrentPeriod = endCurrentTimePeriod;
            }
            currentDate = currentDate.plusDays(1);
            if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY){
                currentDate = currentDate.plusDays(2);
            }
        }
    }

    @Override
    public void setAvailability(EquipmentTimeTable equipmentTimeTable, List<OrderEquipment> equipmentOrders, List<OrderEquipment> assistantsOrders) {
        List<EquipmentWorkTimePeriod> timeTable = equipmentTimeTable.getTimeTable();
        for (OrderEquipment assistantOrder : assistantsOrders) {
            LocalDateTime orderStart = assistantOrder.getRentStartTime();
            LocalDateTime orderEnd = orderStart.ge;
            EquipmentWorkTimePeriod assistantOrdertimePeriod = new EquipmentWorkTimePeriod(assistantOrder.getRentStartTime(),LocalDateTime);
            for (EquipmentWorkTimePeriod currentPeriod : timeTable) {
                if (currentPeriod.containsStartDateTime(assistantOrder.getRentStartTime())){
                    currentPeriod.setAvailability(EquipmentAvailability.AVAILABLE_WITHOUT_ASSISTANT);
                }
            }
        }
        for (OrderEquipment equipmentOrder : equipmentOrders) {
            for (EquipmentWorkTimePeriod currentPeriod : timeTable) {
                if (currentPeriod.containsStartDateTime(equipmentOrder.getRentStartTime())){
                    currentPeriod.setAvailability(EquipmentAvailability.BUSY);
                }
            }
        }
    }
}
