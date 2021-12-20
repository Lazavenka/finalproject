package by.lozovenko.finalproject.model.entity;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Equipment extends CustomEntity{
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
}
