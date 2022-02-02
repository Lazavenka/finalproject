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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (clientId != order.clientId) return false;
        if (state != order.state) return false;
        return orderEquipments != null ? orderEquipments.equals(order.orderEquipments) : order.orderEquipments == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (clientId ^ (clientId >>> 32));
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (orderEquipments != null ? orderEquipments.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("clientId=").append(clientId);
        sb.append(", state=").append(state);
        sb.append(", orderEquipments=").append(orderEquipments);
        sb.append('}');
        return sb.toString();
    }
}
