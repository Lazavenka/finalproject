package by.lozovenko.finalproject.model.entity;

public class Assistant extends User{
    private String imageFilePath;
    private long equipmentTypeId;

    public Assistant(long id, String login, String password, String firstName, String lastName,
                     String email, String phone, UserRole role, UserState state,
                     String imageFilePath, long equipmentTypeId) {
        super(id, login, password, firstName, lastName, email, phone, role, state);
        this.imageFilePath = imageFilePath;
        this.equipmentTypeId = equipmentTypeId;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public long getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(long equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }
}
