package by.lozovenko.finalproject.entity;

public class Assistant extends User{
    private String imageFilePath;
    private long equipmentTypeId;
    private long assistantId;
    public Assistant(){
    }
    public Assistant(User user){
        super(user);
    }
    public Assistant(long id, String login, String password, String firstName, String lastName,
                     String email, String phone, UserRole role, UserState state,
                     String imageFilePath, long equipmentTypeId, long assistantId) {
        super(id, login, password, firstName, lastName, email, phone, role, state);
        this.imageFilePath = imageFilePath;
        this.equipmentTypeId = equipmentTypeId;
        this.assistantId = assistantId;
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

    public long getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(long assistantId) {
        this.assistantId = assistantId;
    }
}
