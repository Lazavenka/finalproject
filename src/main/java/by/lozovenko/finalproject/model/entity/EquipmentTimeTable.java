package by.lozovenko.finalproject.model.entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EquipmentTimeTable implements Comparable<EquipmentWorkTimePeriod>{
    private Equipment equipment;
    private final List<EquipmentWorkTimePeriod> timeTable = new ArrayList<>();
    public static final LocalTime START_WORKING_TIME = LocalTime.of(8, 0,0);
    public static final LocalTime END_WORKING_TIME = LocalTime.of(20, 0,0);
    public static final int COUNT_OF_DAYS = 7;

    public EquipmentTimeTable(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public List<EquipmentWorkTimePeriod> getTimeTable() {
        return timeTable;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EquipmentTimeTable{");
        sb.append("equipmentResearchTime=").append(equipment.getAverageResearchTime().toString());
        sb.append(", timeTable=").append("\n");
        for (EquipmentWorkTimePeriod currentPeriod: timeTable) {
            sb.append(currentPeriod).append("\n");
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(EquipmentWorkTimePeriod o) {
        return 0;
    }
}
