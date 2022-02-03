package by.lozovenko.finalproject.model.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderEquipment extends CustomEntity{
    /*
    First parameter id connects with orderId
    ***/
    private long equipmentId;
    private LocalDateTime rentStartTime;
    private LocalDateTime rentEndTime;
    private long assistantId;

    public OrderEquipment(){}
    public OrderEquipment(long id, long equipmentId, LocalDateTime rentStartTime,
                          LocalDateTime rentTime, long assistantId) {
        super(id);
        this.equipmentId = equipmentId;
        this.rentStartTime = rentStartTime;
        this.rentEndTime = rentTime;
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

    public LocalDateTime getRentEndTime() {
        return rentEndTime;
    }

    public void setRentEndTime(LocalDateTime rentEndTime) {
        this.rentEndTime = rentEndTime;
    }

    public long getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(long assistantId) {
        this.assistantId = assistantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEquipment that = (OrderEquipment) o;

        if (equipmentId != that.equipmentId) return false;
        if (assistantId != that.assistantId) return false;
        if (rentStartTime != null ? !rentStartTime.equals(that.rentStartTime) : that.rentStartTime != null)
            return false;
        return rentEndTime != null ? rentEndTime.equals(that.rentEndTime) : that.rentEndTime == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (equipmentId ^ (equipmentId >>> 32));
        result = 31 * result + (rentStartTime != null ? rentStartTime.hashCode() : 0);
        result = 31 * result + (rentEndTime != null ? rentEndTime.hashCode() : 0);
        result = 31 * result + (int) (assistantId ^ (assistantId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderEquipment{");
        sb.append("equipmentId=").append(equipmentId);
        sb.append(", rentStartTime=").append(rentStartTime);
        sb.append(", rentTime=").append(rentEndTime);
        sb.append(", assistantId=").append(assistantId);
        sb.append('}');
        return sb.toString();
    }
}
