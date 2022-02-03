package by.lozovenko.finalproject.model.entity;

import java.time.LocalDateTime;

public class EquipmentWorkTimePeriod {
    private LocalDateTime startOfPeriod;
    private LocalDateTime endOfPeriod;
    private EquipmentAvailability availability;
    public EquipmentWorkTimePeriod(LocalDateTime startOfPeriod, LocalDateTime endOfPeriod, EquipmentAvailability availability){
        this.startOfPeriod = startOfPeriod;
        this.endOfPeriod = endOfPeriod;
        this.availability = availability;
    }

    public EquipmentAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(EquipmentAvailability availability) {
        this.availability = availability;
    }

    public LocalDateTime getStartOfPeriod() {
        return startOfPeriod;
    }

    public void setStartOfPeriod(LocalDateTime startOfPeriod) {
        this.startOfPeriod = startOfPeriod;
    }

    public LocalDateTime getEndOfPeriod() {
        return endOfPeriod;
    }

    public void setEndOfPeriod(LocalDateTime endOfPeriod) {
        this.endOfPeriod = endOfPeriod;
    }

    public boolean containsStartDateTime(LocalDateTime startOfPeriod){
        return this.startOfPeriod.isEqual(startOfPeriod) || (this.startOfPeriod.isBefore(startOfPeriod) && this.endOfPeriod.isAfter(startOfPeriod));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EquipmentWorkTimePeriod that = (EquipmentWorkTimePeriod) o;

        if (startOfPeriod != null ? !startOfPeriod.equals(that.startOfPeriod) : that.startOfPeriod != null)
            return false;
        return endOfPeriod != null ? endOfPeriod.equals(that.endOfPeriod) : that.endOfPeriod == null;
    }

    @Override
    public int hashCode() {
        int result = startOfPeriod != null ? startOfPeriod.hashCode() : 0;
        result = 31 * result + (endOfPeriod != null ? endOfPeriod.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EquipmentWorkTimePeriod{");
        sb.append("startOfPeriod=").append(startOfPeriod);
        sb.append(", endOfPeriod=").append(endOfPeriod);
        sb.append(", availability=").append(availability);
        sb.append('}');
        return sb.toString();
    }
}
