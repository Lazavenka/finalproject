package by.lozovenko.finalproject.model.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderEquipment extends CustomEntity{
    /*
    First parameter id connects with orderId
    ***/
    private long equipmentId;
    private LocalDateTime rentStartTime;
    private LocalTime rentTime;
    private long assistantId;

    public OrderEquipment(long id, long equipmentId, LocalDateTime rentStartTime,
                          LocalTime rentTime, long assistantId) {
        super(id);
        this.equipmentId = equipmentId;
        this.rentStartTime = rentStartTime;
        this.rentTime = rentTime;
        this.assistantId = assistantId;
    }

    public long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public LocalDateTime getRentStartTime() {
        return rentStartTime;
    }

    public void setRentStartTime(LocalDateTime rentStartTime) {
        this.rentStartTime = rentStartTime;
    }

    public LocalTime getRentTime() {
        return rentTime;
    }

    public void setRentTime(LocalTime rentTime) {
        this.rentTime = rentTime;
    }

    public long getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(long assistantId) {
        this.assistantId = assistantId;
    }
}
