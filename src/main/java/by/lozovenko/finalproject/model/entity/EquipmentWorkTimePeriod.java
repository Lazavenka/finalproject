package by.lozovenko.finalproject.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipmentWorkTimePeriod {
    private LocalDateTime startOfPeriod;
    private LocalDateTime endOfPeriod;
    private EquipmentAvailability availability;
    private final List<Assistant> availableAssistantsInPeriod = new ArrayList<>();

    public EquipmentWorkTimePeriod(LocalDateTime startOfPeriod, LocalDateTime endOfPeriod, EquipmentAvailability availability, List<Assistant> availableAssistantsInPeriod) {
        this.startOfPeriod = startOfPeriod;
        this.endOfPeriod = endOfPeriod;
        this.availability = availability;
        this.availableAssistantsInPeriod.addAll(availableAssistantsInPeriod);
    }

    public EquipmentAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(EquipmentAvailability availability) {
        this.availability = availability;
    }

    public Optional<Assistant> getAvailableAssistantInPeriod() {
        return availableAssistantsInPeriod.stream().findAny();
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

    public List<Assistant> getAvailableAssistantsInPeriod() {
        return availableAssistantsInPeriod;
    }

    public boolean removeAssistantById(long assistantId) {
        return availableAssistantsInPeriod.removeIf(assistant -> assistant.getAssistantId() == assistantId);
    }

    public boolean containsStartDateTime(LocalDateTime startOfPeriod) {
        return this.startOfPeriod.isEqual(startOfPeriod) || (this.startOfPeriod.isBefore(startOfPeriod) && this.endOfPeriod.isAfter(startOfPeriod));
    }

    public boolean containsEndDateTime(LocalDateTime endOfPeriod) {
        return (this.startOfPeriod.isBefore(endOfPeriod) && this.endOfPeriod.isAfter(endOfPeriod)) || this.endOfPeriod.isEqual(endOfPeriod);
    }

    public boolean isInsideAnotherPeriod(EquipmentWorkTimePeriod another) {
        return this.startOfPeriod.isAfter(another.startOfPeriod) && this.endOfPeriod.isBefore(another.endOfPeriod);
    }

    public boolean crossPeriod(EquipmentWorkTimePeriod another) {
        boolean crossing = this.isInsideAnotherPeriod(another);
        if (this.containsStartDateTime(another.startOfPeriod)) {
            crossing = true;
        }
        if (this.containsEndDateTime(another.endOfPeriod)) {
            crossing = true;
        }
        return crossing;
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
