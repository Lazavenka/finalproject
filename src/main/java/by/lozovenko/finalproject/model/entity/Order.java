package by.lozovenko.finalproject.model.entity;

import java.util.List;

public class Order extends CustomEntity{
    private long clientId;
    private OrderState state;
    private List<OrderEquipment> orderEquipments;

    public Order(long id, long clientId, OrderState state, List<OrderEquipment> orderEquipments) {
        super(id);
        this.clientId = clientId;
        this.state = state;
        this.orderEquipments = orderEquipments;
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

    public boolean addEquipmentToOrder(OrderEquipment orderEquipment){
        return orderEquipments.add(orderEquipment);
    }
    public boolean removeEquipmentFromOrder(OrderEquipment orderEquipment){
        return orderEquipments.remove(orderEquipment);
    }
}
