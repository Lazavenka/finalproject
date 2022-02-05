package by.lozovenko.finalproject.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order extends CustomEntity {
    private long clientId;
    private OrderState state;
    private BigDecimal totalCost;
    private long equipmentId;
    private LocalDateTime rentStartTime;
    private LocalDateTime rentEndTime;
    private long assistantId;

    public Order() {
    }

    public Order(long clientId, OrderState state, BigDecimal totalCost, long equipmentId, LocalDateTime rentStartTime, LocalDateTime rentEndTime, long assistantId) {
        this.clientId = clientId;
        this.state = state;
        this.totalCost = totalCost;
        this.equipmentId = equipmentId;
        this.rentStartTime = rentStartTime;
        this.rentEndTime = rentEndTime;
        this.assistantId = assistantId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
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
}
