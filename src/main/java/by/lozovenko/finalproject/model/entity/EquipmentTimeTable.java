package by.lozovenko.finalproject.model.entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EquipmentTimeTable {
    private Equipment equipment;
    private final List<EquipmentWorkTimePeriod> workTimePeriods = new ArrayList<>();
    public static final LocalTime START_WORKING_TIME = LocalTime.of(8, 0, 0);
    public static final LocalTime END_WORKING_TIME = LocalTime.of(20, 0, 0);
    public static final int SEVEN_DAYS = 7;

    public EquipmentTimeTable(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public List<EquipmentWorkTimePeriod> getWorkTimePeriods() {
        return workTimePeriods;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EquipmentTimeTable{");
        sb.append("equipmentResearchTime=").append(equipment.getAverageResearchTime().toString());
        sb.append(", timeTable=").append("\n");
        for (EquipmentWorkTimePeriod currentPeriod : workTimePeriods) {
            sb.append(currentPeriod).append("\n");
        }
        sb.append('}');
        return sb.toString();
    }

}
