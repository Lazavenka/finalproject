package by.lozovenko.finalproject.model.entity;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Equipment extends CustomEntity {
    private String name;
    private String description;
    private String imageFilePath;
    private long equipmentTypeId;
    private long laboratoryId;
    private boolean isNeedAssistant;
    private EquipmentState state;
    private BigDecimal pricePerHour;
    private LocalTime averageResearchTime;

    public Equipment() {
    }

    public Equipment(long id, String name, String description, long equipmentTypeId,
                     long laboratoryId, boolean isNeedAssistant, EquipmentState state,
                     BigDecimal pricePerHour, LocalTime averageResearchTime, String imageFilePath) {
        super(id);
        this.name = name;
        this.description = description;
        this.equipmentTypeId = equipmentTypeId;
        this.laboratoryId = laboratoryId;
        this.isNeedAssistant = isNeedAssistant;
        this.state = state;
        this.pricePerHour = pricePerHour;
        this.averageResearchTime = averageResearchTime;
        this.imageFilePath = imageFilePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(long equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }

    public long getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(long laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public boolean isNeedAssistant() {
        return isNeedAssistant;
    }

    public void setNeedAssistant(boolean needAssistant) {
        isNeedAssistant = needAssistant;
    }

    public EquipmentState getState() {
        return state;
    }

    public void setState(EquipmentState state) {
        this.state = state;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public LocalTime getAverageResearchTime() {
        return averageResearchTime;
    }

    public void setAverageResearchTime(LocalTime averageResearchTime) {
        this.averageResearchTime = averageResearchTime;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Equipment equipment = (Equipment) o;

        if (equipmentTypeId != equipment.equipmentTypeId) return false;
        if (laboratoryId != equipment.laboratoryId) return false;
        if (isNeedAssistant != equipment.isNeedAssistant) return false;
        if (!name.equals(equipment.name)) return false;
        if (!description.equals(equipment.description)) return false;
        if (imageFilePath != null ? !imageFilePath.equals(equipment.imageFilePath) : equipment.imageFilePath != null)
            return false;
        if (state != equipment.state) return false;
        if (!pricePerHour.equals(equipment.pricePerHour)) return false;
        return averageResearchTime.equals(equipment.averageResearchTime);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (imageFilePath != null ? imageFilePath.hashCode() : 0);
        result = 31 * result + (int) (equipmentTypeId ^ (equipmentTypeId >>> 32));
        result = 31 * result + (int) (laboratoryId ^ (laboratoryId >>> 32));
        result = 31 * result + (isNeedAssistant ? 1 : 0);
        result = 31 * result + state.hashCode();
        result = 31 * result + pricePerHour.hashCode();
        result = 31 * result + averageResearchTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Equipment{");
        sb.append("name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", imageFilePath='").append(imageFilePath).append('\'');
        sb.append(", equipmentTypeId=").append(equipmentTypeId);
        sb.append(", laboratoryId=").append(laboratoryId);
        sb.append(", isNeedAssistant=").append(isNeedAssistant);
        sb.append(", state=").append(state);
        sb.append(", pricePerHour=").append(pricePerHour);
        sb.append(", averageResearchTime=").append(averageResearchTime);
        sb.append('}');
        return sb.toString();
    }
}
